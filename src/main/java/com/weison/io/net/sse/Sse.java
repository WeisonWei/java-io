package com.weison.io.net.sse;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * https://www.cnblogs.com/shown/p/6554477.html
 */
public class Sse extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final static int DEFAULT_TIME_OUT = 10 * 60 * 1000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);//注意这里

        AsyncContext actx = req.startAsync(req, resp);
        actx.setTimeout(DEFAULT_TIME_OUT);
        actx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event complete:" + arg0.getSuppliedRequest().getRemoteAddr());
            }

            @Override
            public void onError(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event has error");
            }

            @Override
            public void onStartAsync(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event start:" + arg0.getSuppliedRequest().getRemoteAddr());
            }

            @Override
            public void onTimeout(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event time lost");
            }
        });
        new Thread(new AsyncWebService(actx)).start();
    }
}