package entity;


public class Note {
	private int id;// 编号
	private int caseId;// 案件编号
	private String name;// 名称
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	
	

	public Note(int id, int caseId, String name, String startTime, String endTime) {
		this.id = id;
		this.caseId = caseId;
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
