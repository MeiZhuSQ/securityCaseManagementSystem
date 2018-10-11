package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import util.DateUtil;
import entity.Procedure;

public class ProcedureDAO {
	public void add(Procedure Procedure) throws Exception{
        String sql = "insert into procedure (`name`,`time`,remark,case_id) values (?,?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, Procedure.getName());
            ps.setString(2, Procedure.getTime());
            ps.setString(3, Procedure.getRemark());
            ps.setInt(4, Procedure.getCaseId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                Procedure.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public int update(Procedure Procedure) {
        String sql = "update procedure set name = ?,time = ? ,remark = ? ,case_id = ? where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, Procedure.getName());
            ps.setString(2, Procedure.getTime());
            ps.setString(3, Procedure.getRemark());
            ps.setInt(4, Procedure.getCaseId());
            ps.setInt(5, Procedure.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int id) {
        String sql = "delete from procedure where id = ?";
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


    public List<Procedure> list(int start, int count) {
        String sql = "select * from procedure order by id desc limit ?,?";
        List<Procedure> Procedures = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Procedure Procedure = new Procedure(rs.getInt("id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
                Procedures.add(Procedure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Procedures;
    }

    public List<Procedure> list() {
        return list(0, Short.MAX_VALUE);
    }
    
    public List<Procedure> selectByCaseId(int caseId) {
        String sql = "select * from procedure where case_id = ?";
        List<Procedure> Procedures = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, caseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Procedure Procedure = new Procedure(rs.getInt("id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
                Procedures.add(Procedure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Procedures;
    }

    public int getTotal() {
        String sql = "select count(*) from procedure";
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

	public Procedure selectById(int id) {
		String sql = "select * from procedure where id = ?";
        Procedure Procedure = new Procedure();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Procedure = new Procedure(rs.getInt("id"),rs.getInt("case_id"),rs.getString("name"),rs.getString("time"),
                		rs.getString("remark"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Procedure;
	}
}
