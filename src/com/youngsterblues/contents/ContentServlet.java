package com.youngsterblues.contents;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.youngsterblues.support.State;
import com.youngsterblues.user.User;

@SuppressWarnings("serial")
@WebServlet("/contents/*")
public class ContentServlet extends HttpServlet {

	
	
	
	
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
	

	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF8");
		response.setCharacterEncoding("UTF8"); // this line solves the problem
		response.setContentType("application/json");
		
		String[] path = request.getPathInfo().split("/");
		if (path.length != 2) {
			return;
		}
		
		
		User user = (User) request.getSession();
		// 유저 검증 필요
		
		Gson gson = new Gson();
		Content content = gson.fromJson(request.getParameter("content"), Content.class);
		
		State state;
		
		switch (path[1]) {
		case "add":
			state = content.add();
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
