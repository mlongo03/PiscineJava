public interface UsersList {

	void	addUser(User user);
	User 	getUserByIndex(int index) throws UserNotFoundException;
	User	getUserById(int id) throws UserNotFoundException;
	int		nUsers();
}