package operations;

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

        xValues[0] = points[0].x;
        yValues[0] = (points[1].y - points[0].y) / (points[1].x - points[0].x);
        xValues[n - 1] = points[n - 1].x;
        yValues[n - 1] = (points[n - 1].y - points[n - 2].y) / (points[n - 1].x - points[n - 2].x);

        return factory.create(xValues, yValues);
    }

    @Override
    public double apply(double x) {
        return 0;
    }
}
