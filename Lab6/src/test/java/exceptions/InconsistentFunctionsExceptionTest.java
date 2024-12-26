package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InconsistentFunctionsExceptionTest {

    @Test
    public void testInconsistentFunctionsExceptionNoMessage() {
        InconsistentFunctionsException exception = assertThrows(InconsistentFunctionsException.class, () -> {
            throw new InconsistentFunctionsException();
        });
        assertNull(exception.getMessage(), "Сообщение должно быть null, если не было передано");
    }

    @Test
    public void testInconsistentFunctionsExceptionWithMessage() {
        String expectedMessage = "Функции несовместимы!";
        InconsistentFunctionsException exception = assertThrows(InconsistentFunctionsException.class, () -> {
            throw new InconsistentFunctionsException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения не совпадает с ожидаемым");
    }
}