/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author admin
 */
public class WorkerSchedule {

    private int id, scheduleID, empID, quanity;

    ScheduleCampain schedule;
    Employee employee;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public ScheduleCampain getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleCampain schedule) {
        this.schedule = schedule;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public WorkerSchedule() {
    }

    public WorkerSchedule(int id, int scheduleID, int empID, int quanity) {
        this.id = id;
        this.scheduleID = scheduleID;
        this.empID = empID;
        this.quanity = quanity;
    }

}
