/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import dal.DepartmentDAO;
import dal.EmployeeDAO;
import dal.PlanDAO;
import dal.RoleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.Department;
import models.Employee;
import models.Feature;
import models.Plan;
import models.Role;
import models.User;

/**
 *
 * @author admin
 */
public class EmployeeController extends HttpServlet {

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
            out.println("<title>Servlet EmployeeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EmployeeController at " + request.getContextPath() + "</h1>");
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
        EmployeeDAO ed = new EmployeeDAO();

        if (null == action || action.equals("list")) {
            String name = request.getParameter("name");
            List<Employee> emps = ed.getEmpList(name);
            request.setAttribute("emps", emps);
            request.getRequestDispatcher("emps.jsp").forward(request, response);
            return;
        }

        if ("delete".equals(action)) {
            String id = request.getParameter("id");
            ed.deleteEmp(id);
            response.sendRedirect("employees");
            return;
        }

        RoleDAO rd = new RoleDAO();
        List<Role> roles = rd.getRoles();
        DepartmentDAO dd = new DepartmentDAO();
        List<Department> deps = dd.getDepartments();
        request.setAttribute("roles", roles);
        request.setAttribute("deps", deps);

        if ("add".equals(action)) {
            request.getRequestDispatcher("emp-add.jsp").forward(request, response);
            return;
        }

        String id = request.getParameter("id");
        Employee p = ed.getEmpById(id);
        request.setAttribute("e", p);
        request.getRequestDispatcher("emp-edit.jsp").forward(request, response);
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
        if (u == null) {
            response.sendRedirect("login");
            return;
        }
        String name = request.getParameter("name");
        String salaryLevel = request.getParameter("salaryLevel");
        String departmentId = request.getParameter("departmentId");
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        // Create an Employee object and set the values
        Employee employee = new Employee();
        employee.setEname(name);
        employee.setEid(id);

        employee.setSalaryLevel(salaryLevel);
        employee.setDid(Integer.parseInt(departmentId));
        EmployeeDAO ed = new EmployeeDAO();
        if ("add".equals(action)) {
            employee.setCreatedBy(u.getEid());
            ed.addEmp(employee);
        } else if ("edit".equals(action)) {
            ed.editEmp(employee);
        }
        response.sendRedirect("employees");
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
