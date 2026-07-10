package io.github.greymagic27.jna_clone;

import com.google.errorprone.annotations.Keep;
import io.github.greymagic27.jna_clone.WinDef.LRESULT;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class CallbackReference {

    private static final IdentityHashMap<Callback, MemorySegment> LIVE = new IdentityHashMap<>();
    private static final Map<Class<?>, FunctionDescriptor> DESCRIPTORS = new ConcurrentHashMap<>();
    private static final MethodHandle DISPATCH;

    static {
        try {
            DISPATCH = MethodHandles.lookup().findStatic(CallbackReference.class, "dispatch", MethodType.methodType(Object.class, Callback.class, Method.class, Object[].class));
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

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

    private static MemorySegment createStub(@NonNull Callback cb, @NonNull FunctionDescriptor descriptor) {
        Method sam = findSingleAbstractMethod(cb.getClass());
        sam.setAccessible(true);
        MethodHandle bound = DISPATCH.bindTo(cb).bindTo(sam);
        MethodHandle collected = bound.asCollector(Object[].class, sam.getParameterCount());
        MethodType targetType = descriptor.toMethodType();
        MethodHandle adapted = collected.asType(targetType);

        return Linker.nativeLinker().upcallStub(adapted, descriptor, Arena.ofShared());
    }

    @Keep
    private static @Nullable Object dispatch(Callback cb, @NonNull Method sam, Object @NonNull [] rawArgs) {
        try {
            Class<?>[] paramTypes = sam.getParameterTypes();
            Object[] wrapped = new Object[rawArgs.length];
            for (int i = 0; i < rawArgs.length; i++) {
                wrapped[i] = TypeMapper.fromNative(rawArgs[i], paramTypes[i]);
            }
            Object result = sam.invoke(cb, wrapped);
            Class<?> returnType = sam.getReturnType();
            if (returnType == void.class || returnType == Void.class) {
                return null;
            }
            return TypeMapper.toNative(result, returnType, Arena.global());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Callback threw an exception", e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke callback " + cb.getClass(), e);
        }
    }

    private static @NonNull Method findSingleAbstractMethod(@NonNull Class<?> type) {
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