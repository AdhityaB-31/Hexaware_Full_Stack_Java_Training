package com.hexaware.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hexaware.hibernate.HibernateUtil;
import com.hexaware.hibernate.entity.Passenger;

public class PassengerDAO {

	public List<Passenger> getAllPassengers() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Query<Passenger> query = session.createNamedQuery("getAllPassengers", Passenger.class);

			return query.getResultList();

		} finally {
			if (session != null)
				session.close();
		}
	}

	public long getNumOfPassengers() {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;

		try {
			session = sessionFactory.openSession();

			Query<Long> query = session.createNamedQuery("getNumOfPassengers", Long.class);

			return query.getSingleResult();

		} finally {
			if (session != null)
				session.close();
		}
	}

	public String addPassenger(Passenger passenger) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tr = null;

		try {

			session = sessionFactory.openSession();
			tr = session.beginTransaction();

			Serializable generatedId = session.save(passenger);

			tr.commit();

			return generatedId + " Value is added";

		} catch (Exception e) {

			if (tr != null)
				tr.rollback();

			throw e;

		} finally {

			if (session != null)
				session.close();
		}
	}

	public String updatePassenger(Passenger passenger) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tr = null;

		try {

			session = sessionFactory.openSession();
			tr = session.beginTransaction();

			session.update(passenger);

			tr.commit();

			return "Passenger updated successfully";

		} catch (Exception e) {

			if (tr != null)
				tr.rollback();

			throw e;

		} finally {

			if (session != null)
				session.close();
		}
	}

	public String deletePassenger(int passengerId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		Transaction tr = null;

		try {
			session = sessionFactory.openSession();
			tr = session.beginTransaction();
			Passenger pr = session.get(Passenger.class, passengerId);
			session.remove(pr);
			tr.commit();

			return "Passenger Deleted Successfully";

		} catch (Exception e) {
			if (tr != null)
				tr.rollback();
			throw e;
		}
	}

	public Passenger getPassengerById(int passengerId) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		// HQL

		String selectQuery = "select  p from Passenger p where p.passengerId=?1";

		Query<Passenger> query = session.createQuery(selectQuery);

		query.setParameter(1, passengerId);

		Passenger pr = query.getSingleResult();

		return pr;

	}
}