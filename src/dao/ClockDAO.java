package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Clock;

public class ClockDAO {
	public void add(Clock clock) {
        String sql = "insert into clock (`name`,`time`,remark,type,owner_id) values (?,?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, clock.getName());
            ps.setString(2, clock.getTime());
            ps.setString(3, clock.getRemark());
            ps.setString(4, clock.getType());
            ps.setInt(5, clock.getOwnerId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                clock.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	
	
    public int update(Clock clock) {
        String sql = "update clock set name = ?,time = ? ,remark = ? where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, clock.getName());
            ps.setString(2, clock.getTime());
            ps.setString(3, clock.getRemark());
            ps.setInt(3, clock.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int id) {
        String sql = "delete from clock where id = ?";
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


    public List<Clock> list(int start, int count) {
        String sql = "select * from clock order by id desc limit ?,?";
        List<Clock> clocks = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Clock clock = new Clock(rs.getString("id"),rs.getString("name"),
                		rs.getString("remark"),rs.getString("type"),rs.getInt("owner_id"));
                clocks.add(clock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clocks;
    }

    public List<Clock> list() {
        return list(0, Short.MAX_VALUE);
    }

    public int getTotal() {
        String sql = "select count(*) from clock";
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
