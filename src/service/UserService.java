package service;

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
		if (!checkPassword(username, password)) {
			return requestFail("请输入正确的六位数字密码");
		}
		try {
			User user = userDAO.getByUserName(username);
			if (null == user) {
				userDAO.add(new User(username));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return requestFail("请输入正确的六位数字密码");
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
		if ((Long.valueOf(username) * 71846 - 111111) % 1000000 == Long.valueOf(password)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println((Long.valueOf("064255") * 71846 - 111111) % 1000000);
		boolean checkPassword = new UserService().checkPassword("064255","353619");
		System.out.println(checkPassword);
	}
}
