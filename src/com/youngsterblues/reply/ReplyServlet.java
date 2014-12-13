package com.youngsterblues.reply;

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
@WebServlet("/reply/*")
public class ReplyServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF8");
		response.setContentType("application/json");

		String[] path = request.getPathInfo().split("/");

		User user = (User) request.getSession().getAttribute("user");

		if (path.length == 2) {
			switch (path[1]) {
			case "getreply":
				response.getWriter().write(
						Reply.getReply(request.getParameter("rid")).toJson());
				return;
			case "getreplies":
				response.getWriter().write(
						Reply.getReplyListJson(request.getParameter("cid"),
								request.getParameter("size"),
								request.getParameter("page")));
				return;
			}
		}

		if (path.length == 3) {
			Reply reply = new Reply(request.getParameter("rid"),
					request.getParameter("cid"), user.getId(),
					request.getParameter("reply"), new Date());
			State state;
			switch (path[2]) {
			case "add":
				state = reply.add();
				response.getWriter().write(state.toJson());
				return;
			case "mod":
				state = reply.mod();
				response.getWriter().write(state.toJson());
				return;
			case "delete":
				state = reply.delete();
				response.getWriter().write(state.toJson());
				return;
			default:
				state = new State();
				response.getWriter().write(state.toJson());
				return;
			}
		}

	}
}
