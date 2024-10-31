package functions;


import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static functions.AbstractTabulatedFunction.checkLengthIsTheSame;
import static org.junit.jupiter.api.Assertions.*;

class AbstractTabulateFunctionTest {

    @Test
    void checkLengthIsTheSameTest1() {
        assertThrows(DifferentLengthOfArraysException.class, () -> checkLengthIsTheSame(new double[]{1, 2, 3}, new double[]{4, 5}));
    }

    @Test
    void checkLengthIsTheSameTest2() {
        assertDoesNotThrow(() -> checkLengthIsTheSame(new double[]{1, 2, 3}, new double[]{4, 5, 6}));
    }

    @Test
    void testCheckSortedWithSortedArray() {
        double[] sortedArray = {0.0, 1.0, 2.0, 3.0, 4.0};
        assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(sortedArray),
                "Method should not throw an exception for a sorted array.");
    }

    @Test
    void testCheckSortedWithUnsortedArray() {
        double[] unsortedArray = {0.0, 2.0, 1.0, 3.0, 4.0};
        assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(unsortedArray),
                "Method should throw ArrayIsNotSortedException for an unsorted array.");
    }

    @Test
    void testToString() {
        AbstractTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{0.0, 0.5, 1.0},
                new double[]{0.0, 0.25, 1.0}
        );

        String expected = "LinkedListTabulatedFunction size = 3\n" +
                "[0.0; 0.0]\n" +
                "[0.5; 0.25]\n" +
                "[1.0; 1.0]";

        assertEquals(expected, function.toString());
    }
}