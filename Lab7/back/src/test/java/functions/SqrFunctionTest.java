package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class SqrFunctionTest {

    private SqrFunction function;

    @BeforeEach
    public void setUp() {
        function = new SqrFunction();
    }

    @Test
    public void testSqrFunctionForInt() {
        double expected = 25;
        double actual = function.apply(5);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testSqrFunctionForZero() {
        double expected = 0;
        double actual = function.apply(0);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

    @Test
    public void testSqrFunctionForDouble() {
        double expected = 148.84;
        double actual = function.apply(12.2);
        Assertions.assertEquals(expected, actual, 0.0001);
    }

}
