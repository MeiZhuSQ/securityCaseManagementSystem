package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import vo.CaseItemVO;
import entity.LegalCase;

public class CaseDAO {
	public void add(LegalCase legalCase) throws Exception {
		String sql = "insert into legal_case (`name`,`time`,remark) values (?,?,?)";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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

	public int delete(int id) throws Exception {
		String sql = "delete from legal_case where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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

	public LegalCase selectByName(String name) throws Exception {
		String sql = "select * from legal_case where name = ?";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, name);
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

	public List<LegalCase> list(int start, int count) {
		String sql = "select * from legal_case order by time desc limit ?,?";
		List<LegalCase> legalCases = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCase legalCase = new LegalCase(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"));
				legalCases.add(legalCase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legalCases;
	}

	public List<LegalCase> list() {
		return list(0, Short.MAX_VALUE);
	}
	
	public List<LegalCase> listCaseByKeyWord(String keyWord) {
		String sql = "select * from legal_case where name like ?  order by time desc ";
		List<LegalCase> legalCases = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, "%" + keyWord + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCase legalCase = new LegalCase(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"));
				legalCases.add(legalCase);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legalCases;
	}

	public int getTotal() {
		String sql = "select count(*) from legal_case";
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

	public List<CaseItemVO> getCaseItems(int caseId) {
		String sql = "SELECT id,name,start_time as time,remark ,'1' as type FROM note where case_id = ?" + "UNION "
				+ "SELECT id,name,time,remark ,'2' as type FROM procedure where case_id = ?" + "UNION "
				+ "SELECT id,name,time,remark ,'3' as type FROM clock where case_id = ?" + "ORDER BY time desc";
		List<CaseItemVO> caseItems = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ps.setInt(2, caseId);
			ps.setInt(3, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CaseItemVO caseItemVO = new CaseItemVO(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"));
				caseItems.add(caseItemVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caseItems;

	}

	public List<CaseItemVO> getCaseItemsByKeyWord(int caseId, String keyWord) {
		String sql = "select id,name,time,remark,type from "
				+ "(SELECT id,name,start_time as time,remark ,'1' as type FROM note where case_id = ?" + "UNION "
				+ "SELECT id,name,time,remark ,'2' as type FROM procedure where case_id = ?" + "UNION "
				+ "SELECT id,name,time,remark ,'3' as type FROM clock where case_id = ?) a "
				+ "WHERE a.name LIKE ?  ORDER BY time desc";
		List<CaseItemVO> caseItems = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ps.setInt(2, caseId);
			ps.setInt(3, caseId);
			ps.setString(4, "%" + keyWord + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CaseItemVO caseItemVO = new CaseItemVO(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"));
				caseItems.add(caseItemVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return caseItems;
	}

	public static void main(String[] args) {
		CaseDAO caseDAO = new CaseDAO();
		List<CaseItemVO> caseItemVOs = caseDAO.getCaseItemsByTimeAndKeyWord(1, "笔录", "2000-01-01 00:00:00",
				"2020-01-01 00:00:00");
		for (CaseItemVO caseItemVO : caseItemVOs) {
			System.out.println(caseItemVO.getName());
		}
	}

}
