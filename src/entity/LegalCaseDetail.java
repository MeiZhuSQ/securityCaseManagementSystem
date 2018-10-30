package entity;

import org.apache.commons.lang.StringUtils;

/**
 * 案件细则实体类
 */
public class LegalCaseDetail {
	private int id;// 编号
	private int caseId;// 案件编号（0代表不关联案件）
	private String type;// 1笔录2法律手续3闹钟
	private String name;// 名称
	private String startTime;// 开始时间
	private String endTime;// 结束时间
	private String remark;// 备注
	private String place;// 笔录地点
	private String fileName;// 笔录文件名称

	public LegalCaseDetail(int caseId, String type, String name, String startTime, String endTime, String remark,
			String place, String fileName) {
		this.setCaseId(caseId);
		this.setType(type);
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setRemark(remark);
		this.setPlace(place);
		this.setFileName(fileName);
	}

	public LegalCaseDetail(int id, int caseId, String type, String name, String startTime, String endTime,
			String remark, String place, String fileName) {
		this.setId(id);
		this.setCaseId(caseId);
		this.setType(type);
		this.setName(name);
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setRemark(remark);
		this.setPlace(place);
		this.setFileName(fileName);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		return StringUtils.trimToEmpty(endTime);
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRemark() {
		return StringUtils.trimToEmpty(remark);
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFileName() {
		return StringUtils.trimToEmpty(fileName);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPlace() {
		return StringUtils.trimToEmpty(place);
	}

	public void setPlace(String place) {
		this.place = place;
	}
}
