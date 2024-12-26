package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Экспоненциальная функция", priority = 4)
public class ExpFunction implements MathFunction {
    public double apply(double x) {
        return java.lang.Math.exp(x);
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("exp");
    }
}
