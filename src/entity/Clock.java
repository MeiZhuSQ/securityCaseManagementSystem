package entity;

public class Clock {
	private int id;
	private String name;
	private String time;
	private String remark;
	private String type;// 1笔录2法律手续
	private int ownerId;
	private int caseId;
	private String overFlag;//完成标记：1完成；0未完成

	public Clock(int id, String name, String time, String remark, String type, int ownerId, int caseId, String overFlag) {
		this.setId(id);
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
		this.setType(type);
		this.setOwnerId(ownerId);
		this.setCaseId(caseId);
		this.setOverFlag(overFlag);
	}

	public Clock(String name, String time, String remark, String type, int ownerId, int caseId, String overFlag) {
		this.setName(name);
		this.setTime(time);
		this.setRemark(remark);
		this.setType(type);
		this.setOwnerId(ownerId);
		this.setCaseId(caseId);
		this.setOverFlag(overFlag);
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

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	@Override
	public String toString() {
	    String status = "2";
	    String html = "";
	    if ("1".equals(status)) {
	        html = "<html><font color='gray'>"+time+"</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>未开始</font><br><span style='float:left'>"
	                +name+"</span></html>";
	    } else if ("2".equals(status)) {
	        html = "<html><font color='gray'>"+time+"</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font>已完成</font><br><span style='float:left'>"
                    +name+"</span></html>";
	    }
	    return html;
	}

	public String getOverFlag() {
		return overFlag;
	}

	public void setOverFlag(String overFlag) {
		this.overFlag = overFlag;
	}
	
}
