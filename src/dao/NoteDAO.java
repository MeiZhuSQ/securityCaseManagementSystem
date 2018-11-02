package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import entity.Note;

public class NoteDAO {
	public int add(Note note) throws Exception {
		ResultSet rs = null;
		PreparedStatement sta = null;
		Connection con = null;
		String sql = "insert into note (`case_id`,`name`,`start_time`,`end_time`,remark,place,file_name,asked_person_idcard) values (?,?,?,?,?,?,?,?)";
		try {
			con = JDBCUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, note.getCaseId());
			ps.setString(2, note.getName());
			ps.setString(3, note.getStartTime());
			ps.setString(4, note.getEndTime());
			ps.setString(5, note.getRemark());
			ps.setString(6, note.getPlace());
			ps.setString(7, note.getFileName());
			ps.setString(8, note.getAskedPersonIdcard());
			ps.execute();
			rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
				note.setId(id);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			JDBCUtil.free(rs, sta, con);
		}
	}

	public int update(Note note) {
		String sql = "update note set case_id = ? ,name = ?,start_time = ? ,`end_time` = ?,remark = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, note.getCaseId());
			ps.setString(2, note.getName());
			ps.setString(3, note.getStartTime());
			ps.setString(4, note.getEndTime());
			ps.setString(5, note.getRemark());
			ps.setInt(6, note.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from note where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Note> list(int start, int count) {
		String sql = "select * from note order by id desc limit ?,?";
		List<Note> notes = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Note note = new Note(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("asked_person_idcard"));
				notes.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public List<Note> list() {
		return list(0, Short.MAX_VALUE);
	}

	public int getTotal() {
		String sql = "select count(*) from note";
		try (Connection c = JDBCUtil.getConnection(); Statement s = c.createStatement()) {
			ResultSet rs = s.executeQuery(sql);
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Note> selectNoteByTimeAndPlaceAndPolic(String policeNumber, String startTime, String endTime,
			String place) {
		String sql = "select * from note WHERE start_time < ? and  end_time > ? and  (place = ? or asked_person_idcard like ?)";
		List<Note> notes = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, endTime);
			ps.setString(2, startTime);
			ps.setString(3, place);
			ps.setString(4, "%" + policeNumber + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Note note = new Note(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("asked_person_idcard"));
				notes.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public List<Note> selectConflictingNotesInSameCase(Note note) {
		int caseId = note.getCaseId();
		String askedPersonIdcard = note.getAskedPersonIdcard();
		String startTime = note.getStartTime();
		String endTime = note.getEndTime();
		String place = note.getPlace();
		String sql = "select * from note WHERE start_time < ? and  end_time > ? and  (place = ? or asked_person_idcard = ?) and case_id = ?";
		List<Note> notes = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, endTime);
			ps.setString(2, startTime);
			ps.setString(3, place);
			ps.setString(4, askedPersonIdcard);
			ps.setInt(5, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Note conflictingNote = new Note(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("asked_person_idcardcard"));
				notes.add(conflictingNote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public List<Note> selectByCaseId(int caseId) {
		String sql = "select * from note where case_id = ?";
		List<Note> notes = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Note note = new Note(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("asked_person_idcard"));
				notes.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public Note selectById(int id) {
		String sql = "select * from note where id = ?";
		Note note = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				note = new Note(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("asked_person_idcard"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return note;
	}

	public static void main(String[] args) {
		NoteDAO noteDAO = new NoteDAO();
		List<Note> notes = noteDAO.selectByCaseId(1);
		System.out.println(notes);

	}
}
