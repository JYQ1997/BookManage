package com.bookmanage.controller;

import com.bookmanage.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;

public class BookServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletReflect.dispatcher(this,req,resp);
    }

    /**
     * 用户新增图书
     * @param req
     * @param resp
     */
    public void addBook(HttpServletRequest req, HttpServletResponse resp){
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

    }


}
