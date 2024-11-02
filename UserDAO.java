/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {

    public User login(String username, String password) {
        String sql = "SELECT * FROM [User] where username = ? and password = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User s = new User();
                s.setUserName(rs.getString(1));
                s.setPassword(rs.getString(2));
                s.setDisplayName(rs.getString(3));
                s.setEid(rs.getString(4));
                EmployeeDAO ed = new EmployeeDAO();
                s.setEmployee(ed.getEmpById(rs.getString(4)));
                RoleDAO rd = new RoleDAO();
                List<Role> roles = rd.getRolesByUserName(rs.getString(1));
                s.setRoles(roles);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e);
        }
        return null;
    }
}
