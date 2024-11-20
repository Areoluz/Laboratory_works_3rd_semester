package functions;

public class NewtonsmethodFunction implements MathFunction {
    private final MathFunction function;
    private final double accuracy;
    private final int maxIterations;

    private static final double epsilon = 1e-6; // эпсилон для вычисления производной

    public NewtonsmethodFunction(MathFunction function, double accuracy, int maxIterations) {
        this.function = function;
        this.accuracy = accuracy;
        this.maxIterations = maxIterations;
    }

    private double derivative(double x) {
        return (function.apply(x+ epsilon) - function.apply(x-epsilon))/(2*epsilon); //вычисление производной
    }


    public double findRoot(double initialGuess) {
        double x = initialGuess;
        int iterations = 0;

        while (iterations < maxIterations) {
            double y = function.apply(x);
            double yPrime = derivative(x);

           //проверка что производая не равна 0
            if (Math.abs(yPrime) < epsilon) {
                throw new RuntimeException("Derivative is to small, maybe it's equal to zero");
            }

            double newX = x - y/yPrime;
            // Проверка условия сходимости
            if (Math.abs(newX - x) < accuracy) {
                return newX;
            }
            x = newX;
            iterations++;
        }

        throw new RuntimeException("Method ended with, " + maxIterations + " iterations.");
    }

    @Override
    public double apply(double x) {
        return findRoot(x);
    }

    @Override
    public void stringWrite(StringBuilder sb) {
        throw new UnsupportedOperationException("Пусть второй человек сделает.");
    }
}
