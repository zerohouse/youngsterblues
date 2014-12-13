package com.youngsterblues.reply;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.youngsterblues.support.Methods;
import com.youngsterblues.support.State;

public class Reply {
	private Integer rid;
	private Integer cid;
	private String userId;
	private String reply;
	private Date timestamp;

	public Reply(String rid, String cid, String userId, String reply,
			Date timestamp) {
		if (rid != null)
			this.rid = Integer.parseInt(rid);
		if (cid != null)
			this.cid = Integer.parseInt(cid);
		this.userId = userId;
		this.reply = reply;
		this.timestamp = timestamp;
	}

	public Integer getRid() {
		return rid;
	}

	public Integer getCid() {
		return cid;
	}

	public String getUserId() {
		return userId;
	}

	public String getReply() {
		return reply;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Reply(ArrayList<Object> reply) {
		rid = Integer.parseInt((String) reply.get(0));
		cid = Integer.parseInt((String) reply.get(1));
		userId = (String) reply.get(2);
		this.reply = (String) reply.get(3);
		timestamp = Methods.parseDate(reply.get(4));
	}

	@Override
	public String toString() {
		return "Reply [rid=" + rid + ", cid=" + cid + ", userId=" + userId
				+ ", reply=" + reply + ", timestamp=" + timestamp + "]";
	}

	public State add() {
		State state = new State();
		if (isEmptyContent() || cid == null) {
			state.setState(false, "필드가 비었음");
			return state;
		}
		ReplyDAO dao = new ReplyDAO();
		if (!dao.addDB(this)) {
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

		ReplyDAO dao = new ReplyDAO();
		if (!dao.modDB(this)) {
			state.setState(false, "SQL 에러");
		}
		return state;
	}

	private boolean isEmptyContent() {
		return userId == null || reply == null;
	}

	public State delete() {
		State state = new State();
		ReplyDAO dao = new ReplyDAO();
		if (!dao.deleteDB(this)) {
			state.setState(false, "SQL 에러");
		}
		return state;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public static Reply getReply(String rid) {
		ReplyDAO dao = new ReplyDAO();
		if (rid == null)
			return null;
		try {
			return dao.getReply(Integer.parseInt(rid));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getReplyListJson(String cid, String size, String page) {
		ReplyDAO dao = new ReplyDAO();
		if (cid == null)
			return null;
		if (size == null)
			size = "10";
		if (page == null)
			page = "1";
		ArrayList<Reply> contents = dao.getReplies(Integer.parseInt(cid),
				Integer.parseInt(page), Integer.parseInt(size));
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(new ReplyDAO().getContentCount(cid));
		result.add(contents);
		Gson gson = new Gson();
		return gson.toJson(result);
	}

}
