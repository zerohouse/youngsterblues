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
@WebServlet("/contents/modcontent/")
public class ModContentServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF8"); // this line solves the problem
		resp.setContentType("application/json");

		User user = (User) req.getSession().getAttribute("user");
		String id = req.getParameter("id").replaceAll("\\<.*?\\>", "");
		String head = req.getParameter("head").replaceAll("\\<.*?\\>", "");
		String content = req.getParameter("content").replaceAll("\\<.*?\\>", "").replaceAll("$#@!", "<br/>");
		State state = new State();

		if (user == null || id == null || head == null || content == null) {
			state.setState(false, "필드가 비었음");
			resp.getWriter().write(state.toJson());
			return;
		}
		
		ContentDAO conDAO = new ContentDAO();
		
		if (conDAO.modDB(user.getId(), Integer.parseInt(id), head, content)) {
			state.setState(true, null);
			resp.getWriter().write(state.toJson());
		}

	}

}
