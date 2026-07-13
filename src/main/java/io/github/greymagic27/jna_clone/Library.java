package io.github.greymagic27.jna_clone;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.foreign.Arena;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.jspecify.annotations.NonNull;

@CanIgnoreReturnValue
public interface Library {

    static <T extends Library> @NonNull T load(@NonNull Class<T> interfaceType) {
        return load(interfaceType.getSimpleName(), interfaceType);
    }

    static <T extends Library> @NonNull T load(String libraryName, @NonNull Class<T> interfaceType) {
        NativeLibrary nativeLibrary = new NativeLibrary(libraryName);
        InvocationHandler handler = (_, method, args) -> {
            if (method.getDeclaringClass() == Object.class) return method.invoke(nativeLibrary, args);
            if (method.getName().equals("GetLastError") && method.getParameterCount() == 0) return LastError.get();
            MethodHandle target = nativeLibrary.handleFor(method);
            Class<?>[] paramTypes = method.getParameterTypes();
            try (Arena callArena = Arena.ofConfined()) {
                Object[] nativeArgs = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    Object arg = args == null ? null : args[i];
                    nativeArgs[i] = TypeMapper.toNative(arg, paramTypes[i], callArena);
                }
                Object result = target.invokeWithArguments(nativeArgs);
                int error = nativeLibrary.getLastError();
                LastError.set(error);
                if (args != null) {
                    for (Object arg : args) {
                        if (arg instanceof Structure structArg) {
                            structArg.read();
                        }
                    }
                }
                return TypeMapper.fromNative(result, method.getReturnType());
            }
        };
        return interfaceType.cast(Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class<?>[]{interfaceType}, handler));
    }

    class LastError {
        private static final ThreadLocal<Integer> LAST_ERROR = ThreadLocal.withInitial(() -> 0);
        private static void set(int error) {
            LAST_ERROR.set(error);
        }
        private static int get() {
            return LAST_ERROR.get();
        }
    }
}