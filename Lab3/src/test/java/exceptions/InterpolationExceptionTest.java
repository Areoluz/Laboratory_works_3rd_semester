package exceptions;

import exceptions.InterpolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterpolationExceptionTest {

    @Test
    void testInterpolationExceptionNoMessage() {
        InterpolationException exception = assertThrows(InterpolationException.class, () -> {
            throw new InterpolationException();
        });
        assertNull(exception.getMessage(), "Сообщение должно быть null, если не было передано");
    }

    @Test
    void testInterpolationExceptionWithMessage() {
        String expectedMessage = "Ошибка интерполяции!";
        InterpolationException exception = assertThrows(InterpolationException.class, () -> {
            throw new InterpolationException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения не совпадает с ожидаемым");
    }
}