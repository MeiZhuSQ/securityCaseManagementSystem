package vo;

public class CaseItemVO {
	private int id;
	private String name;
	private String time;
	private String remark;
	private String type;

	public CaseItemVO(int id, String name, String time, String remark, String type) {
		this.setId(id);
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
		this.setType(type);
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

}
