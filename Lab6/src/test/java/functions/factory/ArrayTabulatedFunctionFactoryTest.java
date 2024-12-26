package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayTabulatedFunctionFactoryTest {

    @Test
    void createArrayTest() {
        var arrFunc = new ArrayTabulatedFunctionFactory();
        TabulatedFunction function = arrFunc.create(new double[]{1, 2, 3}, new double[]{1, 4, 9});
        
        Assertions.assertTrue(function instanceof ArrayTabulatedFunction,
                "The created function should be an instance of ArrayTabulatedFunction");
    }
}
