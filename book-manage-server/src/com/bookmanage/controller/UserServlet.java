package com.bookmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bookmanage.dto.UserDto;
import com.bookmanage.response.ResponseMsg;
import com.bookmanage.service.UserService;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author YongQiang
 * @Date 2020/10/13 18:51
 * @Version 1.0
 */
public class UserServlet extends HttpServlet {

    private static final Logger log=Logger.getLogger(UserServlet.class);
    private static final char[] codeSequence = {'A', '1', 'B', 'C', '2', 'D', '3', 'E', '4', 'F', '5', 'G', '6', 'H', '7', 'I',
            '8',
            'J',
            'K', '9', 'L', '1', 'M', '2', 'N', 'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    private static UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletReflect.dispatcher(this,req,resp);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto userDto = new UserDto();
        Map<String, Object> msg = new HashMap<>();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        ResponseMsg responseMsg=new ResponseMsg();
        if (!checkAuthCode(request.getParameter("verify"), request)) {
            responseMsg.setCode(500);
            responseMsg.setMsg("校验码不匹配");
        } else if (request.getParameter("username") == null || request.getParameter("password") == null) {
            responseMsg.setCode(500);
            responseMsg.setMsg("用户名或密码不正确");
        } else {
            userDto.setUserName(request.getParameter("username"));
            userDto.setPassword(request.getParameter("password"));
            UserDto user = userService.login(userDto);
            if (user == null) {
                responseMsg.setCode(500);
                responseMsg.setMsg("用户信息不存在");
            } else {
                HttpSession session = request.getSession();
                user.setPassword(null);//为了安全，session里不存密码
                session.setAttribute("user", user);
                msg.put("user", user);
                responseMsg.setData((HashMap<String, Object>) msg);
            }
        }
        response.getWriter().write(responseMsg.toString());
    }

    /**
     * 打开personal页面层
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void personal(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        log.info(request.getContextPath()+"/jspPage/user/personal.jsp");
        HttpSession session=request.getSession();
        UserDto user = (UserDto) session.getAttribute("user");

        response.sendRedirect(request.getContextPath()+"/jspPage/user/personal.jsp");

    }

    /**
     * 修改密码
     * @param request
     * @param response
     * @throws IOException
     */
    public void updatePassWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session=request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        if (userDto==null){
            //如果session已过期，给出提示
            responseMsg.setCode(500);
            responseMsg.setMsg("用户信息已过期");
        }else {
            UserDto oldUser = userService.selectById(userDto.getUserId());
            if(oldUser ==null){
                responseMsg.setCode(500);
                responseMsg.setMsg("非法用户信息");
            }
            else if (request.getParameter("pwdOld")==null||request.getParameter("pwdOld").equals("")||request.getParameter("pwdNew")==null||request.getParameter("pwdNew").equals("")){
                responseMsg.setCode(500);
                responseMsg.setMsg("密码缺失");
            }
            else if (!oldUser.getPassword().equals(request.getParameter("pwdOld"))){
                //如果旧密码不匹配
                responseMsg.setCode(500);
                responseMsg.setMsg("旧密码错误");
            }
            else {
                UserDto user=new UserDto();
                user.setUserId(userDto.getUserId());
                user.setPassword(request.getParameter("pwdNew"));
                boolean success = userService.updatePassword(user);
                if (success){
                    responseMsg.setCode(200);
                    responseMsg.setMsg("修改成功");
                }
                else {
                    responseMsg.setCode(500);
                    responseMsg.setMsg("修改失败");
                }
            }
        }

        response.getWriter().write(responseMsg.toString());
    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     */
    public void updatePeronal(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session=request.getSession();
        UserDto userDto = (UserDto) session.getAttribute("user");
        Map<String, Object> msg = new HashMap<>();
        ResponseMsg responseMsg=new ResponseMsg();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        if (userDto==null){
            //如果session已过期，给出提示
            responseMsg.setCode(500);
            responseMsg.setMsg("用户信息已过期");
        }else {
            UserDto oldUser = userService.selectById(userDto.getUserId());
            if(oldUser ==null){
                responseMsg.setCode(500);
                responseMsg.setMsg("非法用户信息");
            }
            else {
                UserDto user=new UserDto();
                user.setUserId(Long.valueOf(request.getParameter("userId")));
                user.setName(request.getParameter("name"));
                user.setSex(Integer.valueOf(request.getParameter("sex")));
                if (request.getParameter("birth")!=null){
                    log.info(request.getParameter("birth"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    log.info(request.getParameter("birth"));

                    user.setBirth(sdf.parse(request.getParameter("birth")));
                }
                user.setMobile(request.getParameter("mobile"));
                user.setEmail(request.getParameter("email"));
                user.setProvince(request.getParameter("province"));
                user.setCity(request.getParameter("city"));
                user.setDistrict(request.getParameter("district"));
                user.setLiveAddress(request.getParameter("liveAddress"));
                boolean success = userService.updateBasicMesg(user);
                if (success){
                    responseMsg.setCode(200);
                    responseMsg.setMsg("修改成功");
                }
                else {
                    responseMsg.setCode(500);
                    responseMsg.setMsg("修改失败");
                }
            }
        }

        response.getWriter().write(responseMsg.toString());
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        response.sendRedirect(request.getContextPath());
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        generateCode(response, session);
        response.getOutputStream().flush();
    }

    /**
     * 生成验证码
     *
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
    private String generateCode(HttpServletResponse response, HttpSession session) throws IOException {
        int width = 120;
        int height = 36;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getColor(200, 250));
        g.setFont(new Font("Times New Roman", 0, 28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for (int i = 0; i < 40; i++) {
            g.setColor(this.getColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            strCode = strCode + rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 20 * i + 20, 28);
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("verify", strCode.toLowerCase());
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        return strCode;
    }

    private Color getColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 校验验证码
     *
     * @param inputCode
     * @param request
     * @return
     */
    private boolean checkAuthCode(String inputCode, HttpServletRequest request) {
        String checkCode = (String) request.getSession().getAttribute("verify");
        //返回1 代表判断通过,0代表失败
        boolean isCode = checkCode.equalsIgnoreCase(inputCode) ? true : false;
        return isCode;
    }


}
