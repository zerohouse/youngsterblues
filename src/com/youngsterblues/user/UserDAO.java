package com.youngsterblues.user;

import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class UserDAO {

	DAO dao = new DAO();

	public User getUser(String id) {
		dao.setSql("select * from user where id=?");
		dao.addParameter(id);
		dao.setResultSize(3);
		ArrayList<Object> user = dao.getRecord();
		if (user.size() == 0)
			return null;
		return new User(user);
	}

	public boolean addDB(User user) {
		dao.setSql("insert into user values(?,?,?)");
		dao.addParameter(user.getId());
		dao.addParameter(user.getPassword());
		dao.addParameter(user.getName());
		return dao.doQuery();
	}

	public boolean updateDB(User user) {
		String name, password;
		User userorigin = getUser(user.getId());
		name = user.getName().length() == 0 ? userorigin.getName() : user
				.getName();
		password = user.getPassword().length() == 0 ? userorigin.getPassword()
				: user.getPassword();

		dao.setSql("update user set name=?, password=? where id=?");
		dao.addParameter(name);
		dao.addParameter(password);
		dao.addParameter(user.getId());
		return dao.doQuery();
	}
}
