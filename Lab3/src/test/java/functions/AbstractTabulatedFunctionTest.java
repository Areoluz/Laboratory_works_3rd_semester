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

    @Test
    void testInterpolate() {
        AbstractTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        double interpolatedValue = function.interpolate(1.5, 1);
        double expected = 2.5; // (1 + (1.5 - 1) * (4 - 1) / (2 - 1)) = 2.5
        assertEquals(expected, interpolatedValue, 0.0001, "Interpolate method should return the correct interpolated value.");
    }

    @Test
    void testApply() {
        AbstractTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        assertEquals(1.0, function.apply(1.0), 0.0001);

        double extrapolatedLeft = function.apply(-1.0);
        double expectedLeft = function.extrapolateLeft(-1.0);
        assertEquals(expectedLeft, extrapolatedLeft, 0.0001, "Apply should return correct extrapolated value to the left.");

        double extrapolatedRight = function.apply(3.0);
        double expectedRight = function.extrapolateRight(3.0);
        assertEquals(expectedRight, extrapolatedRight, 0.0001, "Apply should return correct extrapolated value to the right.");

        double interpolated = function.apply(1.5);
        double expectedInterpolated = 2.5; //между (1, 1) и (2, 4)
        assertEquals(expectedInterpolated, interpolated, 0.0001, "Apply should return correct interpolated value.");
    }

    @Test
    void testIndexOfX() {
        AbstractTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        assertEquals(1, function.indexOfX(1.0), "Index of existing value x should be correct.");
        assertEquals(-1, function.indexOfX(3.0), "Index of non-existing value x should be -1.");
    }

    @Test
    void testGetCount() {
        AbstractTabulatedFunction function = new LinkedListTabulatedFunction(
                new double[]{0.0, 1.0, 2.0},
                new double[]{0.0, 1.0, 4.0}
        );

        assertEquals(3, function.getCount(), "Count of the function should be correct.");
    }
}