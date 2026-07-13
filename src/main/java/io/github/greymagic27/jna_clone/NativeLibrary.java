package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jspecify.annotations.NonNull;

public final class NativeLibrary {

    private static final Linker linker = Linker.nativeLinker();
    private static final MethodHandle getLastErrorHandle;
    private static final Arena k32Arena = Arena.ofShared();

    static {
        SymbolLookup kernel32 = SymbolLookup.libraryLookup("kernel32.dll", k32Arena);
        MemorySegment address = kernel32.find("GetLastError").orElseThrow(() -> new UnsatisfiedLinkError("GetLastError not found"));
        getLastErrorHandle = linker.downcallHandle(address, FunctionDescriptor.of(ValueLayout.JAVA_INT));
    }

    private final SymbolLookup lookup;
    private final Map<Method, MethodHandle> handleCache = new ConcurrentHashMap<>();

    NativeLibrary(String libraryName) {
        Arena libraryArena = Arena.ofShared();
        this.lookup = SymbolLookup.libraryLookup(mapLibraryName(libraryName), libraryArena);
    }

    MethodHandle handleFor(Method method) {
        return handleCache.computeIfAbsent(method, this::buildHandle);
    }

    private MethodHandle buildHandle(@NonNull Method method) {
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

    private @NonNull String mapLibraryName(@NonNull String libraryName) {
        String lower = libraryName.toLowerCase(Locale.getDefault());
        return lower.endsWith(".dll") ? lower : lower + ".dll";
    }

    int getLastError() {
        try {
            return (int) getLastErrorHandle.invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
