package com.hexaware.springEMS.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.hexaware.springEMS.dao.*;
import com.hexaware.springEMS.entity.Employee;
import com.hexaware.springEMS.exceptions.EmployeeNotFoundException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
@Scope("singleton")
public class EmployeeDaoImp implements IEmployeeDao {
	
	@Autowired
	DBUtil db;

	@Override
	public int addEmployee(Employee emp) {

		int count = 0;

		try {
			Connection conn = db.getDBConnection();

			String insert = "insert into employee values(?,?,?)";

			PreparedStatement pstmt = conn.prepareStatement(insert);

			pstmt.setInt(1, emp.getEid());
			pstmt.setString(2, emp.getEname());
			pstmt.setDouble(3, emp.getSalary());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public int updateEmployee(Employee emp) {
		// TODO Auto-generated method stub
		try {
			Connection con=db.getDBConnection();
			String update = "update employee set ename = ? , salary =?  where eid = ?";
			PreparedStatement pstmt=con.prepareStatement(update);
			pstmt.setString(1, emp.getEname());
			pstmt.setDouble(2, emp.getSalary());
			pstmt.setInt(3, emp.getEid());
			
			int count=pstmt.executeUpdate();
			
			return count;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		 
		return 0;
	}

	@Override
	public int deleteEmployee(int eid) {

		int count = 0;

		try {
			Connection conn = db.getDBConnection();

			String delete = "delete from employee  where eid = ?";

			PreparedStatement pstmt = conn.prepareStatement(delete);

			pstmt.setInt(1, eid);

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public List<Employee> getAllEmployees() {

		List<Employee> list = new ArrayList<Employee>();

		try {
			Connection conn = db.getDBConnection();

			String select = "select * from employee";

			PreparedStatement pstmt = conn.prepareStatement(select);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int eid = rs.getInt("eid");
				double salary = rs.getDouble("salary");
				String ename = rs.getString("ename");

				Employee emp = new Employee(eid, ename, salary);

				list.add(emp);

			}

		

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Employee> getBySalaryGT(double sal) {


		return null;
	}

	@Override
	public Employee getByEid(int eid) throws EmployeeNotFoundException {

		Employee emp = null;

		try {
			Connection conn = db.getDBConnection();

			String select = "select * from employee where eid = ?";

			PreparedStatement pstmt = conn.prepareStatement(select);
			
					pstmt.setInt(1, eid);

			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {

				int eid1 = rs.getInt("eid");
				double salary = rs.getDouble("salary");
				String ename = rs.getString("ename");
				emp = new Employee(eid1, ename, salary);

			

			}
			
			else {
				
				
				throw  new EmployeeNotFoundException();
				
				
			}
			  
			  
			  
			

		}
		catch (SQLException e) {

				e.printStackTrace();
		}
		
		
		
		return emp;
	}

}
