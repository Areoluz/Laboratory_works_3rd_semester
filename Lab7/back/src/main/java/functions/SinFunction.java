package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Синус", priority = 3)
public class SinFunction implements MathFunction {
    public double apply(double x) {
        return java.lang.Math.sin(x);
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("sin");
    }
}
