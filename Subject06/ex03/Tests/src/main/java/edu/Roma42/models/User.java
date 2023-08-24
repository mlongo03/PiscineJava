package edu.Roma42.models;

public class User {
	private Long Id;
	private String Login;
	private String Password;
	private boolean Auth_status;

	public User(Long Id, String Login, String Password, boolean Auth_status)
	{
		this.Id = Id;
		this.Login = Login;
		this.Password = Password;
		this.Auth_status = Auth_status;
	}

	public Long getId() {
		return (this.Id);
	}

	public void setId(Long Id) {
		this.Id = Id;
	}

	public String getLogin() {
		return (this.Login);
	}

	public void setLogin(String Login) {
		this.Login = Login;
	}

	public String getPassword() {
		return (this.Password);
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	public boolean getAuth_status() {
		return (this.Auth_status);
	}

	public void setAuth_status(boolean Auth_status) {
		this.Auth_status = Auth_status;
	}
}
