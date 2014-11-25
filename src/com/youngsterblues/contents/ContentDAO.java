package com.youngsterblues.contents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.youngsterblues.support.DAO;

public class ContentDAO extends DAO {

	public ArrayList<Content> getContentsList(int size) {
		ArrayList<Content> result = new ArrayList<Content>();
		Content content;
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select * from contents order by id desc limit 0, ?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(size);
		ArrayList<Object> sq = selectQuery(sql, parameters, 6);
		for (int i = 0; i < sq.size(); i = i + 6) {
			try {
				content = new Content((Integer) sq.get(i), sq.get(i + 1)
						.toString(), sq.get(i + 2).toString(), sq.get(i + 3)
						.toString(), sq.get(i + 4).toString(),
						datetime.parse(sq.get(i + 5).toString()));
				result.add(content);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<Content> getContentsList(String type, int size) {
		ArrayList<Content> result = new ArrayList<Content>();
		Content content;
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select * from contents where type=? order by id desc limit 0, ?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(type);
		parameters.add(size);
		ArrayList<Object> sq = selectQuery(sql, parameters, 6);
		for (int i = 0; i < sq.size(); i = i + 6) {
			try {
				content = new Content((Integer) sq.get(i), sq.get(i + 1)
						.toString(), sq.get(i + 2).toString(), sq.get(i + 3)
						.toString(), sq.get(i + 4).toString(),
						datetime.parse(sq.get(i + 5).toString()));
				result.add(content);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public ArrayList<Content> getContentsHeadList(String type, int size) {
		ArrayList<Content> result = new ArrayList<Content>();
		Content content;
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select id, userid, head, timestamp from contents where type=? order by id desc limit 0, ?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(type);
		parameters.add(size);
		ArrayList<Object> sq = selectQuery(sql, parameters, 4);
		for (int i = 0; i < sq.size(); i = i + 4) {
			try {
				content = new Content((Integer) sq.get(i), sq.get(i + 1)
						.toString(), sq.get(i + 2).toString(),
						datetime.parse(sq.get(i + 3).toString()));
				result.add(content);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean addDB(Content content) {
		String sql = "insert into contents values(null,?,?,?,?,?)";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(content.getType());
		parameters.add(content.getUserId());
		parameters.add(content.getHead());
		parameters.add(content.getContent());
		parameters.add(content.getDatetime());
		return executeQuery(sql, parameters);
	}
	
	public boolean deleteDB(int contentId) {
		String sql = "delete from contents where id=?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(contentId);
		return executeQuery(sql, parameters);
	}

	public Content getContent(int id) {
		Content result = null;
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "select * from contents where id=?";
		ArrayList<Object> parameters = new ArrayList<Object>();
		parameters.add(id);
		ArrayList<Object> sq = selectQuery(sql, parameters, 6);
		try {
			result = new Content((Integer) sq.get(0), sq.get(1).toString(), sq
					.get(2).toString(), sq.get(3).toString(), sq.get(4)
					.toString(), datetime.parse(sq.get(5).toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
