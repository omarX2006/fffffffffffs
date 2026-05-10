/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication1;

/**
 *
 * @author LENOVO
 */
public class MyProcess {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int remainingTime; 
    private int waitingTime;
    private int turnAroundTime;
    private int responseTime = -1; 
    private int completionTime;

    public MyProcess(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }

   
    public String getName() { return name; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int wt) { this.waitingTime = wt; }
    public int getTurnAroundTime() { return turnAroundTime; }
    public void setTurnAroundTime(int tat) { this.turnAroundTime = tat; }
    public int getResponseTime() { return responseTime; }
    public void setResponseTime(int rt) { this.responseTime = rt; }
    public int getRemainingTime() { return remainingTime; }
    public void setRemainingTime(int rt) { this.remainingTime = rt; }
    public int getCompletionTime() { return completionTime; }
    public void setCompletionTime(int ct) { this.completionTime = ct; }
}