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
public class MenuController implements Initializable {

    @FXML
    private Button createProperty;

    @FXML
    private Button view_editProperty;

    @FXML
    private Button exit;

    private Repo repo = null;

    //handler function for handling create, view/edit and exit buttons in the menu.fxml file
    @FXML
    public void menuOptionHandler(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        System.out.println(event.getSource());
        if (event.getSource().equals(createProperty)) { //if button clicked is create property
            System.out.println(event.getSource());
            stage = (Stage) createProperty.getScene().getWindow();
            System.out.println(stage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createPropertyMenu.fxml"));
            root = loader.load();
            CreatePropertyMenuController createmenu = loader.<CreatePropertyMenuController>getController();
            createmenu.obtainRepo(repo);
        } else if (event.getSource().equals(view_editProperty)) { ////if button clicked is View/Edit property
            stage = (Stage) view_editProperty.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view_editProperty.fxml"));
            root = loader.load();
            View_editPropertyController viewedit = loader.<View_editPropertyController>getController();
            viewedit.obtainRepo(repo);
        } else { //if button clicked is exit
            stage = (Stage) exit.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
            root = loader.load();
            LoginPageController login = loader.<LoginPageController>getController();
            login.getUsersList();
            //System.exit(0);
        }
        stage.setScene(new Scene(root));
      
       stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

    //sets the repo 
    public void obtainRepo(Repo repo) {
        this.repo = repo;
        createProperty.setVisible(this.repo.admin);
        if (!this.repo.admin) {
            view_editProperty.setText("View Property");
        }
        System.out.println(this.repo.admin);
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
