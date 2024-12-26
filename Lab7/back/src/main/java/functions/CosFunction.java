package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Косинус", priority = 2)
public class CosFunction implements MathFunction {
    public double apply(double x) {
        return java.lang.Math.cos(x);
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("cos");
    }
}
