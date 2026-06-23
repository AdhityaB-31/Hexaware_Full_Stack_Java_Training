package com.hexaware.springEMS.dao;

import java.util.List;

import com.hexaware.springEMS.entity.Employee;
import com.hexaware.springEMS.exceptions.EmployeeNotFoundException;

public interface IEmployeeDao {
	
	int addEmployee(Employee emp);

	int updateEmployee(Employee emp);

	int deleteEmployee(int eid);

	List<Employee> getAllEmployees();
	
	List<Employee> getBySalaryGT(double sal);
	
	  Employee      getByEid(int eid)  throws  EmployeeNotFoundException;

}
