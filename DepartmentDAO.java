package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Department;
import models.Employee;

public class DepartmentDAO extends DBContext {

    public List<Department> getDepartments() {
        List<Department> list = new ArrayList();
        String sql = "SELECT * FROM Department ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department s = new Department();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getDepartments: " + e);
        }
        return list;
    }

    public Department getDepartmentById(String id) {
        String sql = "SELECT * FROM Department where did = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Department s = new Department();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setType(rs.getString(3));
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error getDepartmentById: " + e);
        }
        return null;
    }

}
