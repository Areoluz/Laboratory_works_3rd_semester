package functions;

public interface TabulatedFunction extends MathFunction, Iterable<Point>{

    // количество табулированных значений
    int getCount();

    double getX(int index);

    double getY(int index);

    void setY(int index, double value);

    int indexOfX(double x);

    int indexOfY(double y);

    double leftBound();

    double rightBound();

    @Override
    default void stringWrite(StringBuilder sb) {
        sb.append(this.getClass().getName());
        sb.append("[");
        for(Point p : this){
            sb.append(p.x);
            sb.append(",");
            sb.append(p.y);
            sb.append(";");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
    }
}