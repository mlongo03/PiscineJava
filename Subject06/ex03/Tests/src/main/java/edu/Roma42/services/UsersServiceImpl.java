package main.java.edu.Roma42.services;

import edu.Roma42.models.User;
import edu.Roma42.exceptions.AlreadyAuthenticatedException;
import edu.Roma42.repositories.UsersRepository;

public class UsersServiceImpl {

	UsersRepository Users;

	public UsersServiceImpl(UsersRepository Users) {
		this.Users = Users;
	}

	public boolean authenticate(String login, String password) {
		User user;

		try {
			user = this.Users.findByLogin(login);
		} catch (RuntimeException e) {
			System.out.println("failed the findbyid of " + login);
			return (false);
		}
		if (user.getAuth_status()) {
			throw new AlreadyAuthenticatedException(login + " already authenticated");
		}
		else if (!user.getPassword().equals(password)) {
			return (false);
		}
		user.setAuth_status(true);
		try {
			Users.update(user);
		} catch (RuntimeException e) {
			System.out.println("failed to update " + login);
			return (false);
		}
		return (true);
	}
}
