package service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import service.base.BaseService;
import dao.UserDAO;
import dto.ResultDTO;
import entity.User;

public class UserService extends BaseService {

	private UserDAO userDAO = new UserDAO();

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ResultDTO userLogin(String username, String password) {
		if (StringUtils.isBlank(username) || StringUtils.trimToEmpty(username).length() != 6) {
			return requestFail("请输入六位警号");
		}
		if (StringUtils.isBlank(password) || StringUtils.trimToEmpty(password).length() != 6) {
			return requestFail("请输入正确的六位密码");
		}
		if (!checkPassword(username, password)) {
			return requestFail("密码错误");
		}
		List<User> users;
		boolean usernameExistFlag = false;
		try {
			users = userDAO.list();
			for (User user : users) {
				if (user.getUserName().equals(username)) {
					usernameExistFlag = true;
				}
			}
			if (!usernameExistFlag) {
				userDAO.add(new User(username));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return requestFail();
		}
		return requestSuccess();
	}

	/**
	 * 密码校验
	 * 
	 * @param string
	 * @return
	 */
	private boolean checkPassword(String username, String password) {
		if ((Integer.valueOf(username) * 71846 - 111111) % 1000000 == Integer.valueOf(password)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		UserService UserService = new UserService();
		User user = new User("123456", "774073");
		String username = user.getUserName();
		String password = user.getPassword();
		ResultDTO resultDTO = UserService.userLogin(username, password);
		System.out.println(resultDTO);
	}
}
