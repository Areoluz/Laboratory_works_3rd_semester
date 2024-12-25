package functions;

import ui.annotations.UIFunction;

@UIFunction(localizedName = "Единичная функция")
public class UnitFunction extends ConstantFunction {
    public UnitFunction() {
        super(1);
    }
}
