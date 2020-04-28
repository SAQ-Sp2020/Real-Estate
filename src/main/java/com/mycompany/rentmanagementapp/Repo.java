/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

//a repository class to store all  load the default house, apartment and condo
//contains uitility methods to add, update, delete properties
public class Repo {

    Boolean admin;
    List<Property> allProperties;
//constructor class that loads all the default properties

    public Repo(Boolean adminFlag) throws IOException {
        this.admin = adminFlag;
        allProperties = new ArrayList<>();
        loadHouse();
        loadCondo();
        loadApartment();
    }

    //creates the default house
    private void loadHouse() throws IOException {
        House home = new House();
        home.setDescription("This is a default house");
        home.setTypeOfProp("House");
        home.setAddress("South Heights");
        home.setCity("Houston");
        home.setState("Texas");
        home.setPostalCode("77054");
        home.setNumbOfBedrooms(3);
        home.setNumbOfBathrooms(3);
        home.setPropertyPic(new Image("file:default_house.jpeg"));
        home.setPurchasePrice(237542.0);
        home.setAnnualTaxes((home.getPurchasePrice() * 0.07));
        allProperties.add(home);
    }

    //creates the default condominium
    private void loadCondo() throws IOException {
        Condominiums condo = new Condominiums();
        condo.setDescription("This is a default Condominium");
        condo.setTypeOfProp("Condominium");
        condo.setAddress("Bay Area");
        condo.setCity("Los Angeles");
        condo.setState("California");
        condo.setPostalCode("70123");
        condo.setNumbOfBathrooms(1);
        condo.setNumbOfBedrooms(1);
        condo.setPropertyPic(new Image("file:default_condo.jpg"));
        condo.setPurchasePrice(198610.0);
        condo.setAnnualTaxes(condo.getPurchasePrice() * 0.06);
        condo.setManagementFee(condo.getPurchasePrice() * 0.02);
        allProperties.add(condo);
    }

    //creates the default apartment
    private void loadApartment() throws IOException {
        Apartment apartment = new Apartment();
        apartment.setDescription("This is a default Apartment");
        apartment.setTypeOfProp("Apartment");
        apartment.setAddress("Manhattan");
        apartment.setState("New York");
        apartment.setCity("New York");
        apartment.setNumbOfBathrooms(2);
        apartment.setNumbOfBedrooms(2);
        apartment.setMinimumLeasePeriod(12);
        apartment.setMonthlyFee(2500);
        apartment.setPostalCode("77007");
        apartment.setPropertyPic(new Image("file:default_apartment.jpeg"));
        // System.out.println(apartment.getPropertyPic().toString());
        allProperties.add(apartment);
    }

    //add a new property to the allProperties list
    public void createProperty(Property property) {
        allProperties.add(property);
    }

    //updates the property in the allProperties list
    public void updateProperty(int i, Property property2) {
        allProperties.set(i, property2);
    }

    //deletes the property from all properties list
    public void deleteProperty(Property property) {
        allProperties.remove(property);
    }

    //returns all properties list
    public List<Property> allProperties() {
        return allProperties;
    }

    //returns a list  of houses from allproperties list
    public List<House> allHouses() {
        List<House> allHouses = new ArrayList<>();
        for (Property property : allProperties) {
            if (property instanceof House) {
                allHouses.add((House) property);
            }
        }
        return allHouses;
    }

    //returns list of apartments from all properties list
    public List<Apartment> allApartment() {
        List<Apartment> allApartments = new ArrayList<>();
        for (Property property : allProperties) {
            if (property instanceof Apartment) {
                allApartments.add((Apartment) property);
            }
        }
        return allApartments;
    }

    //returns list of all condominiums from all properties list
    public List<Condominiums> allCondos() {
        List<Condominiums> allCondos = new ArrayList<>();
        for (Property property : allProperties) {
            if (property instanceof Condominiums) {
                allCondos.add((Condominiums) property);
            }
        }
        return allCondos;
    }

}
