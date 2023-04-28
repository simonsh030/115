package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            Transaction t = SESSION.beginTransaction();
            SESSION.createSQLQuery("CREATE TABLE IF NOT EXISTS User (" +
                            "id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(300) NOT NULL, " +
                            "lastName VARCHAR(300) NOT NULL, " +
                            "age TINYINT(3) NOT NULL, " +
                            "PRIMARY KEY(id)) " +
                            "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;")
                    .executeUpdate();
            t.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            Transaction t = SESSION.beginTransaction();
            SESSION.createSQLQuery("DROP TABLE IF EXISTS User")
                    .executeUpdate();
            t.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            t = SESSION.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            SESSION.save(user);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            Transaction t = SESSION.beginTransaction();
            SESSION.createSQLQuery("DELETE FROM User WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            t.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            return SESSION.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session SESSION = Util.getSessionFactory().openSession()) {
            Transaction t = SESSION.beginTransaction();
            SESSION.createSQLQuery("TRUNCATE User")
                    .executeUpdate();
            t.commit();
        }
    }
}