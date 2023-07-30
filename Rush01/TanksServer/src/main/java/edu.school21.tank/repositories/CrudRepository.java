package edu.school21.tank.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    void save(T entity);

    void update(T entity);

    Optional<T> findById(Long id);
}
