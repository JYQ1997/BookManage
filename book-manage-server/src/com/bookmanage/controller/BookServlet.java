package com.bookmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookmanage.dto.BookDto;
import com.bookmanage.dto.UserDto;
import com.bookmanage.enums.BookTypeEnum;
import com.bookmanage.response.PageResponse;
import com.bookmanage.response.ResponseMsg;
import com.bookmanage.response.TypeDo;
import com.bookmanage.service.BookService;
import com.bookmanage.util.PageParam;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookServlet extends HttpServlet {

    private final static BookService bookService=new BookService();
    private static final Logger log=Logger.getLogger(UserServlet.class);

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
    public void addBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        BookDto bookDto=new BookDto();
        bookDto.setTitle(req.getParameter("bookName"));
        bookDto.setAuthor(req.getParameter("author"));
        bookDto.setDesc(req.getParameter("desc"));
        bookDto.setIsDownload(Integer.valueOf(req.getParameter("isDownload")));
        bookDto.setDownloadPay(Integer.valueOf(req.getParameter("downloadPay")));
        boolean success = bookService.addBook(bookDto, user);
        if (success){
            responseMsg.setCode(200);
            responseMsg.setMsg("修改成功");
        }
        else {
            responseMsg.setCode(500);
            responseMsg.setMsg("修改失败");
        }
        resp.getWriter().write(responseMsg.toString());
    }

    /**
     * 图书管理页面
     * @param req
     * @param resp
     */
    public void bookManage(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String target="/jspPage/book/bookList.jsp";
        HttpSession session=req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        log.info(req.getContextPath()+target);

        resp.sendRedirect(req.getContextPath()+target);
    }

    public void manageList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        PageParam pageParam=new PageParam();
        pageParam.setPageNo(Integer.valueOf(req.getParameter("offset")));
        pageParam.setPageSize(Integer.valueOf(req.getParameter("limit")));
        Integer count=bookService.selectCountByParam(null);
        List<BookDto> bookDtos = bookService.selectByPage(pageParam, null);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PageResponse<BookDto> pageResponse=new PageResponse<BookDto>();
        pageResponse.setRows(bookDtos);
        pageResponse.setTotal(count);
        resp.getWriter().write(pageResponse.toString());
    }

    public void type(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookTypeEnum[] values = BookTypeEnum.values();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        List<TypeDo> list=new ArrayList<>();
        for (BookTypeEnum value : values) {
            TypeDo typeDo=new TypeDo();
            typeDo.setCode(value.getCode());
            typeDo.setDesc(value.getDesc());
            list.add(typeDo);
        }
        resp.getWriter().write(JSONObject.toJSONString(list));

    }

    /**
     * 我的上传列表
     * @param req
     * @param resp
     * @throws IOException
     */
    public void myUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        PageParam pageParam=new PageParam();
        pageParam.setOffset(Integer.valueOf(req.getParameter("offset")));
        pageParam.setPageSize(Integer.valueOf(req.getParameter("limit")));
        HashMap<String, Object> params=new HashMap<>();
        params.put("name",user.getName());
        Integer count=bookService.selectCountByParam(params);
        List<BookDto> bookDtos = bookService.selectByPage(pageParam, params);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PageResponse<BookDto> pageResponse=new PageResponse<BookDto>();
        pageResponse.setRows(bookDtos);
        pageResponse.setTotal(count);
        resp.getWriter().write(pageResponse.toString());

    }


    public void myUploadEdit(HttpServletRequest req, HttpServletResponse resp) throws IOException {


    }


}
