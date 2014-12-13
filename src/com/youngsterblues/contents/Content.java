package com.youngsterblues.contents;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.youngsterblues.support.Methods;
import com.youngsterblues.support.State;
import com.youngsterblues.user.User;

public class Content {
	private int id;
	private String userId;
	private String head;
	private String type;
	private String content;
	private Date datetime;

	public Content(int id, String type, String userId, String head,
			String content, Date datetime) {
		this.id = id;
		this.type = type;
		this.userId = userId;
		this.head = head;
		this.content = content;
		this.datetime = datetime;
	}

	public Content(String type, String userId, String head, String content,
			Date datetime) {
		this.type = type;
		this.userId = userId;
		this.head = head;
		this.content = content;
		this.datetime = datetime;
	}

	public Content(ArrayList<Object> content) {
		if (content.size() == 4) {
			id = (Integer) content.get(0);
			userId = content.get(1).toString();
			head = content.get(2).toString();
			datetime = Methods.parseDate(content.get(3).toString());
			return;
		}
		id = (Integer) content.get(0);
		type = content.get(1).toString();
		userId = content.get(2).toString();
		head = content.get(3).toString();
		this.content = content.get(4).toString();
		datetime = Methods.parseDate(content.get(5));
	}

	@Override
	public String toString() {
		return "Content [id=" + id + ", userId=" + userId + ", head=" + head
				+ ", type=" + type + ", contents=" + content + ", datetime="
				+ datetime + "]";
	}

	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getHead() {
		return head;
	}

	public String getContent() {
		return content;
	}

	public Date getDatetime() {
		return datetime;
	}

	public String getType() {
		return type;
	}

	public State add() {
		State state = new State();
		if (isEmptyContent() || type == null) {
			state.setState(false, "필드가 비었음");
			return state;
		}
		ContentDAO conDAO = new ContentDAO();
		if (!conDAO.addDB(this)) {
			state.setState(false, "SQL 에러");
			return state;
		}
		return state;
	}

	public State mod() {
		State state = new State();
		if (isEmptyContent()) {
			state.setState(false, "필드가 비었음");
			return state;
		}

		ContentDAO conDAO = new ContentDAO();
		if (!conDAO.modDB(userId, id, head, content)) {
			state.setState(false, "SQL 에러");
		}
		return state;
	}

	private boolean isEmptyContent() {
		return userId == null || head == null || content == null;
	}

	public State delete() {
		State state = new State();
		ContentDAO conDAO = new ContentDAO();
		if (!conDAO.deleteDB(id, userId)) {
			state.setState(false, "SQL 에러");
		}
		return state;
	}

	public boolean checkUser(User user) {
		if (userId.equals(user.getId()))
			return true;
		return false;
	}

	public static Content getContent(String id) {
		ContentDAO conDAO = new ContentDAO();
		if (id == null)
			return null;
		try {
			return conDAO.getContent(Integer.parseInt(id));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getHeadListJson(String type, String size, String page) {
		ContentDAO conDAO = new ContentDAO();
		if (type == null)
			type = "free";
		if (size == null)
			size = "10";
		if (page == null)
			page = "1";
		ArrayList<Content> contents = conDAO.getContentsHeadList(type,
				Integer.parseInt(page), Integer.parseInt(size));
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(new ContentDAO().getContentCount(type));
		result.add(contents);
		Gson gson = new Gson();
		return gson.toJson(result);
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

}
