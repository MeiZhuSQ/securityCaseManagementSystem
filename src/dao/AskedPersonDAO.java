package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import entity.AskedPerson;

public class AskedPersonDAO {
	public int add(AskedPerson askedPerson) throws Exception {
		String sql = "insert into asked_person (`name`,`sex`,type,adult_flag,id_card,disabled_flag) values (?,?,?,?,?,?)";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, askedPerson.getName());
			ps.setString(2, askedPerson.getSex());
			ps.setString(3, askedPerson.getType());
			ps.setString(4, askedPerson.getAdultFlag());
			ps.setString(5, askedPerson.getIdCard());
			ps.setString(6, askedPerson.getDisabledFlag());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
				askedPerson.setId(id);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int update(AskedPerson askedPerson) {
		String sql = "update asked_person set name = ?,sex = ? ,type = ?,adult_flag = ?,id_card = ?,disabled_flag = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, askedPerson.getName());
			ps.setString(2, askedPerson.getSex());
			ps.setString(3, askedPerson.getType());
			ps.setString(4, askedPerson.getAdultFlag());
			ps.setString(5, askedPerson.getIdCard());
			ps.setString(6, askedPerson.getDisabledFlag());
			ps.setInt(7, askedPerson.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from asked_person where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<AskedPerson> list(int start, int count) {
		String sql = "select * from asked_person order by id desc limit ?,?";
		List<AskedPerson> askedPersons = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				AskedPerson askedPerson = new AskedPerson(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("type"), rs.getString("adult_flag"), rs.getString("id_card"),
						rs.getString("disabled_flag"));
				askedPersons.add(askedPerson);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return askedPersons;
	}

	public List<AskedPerson> list() {
		return list(0, Short.MAX_VALUE);
	}

	public AskedPerson selectById(int id) {
		String sql = "select * from asked_person where id = ?";
		AskedPerson askedPerson = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				askedPerson = new AskedPerson(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("type"), rs.getString("adult_flag"), rs.getString("id_card"),
						rs.getString("disabled_flag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return askedPerson;
	}

	public AskedPerson selectByIdCard(String idCard) {
		String sql = "select * from asked_person where id_card = ?";
		AskedPerson askedPerson = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, idCard);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				askedPerson = new AskedPerson(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("type"), rs.getString("adult_flag"), rs.getString("id_card"),
						rs.getString("disabled_flag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return askedPerson;
	}

	public AskedPerson selectByIdCardInCase(String idCard, int caseId) {
		String sql = "select * from note LEFT JOIN asked_person on note.asked_person_id = asked_person.id"
				+ " where asked_person.id_card = ? and note.case_id = ?";
		AskedPerson askedPerson = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, idCard);
			ps.setInt(2, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				askedPerson = new AskedPerson(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("type"), rs.getString("adult_flag"), rs.getString("id_card"),
						rs.getString("disabled_flag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return askedPerson;
	}

	public int getTotal() {
		String sql = "select count(*) from askedPerson";
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

}
