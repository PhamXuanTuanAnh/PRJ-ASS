/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author admin
 */
public class ScheduleCampain {
    private int id, planCampainId, quantity;
    private Date date;
    private String shift;
    
    Plan plan;
    PlanCampain planCampain;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    

    public ScheduleCampain() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanCampainId() {
        return planCampainId;
    }

    public void setPlanCampainId(int planCampainId) {
        this.planCampainId = planCampainId;
    }

    public PlanCampain getPlanCampain() {
        return planCampain;
    }

    public void setPlanCampain(PlanCampain planCampain) {
        this.planCampain = planCampain;
    }

    

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
