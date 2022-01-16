package servlets;

import DAO.UserDAO;
import entities.UserEntity;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SignInServlet extends HttpServlet {
    private final UserDAO userDAO;

    static {
        try {
            String DB_Driver = "org.h2.Driver";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test1", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SignInServlet(UserDAO userDAO) {
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

        UserEntity profile = null;
        try {
            profile = userDAO.getUserByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (profile == null || !profile.getPass().equals(password)) {
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
