package entity;

public abstract class Person {
	private int id;
	private String name;// 姓名
	private String sex;// 性别：1男；0女

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name; 
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
