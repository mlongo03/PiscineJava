package edu.Roma42.chat.repositories;

import edu.Roma42.chat.models.Chatroom;
import java.util.*;

public interface ChatroomRepository {

	Optional<Chatroom> findById(Long id);
}
