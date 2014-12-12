package com.youngsterblues.user;

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
		state.setState(true, null);
		return state;
	}

}
