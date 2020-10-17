package com.bookmanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ServletReflect {

    /**
     * 通过反射获取到servlet的方法名
     * @param o
     * @param req
     * @param resp
     */
    protected static void dispatcher(Object o,HttpServletRequest req, HttpServletResponse resp){
        // 获取请求的URI地址信息
        String url = req.getRequestURI();
        // 截取其中的方法名
        String methodName = url.substring(url.lastIndexOf("/") + 1);
        Method method = null;
        try {
            // 使用反射机制获取在本类中声明了的方法
            method = o.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            // 执行方法
            method.invoke(o, req, resp);
        } catch (Exception e) {
            throw new RuntimeException("调用方法出错！");
        }
    }
}
