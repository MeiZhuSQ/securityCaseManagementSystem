package entity;


/**
 * 案件实体类
 */
public class LegalCase {
	private int id;//编号
	private String name;//名称
	private String time;//时间
	private String remark;//备注

	public LegalCase(int id, String name, String time,String remark) {
		this.setId(id);
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
	}

	public LegalCase(String name, String time,String remark) {
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
		
	}

	public LegalCase() {
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


}
