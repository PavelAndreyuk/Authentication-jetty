package servlets;

import Dao.UserDao;
import models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final UserDao userDAO;

    public SignInServlet(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User profile = userDAO.getUserByLogin(login);
        if (profile == null || !profile.getPassword().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("You are entered as: " +
                "\nlogin: " + profile.getLogin());
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
