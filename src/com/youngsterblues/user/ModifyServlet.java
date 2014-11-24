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
@WebServlet("/users/modify")
public class ModifyServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF8"); // this line solves the problem
		resp.setContentType("application/json");

		UserDAO userdao = new UserDAO();
		State state = new State();
		Gson gson = new Gson();

		User user = gson.fromJson(req.getParameter("user"), User.class);
		if (!userdao.getUser(user.getId()).matchPassword(
				req.getParameter("password"))) {
			state.setState(false, "기존 비밀번호를 정확하게 입력해주세요.");
			resp.getWriter().write(gson.toJson(state));
			return;
		}
		if (!userdao.updateDB(user)) {
			state.setState(false, "SQL오류가 발생했습니다.");
			resp.getWriter().write(gson.toJson(state));
			return;
		}
		state.setState(true, null);
		resp.getWriter().write(gson.toJson(state));
		req.getSession().setAttribute("user", user);
	}

}
