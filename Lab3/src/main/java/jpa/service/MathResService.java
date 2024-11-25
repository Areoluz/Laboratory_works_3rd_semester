package jpa.service;

import functions.MathFunction;
import jpa.entities.MathRes;
import jpa.repository.MathResRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.OptionalDouble;

@Service
public class MathResService {
    private final MathResRepos mathResRepos;

    @Autowired
    public MathResService(MathResRepos mathResRepos) {
        this.mathResRepos = mathResRepos;
    }

    public double applyCached(MathFunction func, double x) {
        OptionalDouble cache = getCached(func, x);
        if (cache.isPresent()) {
            return cache.getAsDouble();
        } else {
            double result = func.apply(x);
            MathRes mathRes = new MathRes(x, result, (long) func.hash());
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

    @Transactional
    public void deleteAll() {
        mathResRepos.deleteAll();
    }
}
