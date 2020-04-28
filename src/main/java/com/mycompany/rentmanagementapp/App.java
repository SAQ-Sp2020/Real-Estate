package com.mycompany.rentmanagementapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage mainStage;

//start method is called when the application is launched
//Loads the first scene main menu for creating/editing properties and exit application
    @Override
    public void start(Stage primaryStage) throws IOException {
        App.mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("loginPage.fxml"));
        //FXMLLoader loader = new FXMLLoader(App.class.getResource("menu.fxml"));
        Parent root = loader.load();
        LoginPageController logincontrol = loader.<LoginPageController>getController();
        logincontrol.getUsersList();
       //  MenuController menucontrol = loader.<MenuController>getController();
       // menucontrol.obtainRepo(new Repo()); // this calls rep class to load default house, condominium, apartment
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
       // primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
