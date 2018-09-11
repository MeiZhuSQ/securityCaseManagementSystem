package entity;

public class Police extends Person {
	private String policeNumber;//警号

	public Police(int id, String name, String sex, String policeNumber) {
		super.setId(id);
		super.setName(name);
		super.setSex(sex);
		this.setPoliceNumber(policeNumber);
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}
}
