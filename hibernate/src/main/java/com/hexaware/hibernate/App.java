package com.hexaware.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.hexaware.hibernate.entity.*;
import com.hexaware.hibernate.dao.*;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Hibernate");

		boolean iscontinue = true;
		while (iscontinue) {
			System.out.println("1. Add Passenger");
			System.out.println("2. Get All Passenger");
			System.out.println("3. Get count of all Passenger");
			System.out.println("4. Update Passenger");
			System.out.println("5. Delete Passenger");
			System.out.println("6. Get Passenger By ID: ");
			
			
			int choice = Integer.parseInt(sc.nextLine());
			PassengerDAO dao = new PassengerDAO();
			switch (choice) {
			case 1:
				Passenger pr = readData();

				String result = dao.addPassenger(pr);
				System.out.println(result);
				break;
			case 2:
				List<Passenger> list = dao.getAllPassengers();

				list.forEach(System.out::println);
				break;

			case 3:
				long count = dao.getNumOfPassengers();
				System.out.println("Total Count: " + count);
				break;

			case 4:

				System.out.print("Enter Passenger ID: ");
				String input = sc.nextLine();

				int passengerId=Integer.parseInt(input);

				Session session = HibernateUtil.getSessionFactory().openSession();

				Passenger passenger = session.get(Passenger.class, passengerId);

				session.close();

				if (passenger == null) {
					System.out.println("Passenger Not Found");
					break;
				}

				System.out.print("Enter Name (Enter to Skip): ");
				String name = sc.nextLine();

				if (!name.trim().isEmpty())
					passenger.setPassengerName(name);

				System.out.print("Enter Age (Enter to Skip): ");
				String age = sc.nextLine();

				if (!age.trim().isEmpty())
					passenger.setAge(Integer.parseInt(age));

				System.out.print("Enter Gender (Enter to Skip): ");
				String gender = sc.nextLine();

				if (!gender.trim().isEmpty())
					passenger.setGender(gender);

				System.out.print("Enter Seat Fare (Enter to Skip): ");
				String fare = sc.nextLine();

				if (!fare.trim().isEmpty())
					passenger.setSeatFare(new BigDecimal(fare));

				System.out.print("Enter Booking ID (Enter to Skip): ");
				String booking = sc.nextLine();

				if (!booking.trim().isEmpty())
					passenger.setBookingId(Integer.parseInt(booking));

				System.out.print("Enter Seat ID (Enter to Skip): ");
				String seat = sc.nextLine();

				if (!seat.trim().isEmpty())
					passenger(Integer.parseInt(seat));

				System.out.println(dao.updatePassenger(passenger));

				break;
				
			case 5:
				System.out.print("Enter Passenger ID: ");

				int Id=Integer.parseInt(sc.nextLine());
				
				System.out.println(dao.deletePassenger(Id));
				
			case 6:
				System.out.print("Enter Passenger ID: ");
				int Pid=Integer.parseInt(sc.nextLine());
				System.out.println(dao.getPassengerById(Pid));
				break;
				

			default:
				iscontinue = false;

			}
		}

	}

	public static Passenger readData() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your Booking_Id: ");
		int bookingId = Integer.parseInt(sc.nextLine());
		System.out.print("Please enter your preferred seat ID: ");
		int seatId = Integer.parseInt(sc.nextLine());
		System.out.print("Please Enter Your Name: ");
		String name = sc.nextLine();
		System.out.print("Please Enter Your Email: ");
		String email=sc.nextLine();
		System.out.print("Please Enter Your Age: ");
		int age = Integer.parseInt(sc.nextLine());
		System.out.print("Please Specify Your Gender: ");
		String gender = sc.nextLine();
		System.out.print("Please Enter Your Seat Fare: ");
		BigDecimal seatfare = BigDecimal.valueOf(Integer.parseInt(sc.nextLine()));

		Passenger pr = new Passenger(bookingId, seatId, name,email, age, gender, seatfare);

		return pr;
	}
}
