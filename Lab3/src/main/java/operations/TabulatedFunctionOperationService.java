package operations;

import functions.Point;
import functions.TabulatedFunction;

public class TabulatedFunctionOperationService {

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        int count = tabulatedFunction.getCount();
        Point[] points = new Point[count];

        int i = 0;
        for (Point point : tabulatedFunction) {
            points[i] = point; // Заполняем массив точек
            i++;
        }

        return points;
    }
}