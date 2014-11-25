package com.youngsterblues.contents;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet("/contents/getcontent/")
public class GetContentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ContentDAO conDAO = new ContentDAO();
		String id = req.getParameter("id");
		if (id == null)
			id = "1";
		try {
			int parseId = Integer.parseInt(id);
			Content content = conDAO.getContent(parseId);
			Gson gson = new Gson();
			resp.getWriter().write(gson.toJson(content));
		} catch (Exception e) {
			resp.getWriter().write("null");
		}
	}

}
