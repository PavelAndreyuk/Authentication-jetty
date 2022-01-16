package DAO;

import entities.UserEntity;

import java.sql.*;

public class UserDAO {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test1", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDAO() {
    }

    public void addNewUser(UserEntity userEntity) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "INSERT INTO Users(login, password) VALUES('" + userEntity.getLogin() + "', '" + userEntity.getPass() + "')";
        statement.executeUpdate(sql);
        statement.close();
    }

    public UserEntity getUserByLogin(String login) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM Users WHERE login = '" + login + "'";
        ResultSet rs = statement.executeQuery(sql);
        String login1 = "";
        String password = "";
        while (rs.next()) {
             login1 = rs.getString("login");
             password = rs.getString("password");
        }
        return new UserEntity(login1, password);
    }
}