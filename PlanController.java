/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.DepartmentDAO;
import dal.PlanDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.Department;
import models.Feature;
import models.Plan;
import models.Role;
import models.User;

/**
 *
 * @author admin
 */
public class PlanController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlanController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlanController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        User u = (User) ses.getAttribute("user");
        if (u == null) {
            response.sendRedirect("login");
            return;
        }
        String action = request.getParameter("action");
        PlanDAO pd = new PlanDAO();
        if (null == action || action.equals("list")) {
            if (!checkRoleAccess(u.getRoles(), "plans?action=list")) {
                ses.setAttribute("noti", "Bạn không có quyền truy cập trang list of plan");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            }
            String start = request.getParameter("start");
            String end = request.getParameter("end");
            List<Plan> plans = pd.getPlans(start, end);
            request.setAttribute("plans", plans);
            request.getRequestDispatcher("plans.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            pd.deletePlan(Integer.parseInt(id));
            response.sendRedirect("plans");
            return;
        }

        if ("details".equals(action)) {
            if (!checkRoleAccess(u.getRoles(), "plans?action=details")) {
                ses.setAttribute("noti", "Bạn không có quyền truy cập trang plan details");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
                return;
            }
            String id = request.getParameter("id");
            Plan p = pd.getPlanById(id);
            request.setAttribute("p", p);
            request.getRequestDispatcher("plan-details.jsp").forward(request, response);
            return;
        }

        if ("add".equals(action)) {
            if (!checkRoleAccess(u.getRoles(), "plans?action=add")) {
                ses.setAttribute("noti", "Bạn không có quyền truy cập trang add plan");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
                return;
            }
            DepartmentDAO dd = new DepartmentDAO();
            List<Department> deps = dd.getDepartments();
            request.setAttribute("deps", deps);
            request.getRequestDispatcher("plan-add.jsp").forward(request, response);
            return;
        }
        // Mặc định là action "edit" hoặc các hành động khác
        if (!checkRoleAccess(u.getRoles(), "plans?action=edit")) {
            ses.setAttribute("noti", "Bạn không có quyền truy cập trang edit plan");
            request.getRequestDispatcher("manage.jsp").forward(request, response);
            return;
        }
        String id = request.getParameter("id");
        DepartmentDAO dd = new DepartmentDAO();
        List<Department> deps = dd.getDepartments();
        Plan pl = pd.getPlanById(id);
        request.setAttribute("pl", pl);
        request.setAttribute("deps", deps);
        request.getRequestDispatcher("plan-edit.jsp").forward(request, response);
    }

    private boolean checkRoleAccess(List<Role> roles, String url) {
        for (Role r : roles) {
            for (Feature f : r.getFeatures()) {
                if (f.getUrl().contains(url)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession ses = request.getSession();
        User u = (User) ses.getAttribute("user");
        String action = request.getParameter("action");
        PlanDAO pd = new PlanDAO();
        if (action.equals("add")) {
            if (!checkRoleAccess(u.getRoles(), "plans?action=add")) {
                ses.setAttribute("noti", "Bạn không có quyền thêm mới");
                response.sendRedirect("plans");
                return;
            }
            String start = request.getParameter("startdate");
            String end = request.getParameter("enddate");
            String dep = request.getParameter("department");
            pd.create( start, end, dep);
            response.sendRedirect("plans");
            return;
        }

        if (action.equals("edit")) {
            if (!checkRoleAccess(u.getRoles(), "plans?action=edit")) {
                ses.setAttribute("noti", "Bạn không có quyền cập nhật plan ");
                response.sendRedirect("plans");
                return;
            }
            String id = request.getParameter("id");
            String start = request.getParameter("startdate");
            String end = request.getParameter("enddate");
            String dep = request.getParameter("department");
            pd.update(id, start, end, dep);
            response.sendRedirect("plans");
            return;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
