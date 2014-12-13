package com.youngsterblues.contents;

import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class ContentDAO {
	DAO dao = new DAO();

	public ArrayList<Content> getContentsHeadList(String type, int page,
			int size) {
		Content content;
		dao.setSql("select id, userid, head, timestamp from contents where type=? order by id desc limit ?, ?");
		dao.addParameters(type);
		dao.addParameters((page - 1) * 10);
		dao.addParameters(size);
		dao.setResultSize(4);
		ArrayList<ArrayList<Object>> sq = dao.getRecords();
		ArrayList<Content> result = new ArrayList<Content>();
		if (sq.size() == 0)
			return null;
		for (int i = 0; i < sq.size(); i++) {
			content = new Content(sq.get(i));
			result.add(content);
		}
		return result;
	}
	
	public Integer getContentCount(String type) {
		dao.setSql("select count(id) from contents where type=?");
		dao.addParameters(type);
		dao.setResultSize(1);
		ArrayList<Object> sq = dao.getRecord();
		if (sq.size() == 0)
			return null;
		return Integer.parseInt(sq.get(0).toString());
	}

	public Content getContent(int id) {
		dao.setSql("select * from contents where id=?");
		dao.addParameters(id);
		dao.setResultSize(6);
		ArrayList<Object> sq = dao.getRecord();
		if (sq.size() == 0)
			return null;
		return new Content(sq);
	}

	public boolean addDB(Content content) {
		dao.setSql("insert into contents values(null,?,?,?,?,?)");
		dao.addParameters(content.getType());
		dao.addParameters(content.getUserId());
		dao.addParameters(content.getHead());
		dao.addParameters(content.getContent());
		dao.addParameters(content.getDatetime());
		return dao.doQuery();
	}

	public boolean deleteDB(Content content) {
		dao.setSql("delete from contents where id=? and userid=?");
		dao.addParameters(content.getId());
		dao.addParameters(content.getUserId());
		return dao.doQuery();
	}


	public boolean modDB(Content content) {
		dao.setSql("update contents set head=?, content=? where id=? and userid=?");
		dao.addParameters(content.getHead());
		dao.addParameters(content.getContent());
		dao.addParameters(content.getId());
		dao.addParameters(content.getUserId());
		return dao.doQuery();
	}



}
