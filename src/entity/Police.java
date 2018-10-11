package entity;

public class Police extends Person {
	private String policeNumber;// 警号

	public Police() {

	}

	public Police(String name, String sex, String policeNumber) {
		this.setName(name);
		this.setSex(sex);
		this.setPoliceNumber(policeNumber);
	}

	public Police(int id, String name, String sex, String policeNumber) {
		this.setId(id);
		this.setName(name);
		this.setSex(sex);
		this.setPoliceNumber(policeNumber);
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
}
