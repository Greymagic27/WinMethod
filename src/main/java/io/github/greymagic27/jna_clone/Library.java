package io.github.greymagic27.jna_clone;

import java.lang.foreign.Arena;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.jetbrains.annotations.NotNull;

public interface Library {

    static <T> @NotNull T load(String libraryName, @NotNull Class<T> interfaceType) {
        final ThreadLocal<Integer> LAST_ERROR = ThreadLocal.withInitial(() -> 0);
        NativeLibrary nativeLibrary = new NativeLibrary(libraryName);
        InvocationHandler handler = ((_, method, args) -> {
            String name = method.getName();
            if (method.getDeclaringClass() == Object.class) {
                return method.invoke(nativeLibrary, args);
            }
            if (name.equals("SetLastError")) {
                LAST_ERROR.set((Integer) args[0]);
                return null;
            }
            if (name.equals("GetLastError")) {
                return LAST_ERROR.get();
            }
            MethodHandle target = nativeLibrary.handleFor(method);
            Class<?>[] paramTypes = method.getParameterTypes();
            try (Arena callArena = Arena.ofConfined()) {
                Object[] nativeArgs = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    Object arg = args == null ? null : args[i];
                    nativeArgs[i] = TypeMapper.toNative(arg, paramTypes[i], callArena);
                }
                Object result = target.invokeWithArguments(nativeArgs);
                return TypeMapper.fromNative(result, method.getReturnType());
            }
        });
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{interfaceType}, handler);
    }
}
