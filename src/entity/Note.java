package entity;

public class Note {
	private int id;// 编号
	private int caseId;// 案件编号
	private String name;// 名称
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String remark;
	private String place;
	private String fileName;
	private String askedPersonIdcard;

	public Note(int caseId, String name, String startTime, String endTime, String remark, String place,
			String fileName, String askedPersonIdcard) {
		this.setCaseId(caseId);
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setRemark(remark);
		this.setPlace(place);
		this.setFileName(fileName);
		this.setAskedPersonIdcard(askedPersonIdcard);
	}

	public Note(int id, int caseId, String name, String startTime, String endTime, String remark, String place,
			String fileName, String askedPersonIdcard) {
		this.setId(id);
		this.setCaseId(caseId);
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setRemark(remark);
		this.setPlace(place);
		this.setFileName(fileName);
		this.setAskedPersonIdcard(askedPersonIdcard);
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getAskedPersonIdcard() {
		return askedPersonIdcard;
	}

	public void setAskedPersonIdcard(String askedPersonIdcard) {
		this.askedPersonIdcard = askedPersonIdcard;
	}
}
