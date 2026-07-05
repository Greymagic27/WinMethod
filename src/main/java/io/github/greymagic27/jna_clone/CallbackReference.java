package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public final class CallbackReference {

    private static final Map<Callback, MemorySegment> LIVE = new IdentityHashMap<>();
    private static final Map<Class<?>, FunctionDescriptor> DESCRIPTORS = new ConcurrentHashMap<>();

    public static synchronized MemorySegment getStub(Callback cb, FunctionDescriptor descriptor) {
        return LIVE.computeIfAbsent(cb, c -> createStub(c, descriptor));
    }

    public static FunctionDescriptor descriptorFor(Class<?> callbackType) {
        return DESCRIPTORS.computeIfAbsent(callbackType, CallbackReference::buildDescriptor);
    }

    private static FunctionDescriptor buildDescriptor(Class<?> callbackType) {
        Method sam = findSingleAbstractMethod(callbackType);
        Class<?>[] paramTypes = sam.getParameterTypes();
        MemoryLayout[] paramLayouts = new MemoryLayout[paramTypes.length];
        for (int i = 0; i < paramLayouts.length; i++) paramLayouts[i] = TypeMapper.layoutMappings(paramTypes[i]);
        MemoryLayout returnLayout = TypeMapper.layoutMappings(sam.getReturnType());
        return returnLayout == null ? FunctionDescriptor.ofVoid(paramLayouts) : FunctionDescriptor.of(returnLayout, paramLayouts);
    }

    private static MemorySegment createStub(@NotNull Callback cb, FunctionDescriptor descriptor) {
        Method sam = findSingleAbstractMethod(cb.getClass());
        try {
            MethodHandle handle = MethodHandles.lookup().unreflect(sam).bindTo(cb);
            return Linker.nativeLinker().upcallStub(handle, descriptor, Arena.ofShared());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to bind callback " + cb.getClass(), e);
        }
    }

    private static @NotNull Method findSingleAbstractMethod(@NotNull Class<?> type) {
        if (type.isInterface()) {
            for (Method m : type.getMethods()) {
                if (!m.isDefault() && !Modifier.isStatic(m.getModifiers())) return m;
            }
        }
        for (Class<?> iface : type.getInterfaces()) {
            for (Method m : iface.getMethods()) {
                if (!m.isDefault() && !Modifier.isStatic(m.getModifiers())) return m;
            }
        }
        throw new IllegalArgumentException("No single abstract method found on " + type);
    }
}
