package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

public final class NativeLibrary {

    private final Linker linker = Linker.nativeLinker();
    private final SymbolLookup lookup;
    private final Map<Method, MethodHandle> handleCache = new ConcurrentHashMap<>();

    NativeLibrary(String libraryName) {
        Arena libraryArena = Arena.ofShared();
        this.lookup = SymbolLookup.libraryLookup(mapLibraryName(libraryName), libraryArena);
    }

    MethodHandle handleFor(Method method) {
        return handleCache.computeIfAbsent(method, this::buildHandle);
    }

    private MethodHandle buildHandle(@NotNull Method method) {
        String symbol = method.getName();
        MemorySegment address = lookup.find(symbol).orElseThrow(() -> new UnsatisfiedLinkError("Symbol not found: " + symbol));
        Class<?>[] paramTypes = method.getParameterTypes();
        MemoryLayout[] argLayouts = new MemoryLayout[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            argLayouts[i] = TypeMapper.layoutMappings(paramTypes[i]);
        }
        MemoryLayout returnLayout = TypeMapper.layoutMappings(method.getReturnType());
        FunctionDescriptor descriptor = returnLayout == null ? FunctionDescriptor.ofVoid(argLayouts) : FunctionDescriptor.of(returnLayout, argLayouts);
        return linker.downcallHandle(address, descriptor);
    }

    private String mapLibraryName(@NotNull String libraryName) {
        return libraryName.toLowerCase().endsWith(".dll") ? libraryName : libraryName + ".dll";
    }
}
