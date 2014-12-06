package fr.flst.jee.mmarie.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Maximilien on 06/11/2014.
 */
@Slf4j
public class WebappServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.error("test");

        req.getRequestDispatcher("/index.html").forward(req, resp);
    }
}
