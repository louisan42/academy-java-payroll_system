package com.bptn.services;

import com.bptn.App;
import com.bptn.models.*;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.*;

public class DBManager {

    private final SessionFactory sessionFactory;

    public DBManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return buildSessionFactory();
    }

    public void createUser(User user) {
        Optional<Transaction> transaction = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.persist(user);
            transaction.ifPresent(EntityTransaction::commit);
        } catch (NullPointerException e) {
            transaction.ifPresent(EntityTransaction::rollback);
            e.printStackTrace();
        }
    }

    public User readUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public User readUserByUsername(String username) {
        String hql = "FROM User WHERE username = :username";
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Employee", Employee.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee getEmployeeById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Employee.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateEmployee(Map<String, Object> updatedFields, int id) {
        boolean success = false;
        Optional<Transaction> transaction = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            Employee emp = getEmployeeById(id);
            if (emp == null) {
                App.getLogger().warning("Employee with id " + id + " not found.");
            }
            if (emp != null && !emp.compareFields(updatedFields)) {
                updatedFields.forEach((key, value) -> {
                    switch (key) {
                        case "email":
                            emp.setEmail((String) value);
                            break;
                        case "gender":
                            emp.setGender((String) value);
                            break;
                        case "startDate":
                            emp.setStartDate((LocalDate) value);
                            break;
                        case "salary_id":
                            emp.setSalary(session.get(Salary.class, (int) value));
                            break;
                    }
                });
                session.merge(emp);
                transaction.ifPresent(EntityTransaction::commit);
                success = true;
            } else {
                transaction.ifPresent(EntityTransaction::rollback);
            }
        } catch (NullPointerException e) {
            transaction.ifPresent(EntityTransaction::rollback);
            App.getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
        return success;
    }

    public Salary getSalaryById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Salary.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public  List<Salary> getAllSalaries() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Salary", Salary.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Department> getAllDepartments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Department", Department.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteEmployeeById (int employeeId) {
        boolean success = false;
        Optional<Transaction> transaction = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            Employee emp = getEmployeeById(employeeId);
            if (emp != null) {
                session.remove(emp);
                transaction.ifPresent(EntityTransaction::commit);
                success = true;
            } else {
                transaction.ifPresent(EntityTransaction::rollback);
            }
        } catch (NullPointerException e) {
            transaction.ifPresent(EntityTransaction::rollback);
            App.getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
        return success;
    }

    public boolean addStatement(Statement statement) {
        boolean success = false;
        Optional<Transaction> transaction = Optional.empty();
        try (Session session = sessionFactory.openSession()) {
            transaction = Optional.ofNullable(session.beginTransaction());
            session.persist(statement);
            transaction.ifPresent(EntityTransaction::commit);
            success = true;
        } catch (NullPointerException e) {
            transaction.ifPresent(EntityTransaction::rollback);
            App.getLogger().severe(Arrays.toString(e.getStackTrace()));
        }
        return success;
    }

    public Statement getStatementById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Statement.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}