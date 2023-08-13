package edu.Roma42.chat.repositories;

import edu.Roma42.chat.models.User;
import java.util.*;

public interface UserRepository {

	List<User> findAll(int page, int size);
	Optional<User> findById(Long id);
}
