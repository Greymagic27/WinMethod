package io.github.greymagic27.win_method.types;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.Fileapi.CREATE_ALWAYS;
import static io.github.greymagic27.win_method.types.Fileapi.OPEN_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileapiTest {

    @Test
    void testValues() {
        assertEquals(2, CREATE_ALWAYS);
        assertEquals(3, OPEN_EXISTING);
    }
}