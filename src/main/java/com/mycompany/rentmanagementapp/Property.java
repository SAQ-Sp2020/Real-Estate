/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import javafx.scene.image.Image;

/**
 *
 * @author VarnithaPuli
 */


//getters and setters for properties defined in the property class
public abstract class Property {
    private String typeOfProp;
    private String description;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private int numbOfBathrooms;
    private int numbOfBedrooms;
    private Image propertyPic;

    
    //getters and setter for description,address, city, state, bed rooms, bath rooms, picture, postal code
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfProp() {
        return typeOfProp;
    }

    public void setTypeOfProp(String typeOfProp) {
        this.typeOfProp = typeOfProp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getNumbOfBathrooms() {
        return numbOfBathrooms;
    }

    public void setNumbOfBathrooms(int numbOfBathrooms) {
        this.numbOfBathrooms = numbOfBathrooms;
    }

    public int getNumbOfBedrooms() {
        return numbOfBedrooms;
    }

    public void setNumbOfBedrooms(int numbOfBedrooms) {
        this.numbOfBedrooms = numbOfBedrooms;
    }

    public Image getPropertyPic() {
        return propertyPic;
    }

    public void setPropertyPic(Image propertyPic) {
        this.propertyPic = propertyPic;
    }
}
