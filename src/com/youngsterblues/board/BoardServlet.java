package com.youngsterblues.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/board/*")
public class BoardServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] path = req.getPathInfo().split("/");

		RequestDispatcher dispatcher = req
				.getRequestDispatcher("/template/board.jsp");
		if (path.length > 1)
			req.setAttribute("type", path[1]);
		if (path.length > 2)
			req.setAttribute("content", path[2]);
		if (path.length > 3) {
			req.setAttribute("content", "page");
			req.setAttribute("page", path[3]);
		}

		dispatcher.forward(req, resp);

	}
}
