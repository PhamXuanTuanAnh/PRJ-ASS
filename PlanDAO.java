/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Department;
import models.Feature;
import models.Plan;
import models.PlanCampain;
import models.Role;
import models.ScheduleCampain;

/**
 *
 * @author admin
 */
public class PlanDAO extends DBContext {

    public List<Plan> getPlans(String startDate, String endDate) {
        List<Plan> list = new ArrayList();

        String sql = "SELECT * FROM [Plan] where 1=1 ";
        if (startDate != null && !startDate.isEmpty()) {
            sql += " and startd >= ?";
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql += " and endd <= ?";
        }
        System.out.println(sql);
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            int count = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp với CSDL
            if (startDate != null && !startDate.isEmpty()) {
                java.util.Date parsedStartDate = sdf.parse(startDate); // Chuyển String thành java.util.Date
                ps.setDate(++count, new java.sql.Date(parsedStartDate.getTime())); // Chuyển thành java.sql.Date
            }
            if (endDate != null && !endDate.isEmpty()) {
                java.util.Date parsedEndDate = sdf.parse(endDate); // Chuyển String thành java.util.Date
                ps.setDate(++count, new java.sql.Date(parsedEndDate.getTime())); // Chuyển thành java.sql.Date
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plan s = new Plan();
                s.setId(rs.getInt(1));
                s.setStartDate(rs.getDate(2));
                s.setEndDate(rs.getDate(3));
                s.setDepartmentID(rs.getInt(4));

                DepartmentDAO dd = new DepartmentDAO();
                Department d = dd.getDepartmentById(rs.getInt(4) + "");
                s.setDepartment(d);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error: getPlans " + e);
        } catch (ParseException ex) {
            Logger.getLogger(PlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Plan getPlanById(String id) {
        String sql = "SELECT * FROM [Plan] where 1=1 and plid = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plan s = new Plan();
                s.setId(rs.getInt(1));
                s.setStartDate(rs.getDate(2));
                s.setEndDate(rs.getDate(3));
                s.setDepartmentID(rs.getInt(4));
                DepartmentDAO dd = new DepartmentDAO();
                Department d = dd.getDepartmentById(rs.getInt(4) + "");
                s.setDepartment(d);
                List<PlanCampain> cams = getPlanCampainByPlanID(rs.getInt(1));
                s.setPlancampains(cams);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("Error: get plan by id " + e);
        }
        return null;
    }

    public void create( String start, String end, String dep) {
        String getMaxPlanIdSQL = "SELECT MAX(plid) FROM [Plan];";
        String insertSQL = "INSERT INTO [Plan](plid, startd, endd, did) VALUES (?,?,?,?);";

        try {
            // Bước 1: Lấy PlanID lớn nhất từ bảng Plan
            PreparedStatement psGetMaxPlanId = connection.prepareStatement(getMaxPlanIdSQL);
            ResultSet rs = psGetMaxPlanId.executeQuery();

            int newPlanId = 1; // Giả định là nếu bảng rỗng, PlanID sẽ bắt đầu từ 1
            if (rs.next()) {
                newPlanId = rs.getInt(1) + 1; // Lấy giá trị lớn nhất và +1 để tạo ID mới
            }

            // Bước 2: Thực hiện insert với PlanID mới
            PreparedStatement psInsert = connection.prepareStatement(insertSQL);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày phù hợp với CSDL

            // Set giá trị cho từng trường trong câu INSERT
            psInsert.setInt(1, newPlanId); // PlanID mới
            java.util.Date parsedStartDate = sdf.parse(start); // Chuyển String thành java.util.Date
            psInsert.setDate(2, new java.sql.Date(parsedStartDate.getTime())); // StartDate
            java.util.Date endP = sdf.parse(end); // Chuyển String thành java.util.Date
            psInsert.setDate(3, new java.sql.Date(endP.getTime())); // EndDate
            psInsert.setInt(4, Integer.parseInt(dep)); // DepartmentID

            // Thực hiện câu lệnh INSERT
            psInsert.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: create plan " + e);
        } catch (ParseException ex) {
            Logger.getLogger(PlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<PlanCampain> getPlanCampainByPlanID(int aInt) {
        List<PlanCampain> list = new ArrayList();
        String sql = "SELECT *"
                + " FROM [PlanCampaign] "
                + " where plid = ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, aInt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PlanCampain s = new PlanCampain();
                s.setId(rs.getInt(1));
                s.setPlanID(aInt);
                s.setProductID(rs.getInt(3));
                ProductDAO pd = new ProductDAO();
                s.setProduct(pd.getProdcutByID(rs.getInt(3)));
                s.setQuantity(rs.getInt(4));
                List<ScheduleCampain> sches = getScheduleCampainByPlanCampainID(rs.getInt(1));
                s.setSchedules(sches);
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getPlanCampainByPlanID" + e);
        }
        return list;
    }

    private List<ScheduleCampain> getScheduleCampainByPlanCampainID(int aInt) {
        List<ScheduleCampain> list = new ArrayList();
        String sql = "SELECT *"
                + " FROM [ScheduleCampaign] "
                + " where canid = ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, aInt);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleCampain s = new ScheduleCampain();
                s.setId(rs.getInt(1));
                s.setPlanCampainId(rs.getInt(2));
                s.setDate(rs.getDate(3));
                s.setShift(rs.getString(4));
                s.setQuantity(rs.getInt(5));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Error getScheduleCampainByPlanCampainID " + e);
        }
        return list;
    }

    public void update(String id, String start, String end,  String dep) {
        String updateSQL = "UPDATE [Plan] SET  startd = ?, "
                + "endd = ?,  did = ? WHERE plid = ?";

        try {
            // Prepare the update statement
            PreparedStatement psUpdate = connection.prepareStatement(updateSQL);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Date format for the database

            // Set values for the update
            java.util.Date parsedStartDate = sdf.parse(start); // Convert String to java.util.Date
            psUpdate.setDate(1, new java.sql.Date(parsedStartDate.getTime())); // StartDate
            java.util.Date parsedEndDate = sdf.parse(end); // Convert String to java.util.Date
            psUpdate.setDate(2, new java.sql.Date(parsedEndDate.getTime())); // EndDate
            psUpdate.setInt(3, Integer.parseInt(dep)); // DepartmentID
            psUpdate.setInt(4, Integer.parseInt(id)); // PlanID for the WHERE clause

            // Execute the update
            psUpdate.executeUpdate();
            System.out.println("Plan updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error: update plan " + e);
        } catch (ParseException ex) {
            Logger.getLogger(PlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePlan(int planId) {
        String deleteAttendanceSQL = "DELETE FROM [Attendance] WHERE wsid "
                + " IN (SELECT wsid FROM [WorkerSchedule] WHERE scid IN "
                + " (SELECT scid FROM [ScheduleCampaign] WHERE canid IN "
                + " (SELECT canid FROM [PlanCampaign] WHERE plid = ?)))";
        String deleteSchedualEmployeeSQL = "DELETE FROM [WorkerSchedule] "
                + " WHERE scid IN (SELECT scid FROM [ScheduleCampaign] WHERE canid IN "
                + " (SELECT canid FROM [PlanCampaign] WHERE plid = ?))";
        String deleteSchedualCampaignSQL = "DELETE FROM [ScheduleCampaign] "
                + "WHERE canid IN (SELECT canid FROM [PlanCampaign] "
                + "WHERE plid = ?)";
        String deletePlanCampainSQL = "DELETE FROM [PlanCampaign] WHERE plid = ?";
        String deletePlanSQL = "DELETE FROM [Plan] WHERE plid = ?";

        try {
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Delete Attendance
            PreparedStatement psDeleteAttendance = connection.prepareStatement(deleteAttendanceSQL);
            psDeleteAttendance.setInt(1, planId);
            psDeleteAttendance.executeUpdate();

            // Step 2: Delete SchedualEmployee
            PreparedStatement psDeleteSchedualEmployee = connection.prepareStatement(deleteSchedualEmployeeSQL);
            psDeleteSchedualEmployee.setInt(1, planId);
            psDeleteSchedualEmployee.executeUpdate();

            // Step 3: Delete SchedualCampaign
            PreparedStatement psDeleteSchedualCampaign = connection.prepareStatement(deleteSchedualCampaignSQL);
            psDeleteSchedualCampaign.setInt(1, planId);
            psDeleteSchedualCampaign.executeUpdate();

            // Step 4: Delete PlanCampain
            PreparedStatement psDeletePlanCampain = connection.prepareStatement(deletePlanCampainSQL);
            psDeletePlanCampain.setInt(1, planId);
            psDeletePlanCampain.executeUpdate();

            // Step 5: Delete Plan
            PreparedStatement psDeletePlan = connection.prepareStatement(deletePlanSQL);
            psDeletePlan.setInt(1, planId);
            psDeletePlan.executeUpdate();

            connection.commit(); // Commit transaction
            System.out.println("Plan and all related records deleted successfully.");

        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction in case of error
                System.out.println("Error: delete plan " + e);
            } catch (SQLException rollbackEx) {
                System.out.println("Error during rollback: " + rollbackEx);
            }
        } finally {
            try {
                connection.setAutoCommit(true); // Reset auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Error resetting auto-commit: " + ex);
            }
        }
    }

}
