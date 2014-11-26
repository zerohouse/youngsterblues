package com.youngsterblues.contents;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet("/contents/getcontents/")
public class GetContentsServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ContentDAO conDAO = new ContentDAO();
		String type = req.getParameter("type");
		String size = req.getParameter("size");
		String page = req.getParameter("page");
		if(type==null)
			type = "free";
		if(size==null)
			size = "10";
		if(page==null)
			page = "1";
		ArrayList<Content> contents = conDAO.getContentsHeadList(type, Integer.parseInt(page) ,Integer.parseInt(size));
		ArrayList<Object> result = new ArrayList<Object>();
		result.add(new ContentDAO().getContentCount(type));
		result.add(contents);
		Gson gson = new Gson();
		resp.getWriter().write(gson.toJson(result));
	}
}
