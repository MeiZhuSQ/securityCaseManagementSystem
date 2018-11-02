package entity;

public class User {
	private int id;
	private String userName;

	public User(String userName) {
		this.setUserName(userName);
	}

	public User(int id, String userName) {
		this.setId(id);
		this.setUserName(userName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return userName;
	}
}
