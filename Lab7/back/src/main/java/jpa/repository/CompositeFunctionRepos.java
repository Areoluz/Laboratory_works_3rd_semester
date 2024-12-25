package jpa.repository;

import jpa.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jpa.entities.CompositeFunctionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompositeFunctionRepos extends CrudRepository<CompositeFunctionEntity, Integer> {

    List<CompositeFunctionEntity> findByUserId(User user);
    Optional<CompositeFunctionEntity> findByUserIdAndName(User user, String name);


}