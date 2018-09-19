package entity;

public class OtherPerson extends Person {
	private int noteId;
	private String type;
	private String idCard;

	public OtherPerson(int id, String name, String sex, String type, String idCard,int noteId) {
		this.setId(id);
		this.setName(name);
		this.setSex(sex);
		this.setType(type);
		this.setIdCard(idCard);
		this.setNoteId(noteId);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
}
