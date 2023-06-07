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
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (" +
                            "id MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(300) NOT NULL, " +
                            "lastName VARCHAR(300) NOT NULL, " +
                            "age TINYINT(3) NOT NULL, " +
                            "PRIMARY KEY(id)) " +
                            "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;")
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User")
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("DELETE FROM User WHERE id=:id")
                    .setParameter("id", id)
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("TRUNCATE User")
                    .executeUpdate();
            t.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
