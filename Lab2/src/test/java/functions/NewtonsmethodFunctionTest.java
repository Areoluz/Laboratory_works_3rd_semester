package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewtonsmethodFunctionTest {


    @Test
    public void testFindRoot() {
        // f(x) = x^3 - x, корни: x = -1, x = 0, x = 1
        MathFunction Function = x -> x * x * x - x;
        NewtonsmethodFunction newton = new NewtonsmethodFunction(Function, 0.001, 100);

        double root1 = newton.findRoot(0.4);
        assertEquals(0.0, root1, 0.001);

        double root2 = newton.findRoot(-2);
        assertEquals(-1.0, root2, 0.001);

        double root3 = newton.findRoot(2);
        assertEquals(1.0, root3, 0.001);
    }

    @Test
    public void testFindRoot2() {
        // f(x) = 2x - 6, корень: x = 3
        MathFunction Function = x -> 2 * x - 6;
        NewtonsmethodFunction newton = new NewtonsmethodFunction(Function, 0.001, 100);

        double root = newton.findRoot(0);
        assertEquals(3.0, root, 0.001);
    }

    @Test
    public void testFindRoot3() {
        // f(x) = cos(x) - x, x ~ 0.739
        MathFunction Function = x -> Math.cos(x) - x;
        NewtonsmethodFunction newton = new NewtonsmethodFunction(Function, 0.001, 1000);

        double root = newton.findRoot(0.5);
        assertEquals(0.7393, root, 0.001);
    }

    @Test
    public void testDerivativeTooSmall() {
        // f(x) = x^2 производная в x = 0 равна 0
        MathFunction Function = x -> x * x;
        NewtonsmethodFunction newton = new NewtonsmethodFunction(Function, 0.001, 100);

        assertThrows(RuntimeException.class, () -> newton.findRoot(0));
    }

    @Test
    public void testNoRoot() {
        // f(x) = 1 / (x - 1), не имеет корней
        MathFunction Function = x -> 1 / (x - 1);
        NewtonsmethodFunction newton = new NewtonsmethodFunction(Function, 0.001, 1000);

        assertThrows(RuntimeException.class, () -> newton.findRoot(2));
    }
}