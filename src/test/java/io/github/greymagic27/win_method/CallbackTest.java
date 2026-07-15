package io.github.greymagic27.win_method;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CallbackTest {

    private FunctionDescriptor desc;

    @BeforeEach
    void setUp() {
        desc = CallbackReference.descriptorFor(IntCallback.class);
    }

    @Test
    void testDescriptorBuilding() {
        assertNotNull(desc);
        assertEquals(ValueLayout.JAVA_INT, desc.returnLayout().orElseThrow());
        assertEquals(1, desc.argumentLayouts().size());
        assertEquals(ValueLayout.JAVA_INT, desc.argumentLayouts().getFirst());
        FunctionDescriptor voidDesc = CallbackReference.descriptorFor(VoidCallback.class);
        assertNotNull(voidDesc);
        assertTrue(voidDesc.returnLayout().isEmpty(), "Void callback should have no return layout");
    }

    @Test
    void testStubCaching() {
        IntCallback cb = (v) -> v * 2;
        MemorySegment stub1 = CallbackReference.getStub(cb, desc);
        MemorySegment stub2 = CallbackReference.getStub(cb, desc);
        assertNotNull(stub1);
        assertNotNull(stub2);
        assertEquals(stub1.address(), stub2.address(), "Stubs should be cached and identical");
    }

    @Test
    void testUpcallExecution() throws Throwable {
        AtomicInteger result = new AtomicInteger(0);
        IntCallback cb = (v) -> {
            result.set(v);
            return v + 1;
        };
        MemorySegment stub = CallbackReference.getStub(cb, desc);
        MethodHandle downcall = Linker.nativeLinker().downcallHandle(stub, desc);
        Object returnValue = downcall.invoke(42);
        assertEquals(42, result.get(), "Java callback logic should execute with the provided argument");
        assertEquals(43, returnValue, "Return value should be passed back through the stub");
    }

    @Test
    void testVoidCallbackExecution() throws Throwable {
        boolean[] called = {false};
        VoidCallback cb = () -> called[0] = true;
        FunctionDescriptor desc = CallbackReference.descriptorFor(VoidCallback.class);
        MemorySegment stub = CallbackReference.getStub(cb, desc);
        MethodHandle downcall = Linker.nativeLinker().downcallHandle(stub, desc);
        downcall.invoke();
        assertTrue(called[0], "Void callback should have been triggered");
    }

    @Test
    void testInvalidCallbackInterface() {
        interface NotASam extends Callback {
        }
        assertThrows(IllegalArgumentException.class, () -> CallbackReference.descriptorFor(NotASam.class));
    }

    @SuppressWarnings("unused")
    @FunctionalInterface
    public interface IntCallback extends Callback {
        int run(int value);
    }

    @SuppressWarnings("unused")
    @FunctionalInterface
    public interface VoidCallback extends Callback {
        void run();
    }
}
