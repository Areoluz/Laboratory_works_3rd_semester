package functions;

public class SqrFunction implements MathFunction {
    public double apply(double x) {
        return java.lang.Math.pow(x,2);
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("x^2");
    }
}
