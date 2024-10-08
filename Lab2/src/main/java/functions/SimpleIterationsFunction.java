package functions;

public class SimpleIterationsFunction {

    private final MathFunction g;
    private final double accuracy;
    private final int maxIterations;

    public SimpleIterationsFunction(MathFunction g, double accuracy, int maxIterations) {
        this.g = g;
        this.accuracy = accuracy;
        this.maxIterations = maxIterations;
    }

    // метод простых итераций
    public double findRoot(double initialGuess) {
        double x = initialGuess;
        int iterations = 0;

        while (iterations < maxIterations) {
            double nextX = g.apply(x);  // вычисление следующего приближения

            // проверка условия сходимости (разница между текущим и следующим приближением меньше точности)
            if (Math.abs(nextX - x) < accuracy) {
                return nextX;
            }

            x = nextX;
            iterations++;
        }

        return x;
    }
}
