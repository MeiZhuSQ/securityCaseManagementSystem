package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.JDBCUtil;

public class UserDAO {
	 public void add(User user) {
	        String sql = "insert into user (`user_name`,`password`) values (?,?)";
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setString(1, user.getUserName());
	            ps.setString(2, user.getPassword());
	            ps.execute();
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                int id = rs.getInt(1);
	                user.setId(id);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public int update(User user) {
	        String sql = "update user set user_name = ?,password = ? where id = ?";
	        int result = 0;
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setString(1, user.getUserName());
	            ps.setString(2, user.getPassword());
	            ps.setInt(3, user.getId());
	            result = ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }

	    public int delete(int id) {
	        String sql = "delete from user where id = ?";
	        int result = 0;
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            result = ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }

	    public User get(int id) {
	        User user = null;
	        String sql = "select * from user where id = ?";
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            ResultSet result = ps.executeQuery();
	            if (result.next()) {
	                user = new User(result.getInt("id"),result.getString("user_name"),
	                        result.getString("password"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return user;
	    }

	    public User getByUserName(String userName) {
	        User user = null;
	        String sql = "select * from user where user_name = ?";
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setString(1, userName);
	            ResultSet result = ps.executeQuery();
	            if (result.next()) {
	            	user = new User(result.getInt("id"),result.getString("user_name"),
	                        result.getString("password"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return user;
	    }

	    public List<User> list(int start, int count) throws SQLException {
	        String sql = "select * from user order by id desc limit ?,?";
	        List<User> users = new ArrayList<>();
	        try (Connection c = JDBCUtil.getConnection();
	             PreparedStatement ps = c.prepareStatement(sql)) {
	            ps.setInt(1, start);
	            ps.setInt(2, count);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                User user = new User(rs.getInt("id"),rs.getString("user_name"),
	                		rs.getString("password"));
	                users.add(user);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        }
	        return users;
	    }

	    public List<User> list() throws SQLException {
	        return list(0, Short.MAX_VALUE);
	    }

	    public int getTotal() {
	        String sql = "select count(*) from user";
	        try (Connection c = JDBCUtil.getConnection();
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
	    
	    public static void main(String[] args) throws SQLException {
			UserDAO userDao = new UserDAO();
			//userDao.add(new User( "username1", "pwd"));
			
			User user = userDao.get(1);
			System.out.println(user);
			
			user.setPassword("11111111111");
			userDao.update(user);
			
			User user2 = userDao.getByUserName("1");
			System.out.println(user2);
			
			//userDao.delete(user.getId());
			List<User> users = userDao.list();
			for (User user3 : users) {
				System.out.println(user3);
			}
	}

}
