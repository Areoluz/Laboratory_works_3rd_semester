package jpa.repository;

import jpa.entities.MathRes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MathResRepos extends CrudRepository<MathRes, Integer> {
MathRes findByXAndHash(double x, long hash);
List<MathRes> findByHash(long hash);
List<MathRes> findByHashOrderByX(long hash);
List<MathRes> findByXOrderByY(double x);
}