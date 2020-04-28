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
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class View_editPropertyController implements Initializable {

    @FXML
    private Button house;

    @FXML
    private Button apartment;

    @FXML
    private Button condo;

    @FXML
    private Button back;

    @FXML
    private Button property;

    private Repo repo = null;
    @FXML
    private Label view_edit_label;

    //handler method to handle all the events of view/edit properties menu
    @FXML
    void viewPropertyHandler(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        //when back buton is clicked, it is redirected to the main menu
        if (event.getSource().equals(back)) {
            stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("menu.fxml"));
            root = loader.load();
            MenuController mc = loader.<MenuController>getController();
            mc.obtainRepo(repo);
            //when button clicked is view/edit prperty, it loads the list of all properties
        } else if (event.getSource().equals(property)) {
            stage = (Stage) property.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("allProperties.fxml"));
            root = loader.load();
            AllPropertiesController mc = loader.<AllPropertiesController>getController();
            mc.obtainRepo(repo);
            //when button clicked is view/edit house, it loads the list of all houses
        } else if (event.getSource().equals(house)) {
            stage = (Stage) house.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("houses.fxml"));
            root = loader.load();
            HousesController mc = loader.<HousesController>getController();
            mc.obtainRepo(repo);
            //when button clicked is view/edit house, it loads the list of all condominiums
        } else if (event.getSource().equals(condo)) {
            stage = (Stage) condo.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("condos.fxml"));
            root = loader.load();
            CondosController mc = loader.<CondosController>getController();
            mc.obtainRepo(repo);

            //when button clicked is view/edit apartment, it loads the list of all apartments
        } else {
            stage = (Stage) apartment.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("apartments.fxml"));
            root = loader.load();
            ApartmentsController mc = loader.<ApartmentsController>getController();
            mc.obtainRepo(repo);
        }
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

    //sets the rep variable
    public void obtainRepo(Repo repo) {
        this.repo = repo;
        if (!this.repo.admin) {
            view_edit_label.setText("View Properties Menu");
            property.setText("View Property");
            condo.setText("View Condominium");
            house.setText("View House");
            apartment.setText("View Apartment");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
