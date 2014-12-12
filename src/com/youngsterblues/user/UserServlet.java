package com.youngsterblues.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youngsterblues.support.State;

@SuppressWarnings("serial")
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");

		// users/로 들어오는 모든 요청을 받아 두번쨰 파라미터에 따라 다른 실행
		String[] path = request.getPathInfo().split("/");
		if (path.length != 2) {
			return;
		}

		// 아이디 중복체크
		if (path[1].equals("checkid")) {
			response.getWriter()
					.write(User.checkid(request.getParameter("id")));
			return;
		}

		User user = new User(request.getParameter("id"),
				request.getParameter("password"), request.getParameter("name"));
		State state;
		
		switch (path[1]) {
		case "login":
			state = user.login();
			break;
		case "signup":
			signup(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		}
	}

}
