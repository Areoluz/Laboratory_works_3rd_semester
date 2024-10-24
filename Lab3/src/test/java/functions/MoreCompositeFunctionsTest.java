package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoreCompositeFunctionsTest {
    @Test
    public void test1() {
        MathFunction func = new CompositeFunction(
                new LinkedListTabulatedFunction(new double[]{1.7, 2.3, 3.8}, new double[]{-0.1, 2, 3.3}),
                new LinkedListTabulatedFunction(new double[]{1.2, 2.1, 5.5}, new double[]{0.3, 2.5, 3.7})
        );
        Assertions.assertEquals(2.8011764705882354, func.apply(3.4), 0.0001);
        Assertions.assertEquals(-8.866666666666667, func.apply(1), 0.0001);
        Assertions.assertEquals(-2.8777777777777778, func.apply(1.7), 0.0001);
        Assertions.assertEquals(-34.53333333333334, func.apply(-2), 0.0001);
        Assertions.assertEquals(8.490588235294117, func.apply(22), 0.0001);
    }

    @Test
    public void test2()
    {
        MathFunction func = new CompositeFunction(
                new LinkedListTabulatedFunction(new double[]{1,2,3,4}, new double[]{1,4,9,16}),
                new SqrFunction()
        );
        Assertions.assertEquals(81,func.apply(3));
        Assertions.assertEquals(156.25,func.apply(3.5));
        Assertions.assertEquals(3364,func.apply(10));
    }

    @Test
    public void testCompositeWithArrayTabulatedFunction1() {
        MathFunction func = new CompositeFunction(
                new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 4, 9}),
                new ArrayTabulatedFunction(new double[]{0, 1, 2}, new double[]{0, 1, 4})
        );

        Assertions.assertEquals(10, func.apply(2), 0.0001);
        Assertions.assertEquals(25, func.apply(3), 0.0001);
        Assertions.assertEquals(1, func.apply(1), 0.0001);
        Assertions.assertEquals(-2, func.apply(0), 0.0001);
    }


    @Test
    public void testCompositeWithArrayTabulatedFunction2() {
        MathFunction func = new CompositeFunction(
                new ArrayTabulatedFunction(new double[]{0, 1, 2, 3}, new double[]{0, 1, 4, 9}),
                new SqrFunction()
        );

        Assertions.assertEquals(0, func.apply(0), 0.0001);
        Assertions.assertEquals(1, func.apply(1), 0.0001);
        Assertions.assertEquals(16, func.apply(2), 0.0001);
        Assertions.assertEquals(81, func.apply(3), 0.0001);
        Assertions.assertEquals(196, func.apply(4), 0.0001);
    }


}

