package jpa.service;

import functions.MathFunction;
import jpa.entities.MathRes;
import jpa.repository.MathResRepos;
import lombok.AllArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.OptionalDouble;

@Component
@AllArgsConstructor
public class MathResService {
    @Delegate(types = MathResRepos.class)
    private MathResRepos mathResRepos;

    public double applyCached(MathFunction func, double x) {
        OptionalDouble cache = getCached(func, x);
        if (cache.isPresent()) {
            return cache.getAsDouble();
        } else {
            double result = func.apply(x);
            MathRes mathRes = new MathRes();
            mathRes.setX(x);
            mathRes.setY(result);
            mathRes.setHash(func.hash());
            mathResRepos.save(mathRes);
            return result;
        }
    }

    public OptionalDouble getCached(MathFunction func, double x) {
        MathRes var = mathResRepos.findByXAndHash(x, func.hash());
        if (var != null) {
            return OptionalDouble.of(var.getY());
        }
        return OptionalDouble.empty();
    }
}


