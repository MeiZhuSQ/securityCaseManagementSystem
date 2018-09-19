package entity;

public class AskedPerson extends Person{
	private int noteId;
	private String type;
	private String idCard;
	private String adultFlag;
	private String disabledFlag;

	public AskedPerson(int id, int noteId, String name, String sex, String type, String adultFlag, String idCard,String disabledFlag) {
		this.setId(noteId);
		this.setNoteId(noteId);
		this.setName(name);
		this.setSex(sex);
		this.setType(type);
		this.setAdultFlag(adultFlag);
		this.setIdCard(idCard);
		this.setDisabledFlag(disabledFlag);;
	}

	public AskedPerson() {
		// TODO Auto-generated constructor stub
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getAdultFlag() {
		return adultFlag;
	}

	public void setAdultFlag(String adultFlag) {
		this.adultFlag = adultFlag;
	}

	public String getDisabledFlag() {
		return disabledFlag;
	}

	public void setDisabledFlag(String disabledFlag) {
		this.disabledFlag = disabledFlag;
	}
}
