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
@WebServlet("/contents/*")
public class ContentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");

		String[] path = request.getPathInfo().split("/");

		User user = (User) request.getSession().getAttribute("user");

		if (path.length == 2) {
			switch (path[1]) {
			case "getcontent":
				response.getWriter()
						.write(Content.getContent(request.getParameter("id"))
								.toJson());
				return;
			case "getcontents":
				response.getWriter().write(
						Content.getHeadListJson(request.getParameter("type"),
								request.getParameter("size"),
								request.getParameter("page")));
				return;
			}
		}

		if (path.length == 3) {
			int cid = 0;
			String id = request.getParameter("id");
			if (id != null)
				cid = Integer.parseInt(id);
			Content content = new Content(cid, request.getParameter("type"),
					user.getId(), request.getParameter("head"),
					request.getParameter("content"), new Date());
			
			State state;
			switch (path[2]) {
			case "add":
				state = content.add();
				response.getWriter().write(state.toJson());
				return;
			case "mod":
				state = content.mod();
				response.getWriter().write(state.toJson());
				return;
			case "delete":
				state = content.delete();
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
