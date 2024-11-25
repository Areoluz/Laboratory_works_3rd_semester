package jpa.repository;

import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import jpa.DbConfig;
import jpa.entities.MathRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MathResRepos.class, DbConfig.class})
@ExtendWith(SpringExtension.class)
class MathResReposTest {

    @Autowired
    private MathResRepos mathRep;

    @Test
    void testHashing() {
        MathFunction function1 = x -> x + 1;
        MathFunction function2 = x -> x * 2;

        assertNotEquals(function1.hash(), function2.hash(), "Хеши функций должны различаться");

        MathFunction compositeFunction = function1.andThen(function2);
        MathFunction compositeFunction2 = function2.andThen(function1);

        assertNotEquals(function1.hash(), compositeFunction.hash(), "Хеши составной функции и оригинальных должны различаться");
        assertNotEquals(compositeFunction.hash(), compositeFunction2.hash(), "Хеши составных должны отличаться");
    }

    @Test
    void testCreateAndRead() {
        double[] xValues = {1, 2, 3, 4};
        double[] yValues = {2, 4, 6, 8};
        ArrayTabulatedFunction arrayFunction = new ArrayTabulatedFunction(xValues, yValues);

        mathRep.deleteAll();
        assertEquals(0, mathRep.count());

        MathRes mathRes = new MathRes();
        mathRes.setX(Double.valueOf(1.0));
        mathRes.setY(Double.valueOf(2.0));
        mathRes.setHash(Long.valueOf(arrayFunction.hash()));

        MathRes savedMathRes = mathRep.save(mathRes);
        assertNotNull(savedMathRes.getId());
        assertEquals(1, mathRep.count());
    }

    @Test
    void testUpdate() {
        MathFunction function1 = x -> x*x + 1;
        MathFunction function2 = x -> x * 2;
        MathFunction compositeFunction = function1.andThen(function2);

        mathRep.deleteAll();
        MathRes mathRes = new MathRes();
        mathRes.setX(Double.valueOf(2.0));
        mathRes.setY(Double.valueOf(4.0));
        mathRes.setHash(Long.valueOf(compositeFunction.hash()));

        MathRes savedMathRes = mathRep.save(mathRes);
        assertNotNull(savedMathRes.getId());

        savedMathRes.setY(Double.valueOf(8.0));
        mathRep.save(savedMathRes);

        assertEquals(Double.valueOf(8.0), mathRep.findById(savedMathRes.getId()).get().getY());
    }

    @Test
    void testDelete() {
        mathRep.deleteAll();
        MathRes mathRes = new MathRes();
        mathRes.setX(Double.valueOf(3.0));
        mathRes.setY(Double.valueOf(6.0));
        mathRes.setHash(Long.valueOf(123));

        MathRes savedMathRes = mathRep.save(mathRes);
        assertNotNull(savedMathRes.getId());
        assertEquals(1, mathRep.count());

        mathRep.delete(savedMathRes);
        assertEquals(0, mathRep.count());
    }
}
