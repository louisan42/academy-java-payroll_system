package com.bptn.services;

import com.bptn.models.User;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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

    public static User readuser (String username) {
        // We are implementing try-with-resources since session implements AutoClosable
        try (Session session = DBManager.getSessionFactory().openSession()) {
            return session.get(User.class,username);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
