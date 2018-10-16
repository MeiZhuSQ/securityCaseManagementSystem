package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import entity.Police;

public class PoliceDAO {
	public void add(Police police) throws Exception {
		String sql = "insert into police (`name`,`sex`,police_number) values (?,?,?)";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, police.getName());
			ps.setString(2, police.getSex());
			ps.setString(3, police.getPoliceNumber());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				police.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int update(Police police) {
		String sql = "update police set name = ?,sex = ? ,police_number = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, police.getName());
			ps.setString(2, police.getSex());
			ps.setString(3, police.getPoliceNumber());
			ps.setInt(3, police.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from police where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Police> list(int start, int count) {
		String sql = "select * from police order by id desc limit ?,?";
		List<Police> polices = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Police police = new Police(rs.getInt("id"), rs.getString("id"), rs.getString("name"),
						rs.getString("police_number"));
				polices.add(police);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return polices;
	}

	public List<Police> list() {
		return list(0, Short.MAX_VALUE);
	}

	public Police selectByPoliceNumber(String policeNumber) {
		String sql = "select * from police where police_number = ?";
		Police police = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, policeNumber);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				police = new Police(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("police_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return police;
	}

	public List<Police> selectForNote(String policeList) {
		String[] policeNumbers = policeList.split(",");
		String sql = "select * from police where police_number in (" ;
		for (String string : policeNumbers) {
			sql += "'" + string + "'" + ",";
		}
		sql = sql.substring(0, sql.length()-1);
		sql += ")";
		List<Police> polices = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Police police = new Police(rs.getInt("id"), rs.getString("name"), rs.getString("sex"),
						rs.getString("police_number"));
				polices.add(police);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return polices;
	}

	public int getTotal() {
		String sql = "select count(*) from police";
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
