/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import models.Department;
import models.Plan;
import models.PlanCampain;
import models.Product;

/**
 *
 * @author admin
 */
class ProductDAO extends DBContext {

    public Product getProdcutByID(int id) {
        String sql = "SELECT * FROM [Product] where 1=1 and pid = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product s = new Product();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));

                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error: get plan by id " + e);
        }
        return null;
    }
}
