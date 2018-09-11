package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Note;

public class NoteDAO {
	public void add(Note note) {
        String sql = "insert into note (`case_id`,`name`,`start_time`,`end_time`) values (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setInt(1, note.getCaseId());
            ps.setString(2, note.getName());
            ps.setString(3, note.getStartTime());
            ps.setString(4, note.getEndTime());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                note.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int update(Note note) {
        String sql = "update note set case_id = ? ,name = ?,start_time = ? ,`end_time` = ? where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
        	ps.setInt(1, note.getCaseId());
            ps.setString(2, note.getName());
            ps.setString(3, note.getStartTime());
            ps.setString(4, note.getEndTime());
            ps.setInt(5, note.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int id) {
        String sql = "delete from note where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
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
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Note note = new Note(rs.getInt("id"),rs.getInt("case_id"),rs.getString("start_time"),rs.getString("name"),
                		rs.getString("procedures"));
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
        try (Connection c = DBUtil.getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
