package com.youngsterblues.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/users/checkid")
public class IdCheckServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		UserDAO userdao = new UserDAO();
		if (userdao.getUser(req.getParameter("id")) == null) {
			resp.getWriter().write("false");
			return;
		}
		resp.getWriter().write("true");
	}
}
