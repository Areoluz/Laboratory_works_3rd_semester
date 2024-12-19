package functions.factory;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LinkedListTabulatedFunctionFactoryTest {

    @Test
    void createListTest() {
        var listF = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction function = listF.create(new double[]{1, 4, 9}, new double[]{1, 2, 3});

        Assertions.assertTrue(function instanceof LinkedListTabulatedFunction,
                "The created function should be an instance of LinkedListTabulatedFunction");
    }
}
