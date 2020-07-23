package com.weison.io.net.sse;

import javax.servlet.AsyncContext;
import java.io.PrintWriter;
import java.util.Date;

public class AsyncWebService implements Runnable {
    AsyncContext ctx;

    public AsyncWebService(AsyncContext ctx) {
        this.ctx = ctx;
    }

    public void run() {
        try {
            //等待十秒钟，以模拟业务方法的执行
            Thread.sleep(10000);
            PrintWriter out = ctx.getResponse().getWriter();
            out.println("data:中文" + new Date() + "\r\n");  //js页面EventSource接收数据格式：data：数据 + "\r\n"

            out.flush();

            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
