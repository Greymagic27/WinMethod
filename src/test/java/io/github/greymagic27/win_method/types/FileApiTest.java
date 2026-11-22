package io.github.greymagic27.win_method.types;

import org.junit.jupiter.api.Test;

import static io.github.greymagic27.win_method.types.FileApi.CREATE_ALWAYS;
import static io.github.greymagic27.win_method.types.FileApi.OPEN_EXISTING;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileApiTest {

    @Test
    void testValues() {
        assertEquals(2, CREATE_ALWAYS);
        assertEquals(3, OPEN_EXISTING);
    }
}