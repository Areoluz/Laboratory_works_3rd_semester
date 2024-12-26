package operations;

import concurrent.SynchronizedTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import functions.Point;

public class TabulatedDifferentialOperator implements DifferentialOperator<TabulatedFunction> {

    private TabulatedFunctionFactory factory;

    public TabulatedDifferentialOperator() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedDifferentialOperator(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }


    @Override
    public TabulatedFunction derive(TabulatedFunction function) {
        Point[] points = TabulatedFunctionOperationService.asPoints(function);
        int n = points.length;
        double[] xValues = new double[n];
        double[] yValues = new double[n];

        for (int i = 1; i < n - 1; i++) {
            xValues[i] = points[i].x;
            yValues[i] = (points[i + 1].y - points[i - 1].y) / (points[i + 1].x - points[i - 1].x);
        }

        xValues[xValues.length - 1] = points[points.length - 1].x;
        yValues[yValues.length - 1] = yValues[yValues.length - 2];
        return factory.create(xValues, yValues);
    }

    public TabulatedFunction deriveSynchronously(TabulatedFunction function) {
        SynchronizedTabulatedFunction synchronizedFunction;

        // Проверяем, является ли функция уже синхронизированной обёрткой
        if (function instanceof SynchronizedTabulatedFunction) {
            synchronizedFunction = (SynchronizedTabulatedFunction) function;
        } else {
            synchronizedFunction = new SynchronizedTabulatedFunction(function);
        }

        return synchronizedFunction.doSynchronously(this::derive);
    }

    @Override
    public double apply(double x) {
        throw new UnsupportedOperationException();
    }
}
