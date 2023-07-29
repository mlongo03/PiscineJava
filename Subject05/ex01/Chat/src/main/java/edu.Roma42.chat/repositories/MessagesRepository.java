package edu.Roma42.chat.repositories;

import edu.Roma42.chat.models.Message;
import java.util.*;

public interface MessagesRepository {

	Optional<Message> findById(Long id);
}
