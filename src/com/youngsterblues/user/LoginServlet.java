package com.youngsterblues.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet("/users/login")
public class LoginServlet extends HttpServlet {


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF8"); // this line solves the problem
	    resp.setContentType("application/json");
	    
		String id = req.getParameter("id");
		String password = req.getParameter("password");

		UserDAO userdao = new UserDAO();
		User user = userdao.getUser(id);

		State state = new State();
		Gson gson = new Gson();
		
		if (user == null) {
			state.setState(false, "사용자가 없습니다.");
			resp.getWriter().write(gson.toJson(state));
			return;
		}
		if (!user.matchPassword(password)) {
			state.setState(false, "비밀번호가 맞지 않습니다.");
			resp.getWriter().write(gson.toJson(state));
			return;
		}
		state.setState(true, null);
		resp.getWriter().write(gson.toJson(state));
		req.getSession().setAttribute("user", user);
	}

}
