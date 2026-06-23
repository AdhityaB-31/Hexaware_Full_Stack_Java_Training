package com.hexaware.springJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.springJPA.entity.Passenger;
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

	public Passenger findByPassengerName(String passengerName);

	public List<Passenger> findByAgeGreaterThan(int age);

	public List<Passenger> findByPassengerNameContaining(String passengerName);
	
	public List<Passenger> findByPassengerIdGreaterThan(int passengerId);
	
	public List<Passenger> findByPassengerIdBetween(int initial,int fin);

	@Query("select  p from Passenger p  where p.age > ?1 order by p.age  ")
	public List<Passenger> getAllSortedByAge(int age);


	@Modifying
	@Query("update  Passenger p   set p.age = ?2 where p.passengerId = ?1")
	public void updatePassengerAge(int passengerId, int age);
	
	public List<Passenger> getAllAgeLT(int age);
	
	
	@Modifying
	@NativeQuery("update passenger_info set passenger_name=?2 where passenger_id=?1")
	public void updateName(int passengerId,String passengerName);
	
	

}
