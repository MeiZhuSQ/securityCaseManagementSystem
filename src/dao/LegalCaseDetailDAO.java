package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.JDBCUtil;
import entity.LegalCaseDetail;

public class LegalCaseDetailDAO {
	public int add(LegalCaseDetail legal_case_detail) throws Exception {
	    ResultSet rs = null;
	    PreparedStatement sta = null;
	    Connection con = null;
		String sql = "insert into legal_case_detail (`case_id`,`name`,`start_time`,`end_time`,remark,place,file_name,type) values (?,?,?,?,?,?,?,?)";
        try {
		    con = JDBCUtil.getConnection();
		    PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, legal_case_detail.getCaseId());
			ps.setString(2, legal_case_detail.getName());
			ps.setString(3, legal_case_detail.getStartTime());
			ps.setString(4, legal_case_detail.getEndTime());
			ps.setString(5, legal_case_detail.getRemark());
			ps.setString(6, legal_case_detail.getPlace());
			ps.setString(7, legal_case_detail.getFileName());
			ps.setString(8, legal_case_detail.getType());
			ps.execute();
			rs = ps.getGeneratedKeys();
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
				legal_case_detail.setId(id);
			}
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
            JDBCUtil.free(rs, sta, con);
        }
	}

	public int update(LegalCaseDetail legal_case_detail) {
		String sql = "update legal_case_detail set case_id = ? ,name = ?,start_time = ? ,`end_time` = ?,remark = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, legal_case_detail.getCaseId());
			ps.setString(2, legal_case_detail.getName());
			ps.setString(3, legal_case_detail.getStartTime());
			ps.setString(4, legal_case_detail.getEndTime());
			ps.setString(5, legal_case_detail.getRemark());
			ps.setInt(6, legal_case_detail.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from legal_case_detail where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<LegalCaseDetail> list(int start, int count) {
		String sql = "select * from legal_case_detail order by id desc limit ?,?";
		List<LegalCaseDetail> legal_case_details = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCaseDetail legal_case_detail = new LegalCaseDetail(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("type"));
				legal_case_details.add(legal_case_detail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legal_case_details;
	}

	public List<LegalCaseDetail> list() {
		return list(0, Short.MAX_VALUE);
	}

	public int getTotal() {
		String sql = "select count(*) from legal_case_detail";
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

	public List<LegalCaseDetail> selectLegalCaseDetailByTimeAndPlaceAndPolic(String policeNumber, String startTime, String endTime,
			String place) {
		String sql = "select * from legal_case_detail WHERE start_time < ? and  end_time > ? and  (place = ? or type like ?)";
		List<LegalCaseDetail> legal_case_details = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, endTime);
			ps.setString(2, startTime);
			ps.setString(3, place);
			ps.setString(4, "%" + policeNumber + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCaseDetail legal_case_detail = new LegalCaseDetail(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("type"));
				legal_case_details.add(legal_case_detail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legal_case_details;
	}

	public List<LegalCaseDetail> selectByCaseId(int caseId) {
		String sql = "select * from legal_case_detail where case_id = ?";
		List<LegalCaseDetail> legal_case_details = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				LegalCaseDetail legal_case_detail = new LegalCaseDetail(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("type"));
				legal_case_details.add(legal_case_detail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legal_case_details;
	}

	public LegalCaseDetail selectById(int id) {
		String sql = "select * from legal_case_detail where id = ?";
		LegalCaseDetail legal_case_detail = null;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				legal_case_detail = new LegalCaseDetail(rs.getInt("id"), rs.getInt("case_id"), rs.getString("name"),
						rs.getString("start_time"), rs.getString("end_time"), rs.getString("remark"),
						rs.getString("place"), rs.getString("file_name"), rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return legal_case_detail;
	}

	public static void main(String[] args) {
		LegalCaseDetailDAO legal_case_detailDAO = new LegalCaseDetailDAO();
		List<LegalCaseDetail> legal_case_details = legal_case_detailDAO.selectByCaseId(1);
		System.out.println(legal_case_details);

	}
}
