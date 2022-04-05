package Dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public User getUserByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Query query = session.createQuery("From " + User.class.getSimpleName() + " u where u.login = : login");
        query.setParameter("login", login);
        List<User> users = query.getResultList();
        if (users.isEmpty()) return null;
        return users.get(0);
    }
}