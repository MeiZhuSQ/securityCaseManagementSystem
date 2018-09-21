package entity;


/**
 * 案件实体类
 */
public class LegalCase {
	private int id;//编号
	private String name;//名称
	private String time;//时间

	public LegalCase(int id, String name, String time) {
		this.id = id;
		this.name = name;
		this.time = time;
	}

	public LegalCase(String name, String time) {
		this.name = name;
		this.time = time;
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


}
