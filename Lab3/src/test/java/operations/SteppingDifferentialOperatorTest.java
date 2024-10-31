package operations;

import functions.*;
import operations.LeftSteppingDifferentialOperator;
import operations.RightSteppingDifferentialOperator;
import operations.MiddleSteppingDifferentialOperator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class SteppingDifferentialOperatorTest {

    @Test
    public void testLeftSteppingDifferentialOperator() {
        double step = 0.1;
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(step);
        MathFunction derivative = operator.derive(new SqrFunction());

        double x = 2.0;
        double expected = (4.0 - Math.pow(x - step, 2)) / step;
        Assertions.assertEquals(expected, derivative.apply(x), 1e-5);
    }

    @Test
    public void testRightSteppingDifferentialOperator() {
        double step = 0.1;
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);
        MathFunction derivative = operator.derive(new SqrFunction());

        double x = 2.0;
        double expected = (Math.pow(x + step, 2) - 4.0) / step;
        Assertions.assertEquals(expected, derivative.apply(x), 1e-5);
    }

    @Test
    public void testMiddleSteppingDifferentialOperator() {
        double step = 0.1;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);
        MathFunction derivative = operator.derive(new SqrFunction());

        double x = 2.0;
        double expected = (Math.pow(x + step, 2) - Math.pow(x - step, 2)) / (2 * step);
        Assertions.assertEquals(expected, derivative.apply(x), 1e-5);
    }
}
