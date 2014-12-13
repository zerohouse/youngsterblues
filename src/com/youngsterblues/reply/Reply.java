package com.youngsterblues.reply;

import java.util.ArrayList;
import java.util.Date;

import com.youngsterblues.support.Methods;

public class Reply {
	private int rid;
	private int cid;
	private String userId;
	private String reply;
	private Date timestamp;
	
	
	

	public Reply(int rid, int cid, String userId, String reply, Date timestamp) {
		this.rid = rid;
		this.cid = cid;
		this.userId = userId;
		this.reply = reply;
		this.timestamp = timestamp;
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


}
