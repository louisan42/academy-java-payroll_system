package com.bptn.services;

import com.bptn.models.Employee;
import com.bptn.models.User;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBManager {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static void createUser(User user) {
        Optional<Transaction> transaction = Optional.empty();
        // We are implementing try-with-resources since session implements AutoClosable
        try (Session session = DBManager.getSessionFactory().openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.persist(user);
            transaction.ifPresent(EntityTransaction::commit);
        } catch (NullPointerException e) {
            transaction.ifPresent(EntityTransaction::rollback);
            e.printStackTrace();
        }
    }

    public static User readUserById (int id) {
        // We are implementing try-with-resources since session implements AutoClosable
        try (Session session = DBManager.getSessionFactory().openSession()) {
            return session.get(User.class,id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static User readUserByUsername (String username) {
        System.out.println(username);
        String hql = "FROM User WHERE username = :username";
        // We are implementing try-with-resources since session implements AutoClosable
        try (Session session = DBManager.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Employee> getAllEmployees(){
        try (Session session = DBManager.getSessionFactory().openSession()) {
            return session.createQuery("FROM Employee",Employee.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static Employee getEmployeeById (int id) {
        // We are implementing try-with-resources since session implements AutoClosable
        try (Session session = DBManager.getSessionFactory().openSession()) {
            return session.get(Employee.class,id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
