package com.youngsterblues.contents;

import java.util.Date;


import com.youngsterblues.support.State;

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

	public Content(Integer id, String userId, String head, Date datetime) {
		this.id = id;
		this.userId = userId;
		this.head = head;
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
		if (userId == null || head == null || content == null || type == null) {
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

}
