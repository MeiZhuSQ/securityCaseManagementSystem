package entity;

public class Clock {
	private int id;
	private String name;
	private String time;
	private String remark;
	private String type;//1笔录2法律手续
	private int ownerId;


	public Clock(int id,String name, String time, String remark, String type, int ownerId) {
		this.setId(id);
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);;
		this.setType(type);
		this.setOwnerId(ownerId);
	}

	public Clock(String name, String time, String remark, String type, int ownerId) {
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);;
		this.setType(type);
		this.setOwnerId(ownerId);
	}

	public Clock() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

}
