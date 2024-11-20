package jpa.repository;

import jpa.entities.Log;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepos extends CrudRepository<Log, Integer> {
    List<Log> findByOrderByTimestampDesc();
}