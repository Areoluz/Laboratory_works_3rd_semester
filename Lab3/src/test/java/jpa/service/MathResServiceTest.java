package jpa.service;

import functions.IdentifyFunction;
import functions.MathFunction;
import functions.SqrFunction;
import jpa.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {MathResService.class, DbConfig.class})
@ExtendWith(SpringExtension.class)
class MathResServiceTest {

    @Autowired
    private MathResService mathResService;

    @Test
    void applyAndCacheTest() {
        mathResService.deleteAll();
        MathFunction func = new SqrFunction().andThen(new IdentifyFunction());
        assertTrue(mathResService.getCached(func, 0).isEmpty());

        assertEquals(mathResService.applyCached(func, 0), 0.0, 0.00001);
        assertEquals(mathResService.applyCached(func, 0), 0.0, 0.00001);
        assertTrue(mathResService.getCached(func, 0).isPresent());

        assertTrue(mathResService.getCached(func, 1).isEmpty());
        assertTrue(mathResService.getCached(func, 52).isEmpty());

        assertEquals(mathResService.applyCached(func, 52), 2704.0, 0.00001);
        assertTrue(mathResService.getCached(func, 52).isPresent());
    }
}
