package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class IdentifyFunctionTest {
    private IdentifyFunction function;

    @BeforeEach
    void setUp() {
        function = new IdentifyFunction();
    }

    @Test
    void applyInteger() {
        double res = function.apply(2);
        Assertions.assertEquals(2,res);
    }

    @Test
    void applyDouble() {
        double res = function.apply(-2.2);
        Assertions.assertEquals(-2.2,res);
    }

}