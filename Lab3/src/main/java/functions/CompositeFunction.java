package functions;

public class CompositeFunction implements MathFunction{
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public CompositeFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    public double apply(double x){
        return secondFunction.apply(firstFunction.apply(x));
    }
    @Override
    public void stringWrite(StringBuilder sb) {
        secondFunction.stringWrite(sb);
        sb.append('{');
        firstFunction.stringWrite(sb);
        sb.append('}');
    }
}
