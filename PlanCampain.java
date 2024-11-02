/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class PlanCampain {
    private int id, planID, productID, quantity;

    private Plan plan;
    private Product product;
    
    List<ScheduleCampain> schedules = new ArrayList();

    public List<ScheduleCampain> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleCampain> schedules) {
        this.schedules = schedules;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanID() {
        return planID;
    }

    public void setPlanID(int planID) {
        this.planID = planID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

   

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public PlanCampain() {
    }

    public PlanCampain(int id, int planID, int productID, int quantity) {
        this.id = id;
        this.planID = planID;
        this.productID = productID;
        this.quantity = quantity;
    }
    
}
