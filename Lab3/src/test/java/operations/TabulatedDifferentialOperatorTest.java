package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedDifferentialOperatorTest {

    @Test
    void applyTest() {
        var diff = new TabulatedDifferentialOperator();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> diff.apply(2));
    }

    @Test
    void deriveLinkedListFabricTest() {
        var list = new LinkedListTabulatedFunctionFactory();
        var diffop = new TabulatedDifferentialOperator(list);
        TabulatedFunction listdiff = diffop.derive(diffop.getFactory().create(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16}));
        Assertions.assertEquals(6, listdiff.getY(2));
        Assertions.assertEquals(4, listdiff.getY(1));
    }

    @Test
    void deriveLinkedListTest() {
        TabulatedFunction list = new LinkedListTabulatedFunctionFactory().create(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16});
        var diffop = new TabulatedDifferentialOperator();
        TabulatedFunction listdiff = diffop.derive(list);
        Assertions.assertEquals(6, listdiff.getY(2));
        Assertions.assertEquals(4, listdiff.getY(1));
    }

    @Test
    void deriveLinkedArrayFabricTest() {
        var arrfac = new ArrayTabulatedFunctionFactory();
        var diffarrop = new TabulatedDifferentialOperator(arrfac);
        TabulatedFunction arrdiff = diffarrop.derive(diffarrop.getFactory().create(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16}));
        Assertions.assertTrue(arrdiff instanceof ArrayTabulatedFunction, "Expected an instance of ArrayTabulatedFunction");
        Assertions.assertEquals(6, arrdiff.getY(2));
        Assertions.assertEquals(4, arrdiff.getY(1));
    }

    @Test
    void deriveLinkedArrayTest() {
        var fac = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction arr = fac.create(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16});
        var diffarrop = new TabulatedDifferentialOperator(fac);
        TabulatedFunction listdiff = diffarrop.derive(arr);
        Assertions.assertTrue(listdiff instanceof LinkedListTabulatedFunction, "Expected an instance of LinkedListTabulatedFunction");
        Assertions.assertEquals(6, listdiff.getY(2));
        Assertions.assertEquals(4, listdiff.getY(1));
    }

    @Test
    void setest() {
        var diff = new TabulatedDifferentialOperator();
        var fac = new LinkedListTabulatedFunctionFactory();
        diff.setFactory(fac);
        Assertions.assertEquals(fac, diff.getFactory());
    }

    @Test
    void testDeriveSynchronouslyWithNonSynchronizedFunction() {
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 4, 9, 16, 25}; // y = x^2
        TabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        TabulatedFunction derivedFunction = operator.deriveSynchronously(func);


        double[] expectedDerivatives = {2.0, 4.0, 6.0, 8.0, 10.0};

        for (int i = 1; i < expectedDerivatives.length - 1; i++) {
            Assertions.assertEquals(expectedDerivatives[i], derivedFunction.getY(i), 0.001);
        }
    }

    @Test
    void testDeriveSynchronouslyWithSynchronizedFunction() {
        // y = x^2
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {1, 4, 9, 16, 25};
        TabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        SynchronizedTabulatedFunction synchronizedFunc = new SynchronizedTabulatedFunction(func);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        TabulatedFunction derivedFunction = operator.deriveSynchronously(synchronizedFunc);

        //y = 2x
        double[] expectedDerivatives = {2.0, 4.0, 6.0, 8.0, 10.0};

        for (int i = 1; i < expectedDerivatives.length - 1; i++) {
            Assertions.assertEquals(expectedDerivatives[i], derivedFunction.getY(i), 0.001);
        }
    }

    @Test
    void testDeriveSynchronouslyWithConstantFunction() {
        // y = 5
        double[] arrX = {1, 2, 3, 4, 5};
        double[] arrY = {5, 5, 5, 5, 5}; // Константная функция y = 5
        TabulatedFunction func = new ArrayTabulatedFunction(arrX, arrY);
        TabulatedDifferentialOperator operator = new TabulatedDifferentialOperator();

        // y=0
        TabulatedFunction derivedFunction = operator.deriveSynchronously(func);

        // Ожидаем, что производная будет равна 0 для всех значений x
        for (int i = 1; i < arrX.length - 1; i++) {
            Assertions.assertEquals(0.0, derivedFunction.getY(i), 0.001);
        }
    }
}