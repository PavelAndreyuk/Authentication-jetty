package servlets;

import Dao.UserDao;
import models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    private final UserDao userDAO;

    public SignUpServlet(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User profile = new User(login, password);
        userDAO.save(profile);
        response.sendRedirect("succesful_registration.html");
    }
}
