package functions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class SimpleIterationsFunctionTest {

    @Test
    void testFindRoot() {
        MathFunction exampleFunction = (x) -> 0.5 * (x + 3 / x);
        SimpleIterationsFunction iterationMethod = new SimpleIterationsFunction(exampleFunction, 1e-6, 1000);

        double initialGuess = 1.0;
        double root = iterationMethod.findRoot(initialGuess);

        Assertions.assertEquals(Math.sqrt(3), root, 1e-6);
        //корень совпадает с ожидаемым результатом с точностью 1e-6
    }

    // функция, которая быстро сходится
    @Test
    void testFindRootZero(){
        MathFunction testFindRootZero = (x) -> 0.1 * x;
        SimpleIterationsFunction iterationMethod = new SimpleIterationsFunction(testFindRootZero, 1e-6, 1000);

        double initialGuess = 100.0;
        double root = iterationMethod.findRoot(initialGuess);

        Assertions.assertEquals(0, root, 1e-6);
    }

}