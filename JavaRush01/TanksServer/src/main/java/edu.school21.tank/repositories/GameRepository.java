package edu.school21.tank.repositories;

import java.util.Optional;

import edu.school21.tank.models.Game;

public interface GameRepository extends CrudRepository<Game> {
    Optional<Game> findWaitingGame();
}