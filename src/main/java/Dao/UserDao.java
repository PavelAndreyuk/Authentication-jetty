package Dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import utils.HibernateSessionFactoryUtil;

import javax.persistence.Query;
import java.sql.*;
import java.util.List;

public class UserDao {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:h2:~/test1", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void addNewUser(User user) throws SQLException {
//        Statement statement = connection.createStatement();
//        String sql = "INSERT INTO Users(login, password) VALUES('" + user.getLogin() + "', '" + user.getPassword() + "')";
//        statement.executeUpdate(sql);
//        statement.close();
//    }

//    public User getUserByLogin(String login) throws SQLException {
//        Statement statement = connection.createStatement();
//        String sql = "SELECT * FROM Users WHERE login = '" + login + "'";
//        ResultSet rs = statement.executeQuery(sql);
//        String login1 = "";
//        String password = "";
//        while (rs.next()) {
//             login1 = rs.getString("login");
//             password = rs.getString("password");
//        }
//        return new User(login1, password);
//    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public User getUserByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
//        User user = session.get(User.class, login);
//        session.close();
//        return user;
//        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
//        criteria.add(Restrictions.eq("login", login));
        Query query = session.createQuery("From " + User.class.getSimpleName() + " u where u.login = : login");
        query.setParameter("login", login);
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        return users.get(0);
    }
}