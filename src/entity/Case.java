package entity;


/**
 * 案件实体类
 */
public class Case {
	private int id;//编号
	private String name;//名称
	private String time;//时间
	private String procedures;//法律程序

	public Case(int id, String name, String time, String procedures) {
		this.id = id;
		this.name = name;
		this.time = time;
		this.procedures = procedures;
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

	public String getProcedures() {
		return procedures;
	}

	public void setProcedures(String procedures) {
		this.procedures = procedures;
	}

}
