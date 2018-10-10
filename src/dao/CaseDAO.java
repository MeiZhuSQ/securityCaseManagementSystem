package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.LegalCase;

public class CaseDAO {
	public void add(LegalCase legalCase) throws Exception {
		String sql = "insert into legal_case (`name`,`time`,remark) values (?,?,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, legalCase.getName());
			ps.setString(2, legalCase.getTime());
			ps.setString(3, legalCase.getRemark());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				legalCase.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int update(LegalCase legalCase) throws Exception {
		String sql = "update legal_case set name = ?,time = ? ,remark = ? where id = ?";
		int result = 0;
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, legalCase.getName());
			ps.setString(2, legalCase.getTime());
			ps.setString(3, legalCase.getRemark());
			ps.setInt(4, legalCase.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int delete(int id) throws Exception{
		String sql = "delete from legal_case where id = ?";
		int result = 0;
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public LegalCase selectById(int id) throws Exception {
		String sql = "select * from legal_case where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new LegalCase(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	public List<LegalCase> list(int start, int count){
		String sql = "select * from legal_case order by time desc limit ?,?";
		List<LegalCase> legalCases = new ArrayList<>();
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCase legalCase = new LegalCase(rs.getInt("id"), rs.getString("time"), rs.getString("name"),
						rs.getString("remark"));
				legalCases.add(legalCase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legalCases;
	}

	public List<LegalCase> list(){
		return list(0, Short.MAX_VALUE);
	}

	public int getTotal() {
		String sql = "select count(*) from legal_case";
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
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
