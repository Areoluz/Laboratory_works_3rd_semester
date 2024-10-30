package operations;

import functions.ArrayTabulatedFunction;
import functions.Point;
import functions.TabulatedFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TabulatedFunctionOperationServiceTest {

    @Test
    void test1() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction newTabFunc = service.multiplication(function1,function2);
        Assertions.assertArrayEquals(new Point[]{new Point(1,1),new Point(2,4),new Point(3,9),new Point(4,16),new Point(5,25)}, TabulatedFunctionOperationService.asPoint(newTabFunc));
    }
    @Test
    void test2() {
        TabulatedFunction function1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunction function2 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 3, 4, 5});
        TabulatedFunctionOperationService service = new TabulatedFunctionOperationService();
        TabulatedFunction newTabFunc = service.division(function1,function2);
        Assertions.assertArrayEquals(new Point[]{new Point(1,1),new Point(2,1),new Point(3,1),new Point(4,1),new Point(5,1)}, TabulatedFunctionOperationService.asPoint(newTabFunc));
    }
}