package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import util.DateUtil;
import util.JDBCUtil;
import entity.Clock;

public class ClockDAO {
	public void add(Clock clock) throws Exception {
		String sql = "insert into clock (`name`,`time`,remark,type,owner_id,case_id,over_flag) values (?,?,?,?,?,?,?)";
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, clock.getName());
			ps.setString(2, clock.getTime());
			ps.setString(3, clock.getRemark());
			ps.setString(4, clock.getType());
			ps.setInt(5, clock.getOwnerId());
			ps.setInt(6, clock.getCaseId());
			ps.setString(7, "0");
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				clock.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int update(Clock clock) {
		String sql = "update clock set name = ?,time = ? ,remark = ?,type = ?, owner_id = ? ,case_id = ?,over_flag = ? where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, clock.getName());
			ps.setString(2, clock.getTime());
			ps.setString(3, clock.getRemark());
			ps.setString(4, clock.getType());
			ps.setInt(5, clock.getOwnerId());
			ps.setInt(6, clock.getCaseId());
			ps.setString(7, clock.getOverFlag());
			ps.setInt(8, clock.getId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int id) {
		String sql = "delete from clock where id = ?";
		int result = 0;
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Clock> getClocksInThreeDaysAndLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String startTime = DateUtil.formatDateTime(DateUtil.getZeroTimeOfDay(calendar.getTime()));
		calendar.add(Calendar.DATE, 4);
		String endTime = DateUtil.formatDateTime(DateUtil.getEndTimeOfDay(calendar.getTime()));
		String sql = "select * from clock where ? < time and time < ?  order by time desc";
		List<Clock> clocks = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, startTime);
			ps.setString(2, endTime);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Clock clock = new Clock(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"), rs.getInt("owner_id"), rs.getInt("case_id"), rs.getString("over_flag"));
				clocks.add(clock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clocks;
	}


	public List<Clock> selectByTypeAndOwnerId(String type, int ownerId) {
		String sql = "select * from clock where type = ? and owner_id = ?";
		List<Clock> clocks = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setString(1, type);
			ps.setInt(2, ownerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Clock clock = new Clock(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"), rs.getInt("owner_id"), rs.getInt("case_id"), rs.getString("over_flag"));
				clocks.add(clock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clocks;
	}
	
	public List<Clock> selectBycaseId(int caseId) {
		String sql = "select * from clock where owner_id = ?";
		List<Clock> clocks = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Clock clock = new Clock(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"), rs.getInt("owner_id"), rs.getInt("case_id"), rs.getString("over_flag"));
				clocks.add(clock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clocks;
	}

	public Clock selectById(int id) {
		String sql = "select * from clock where id = ?";
		Clock clock = new Clock();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				clock = new Clock(rs.getInt("id"), rs.getString("name"), rs.getString("time"), rs.getString("remark"),
						rs.getString("type"), rs.getInt("owner_id"), rs.getInt("case_id"), rs.getString("over_flag"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clock;
	}

	public int getTotal() {
		String sql = "select count(*) from clock";
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

	public List<Clock> selectClocksByCaseId(int caseId) {
		String sql = "select * from clock where case_id = ? ";
		List<Clock> clocks = new ArrayList<>();
		try (Connection c = JDBCUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
			ps.setInt(1, caseId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Clock clock = new Clock(rs.getInt("id"), rs.getString("name"), rs.getString("time"),
						rs.getString("remark"), rs.getString("type"), rs.getInt("owner_id"), rs.getInt("case_id"), rs.getString("over_flag"));
				clocks.add(clock);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clocks;
	}

}
