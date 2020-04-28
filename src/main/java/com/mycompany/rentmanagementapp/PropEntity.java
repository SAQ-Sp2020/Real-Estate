/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

/**
 *
 * @author VarnithaPuli
 */

//class to store all the properties required to display the list on TableView
public class PropEntity {

    private SimpleStringProperty type;
    private SimpleStringProperty price;
    private ImageView image;
    private SimpleStringProperty address;
    private SimpleStringProperty city;
    private SimpleStringProperty state;
    private SimpleStringProperty postalCode;

    //constructor defined to set the image, type of property, city, state, price on the tableview
    public PropEntity(String type, String price, String city, String state, String postalCode, ImageView image) {
        this.image=image;
        this.price = new SimpleStringProperty(price);
        this.type = new SimpleStringProperty(type);
        this.city = new SimpleStringProperty(city);
        this.state = new SimpleStringProperty(state);
        this.postalCode = new SimpleStringProperty(postalCode);
    }

    //getter and setters defined for address, city, state, postal code, image, type of property, price
    
    public void setAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }

    public String getAddress() {
        return address.get();
    }

    public void setCity(String city) {
        this.city = new SimpleStringProperty(city);
    }

    public String getCity() {
        return city.get();
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price = new SimpleStringProperty(price);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type);
    }

    public void setState(String state) {
        this.state = new SimpleStringProperty(state);
    }

    public String getState() {
        return state.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = new SimpleStringProperty(postalCode);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

}
