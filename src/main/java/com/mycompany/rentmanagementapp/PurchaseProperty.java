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
public abstract class PurchaseProperty extends Property{
    
    private double purchasePrice;
    private double annualTaxes;

    //getters and setters defined for purchase price, annual taxes
    
    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getAnnualTaxes() {
        return annualTaxes;
    }

    public void setAnnualTaxes(double annualTaxes) {
        this.annualTaxes = annualTaxes;
    }
     
}
