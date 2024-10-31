package exceptions;

import functions.AbstractTabulatedFunction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthOfArraysExceptionTest {

    @Test
    void testDifferentLengthOfArraysExceptionThrown() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 2.0}; // Массив короче

        DifferentLengthOfArraysException exception = assertThrows(
                DifferentLengthOfArraysException.class,
                () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues),
                "Expected DifferentLengthOfArraysException to be thrown"
        );

        assertEquals("Lengths of xValues and yValues are different.", exception.getMessage());
    }

    @Test
    void testNoExceptionWhenLengthsAreSame() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {1.0, 2.0, 3.0}; // Длина совпадает

        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }
}
