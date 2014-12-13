package com.youngsterblues.contents;

import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class ContentDAO {
	DAO dao;

	public ContentDAO() {
		dao = new DAO();
	}

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
			content = new Content((Integer) sq.get(i).get(0), sq.get(i).get(1)
					.toString(), sq.get(i).get(2).toString(), dao.parseDate(sq
					.get(i).get(3).toString()));
			result.add(content);
		}
		return result;
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

	public boolean deleteDB(int contentId, String userId) {
		dao.setSql("delete from contents where id=? and userid=?");
		dao.addParameters(contentId);
		dao.addParameters(userId);
		return dao.doQuery();
	}

	public Content getContent(int id) {
		dao.setSql("select * from contents where id=?");
		dao.addParameters(id);
		dao.setResultSize(6);
		ArrayList<Object> sq = dao.getRecord();
		if (sq.size() == 0)
			return null;
		return new Content((Integer) sq.get(0), sq.get(1).toString(), sq.get(2)
				.toString(), sq.get(3).toString(), sq.get(4).toString(),
				dao.parseDate(sq.get(5)));
	}

	public boolean modDB(String userId, Integer contentId, String head,
			String content) {
		dao.setSql("update contents set head=?, content=? where id=? and userid=?");
		dao.addParameters(head);
		dao.addParameters(content);
		dao.addParameters(contentId);
		dao.addParameters(userId);
		return dao.doQuery();
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

}
