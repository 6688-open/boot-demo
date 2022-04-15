package com.dj.boot.controller.other;

import org.springframework.http.HttpRequest;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ProjectName: demo
 * @PackageName: com.dj.boot.controller.other
 * @Author: wangJia
 * @Date: 2021-11-05-16-54
 */
@Component("/myController2")
public class MyController2 implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println("myController2");
    }
}
