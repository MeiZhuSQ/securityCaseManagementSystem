package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;
import entity.Case;

public class CaseDAO {
	public void add(Case legalCase) {
        String sql = "insert into case (`name`,`time`,remark) values (?,?,?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, legalCase.getName());
            ps.setString(2, legalCase.getTime());
            ps.setString(3, legalCase.getProcedures());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                legalCase.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int update(Case legalCase) {
        String sql = "update case set name = ?,time = ? ,remark = ? where id = ?";
        int result = 0;
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, legalCase.getName());
            ps.setString(2, legalCase.getTime());
            ps.setString(3, legalCase.getProcedures());
            ps.setInt(3, legalCase.getId());
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int delete(int id) {
        String sql = "delete from legalCase where id = ?";
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


    public List<Case> list(int start, int count) {
        String sql = "select * from case order by id desc limit ?,?";
        List<Case> legalCases = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Case legalCase = new Case(rs.getInt("id"),rs.getString("time"),rs.getString("name"),
                		rs.getString("procedures"));
                legalCases.add(legalCase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return legalCases;
    }

    public List<Case> list() {
        return list(0, Short.MAX_VALUE);
    }

    public int getTotal() {
        String sql = "select count(*) from legalCase";
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
