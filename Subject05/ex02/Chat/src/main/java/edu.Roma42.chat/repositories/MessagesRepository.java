package edu.Roma42.chat.repositories;

import edu.Roma42.chat.models.Message;
import java.util.*;

public interface MessagesRepository {

	void save(Message message);
	Optional<Message> findById(Long id);
}
