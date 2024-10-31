package operations;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.*;
import exceptions.InconsistentFunctionsException;

public class TabulatedFunctionOperationService {
    private TabulatedFunctionFactory factory;

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public TabulatedFunctionOperationService() {
        this.factory = new ArrayTabulatedFunctionFactory();
    }

    public TabulatedFunctionFactory getFactory() {
        return factory;
    }

    public void setFactory(TabulatedFunctionFactory factory) {
        this.factory = factory;
    }

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        int count = tabulatedFunction.getCount();
        Point[] points = new Point[count];

        int i = 0;
        for (Point point : tabulatedFunction) {
            points[i] = point; // Заполнение массива точками
            i++;
        }

        return points;
    }

    private interface BiOperation {
        double apply(double u, double v);
    }


    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation) {
        if (a.getCount() != b.getCount()) {
            throw new InconsistentFunctionsException("Функции имеют разное количество точек.");
        }

        Point[] pointsA = asPoints(a);
        Point[] pointsB = asPoints(b);

        double[] xValues = new double[pointsA.length];
        double[] yValues = new double[pointsA.length];

        for (int i = 0; i < pointsA.length; i++) {
            if (pointsA[i].x != pointsB[i].x) {
                throw new InconsistentFunctionsException("Значения x у функций не совпадают.");
            }
            xValues[i] = pointsA[i].x;
            yValues[i] = operation.apply(pointsA[i].y, pointsB[i].y);
        }

        return factory.create(xValues, yValues);
    }

    public TabulatedFunction addition(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (x, y) -> x + y);
    }

    public TabulatedFunction subtraction(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (x, y) -> x - y);
    }

    // методы умножения и деления функций
    public TabulatedFunction division(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (x, y) -> x / y);
    }

    public TabulatedFunction multiplication(TabulatedFunction a, TabulatedFunction b) {
        return doOperation(a, b, (x, y) -> x * y);
    }
}