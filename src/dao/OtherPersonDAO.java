package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import entity.OtherPerson;

public class OtherPersonDAO {
	public void add(OtherPerson otherPerson) throws Exception {
		String sql = "insert into other_person (`name`,`sex`,type,id_card,note_id) values (?,?,?,?,?)";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, otherPerson.getName());
			ps.setString(2, otherPerson.getSex());
			ps.setString(3, otherPerson.getType());
			ps.setString(4, otherPerson.getIdCard());
			ps.setInt(5, otherPerson.getNoteId());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				otherPerson.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int update(OtherPerson otherPerson) {
		String sql = "update other_person set name = ?,sex = ? ,type = ?,id_card = ? ,note_id = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, otherPerson.getName());
			ps.setString(2, otherPerson.getSex());
			ps.setString(3, otherPerson.getType());
			ps.setString(4, otherPerson.getIdCard());
			ps.setInt(5, otherPerson.getNoteId());
			ps.setInt(6, otherPerson.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from other_person where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<OtherPerson> list(int start, int count) {
		String sql = "select * from other_person order by id desc limit ?,?";
		List<OtherPerson> otherPersons = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OtherPerson otherPerson = new OtherPerson(rs.getInt("id"), rs.getInt("note_id"), rs.getString("name"),
						rs.getString("sex"), rs.getString("type"), rs.getString("id_card"));
				otherPersons.add(otherPerson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return otherPersons;
	}

	public List<OtherPerson> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<OtherPerson> selectByNoteId(int noteId) {
		String sql = "select * from other_person where note_id = ?";
		List<OtherPerson> otherPersons = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, noteId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				OtherPerson otherPerson = new OtherPerson(rs.getInt("id"), rs.getInt("note_id"), rs.getString("name"),
						rs.getString("sex"), rs.getString("type"), rs.getString("id_card"));
				otherPersons.add(otherPerson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return otherPersons;
	}

	public int getTotal() {
		String sql = "select count(*) from otherPerson";
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

	public OtherPerson selectById(int id) {
		String sql = "select * from other_person where id = ?";
		OtherPerson otherPerson = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				otherPerson = new OtherPerson(rs.getInt("id"), rs.getInt("note_id"), rs.getString("name"),
						rs.getString("sex"), rs.getString("type"), rs.getString("id_card"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return otherPerson;
	}

	public OtherPerson selectByIdCard(String idCard){
		String sql = "select * from other_person where id_card = ?";
		OtherPerson otherPerson = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, idCard);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				otherPerson = new OtherPerson(rs.getInt("id"), rs.getInt("note_id"), rs.getString("name"),
						rs.getString("sex"), rs.getString("type"), rs.getString("id_card"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return otherPerson;
	}
}
