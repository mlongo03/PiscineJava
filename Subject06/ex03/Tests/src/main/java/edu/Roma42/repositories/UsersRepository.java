package edu.Roma42.repositories;

import java.lang.String;
import edu.Roma42.models.User;;

public interface UsersRepository {
	User findByLogin(String login);
	void update(User user);
}
