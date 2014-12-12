package com.youngsterblues.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.youngsterblues.support.State;

@SuppressWarnings("serial")
@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().removeAttribute("user");
		resp.sendRedirect("/");
	}

	@Override
	protected void doPost(HttpServletRequest request,
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

		Gson gson = new Gson();
		User user = gson.fromJson(request.getParameter("user"), User.class);
		
		State state;
		switch (path[1]) {
		case "login":
			state = user.login();
			break;
		case "modify":
			state = user.update(request.getParameter("password"));
			break;
		case "signup":
			state = user.signup();
			break;
		default:
			state = new State();
			break;
		}
		
		response.getWriter().write(state.toJson());
		if (state.isSuccess())
			request.getSession().setAttribute("user", user);

	}

}
