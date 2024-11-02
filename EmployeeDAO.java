/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Department;
import models.Employee;
import models.Role;

/**
 *
 * @author admin
 */
public class EmployeeDAO extends DBContext {

    public List<Employee> getEmpList(String name) {
        List<Employee> list = new ArrayList();
        String sql = "SELECT * FROM Employee ";
        if (name != null && !name.isEmpty()) {
            sql += "where ename like ?";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            if (name != null && !name.isEmpty()) {
                ps.setString(1, "%" + name + "%");
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee s = new Employee();
                s.setEid(rs.getString(1));
                s.setEname(rs.getString(2));
                s.setSalaryLevel(rs.getString(3));
                s.setDid(rs.getInt(4));
                s.setCreatedBy(rs.getString(5));
                DepartmentDAO dd = new DepartmentDAO();
                Department d = dd.getDepartmentById(rs.getInt(4) + "");
                s.setDepartment(d);
                Employee e = getEmpById(rs.getString(5));
                s.setCreatedUser(e);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getEmp: " + e);
        }
        return list;
    }

    public Employee getEmpById(String id) {
        String sql = "SELECT * FROM Employee where eid = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Employee s = new Employee();
                s.setEid(rs.getString(1));
                s.setEname(rs.getString(2));
                s.setSalaryLevel(rs.getString(3));
                s.setDid(rs.getInt(4));
                s.setCreatedBy(rs.getString(5));
                DepartmentDAO dd = new DepartmentDAO();
                Department d = dd.getDepartmentById(rs.getInt(4) + "");
                s.setDepartment(d);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error getEmp: " + e);
        }
        return null;
    }

    public void addEmp(Employee employee) {
        String insertSQL = "INSERT INTO Employee (eid, ename, salaryLevel, did, createdby) VALUES (?, ?, ?, ?, ?)";

        try {
            // Step 2: Thêm nhân viên mới với EmployeeID mới
            PreparedStatement psInsert = connection.prepareStatement(insertSQL);
            psInsert.setString(1, employee.getEid());
            psInsert.setString(2, employee.getEname());
            psInsert.setString(3, employee.getSalaryLevel());
            psInsert.setInt(4, employee.getDid());
            psInsert.setString(5, employee.getCreatedBy());

            psInsert.executeUpdate();
            psInsert.close();

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e);
        }
    }

    public void editEmp(Employee employee) {
        String sql = "UPDATE Employee SET ename = ?, salaryLevel = ?,"
                + " did = ? "
                + "WHERE eid = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, employee.getEname());
            ps.setString(2, employee.getSalaryLevel());
            ps.setInt(3, employee.getDid());
            ps.setString(4, employee.getEid());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error editing employee: " + e);
        }
    }

    public void deleteEmp(String employeeId) {
        String getSchedualEmpSQL = "SELECT wsid FROM WorkerSchedule WHERE eid = ?";
        String deleteAttendanceSQL = "DELETE FROM Attendence WHERE wsid = ?";
        String deleteSchedualEmpSQL = "DELETE FROM WorkerSchedule WHERE eid = ?";
        String deleteEmployeeSQL = "DELETE FROM Employee WHERE eid = ?";

        try {
            // Step 1: Lấy danh sách SchEmpID từ bảng SchedualEmployee dựa vào EmployeeID
            PreparedStatement psGetSchedualEmp = connection.prepareStatement(getSchedualEmpSQL);
            psGetSchedualEmp.setString(1, employeeId);
            ResultSet rs = psGetSchedualEmp.executeQuery();

            List<Integer> schEmpIds = new ArrayList<>();
            while (rs.next()) {
                schEmpIds.add(rs.getInt("wsid"));
            }
            rs.close();
            psGetSchedualEmp.close();

            // Step 2: Xóa các record trong bảng Attendance dựa vào SchEmpID
            PreparedStatement psDeleteAttendance = connection.prepareStatement(deleteAttendanceSQL);
            for (int schEmpId : schEmpIds) {
                psDeleteAttendance.setInt(1, schEmpId);
                psDeleteAttendance.executeUpdate();
            }
            psDeleteAttendance.close();

            // Step 3: Xóa các record trong bảng SchedualEmployee dựa vào EmployeeID
            PreparedStatement psDeleteSchedualEmp = connection.prepareStatement(deleteSchedualEmpSQL);
            psDeleteSchedualEmp.setString(1, employeeId);
            psDeleteSchedualEmp.executeUpdate();
            psDeleteSchedualEmp.close();

            // Step 4: Xóa record trong bảng Employee dựa vào EmployeeID
            PreparedStatement psDeleteEmployee = connection.prepareStatement(deleteEmployeeSQL);
            psDeleteEmployee.setString(1, employeeId);
            psDeleteEmployee.executeUpdate();
            psDeleteEmployee.close();

        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e);
        }
    }

}
