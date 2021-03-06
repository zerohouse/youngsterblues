package com.youngsterblues.user;

import java.util.ArrayList;

import com.youngsterblues.support.State;

public class User {
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name
				+ "]";
	}

	private String id;
	private String password;
	private String name;

	public User(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public User(ArrayList<Object> user) {
		id = (String) user.get(0);
		password = (String) user.get(1);
		name = (String) user.get(2);
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public boolean matchPassword(String password) {
		if (this.password.equals(password))
			return true;
		return false;
	}

	public static String checkid(String id) {
		UserDAO userdao = new UserDAO();
		if (userdao.getUser(id) == null) {
			return "false";
		}
		return "true";
	}

	public State login() {
		UserDAO userdao = new UserDAO();
		User user = userdao.getUser(id);
		State state = new State();

		if (user == null) {
			state.setState(false, "사용자가 없습니다.");
			return state;
		}
		if (!user.matchPassword(password)) {
			state.setState(false, "비밀번호가 맞지 않습니다.");
			return state;
		}
		return state;
	}

	public State update(String originpassword) {
		UserDAO userdao = new UserDAO();
		State state = new State();
		User user = userdao.getUser(id);
		
		if(user == null){
			state.setState(false, "사용자가 없습니다.");
			return state;
		}
		if (!user.matchPassword(originpassword)) {
			state.setState(false, "기존 비밀번호를 정확하게 입력해주세요.");
			return state;
		}
		if (!userdao.updateDB(this)) {
			state.setState(false, "SQL오류가 발생했습니다.");
			return state;
		}
		return state;

	}

	public State signup() {
		UserDAO userdao = new UserDAO();
		State state = new State();

		if (userdao.getUser(id) != null) {
			state.setState(false, "이미 존재하는 아이디입니다.");
			return state;
		}
		if (!userdao.addDB(this)) {
			state.setState(false, "SQL오류가 발생했습니다.");
			return state;
		}
		return state;
	}

}
