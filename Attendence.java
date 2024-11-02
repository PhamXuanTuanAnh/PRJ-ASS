/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author admin
 */
public class Attendence {
    private int id,  quantity, wsid;
    private float alpha;

    WorkerSchedule schemp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWsid() {
        return wsid;
    }

    public void setWsid(int wsid) {
        this.wsid = wsid;
    }

   

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public WorkerSchedule getSchemp() {
        return schemp;
    }

    public void setSchemp(WorkerSchedule schemp) {
        this.schemp = schemp;
    }
    
    
    public Attendence() {
    }

    
    public Attendence(int id, int quantity, float alpha) {
        this.id = id;
        this.quantity = quantity;
        this.alpha = alpha;
    }
    
    
}
