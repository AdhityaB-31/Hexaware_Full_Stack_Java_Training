package com.hexaware.MySpring.DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hexaware.MySpring.Entity.*;

public class PassengerDAO{

	public int addPassenger(Passenger pr) {

		int count = 0;

		try {
			Connection conn = DBUtil.getDBConnection();

			String insert = "insert into employee values(?,?,?,?)";

			PreparedStatement pstmt = conn.prepareStatement(insert);

			pstmt.setInt(1, pr.getPassengerId());
			pstmt.setInt(2, pr.getAge());
			pstmt.setString(3,pr.getGender());
			pstmt.setString(4,pr.getPassengerName());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	/*
	 * @Override public int updateEmployee(Employee emp) { // TODO Auto-generated
	 * method stub
	 * 
	 * String update = "update employee set ename = ? , salary =?  where eid = ?";
	 * return 0; }
	 * 
	 * @Override public int deleteEmployee(int eid) {
	 * 
	 * int count = 0;
	 * 
	 * try { Connection conn = DBUtil.getDBConnection();
	 * 
	 * String delete = "delete from employee  where eid = ?";
	 * 
	 * PreparedStatement pstmt = conn.prepareStatement(delete);
	 * 
	 * pstmt.setInt(1, eid);
	 * 
	 * count = pstmt.executeUpdate();
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return count; }
	 * 
	 * @Override public List<Employee> getAllEmployees() {
	 * 
	 * List<Employee> list = new ArrayList<Employee>();
	 * 
	 * try { Connection conn = DBUtil.getDBConnection();
	 * 
	 * String select = "select * from employee";
	 * 
	 * PreparedStatement pstmt = conn.prepareStatement(select);
	 * 
	 * ResultSet rs = pstmt.executeQuery();
	 * 
	 * while (rs.next()) {
	 * 
	 * int eid = rs.getInt("eid"); double salary = rs.getDouble("salary"); String
	 * ename = rs.getString("ename"); Date doj = rs.getDate(4); // using column
	 * number in table ie. 4
	 * 
	 * Employee emp = new Employee(eid, ename, salary, doj);
	 * 
	 * list.add(emp);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * return list; }
	 * 
	 * @Override public List<Employee> getBySalaryGT(double sal) {
	 * 
	 * 
	 * return null; }
	 * 
	 * @Override public Employee getByEid(int eid) throws EmployeeNotFoundException
	 * {
	 * 
	 * Employee emp = null;
	 * 
	 * try { Connection conn = DBUtil.getDBConnection();
	 * 
	 * String select = "select * from employee where eid = ?";
	 * 
	 * PreparedStatement pstmt = conn.prepareStatement(select);
	 * 
	 * pstmt.setInt(1, eid);
	 * 
	 * ResultSet rs = pstmt.executeQuery();
	 * 
	 * if (rs.next()) {
	 * 
	 * int eid1 = rs.getInt("eid"); double salary = rs.getDouble("salary"); String
	 * ename = rs.getString("ename"); Date doj = rs.getDate(4); // using column
	 * number in table ie. 4
	 * 
	 * emp = new Employee(eid1, ename, salary, doj);
	 * 
	 * 
	 * 
	 * }
	 * 
	 * else {
	 * 
	 * 
	 * throw new EmployeeNotFoundException();
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * 
	 * return emp; }
	 */

}
