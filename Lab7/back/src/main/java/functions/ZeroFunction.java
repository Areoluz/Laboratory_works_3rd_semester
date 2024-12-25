package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Нулевая функция")
public class ZeroFunction extends ConstantFunction {
    public ZeroFunction() {
        super(0);
    }
}
