package functions;

public class IdentifyFunction implements MathFunction {
    public double apply(double x){
        return x;
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("x");
    }

}
