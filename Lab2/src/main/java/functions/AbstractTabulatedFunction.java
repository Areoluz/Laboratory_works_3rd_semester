package functions;

public abstract class AbstractTabulatedFunction implements MathFunction {

    protected int count;

    protected abstract int floorIndexOfX(double index);
    protected abstract double getX(int index);
    protected abstract double getY(int index);

    protected abstract double extrapolateLeft(double x);     // Метод экстраполяции слева
    protected abstract double extrapolateRight(double x);     // Метод экстраполяции справа
    protected abstract double interpolate(double x, int floorIndex);  // Метод для интерполяции внутри интервала

    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return leftY + (x - leftX) * (rightY - leftY) / (rightX - leftX);
    }

    public int getCount() {
        return count;
    }

    protected int indexOfX(double x) {
        for (int i = 0; i < count; i++) {
            if (getX(i) == x) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public double apply(double x) {
        if (x < getX(0)) { // Если x меньше левого края
            return extrapolateLeft(x);
        }
        if (x > getX(count - 1)) {
            return extrapolateRight(x);  // Если x больше правого края
        }
        int index = indexOfX(x); // Проверяем, есть ли x в таблице
        if (index != -1) {
            return getY(index);
        }
        int floorIndex = floorIndexOfX(x); // Если x не найден
        return interpolate(x, floorIndex);
    }


}