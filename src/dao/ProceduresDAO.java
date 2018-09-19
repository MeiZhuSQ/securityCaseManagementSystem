package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Procedures;

public class ProceduresDAO {
	public void add(Procedures procedures) throws Exception{
        String sql = "insert into procedures (`name`,`time`,remark,case_id) values (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, procedures.getName());
            ps.setString(2, procedures.getTime());
            ps.setString(3, procedures.getRemark());
            ps.setInt(4, procedures.getCaseId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                procedures.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int update(Procedures procedures) {
        String sql = "update procedures set name = ?,time = ? ,remark = ? ,case_id = ? where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, procedures.getName());
            ps.setString(2, procedures.getTime());
            ps.setString(3, procedures.getRemark());
            ps.setInt(4, procedures.getCaseId());
            ps.setInt(5, procedures.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int id) {
        String sql = "delete from procedures where id = ?";
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


    public List<Procedures> list(int start, int count) {
        String sql = "select * from procedures order by id desc limit ?,?";
        List<Procedures> proceduress = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Procedures procedures = new Procedures(rs.getInt("id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
                proceduress.add(procedures);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proceduress;
    }

    public List<Procedures> list() {
        return list(0, Short.MAX_VALUE);
    }
    
    public List<Procedures> selectByCaseId(int caseId) {
        String sql = "select * from procedures where case_id = ?";
        List<Procedures> proceduress = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, caseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Procedures procedures = new Procedures(rs.getInt("id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
                proceduress.add(procedures);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proceduress;
    }

    public int getTotal() {
        String sql = "select count(*) from procedures";
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

	public Procedures selectById(int id) {
		String sql = "select * from procedures where case_id = ?";
        Procedures procedures = new Procedures();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                procedures = new Procedures(rs.getInt("id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return procedures;
	}
}
