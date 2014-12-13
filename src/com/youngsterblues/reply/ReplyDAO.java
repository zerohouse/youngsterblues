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

	public boolean addDB(Reply reply) {
		dao.setSql("insert into reply values(null,?,?,?,?)");
		dao.addParameters(reply.getCid());
		dao.addParameters(reply.getUserId());
		dao.addParameters(reply.getReply());
		dao.addParameters(reply.getTimestamp());
		return dao.doQuery();
	}
	
	public boolean modDB(Reply reply) {
		dao.setSql("update reply set reply=? where rid=? and userid=?");
		dao.addParameters(reply.getReply());
		dao.addParameters(reply.getRid());
		dao.addParameters(reply.getUserId());
		return dao.doQuery();
	}
	
	public boolean deleteDB(Reply reply) {
		dao.setSql("delete from reply where rid=? and userid=?");
		dao.addParameters(reply.getRid());
		dao.addParameters(reply.getUserId());
		return dao.doQuery();
	}

	public Integer getContentCount(String cid) {
		dao.setSql("select count(rid) from contents where cid=?");
		dao.addParameters(Integer.parseInt(cid));
		dao.setResultSize(1);
		ArrayList<Object> sq = dao.getRecord();
		if (sq.size() == 0)
			return null;
		return Integer.parseInt(sq.get(0).toString());
	}

}
