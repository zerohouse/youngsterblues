package com.youngsterblues.reply;

import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class ReplyDAO {

	DAO dao = new DAO();

	public Reply getReply(int rid) {
		dao.setSql("select * from reply where rid=?");
		dao.addParameters(rid);
		dao.setResultSize(5);
		ArrayList<Object> reply = dao.getRecord();
		if (reply.size() == 0)
			return null;
		return new Reply(reply);
	}

	public ArrayList<Reply> getReplies(int cid, int page, int size) {
		Reply reply;
		dao.setSql("select * from reply where cid=? order by id desc limit ?, ?");
		dao.addParameters(cid);
		dao.addParameters((page - 1) * 10);
		dao.addParameters(size);
		dao.setResultSize(5);
		ArrayList<ArrayList<Object>> sq = dao.getRecords();
		ArrayList<Reply> result = new ArrayList<Reply>();
		if (sq.size() == 0)
			return null;
		for (int i = 0; i < sq.size(); i++) {
			reply = new Reply(sq.get(i));
			result.add(reply);
		}
		return result;
	}

}
