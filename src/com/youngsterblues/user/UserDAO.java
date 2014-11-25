package com.youngsterblues.user;

import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class UserDAO {

	DAO dao;

	public UserDAO() {
		dao = new DAO();
	}

	public User getUser(String id) {
		String sql = "select * from user where id=?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		ArrayList<Object> userString = dao.selectQuery(sql, parameters, 3);
		if (userString.size() == 0)
			return null;
		return new User((String) userString.get(0), (String) userString.get(1),
				(String) userString.get(2));
	}

	public boolean addDB(User user) {
		String sql = "insert into user values(?,?,?)";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(user.getId());
		parameters.add(user.getPassword());
		parameters.add(user.getName());
		return dao.executeQuery(sql, parameters);
	}

	public boolean updateDB(User user) {
		String name, password;
		User userorigin = getUser(user.getId());
		name = user.getName().length() == 0 ? userorigin.getName() : user
				.getName();
		password = user.getPassword().length() == 0 ? userorigin.getPassword()
				: user.getPassword();
		String sql = "update user set name=?, password=? where id=?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(name);
		parameters.add(password);
		parameters.add(user.getId());
		return dao.executeQuery(sql, parameters);
	}
}
