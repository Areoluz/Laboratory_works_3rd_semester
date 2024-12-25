package functions;

public class IdentityFunction implements MathFunction {
    public double apply(double x){
        return x;
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("x");
    }

}
