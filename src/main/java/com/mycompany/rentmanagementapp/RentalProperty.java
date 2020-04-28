/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

/**
 *
 * @author VarnithaPuli
 */
public abstract class RentalProperty extends Property{
    
    private int minimumLeasePeriod;
    private double monthlyFee;

    //getters and setters defined for lease period, monthly fee for apartments
    
    public int getMinimumLeasePeriod() {
        return minimumLeasePeriod;
    }

    public void setMinimumLeasePeriod(int minimumLeasePeriod) {
        this.minimumLeasePeriod = minimumLeasePeriod;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
    
}
