package entity;


public class Police extends Person {
	private int noteId;// 笔录id

	public Police() {

	}

	public Police(String name, String sex, int noteId) {
		this.setName(name);
		this.setSex(sex);
		this.setNoteId(noteId);
	}

	public Police(int id, String name, String sex, int noteId) {
		this.setId(id);
		this.setName(name);
		this.setSex(sex);
		this.setNoteId(noteId);
	}


	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
}
