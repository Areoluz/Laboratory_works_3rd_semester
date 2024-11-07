package operations;

import exceptions.InconsistentFunctionsException;
import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
class TabulatedFunctionOperationServiceTest {

    @Test
    void test1() {
        TabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction newTabFunc = service.multiplication(function1, function2);
        Point[] expectedPoints = new Point[]{
                new Point(1, 1),
                new Point(2, 4),
                new Point(3, 9),
                new Point(4, 16),
                new Point(5, 25)
        };
        Point[] actualPoints = TabulatedFunctionOperationService.asPoints(newTabFunc);
        for (int i = 0; i < expectedPoints.length; i++) {
            Assertions.assertEquals(expectedPoints[i].x, actualPoints[i].x, 1e-5);
            Assertions.assertEquals(expectedPoints[i].y, actualPoints[i].y, 1e-5);
        }
    }
    @Test
    void test2() {
        TabulatedFunction function1 = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction newTabFunc = service.division(function1, function2);
        Point[] expectedPoints = new Point[]{
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(4, 1),
                new Point(5, 1)
        };
        Point[] actualPoints = TabulatedFunctionOperationService.asPoints(newTabFunc);
        for (int i = 0; i < expectedPoints.length; i++) {
            Assertions.assertEquals(expectedPoints[i].x, actualPoints[i].x, 1e-5);
            Assertions.assertEquals(expectedPoints[i].y, actualPoints[i].y, 1e-5);
        }
    }

    @Test
    public void testAdditionWithSameFactory() {
        TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues = {1, 2, 3};
        double[] yValues1 = {4, 5, 6};
        double[] yValues2 = {7, 8, 9};

        TabulatedFunction f1 = factory.create(xValues, yValues1);
        TabulatedFunction f2 = factory.create(xValues, yValues2);

        TabulatedFunction result = service.addition(f1, f2);

        double[] expectedYValues = {11, 13, 15};
        for (int i = 0; i < expectedYValues.length; i++) {
            Assertions.assertEquals(expectedYValues[i], result.getY(i), 1e-5);
        }
    }
    @Test
    public void testMultiplication() {
        TabulatedFunctionFactory factory1 = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory2 = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory1);

        double[] xValues = {1, 2, 3};
        double[] yValues1 = {2, 4, 6};
        double[] yValues2 = {3, 6, 9};

        TabulatedFunction f1 = factory1.create(xValues, yValues1);
        TabulatedFunction f2 = factory2.create(xValues, yValues2);

        TabulatedFunction result = service.multiplication(f1, f2);

        double[] expectedYValues = {6, 24, 54};
        for (int i = 0; i < expectedYValues.length; i++) {
            Assertions.assertEquals(expectedYValues[i], result.getY(i), 1e-5);
        }
    }

    @Test
    public void testSubtraction() {
        TabulatedFunctionFactory factory1 = new ArrayTabulatedFunctionFactory();
        TabulatedFunctionFactory factory2 = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory1);

        double[] xValues = {1, 2, 3};
        double[] yValues1 = {10, 20, 30};
        double[] yValues2 = {1, 2, 3};

        TabulatedFunction f1 = factory1.create(xValues, yValues1);
        TabulatedFunction f2 = factory2.create(xValues, yValues2);

        TabulatedFunction result = service.subtraction(f1, f2);

        double[] expectedYValues = {9, 18, 27};
        for (int i = 0; i < expectedYValues.length; i++) {
            Assertions.assertEquals(expectedYValues[i], result.getY(i), 1e-5);
        }
    }

    @Test
    public void testInconsistentFunctionsException() {
        TabulatedFunctionFactory factory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(factory);

        double[] xValues1 = {1, 2, 3};
        double[] yValues1 = {4, 5, 6};
        double[] xValues2 = {1, 2, 4};
        double[] yValues2 = {7, 8, 9};

        TabulatedFunction f1 = factory.create(xValues1, yValues1);
        TabulatedFunction f2 = factory.create(xValues2, yValues2);

        Assertions.assertThrows(InconsistentFunctionsException.class, () -> {
            service.addition(f1, f2);
        });
    }

    @Test
    public void testGetAndSetFactory() {
        TabulatedFunctionFactory initialFactory = new LinkedListTabulatedFunctionFactory();
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService(initialFactory);

        Assertions.assertEquals(initialFactory, service.getFactory());

        TabulatedFunctionFactory newFactory = new ArrayTabulatedFunctionFactory();
        service.setFactory(newFactory);

        Assertions.assertEquals(newFactory, service.getFactory());
    }

    @Test
    void testMiddleSteppingDifferentialOperatorDerive() {
        double step = 0.01;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

        MathFunction function = x -> x * x;

        MathFunction derivative = operator.derive(function);

        // Производная функции y = x^2 равна 2x
        double x = 3.0;
        double expectedDerivative = 2 * x;
        double actualDerivative = derivative.apply(x);

        Assertions.assertEquals(expectedDerivative, actualDerivative, 1e-2,
                "Derived value for x^2 should be approximately equal to 2 * x.");
    }

    @Test
    void testMiddleSteppingDifferentialOperatorDeriveWithSinFunction() {
        double step = 0.01;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

        MathFunction function = Math::sin;

        MathFunction derivative = operator.derive(function);

        // Производная sin(x) равна cos(x)
        double x = Math.PI / 4; // 45 градусов
        double expectedDerivative = Math.cos(x);
        double actualDerivative = derivative.apply(x);

        Assertions.assertEquals(expectedDerivative, actualDerivative, 1e-2,
                "Derived value for sin(x) should be approximately equal to cos(x).");
    }
    @Test
    void testUnsupportedOperationExceptionForMiddleSteppingOperator() {
        double step = 0.01;
        MiddleSteppingDifferentialOperator operator = new MiddleSteppingDifferentialOperator(step);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            operator.apply(2.0);
        }, "Calling apply on MiddleSteppingDifferentialOperator should throw UnsupportedOperationException.");
    }

    @Test
    void testUnsupportedOperationExceptionForRightSteppingOperator() {
        double step = 0.01;
        RightSteppingDifferentialOperator operator = new RightSteppingDifferentialOperator(step);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            operator.apply(2.0);
        }, "Calling apply on RightSteppingDifferentialOperator should throw UnsupportedOperationException.");
    }
    @Test
    void testUnsupportedOperationExceptionForLeftSteppingOperator() {
        double step = 0.01;
        LeftSteppingDifferentialOperator operator = new LeftSteppingDifferentialOperator(step);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            operator.apply(2.0);
        }, "Calling apply on LeftSteppingDifferentialOperator should throw UnsupportedOperationException.");
    }
}

