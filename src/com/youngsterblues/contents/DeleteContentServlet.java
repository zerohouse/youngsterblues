package com.youngsterblues.contents;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youngsterblues.support.State;
import com.youngsterblues.user.User;

@SuppressWarnings("serial")
@WebServlet("/contents/deletecontent/")
public class DeleteContentServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF8"); // this line solves the problem
		resp.setContentType("application/json");
		
		String id = req.getParameter("id");
		String content = req.getParameter("content");

		User user = (User) req.getSession().getAttribute("user");
		State state = new State();

		if (!user.getId().equals(id)) {
			state.setState(false, "아이디가 일치하지 않음");
			resp.getWriter().write(state.toJson());
			return;
		}
		ContentDAO conDAO = new ContentDAO();
		if (conDAO.deleteDB(Integer.parseInt(content))) {
			state.setState(true, null);
			resp.getWriter().write(state.toJson());
		}
	}
}
