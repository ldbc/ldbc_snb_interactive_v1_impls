package com.hp.hpl.livegraph;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//public class Main {
//    public static void main(String[] args) throws Exception {
//        // Create a basic jetty server object that will listen on port 8080.  Note that if you set this to port 0
//        // then a randomly available port will be assigned that you can either look in the logs for the port,
//        // or programmatically obtain it for use in test cases.
//        Server server = new Server(8080);
//
//        // The ServletHandler is a dead simple way to create a context handler that is backed by an instance of a
//        // Servlet.  This handler then needs to be registered with the Server object.
//        ServletHandler handler = new ServletHandler();
//        server.setHandler(handler);
//
//        // Passing in the class for the servlet allows jetty to instantite an instance of that servlet and mount it
//        // on a given context path.
//
//        // !! This is a raw Servlet, not a servlet that has been configured through a web.xml or anything like that !!
//        handler.addServletWithMapping(HelloServlet.class, "/servlet/*");
//
//        ResourceHandler res = new ResourceHandler();
//        File webDir = new File("control_panel/web");
//        res.setResourceBase(webDir.getAbsolutePath());
//        ContextHandler root = new ContextHandler("/");
//        root.setHandler(res);
//        HandlerList list = new HandlerList();
//        list.addHandler(root);
//        list.addHandler(handler);
//        server.setHandler(list);
//
//        try {
//            // Start things up! By using the server.join() the server thread will join with the current thread.
//            // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
//            server.start();
//            server.join();
//        } catch (Exception e) {
//            System.err.println("Exception while running service");
//            e.printStackTrace();
//        }
//    }
//
//    public static class HelloServlet extends HttpServlet {
//        @Override
//        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            response.setContentType("text/html");
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().println("<h1>Hello SimpleServlet</h1>");
//        }
//    }
//}
public class CPjettyAppMain extends AbstractHandler {

@Override
public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>Hello World</h1>");
        }

public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new CPjettyAppMain());
        //server.start();
        server.join();
        }
}