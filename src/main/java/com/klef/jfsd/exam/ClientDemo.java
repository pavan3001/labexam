package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ClientDemo {

    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        // Insert a record
        insertDepartment(factory);

        // Delete a record by Department ID
        deleteDepartment(factory, 1);

        // Close SessionFactory
        factory.close();
    }

    private static void insertDepartment(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Create a new Department object
        Department dept = new Department("IT", "New York", "John Doe");
        session.persist(dept);

        tx.commit();
        System.out.println("Record inserted successfully.");
        session.close();
    }

    private static void deleteDepartment(SessionFactory factory, int deptId) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        // Use HQL to delete the record with a positional parameter
        String hql = "DELETE FROM Department WHERE id = ?1";
        Query<?> query = session.createQuery(hql);
        query.setParameter(1, deptId);

        int result = query.executeUpdate();
        if (result > 0) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("No record found with the given Department ID.");
        }

        tx.commit();
        session.close();
    }
}
