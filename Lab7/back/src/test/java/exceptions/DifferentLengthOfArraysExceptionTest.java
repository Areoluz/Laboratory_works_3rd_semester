package exceptions;

import functions.AbstractTabulatedFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthOfArraysExceptionTest {

    @Test
    public void testDifferentLengthOfArraysExceptionNoMessage() {
        DifferentLengthOfArraysException exception = assertThrows(DifferentLengthOfArraysException.class, () -> {
            throw new DifferentLengthOfArraysException();
        });
        assertNull(exception.getMessage(), "Сообщение должно быть null, если не было передано");
    }

    @Test
    public void testDifferentLengthOfArraysExceptionWithMessage() {
        String expectedMessage = "Длины массивов не совпадают!";
        DifferentLengthOfArraysException exception = assertThrows(DifferentLengthOfArraysException.class, () -> {
            throw new DifferentLengthOfArraysException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения не совпадает с ожидаемым");
    }
}
