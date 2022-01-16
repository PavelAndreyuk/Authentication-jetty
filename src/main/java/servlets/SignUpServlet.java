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

public class SignUpServlet extends HttpServlet {
    private final UserDAO userDAO;

    static {
        try {
            String DB_Driver = "org.h2.Driver";
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test1", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SignUpServlet(UserDAO userDAO) {
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

        UserEntity profile = new UserEntity(login, password);
        try {
            userDAO.addNewUser(profile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("succesful_registration.html");
    }
}
