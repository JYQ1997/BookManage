package com.bookmanage.controller;

import com.bookmanage.dto.UserDto;
import com.bookmanage.service.UserService;

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
import java.util.Random;

/**
 * @Author YongQiang
 * @Date 2020/10/13 18:51
 * @Version 1.0
 */
public class UserServlet extends HttpServlet {


    private static final char[] codeSequence = {'A', '1', 'B', 'C', '2', 'D', '3', 'E', '4', 'F', '5', 'G', '6', 'H', '7', 'I',
            '8',
            'J',
            'K', '9', 'L', '1', 'M', '2', 'N', 'P', '3', 'Q', '4', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z'};

    private static UserService userService=new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求的URI地址信息
        String url = req.getRequestURI();
        // 截取其中的方法名
        String methodName = url.substring(url.lastIndexOf("/") + 1);
        Method method = null;
        try {
            // 使用反射机制获取在本类中声明了的方法
            method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 执行方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            throw new RuntimeException("调用方法出错！");
        }
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDto userDto = new UserDto();
        if (!checkAuthCode(request.getParameter("verify"),request)){
            request.setAttribute("error","校验码不匹配");
            request.getRequestDispatcher( "/jspPage/Home.html ").forward(request,response);
        }
        else if (request.getParameter("username") == null || request.getParameter("password") == null) {
            request.setAttribute("error","用户名或密码不正确");
            request.getRequestDispatcher( "/jspPage/Home.html ").forward(request,response);
        }
        else {
            userDto.setUserName(request.getParameter("username"));
            userDto.setPassword(request.getParameter("password"));
            UserDto user= userService.login(userDto);
            if (user==null){
                request.setAttribute("error","用户信息不存在");
                request.getRequestDispatcher( "/jspPage/Home.html ").forward(request,response);
            }
            else {
                HttpSession session=request.getSession();
                session.setAttribute("user",user);
                RequestDispatcher dispathcer = request.getRequestDispatcher("/index");
                dispathcer.forward(request,response);
            }
        }
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
            g.drawString(rand, 20 * i+20 , 28);
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
