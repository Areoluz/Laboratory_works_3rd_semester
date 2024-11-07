package exceptions;

import exceptions.NotSortedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotSortedExceptionTest {

    @Test
    void testNotSortedExceptionNoMessage() {
        NotSortedException exception = assertThrows(NotSortedException.class, () -> {
            throw new NotSortedException();
        });
        assertNull(exception.getMessage(), "Сообщение должно быть null, если не было передано");
    }

    @Test
    void testNotSortedExceptionWithMessage() {
        String expectedMessage = "Массив не отсортирован!";
        NotSortedException exception = assertThrows(NotSortedException.class, () -> {
            throw new NotSortedException(expectedMessage);
        });
        assertEquals(expectedMessage, exception.getMessage(), "Сообщение исключения не совпадает с ожидаемым");
    }
}
