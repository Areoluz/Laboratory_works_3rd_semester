package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayTabulatedFunctionTest {

    private ArrayTabulatedFunction arrayFunction;

    @BeforeEach
    public void setUp() {
        double[] xValues = {1, 2, 3, 4};
        double[] yValues = {2, 4, 6, 8};
        arrayFunction = new ArrayTabulatedFunction(xValues, yValues);
    }

    @Test
    public void testArrayTabulatedFunctionConstructor() {
        double[] xValues = {1, 2, 3, 4};
        double[] yValues = {2, 4, 6, 8};
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        Assertions.assertEquals(4, function.getCount());
        Assertions.assertEquals(1, function.getX(0));
        Assertions.assertEquals(8, function.getY(3));
    }

    @Test
    public void testLeftBound() {
        double expected = 1;
        double actual = arrayFunction.leftBound();
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testRightBound() {
        double expected = 4;
        double actual = arrayFunction.rightBound();
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testIndexOfX() {
        int expected = 1;
        int actual = arrayFunction.indexOfX(2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testIndexOfY() {
        int expected = 2;
        int actual = arrayFunction.indexOfY(6);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testFloorIndexOfX() {
        int expected = 1;
        int actual = arrayFunction.floorIndexOfX(2.5);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testInterpolate() {
        double expected = 5;
        double actual = arrayFunction.interpolate(2.5, 1);
        Assertions.assertEquals(expected, actual, 0.0001);
    }


    @Test
    public void testExtrapolateLeft() {
        double expected = 0;
        double actual = arrayFunction.extrapolateLeft(0);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testExtrapolateRight() {
        double expected = 10;
        double actual = arrayFunction.extrapolateRight(5);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testSetY() {
        arrayFunction.setY(1, 10);
        double expected = 10;
        double actual = arrayFunction.getY(1);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testInvalidSetYIndex() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> arrayFunction.setY(-1, 5));
    }

    @Test
    public void testInvalidGetXIndex() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> arrayFunction.getX(-1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> arrayFunction.getX(4));
    }

    @Test
    public void testInvalidGetYIndex() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> arrayFunction.getY(-1));
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> arrayFunction.getY(4));
    }

    @Test
    public void testIterator() {
        Iterator<Point> iterator = arrayFunction.iterator();
        Assertions.assertTrue(iterator.hasNext());

        Point point1 = iterator.next();
        Assertions.assertEquals(1.0, point1.x, 0.0001);
        Assertions.assertEquals(2.0, point1.y, 0.0001);

        Point point2 = iterator.next();
        Assertions.assertEquals(2.0, point2.x, 0.0001);
        Assertions.assertEquals(4.0, point2.y, 0.0001);

        iterator.next();
        iterator.next();

        Assertions.assertFalse(iterator.hasNext());
        Assertions.assertThrows(NoSuchElementException.class, iterator::next);
    }
}
