package exceptions;

import functions.ArrayTabulatedFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DifferentLengthExceptionTest {

    @Test
    public void testArrayIsNotSortedException() {
        double[] xValues = {1.0, 2.0, 3.0};
        double[] yValues = {2.0, 3.0};  // Разная длина массивов
        assertThrows(DifferentLengthOfArraysException.class, () -> {
            new ArrayTabulatedFunction(xValues, yValues);
        });
    }
}