/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class CreatePropertyMenuController implements Initializable {

    @FXML
    private Button createHouse;

    @FXML
    private Button createApartment;

    @FXML
    private Button createCondo;

    @FXML
    private Button back;

    private Repo repo = null;

    //handler class that defines the action events when create house, create condominium, create apartment buttons are clicked
    @FXML
    public void createPropertyHandler(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        //when button clicked is create house, loads a new form to enter the values for new house
        if (event.getSource().equals(createHouse)) {
            stage = (Stage) createHouse.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createHouse.fxml"));
            root = loader.load();
            CreateHouseController chc = loader.<CreateHouseController>getController();
            chc.obtainRepo(repo);
            //when button clicked is create apartment, loads a new form to enter the values for new apartment
        } else if (event.getSource().equals(createApartment)) {
            stage = (Stage) createApartment.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createApartment.fxml"));
            root = loader.load();
            CreateApartmentController cac = loader.<CreateApartmentController>getController();
            cac.obtainRepo(repo);
            //when button clicked is create condominium, loads a new form to enter the values for new condominium
        } else if (event.getSource().equals(createCondo)) {
            stage = (Stage) createCondo.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createCondo.fxml"));
            root = loader.load();
            CreateCondoController ccc = loader.<CreateCondoController>getController();
            ccc.obtainRepo(repo);
        } else {
            //when button clicked is back, loads a main menu to make a new selection
            stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            root = loader.load();
            MenuController mc = loader.<MenuController>getController();
            mc.obtainRepo(repo);
        }
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

    //sets the repo variable
    public void obtainRepo(Repo repo) {
        this.repo = repo;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
