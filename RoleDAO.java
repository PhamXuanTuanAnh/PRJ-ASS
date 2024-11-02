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
import models.Feature;
import models.Role;

/**
 *
 * @author admin
 */
public class RoleDAO extends DBContext {

    public List<Role> getRolesByUserName(String username) {
        List<Role> list = new ArrayList();
        String sql = "SELECT r.roleId, r.roleName "
                + " FROM [UserRole] ur join Role r on ur.roleId = r.roleId"
                + " where username = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role s = new Role();
                s.setRoleID(rs.getInt(1));
                s.setRoleName(rs.getString(2));
                FeatureDAO fd = new FeatureDAO();
                List<Feature> fs = fd.getFeaturesByRoleId(rs.getInt(1));
                s.setFeatures(fs);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getRolesByUserName: " + e);
        }
        return list;
    }

    public Role getRoleById(int roleId) {
        String sql = "SELECT r.roleId, r.RoleName "
                + " FROM [Role] r left join RoleFeature rf on r.roleId = rf.roleId"
                + " where r.roleId = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Role s = new Role();
                s.setRoleID(rs.getInt(1));
                s.setRoleName(rs.getString(2));
                FeatureDAO fd = new FeatureDAO();
                List<Feature> fs = fd.getFeaturesByRoleId(rs.getInt(1));
                s.setFeatures(fs);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error getRolesByUserName: " + e);
        }
        return null;
    }

    public List<Role> getRoles() {
        List<Role> list = new ArrayList();
        String sql = "SELECT * from Role";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Role s = new Role();
                s.setRoleID(rs.getInt(1));
                s.setRoleName(rs.getString(2));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getRoles: " + e);
        }
        return list;
    }
}
