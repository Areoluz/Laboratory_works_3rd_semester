package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Идентичная функция")
public class IdentityFunction implements MathFunction {
    public double apply(double x){
        return x;
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        sb.append("x");
    }

}
