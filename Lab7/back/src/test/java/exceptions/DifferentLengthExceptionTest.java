package exceptions;

import exceptions.DifferentLengthException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DifferentLengthExceptionTest {

    @Test
    void testDifferentLengthExceptionNoMessage() {
        DifferentLengthException exception = assertThrows(DifferentLengthException.class, () -> {
            throw new DifferentLengthException();
        });
        assertNull(exception.getMessage(), "Сообщение должно быть null, если не было передано");
    }

    @Test
    void testDifferentLengthExceptionWithMessage() {
        String expectedMessage = "Длины массивов не совпадают!";
        DifferentLengthException exception = assertThrows(DifferentLengthException.class, () -> {
            throw new DifferentLengthException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения не совпадает с ожидаемым");
    }
}
