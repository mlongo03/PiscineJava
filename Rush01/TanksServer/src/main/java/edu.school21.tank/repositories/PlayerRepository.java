package edu.school21.tank.repositories;

import java.util.Optional;

import edu.school21.tank.models.Player;

public interface PlayerRepository extends CrudRepository<Player> {

    @Override
    Optional<Player> findById(Long id);
}
