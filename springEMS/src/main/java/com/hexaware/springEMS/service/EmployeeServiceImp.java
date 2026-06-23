package com.hexaware.springEMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hexaware.springEMS.dao.EmployeeDaoImp;
import com.hexaware.springEMS.dao.IEmployeeDao;
import com.hexaware.springEMS.entity.Employee;
import com.hexaware.springEMS.exceptions.EmployeeNotFoundException;

@Service
@Scope("singleton")
public class EmployeeServiceImp  implements IEmployeeService{
	
	@Autowired
	 IEmployeeDao dao;

	@Override
	public int addEmployee(Employee emp) {
	
		return dao.addEmployee(emp);
	}

	@Override
	public int updateEmployee(Employee emp) {
		
		return  dao.updateEmployee(emp);
	}

	@Override
	public int deleteEmployee(int eid) {
	
		return dao.deleteEmployee(eid);
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return dao.getAllEmployees();
	}

	@Override
	public List<Employee> getBySalaryGT(double sal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByEid(int eid) throws EmployeeNotFoundException {


		return dao.getByEid(eid);
	}
	
	
	
	
	    public static boolean   validateData(Employee emp) {
	    	
	    	boolean flag = false;
	    	
	    	if(emp.getEid() > 99 &&  emp.getEname().length()  > 3 && emp.getSalary() > 5000) {
	    		
	    		
	    		flag = true;
	    		
	    	}
	    	
	    	return flag;
	    	
	    	
	    }
	
	
	
	

}
