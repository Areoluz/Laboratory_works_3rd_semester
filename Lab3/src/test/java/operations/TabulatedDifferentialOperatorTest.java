package operations;

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
    void applyTest()
    {
        var diff = new TabulatedDifferentialOperator();
        Assertions.assertThrows(UnsupportedOperationException.class,()->diff.apply(2));
    }

    @Test
    void deriveLinkedListFabricTest()
    {
        var list = new LinkedListTabulatedFunctionFactory();
        var diffop = new TabulatedDifferentialOperator(list);
        TabulatedFunction listdiff = diffop.derive(diffop.getFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16}));
        Assertions.assertEquals(6,listdiff.getY(2));
        Assertions.assertEquals(4,listdiff.getY(1));
    }

    @Test
    void deriveLinkedListTest()
    {
        TabulatedFunction list = new LinkedListTabulatedFunctionFactory().create(new double[]{1,2,3,4},new double[]{1,4,9,16});
        var diffop = new TabulatedDifferentialOperator();
        TabulatedFunction listdiff = diffop.derive(list);
        Assertions.assertEquals(6,listdiff.getY(2));
        Assertions.assertEquals(4,listdiff.getY(1));
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
    void setest()
    {
        var diff = new TabulatedDifferentialOperator();
        var fac = new LinkedListTabulatedFunctionFactory();
        diff.setFactory(fac);
        Assertions.assertEquals(fac,diff.getFactory());
    }
}