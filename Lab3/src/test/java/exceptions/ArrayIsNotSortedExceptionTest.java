package exceptions;

import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIsNotSortedExceptionTest {

    @Test
    public void testArrayIsNotSortedException() {
        double[] xValues = {1.0, 3.0, 2.0};  // Несортированный массив
        double[] yValues = {2.0, 3.0, 4.0};
        assertThrows(ArrayIsNotSortedException.class, () -> {
            new ArrayTabulatedFunction(xValues, yValues);
        });
    }

}