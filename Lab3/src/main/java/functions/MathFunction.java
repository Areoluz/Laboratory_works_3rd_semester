package functions;

public interface MathFunction {
    double apply(double x);

    default void stringWrite(StringBuilder sb) {
        sb.append(this.getClass().getName());
    }

    default int hash() {
        StringBuilder sb = new StringBuilder();
        stringWrite(sb);
        return sb.toString().hashCode();
    }


    default CompositeFunction andThen(MathFunction afterFunction) {
        return new CompositeFunction(this, afterFunction);
    }
}