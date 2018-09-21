package entity;


public class Procedures {

	private int id;
	private int caseId;// 案件编号
	private String name;
	private String time;
	private String remark;

	public Procedures(int id, String name, String time, String remark) {
		this.setId(id);
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
	}

	public Procedures() {
		// TODO Auto-generated constructor stub
	}

	public int getId() { 
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}
}
