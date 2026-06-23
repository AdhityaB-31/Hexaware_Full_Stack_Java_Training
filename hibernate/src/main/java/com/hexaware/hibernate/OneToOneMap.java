package com.hexaware.hibernate;

import java.util.Arrays;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hexaware.hibernate.entity.Booking;
import com.hexaware.hibernate.entity.Passenger;

public class OneToOneMap {

    public static void main(String[] args) {

        SessionFactory sessionFactory =
                HibernateUtil.getSessionFactory();

        Session session =
                sessionFactory.openSession();

        Transaction tr =
                session.beginTransaction();

        try {

            // Create Booking

            Booking booking = new Booking();

            booking.setTotalFare(1500);

            // Create Passenger 1

            Passenger p1 = new Passenger();

            p1.setPassengerName("Adhitya");
            p1.setAge(21);
            p1.setGender("MALE");

            // Create Passenger 2

            Passenger p2 = new Passenger();

            p2.setPassengerName("Rahul");
            p2.setAge(24);
            p2.setGender("MALE");

            // Establish Relationship

            p1.setBooking(booking);
            p2.setBooking(booking);

            booking.setPassengers(
                    Arrays.asList(p1, p2));

            // Save Parent

            session.save(booking);

            tr.commit();

            System.out.println(
                    "Booking and Passengers Saved Successfully");

        } catch (Exception e) {

            tr.rollback();

            e.printStackTrace();
        }

        session.close();

        // Fetch Data

        Session session2 =
                sessionFactory.openSession();

        Booking booking =
                session2.get(
                        Booking.class,
                        1);

        if (booking != null) {

            System.out.println(
                    "\nBooking ID : "
                    + booking.getBookingId());

            System.out.println(
                    "Total Fare : "
                    + booking.getTotalFare());

            System.out.println(
                    "\nPassenger Details");

            for (Passenger passenger :
                    booking.getPassengers()) {

                System.out.println(passenger);
            }

        } else {

            System.out.println(
                    "Booking Not Found");
        }

        session2.close();

        sessionFactory.close();
    }
}