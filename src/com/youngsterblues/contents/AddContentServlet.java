package com.youngsterblues.contents;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youngsterblues.support.State;
import com.youngsterblues.user.User;

@SuppressWarnings("serial")
@WebServlet("/contents/addcontent/")
public class AddContentServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setCharacterEncoding("UTF8"); // this line solves the problem
		resp.setContentType("application/json");

		User user = (User) req.getSession().getAttribute("user");
		String id = req.getParameter("id").replaceAll("\\<.*?\\>", "");
		String head = req.getParameter("head").replaceAll("\\<.*?\\>", "");
		String content = req.getParameter("content").replaceAll("\\<.*?\\>", "").replaceAll("$#@!", "<br/>");
		String type = req.getParameter("type").replaceAll("\\<.*?\\>", "");
		State state = new State();

		if (user == null || id == null || head == null || content == null
				|| type == null) {
			state.setState(false, "필드가 비었음");
			resp.getWriter().write(state.toJson());
			return;
		}
		if (!id.equals(user.getId())) {
			state.setState(false, "아이디가 일치하지 않음");
			resp.getWriter().write(state.toJson());
			return;
		}
		ContentDAO conDAO = new ContentDAO();

		if (conDAO.addDB(new Content(type, id, head, content, new Date()))) {
			state.setState(true, null);
			resp.getWriter().write(state.toJson());
		}

	}

}
