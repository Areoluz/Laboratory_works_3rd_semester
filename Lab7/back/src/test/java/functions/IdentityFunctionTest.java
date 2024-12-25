package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class IdentityFunctionTest {
    private IdentityFunction function;

    @BeforeEach
    void setUp() {
        function = new IdentityFunction();
    }

    @Test
    void applyInteger() {
        double res = function.apply(2);
        Assertions.assertEquals(2,res);
    }

    @Test
    void applyNegativeDouble() {
        double res = function.apply(-2.2);
        Assertions.assertEquals(-2.2,res);
    }

}