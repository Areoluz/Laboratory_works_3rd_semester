package functions;
import exceptions.InterpolationException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import java.util.Arrays;
public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements TabulatedFunction, Serializable {
    @Serial
    private static final long serialVersionUID = 3621396615035252299L;

    private final double[] xValues;
    private final double[] yValues;

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if(xValues.length == 0) {
            throw new IllegalArgumentException("You must specify at least one x:y value");
        }
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }
    @Override
    public Iterator<Point> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < count;
            }

            @Override
            public Point next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in the iterator");
                }
                Point point = new Point(xValues[i], yValues[i]);
                i++;
                return point;
            }
        };
    }
    public  ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count){
        if(xFrom > xTo) {
            double temp = xTo;
            xTo = xFrom;
            xFrom = temp;
        }

        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        double step = (xTo - xFrom) / (count - 1); // Дискретизация
        for (int i = 0; i < count; i++) {
            xValues[i] = xFrom + i * step;
            yValues[i] = source.apply(xValues[i]);
        }

        if (xFrom == xTo) { // Заполняем массивы одним значением
            Arrays.fill(xValues, xFrom);
            Arrays.fill(yValues, source.apply(xFrom));
        }
    }

    public void setY(int index, double value) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        yValues[index] = value;
    }

    public double leftBound() {
        return xValues[0];
    }

    public double rightBound() {
        return xValues[count - 1];
    }

    public int indexOfY(double y) {
        for (int i = 0; i < count; i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1; // Если y не найден
    }

    @Override
    public double getX(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return yValues[index];
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        if (x < xValues[0]) {
            return 0;
        }
        for (int i = 1; i < count; i++) {
            if (xValues[i] > x) {
                return i - 1;
            }
        }
        return count - 1;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (x < xValues[floorIndex] || x > xValues[floorIndex + 1]) {
            throw new InterpolationException("Value of x is out of the interpolation interval.");}

        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        if (leftX == rightX) {     // Защищенный метод
            return leftY;
        }
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX); // Линейная интерполяция
    }
}