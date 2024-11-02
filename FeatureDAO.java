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

/**
 *
 * @author admin
 */
class FeatureDAO extends DBContext {

    public List<Feature> getFeaturesByRoleId(int id) {
        List<Feature> list = new ArrayList();
        String sql = "SELECT f.FeatureID, f.FeatureName, f.url "
                + " FROM RoleFeature rf join Feature f on rf.FeatureID = f.FeatureID"
                + " where rf.RoleID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feature s = new Feature();
                s.setFeatureID(rs.getInt(1));
                s.setFeatureName(rs.getString(2));
                s.setUrl(rs.getString(3));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getFeaturesByRoleId: " + e);
        }
        return list;
    }
}
