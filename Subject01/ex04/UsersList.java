public interface UsersList {

	void	addUser(User user);
	User 	getUserByIndex(int index);
	User	getUserById(int id);
	int		nUsers();
}
