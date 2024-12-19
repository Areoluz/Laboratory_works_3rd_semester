package jpa.repository;

import jpa.entities.MathRes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathResRepos extends CrudRepository<MathRes, Integer> {
    List<MathRes> findByHash(long hash);
    MathRes findByXAndHash(double x, long hash);
    void deleteByHash(long hash);
    void deleteByXAndHash(double x, long hash);
}