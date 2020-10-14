package com.bookmanage.controller;



import com.bookmanage.dao.MenuDao;
import com.bookmanage.dto.MenuDto;
import com.bookmanage.dto.UserDto;
import com.bookmanage.service.MenuService;
import com.bookmanage.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author YongQiang
 * @Date 2020/10/12 15:56
 * @Version 1.0
 */
public class IndexServlet extends HttpServlet {


    private final static Logger log = Logger.getLogger(IndexServlet.class);

    private final static MenuService menuService=new MenuService();

    private final static UserService userService=new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println(req.getContextPath());
        log.info("请求跳转"+req.getContextPath()+"/jspPage/index.jsp");
        resp.sendRedirect(req.getContextPath()+"/jspPage/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);

        HttpSession session=req.getSession();
        UserDto userDto=new UserDto();
        userDto.setUserName((String) session.getAttribute("usernname"));
        userDto.setPassword((String) session.getAttribute("password"));
        UserDto userDto1 = userService.select(userDto);
        req.setAttribute("user",userDto1);
        //List<MenuDto> menuByUser = menuService.getMenuByUser(userDto);
        log.info(req.getContextPath()+"/jspPage/index.jsp");
        req.getRequestDispatcher(req.getContextPath()+"/jspPage/index.jsp").forward(req,resp);
    }
}
