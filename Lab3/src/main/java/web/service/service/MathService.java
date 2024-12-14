package web.service.service;

import jpa.entities.MathRes;
import jpa.repository.MathResRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MathService {
    @Autowired
    private MathResRepos mathResRepository;

    public MathService(MathResRepos mathResRepository) {
        this.mathResRepository = mathResRepository;
    }

    public List<MathRes> findAll() {
        return (List<MathRes>) mathResRepository.findAll();
    }

    public List<MathRes> findByHash(long hash) {
        return mathResRepository.findByHash(hash);
    }

    public MathRes findByXAndHash(double x, long hash) {
        return mathResRepository.findByXAndHash(x, hash);
    }

    @Transactional
    public void deleteByHash(long hash) {
        mathResRepository.deleteByHash(hash);
    }

    @Transactional
    public void deleteByXAndHash(double x, long hash) {
        mathResRepository.deleteByXAndHash(x, hash);
    }

    @Transactional
    public void deleteAll() {
        mathResRepository.deleteAll();
    }
}