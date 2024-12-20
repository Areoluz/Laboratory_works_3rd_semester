package functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompositeFunctionTest {
    private CompositeFunction compFunction;

    @Test
    void applyCompositeFunction() {
        FirstFunc function1 = new FirstFunc();
        SecondFunc function2 = new SecondFunc();
        compFunction = new CompositeFunction(function1,function2);
        double res = compFunction.apply(2);
        Assertions.assertEquals(Math.pow(Math.cos(2), 3), res);
    }
    // косинус числа
    @Test
    void applyCompositeFunctionForFirst() {
        FirstFunc function1 = new FirstFunc();
        IdentityFunction function2 = new IdentityFunction();
        compFunction = new CompositeFunction(function1,function2);
        double res = compFunction.apply(3.14);
        Assertions.assertEquals(Math.cos(3.14), res);
    }
    // то же значение что и на входе
    @Test
    void applyCompositeFunctionForSecond() {
        IdentityFunction function1 = new IdentityFunction();
        IdentityFunction function2 = new IdentityFunction();
        compFunction = new CompositeFunction(function1,function2);
        double res = compFunction.apply(.2);
        Assertions.assertEquals(.2, res);
    }
}


// классы реализующие интерфейс

// косинус числа
class FirstFunc implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.cos(x);
    }
}
// возведение в третью степень
class SecondFunc implements MathFunction {
    @Override
    public double apply(double x) {
        return Math.pow(x, 3);
    }
}