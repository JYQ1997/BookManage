package com.bookmanage.controller;

import com.alibaba.fastjson.JSONArray;
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
import java.awt.print.Book;
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
        bookDto.setDownloadPay(Integer.valueOf(req.getParameter("downloadPay")));
        bookDto.setType(BookTypeEnum.getDesc(Integer.valueOf(req.getParameter("type"))));
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
     * 图书列表菜单跳转
     * @param req
     * @param resp
     */
    public void bookList(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String target="/jspPage/book/bookList.jsp";
        HttpSession session=req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        log.info(req.getContextPath()+target);

        resp.sendRedirect(req.getContextPath()+target);
    }

    /**
     * 图书列表菜单数据
     * @param req
     * @param resp
     * @throws IOException
     */
    public void dataList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        PageParam pageParam=new PageParam();
        pageParam.setOffset(Integer.valueOf(req.getParameter("offset")));
        pageParam.setPageSize(Integer.valueOf(req.getParameter("limit")));

        HashMap<String,Object> params=new HashMap<>();
        params.put("status",1);
        if (req.getParameter("bookName")!=null&&req.getParameter("bookName")!=""){
            params.put("bookName",req.getParameter("bookName"));
        }
        if (req.getParameter("name")!=null&&req.getParameter("name")!=""){
            params.put("name",req.getParameter("name"));
        }
        /**
         * 图书类型为0，说明没有选择，默认全部类型
         */
        if (req.getParameter("type")!=null&&req.getParameter("type")!=""&&req.getParameter("type")!="0"){
            String type = BookTypeEnum.getDesc(Integer.valueOf(req.getParameter("type")));
            if (type!=null){
                params.put("type",type);
            }
        }
        if (req.getParameter("author")!=null&&req.getParameter("author")!=""){
            params.put("author",req.getParameter("author"));
        }
        Integer count=bookService.selectCountByParam(params);
        List<BookDto> bookDtos = bookService.selectByPage(pageParam, params);
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


    /**
     * 通过id获取书本信息
     * @param req
     * @param resp
     * @throws IOException
     */
    public void getBookById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long bid = Long.valueOf(req.getParameter("bid"));
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        BookDto bookDto = bookService.selectBookByBookId(bid);
        if (bookDto!=null){
            bookDto.setTypeCode(BookTypeEnum.getCode(bookDto.getType()));
        }
        HashMap<String, Object> maps=new HashMap<>();
        maps.put("book",bookDto);
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setCode(200);
        responseMsg.setData(maps);
        resp.getWriter().write(responseMsg.toString());
    }

    /**
     * 上传者编辑图书
     * @param req
     * @param resp
     * @throws IOException
     */
    public void editBook(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        if (userDto==null){
            //如果session已过期，给出提示
            responseMsg.setCode(500);
            responseMsg.setMsg("用户信息已过期");
        }
        else {
            if (req.getParameter("bid")!=null&&req.getParameter("bid")!=""){
                BookDto bookDto = bookService.selectBookByBookId(Long.valueOf(req.getParameter("bid")));
                if(bookDto!=null){
                    bookDto.setDesc(req.getParameter("desc"));
                    if (req.getParameter("type")!=null){
                        String type = BookTypeEnum.getDesc(Integer.valueOf(req.getParameter("type")));
                        if (type!=null){
                            bookDto.setType(type);
                        }
                    }
                    bookDto.setIsDownload(Integer.valueOf(req.getParameter("isDownload")));
                    bookDto.setDownloadPay(Integer.valueOf(req.getParameter("downloadPay")));
                    bookDto.setModified(userDto.getUserId());
                    boolean success = bookService.updateById(bookDto);
                    if (success){
                        responseMsg.setCode(200);
                        responseMsg.setMsg("修改成功");
                    }
                    else {
                        responseMsg.setCode(500);
                        responseMsg.setMsg("修改失败");
                    }
                }
                else {
                    responseMsg.setCode(500);
                    responseMsg.setMsg("无书本信息");
                }
            }
            else {
                responseMsg.setCode(500);
                responseMsg.setMsg("请求参数缺失");
            }
        }
        resp.getWriter().write(responseMsg.toString());
    }

    /**
     * 删除一本书
     * @param req
     * @param resp
     * @throws IOException
     */
    public void remove(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String bid = req.getParameter("bid");
        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        boolean success = bookService.deleteById(Long.valueOf(bid));

        if (success){
            responseMsg.setCode(200);
            responseMsg.setMsg("删除成功");
        }
        else {
            responseMsg.setCode(500);
            responseMsg.setMsg("删除失败");
        }

        resp.getWriter().write(responseMsg.toString());

    }

    /**
     * 批量删除多本书
     * @param req
     * @param resp
     * @throws IOException
     */
    public void batchRemove(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String ids = req.getParameter("bid");
        JSONArray bids = JSONObject.parseArray(ids);
        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        boolean success = bookService.deleteByIds(bids);

        if (success){
            responseMsg.setCode(200);
            responseMsg.setMsg("删除成功");
        }
        else {
            responseMsg.setCode(500);
            responseMsg.setMsg("删除失败");
        }

        resp.getWriter().write(responseMsg.toString());

    }


    /**
     * 图书馆里菜单跳转
     * @param req
     * @param resp
     * @throws IOException
     */
    public void bookManage(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String target="/jspPage/book/bookManage.jsp";
        HttpSession session=req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        log.info(req.getContextPath()+target);

        resp.sendRedirect(req.getContextPath()+target);
    }

    /**
     * 图书管理菜单数据
     * @param req
     * @param resp
     * @throws IOException
     */
    public void manageList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        PageParam pageParam=new PageParam();
        pageParam.setOffset(Integer.valueOf(req.getParameter("offset")));
        pageParam.setPageSize(Integer.valueOf(req.getParameter("limit")));
        HashMap<String,Object> params=new HashMap<>();
        if (req.getParameter("bookName")!=null&&req.getParameter("bookName")!=""){
            params.put("bookName",req.getParameter("bookName"));
        }
        if (req.getParameter("name")!=null&&req.getParameter("name")!=""){
            params.put("name",req.getParameter("name"));
        }
        /**
         * 图书类型为0，说明没有选择，默认全部类型
         */
        if (req.getParameter("type")!=null&&req.getParameter("type")!=""&&req.getParameter("type")!="0"){
            String type = BookTypeEnum.getDesc(Integer.valueOf(req.getParameter("type")));
            if (type!=null){
                params.put("type",type);
            }
        }
        if (req.getParameter("author")!=null&&req.getParameter("author")!=""){
            params.put("author",req.getParameter("author"));
        }
        Integer count=bookService.selectCountByParam(params);
        List<BookDto> bookDtos = bookService.selectByPage(pageParam, params);
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PageResponse<BookDto> pageResponse=new PageResponse<BookDto>();
        pageResponse.setRows(bookDtos);
        pageResponse.setTotal(count);
        resp.getWriter().write(pageResponse.toString());
    }

    /**
     * 管理员修改图书状态（启用/禁用）
     * @param req
     * @param resp
     * @throws IOException
     */
    public void changeStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session=req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
        if (user.getRoleId()!=1){
            responseMsg.setCode(500);
            responseMsg.setMsg("非管理员无法执行操作");
        }
        else {
            if (req.getParameter("bid")!=null&&req.getParameter("bid")!=""){
                BookDto bookDto = bookService.selectBookByBookId(Long.valueOf(req.getParameter("bid")));
                if (bookDto!=null){
                    Integer status = bookDto.getStatus();
                    if (status==1){
                        bookDto.setStatus(0);
                    }
                    else if (status==0||status==2){
                        bookDto.setStatus(1);
                    }
                    boolean success = bookService.updateStatus(bookDto);
                    if (success){
                        responseMsg.setCode(200);
                        responseMsg.setMsg("修改成功");
                    }
                    else {
                        responseMsg.setCode(500);
                        responseMsg.setMsg("修改失败");
                    }
                }
                else {
                    responseMsg.setCode(500);
                    responseMsg.setMsg("书籍信息不存在");
                }
            }
            else {

                responseMsg.setCode(500);
                responseMsg.setMsg("参数缺失");
            }
        }
        resp.getWriter().write(responseMsg.toString());
    }

    /**
     * 上传者申请恢复图书
     * @param req
     * @param resp
     * @throws IOException
     */
    public void apply(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session=req.getSession();
        UserDto user = (UserDto) session.getAttribute("user");
            if (req.getParameter("bid")!=null&&req.getParameter("bid")!=""){
                BookDto bookDto = bookService.selectBookByBookId(Long.valueOf(req.getParameter("bid")));
                if (bookDto!=null){
                    Integer status = bookDto.getStatus();
                    if (status==0){
                        bookDto.setStatus(2);
                    }
                    boolean success = bookService.updateStatus(bookDto);
                    if (success){
                        responseMsg.setCode(200);
                        responseMsg.setMsg("申请成功，请等待处理结果");
                    }
                    else {
                        responseMsg.setCode(500);
                        responseMsg.setMsg("申请失败");
                    }
                }
                else {
                    responseMsg.setCode(500);
                    responseMsg.setMsg("书籍信息不存在");
                }
            }
            else {

                responseMsg.setCode(500);
                responseMsg.setMsg("参数缺失");
            }
        resp.getWriter().write(responseMsg.toString());
    }
}
