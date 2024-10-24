package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

class LinkedListTabulatedFunctionTest {

    private LinkedListTabulatedFunction list;
    private final double[] first = {0, 1, 2, 3, 4, 5, 6, 7};
    private final double[] second = {0, 1, 4, 7, 10, 28, 33, 35};

    @BeforeEach
    void createList2argumentsTest() {
        list = new LinkedListTabulatedFunction(first, second);
    }

    @Test
    void createList4argumentsTest() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 0, 10, 11);

        for (int i = 0; i < 11; i++) {
            double expectedY = Math.pow(i, 2);
            Assertions.assertEquals(expectedY, list.getY(i), 1e-5);
        }
    }

    @Test
    void createList4argumentsTestDoubles() {
        MathFunction func = new SqrFunction();
        list = new LinkedListTabulatedFunction(func, 1.3, 14.2, 4);

        double step = (14.2 - 1.3) / (4 - 1);
        for (int i = 0; i < 4; i++) {
            double x = 1.3 + i * step;
            double expectedY = Math.pow(x, 2);
            Assertions.assertEquals(expectedY, list.getY(i), 1e-5);
        }
    }

    @Test
    void getXTest() {
        double res = list.getX(2);
        Assertions.assertEquals(2, res);
    }

    @Test
    void getYTest() {
        double res = list.getY(3);
        Assertions.assertEquals(7, res);
    }

    @Test
    void setYTest() {
        list.setY(2, 10);
        Assertions.assertEquals(10, list.getY(2));
    }

    @Test
    void leftBoundTest() {
        double res = list.leftBound();
        Assertions.assertEquals(0, res);
    }

    @Test
    void rightBoundTest() {
        double res = list.rightBound();
        Assertions.assertEquals(7, res);
    }

    @Test
    void exceptionsGetTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.getNode(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.getY(-1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> list.getX(-1));
    }

    @Test
    void exceptionsFloorTest()
    {
        Assertions.assertThrows(IllegalArgumentException.class,()->list.floorNodeOfX(-1));
        Assertions.assertThrows(IllegalArgumentException.class,()->list.floorIndexOfX(-1));
    }

    @Test
    void exceptionsConstructorTest()
    {
        Assertions.assertThrows(IllegalArgumentException.class,()->new LinkedListTabulatedFunction(new double[]{1},new double[]{1}));
        Assertions.assertThrows(IllegalArgumentException.class,()->new LinkedListTabulatedFunction(new double[]{},new double[]{}));
        Assertions.assertThrows(IllegalArgumentException.class,()->new LinkedListTabulatedFunction(new SqrFunction(),1,10,1));
        Assertions.assertThrows(IllegalArgumentException.class,()->new LinkedListTabulatedFunction(new SqrFunction(),1,10,0));
    }

    @Test
    void iteratorForEachTest()
    {
        double[] arrx = new double[8];
        double[] arry = new double[8];
        int i = 0;
        for (Point point : list)
        {
            arrx[i] = point.x;
            arry[i] = point.y;
            ++i;
        }
        Assertions.assertArrayEquals(first,arrx);
        Assertions.assertArrayEquals(second,arry);
    }
    @Test
    void iteratorWhileTest() {
        Iterator<Point> iterator = list.iterator();
        Point point;
        double[] arrx = new double[8];
        double[] arry = new double[8];
        int i = 0;
        while (iterator.hasNext()) {
            point = iterator.next();
            arrx[i] = point.x;
            arry[i] = point.y;
            ++i;
        }
        Assertions.assertArrayEquals(first, arrx);
        Assertions.assertArrayEquals(second, arry);
    }

    @Test
    void indexOfXExistingTest() {
        int res = list.indexOfX(4);
        Assertions.assertEquals(4, res);
    }

    @Test
    void indexOfXNotExistingTest() {
        int res = list.indexOfX(10);
        Assertions.assertEquals(-1, res);
    }

    @Test
    void indexOfYExistingTest() {
        int res = list.indexOfY(4);
        Assertions.assertEquals(2, res);
    }

    @Test
    void indexOfYNotExistingTest() {
        int res = list.indexOfY(100);
        Assertions.assertEquals(-1, res);
    }

    @Test
    void floorIndexOfXTest() {
        int res = list.floorIndexOfX(5.5);
        Assertions.assertEquals(5, res);
    }

    @Test
    void getCountTest() {
        Assertions.assertEquals(8, list.getCount());
    }

    @Test
    void extrapolateLeftTest() {
        double a = list.apply(-2);
        Assertions.assertEquals(-2, a);
    }

    @Test
    void extrapolateRightTest() {
        double a = list.apply(8);
        Assertions.assertEquals(37, a);
    }

    @Test
    void interpolateTest() {
        double a = list.apply(2.5);
        Assertions.assertEquals(5.5, a);
    }

    @Test
    void applyTest() {
        double a = list.apply(4.1);
        Assertions.assertEquals(11.8, a, 1e-5);
    }
}
