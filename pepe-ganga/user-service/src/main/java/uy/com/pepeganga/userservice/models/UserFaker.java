package uy.com.pepeganga.userservice.models;

public class UserFaker {

	private String nickName;

	private String username;

	private String password;

	public UserFaker(String nickName, String username, String password) {
		this.nickName = nickName;
		this.username = username;
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Users [nickName=" + nickName + ", username=" + username + ", password=" + password + "]";
	}

}
