/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AbhinayReddy
 */
public class LoginPageController implements Initializable {

    static ArrayList<String> usersList = new ArrayList();
    static ArrayList<String> usernamesList = new ArrayList();
    static ArrayList<String> passwordsList = new ArrayList();
    static ArrayList<String> adminusernamesList = new ArrayList();
    static ArrayList<String> adminpasswordsList = new ArrayList();
    static ArrayList<String> adminusersList=new ArrayList<String>();
    static String usernameVar;
    static String passwordVar;
    static String result;
    static int wrongAttempts = 0;
    static int attemptsLeft = 3;

    @FXML
    private Button register;
    @FXML
    private Button signin;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField admin_username;
    @FXML
    private PasswordField admin_password;
    @FXML
    private Label error_message;
    @FXML
    private Button admin_signin;
    @FXML
    private Label admin_error_message;

    public void getUsersList() throws IOException {
       wrongAttempts=0;
       attemptsLeft=3;
       adminusernamesList=new ArrayList<String>();
       adminpasswordsList=new ArrayList<String>();
       adminusersList=new ArrayList<String>();
        usernamesList = new ArrayList<String>();
        passwordsList = new ArrayList<String>();
        usersList = new ArrayList<String>();
        error_message.setVisible(false);
        admin_error_message.setVisible(false);
        
        FileReader reader;
        reader = new FileReader("users.txt");

        Scanner in = new Scanner(reader);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            usernamesList.add(line.split("\\++")[2]);
            passwordsList.add(line.split("\\++")[3]);
            usersList.add(line);
            System.out.println(usersList);
            System.out.println(usernamesList);
            System.out.println(passwordsList);
        }
        FileReader reader1 = new FileReader("admins.txt");

        Scanner in1 = new Scanner(reader1);
        while (in1.hasNextLine()) {
            String line = in1.nextLine();
            adminusernamesList.add(line.split("\\++")[2]);
            adminpasswordsList.add(line.split("\\++")[3]);
            adminusersList.add(line);
            System.out.println(adminusersList);
            System.out.println(adminusernamesList);
            System.out.println(adminpasswordsList);
        }
    }

    @FXML
    public void newUserRegistration(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        stage = (Stage) register.getScene().getWindow();
        System.out.println(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userRegistration.fxml"));
        root = loader.load();
        UserRegistrationController userReg = loader.<UserRegistrationController>getController();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void validateUserCredentials(ActionEvent event) throws IOException {
        // this.getUsersList();
        Stage stage = null;
        Parent root = null;
        usernameVar = username.getText();
        usernameVar = usernameVar.toLowerCase();
        passwordVar = password.getText();

        for (int i = 0; i < usernamesList.size(); i++) {
            if (usernamesList.get(i).equals(usernameVar)) {
                System.out.print("username exists "+i);
                if (passwordsList.get(i).equals(passwordVar)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Login successful!");
                    Optional<ButtonType> loginSuccess = alert.showAndWait();
                    //after OK button is clicked it is redirected to the create property menu
                    if (loginSuccess.get() == ButtonType.OK) {
                        stage = (Stage) signin.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                        root = loader.load();
                        MenuController menu = loader.<MenuController>getController();
                        menu.obtainRepo(new Repo(false));
                        stage.setScene(new Scene(root));
                        stage.setFullScreen(true);
                        stage.setFullScreenExitHint("");
                        stage.setMaximized(true);
                        stage.show();
                    }
                    result = "You're logged in";
                    System.out.println(result);
                } else if (!passwordsList.get(i).equals(passwordVar)) {
                    //&& (i == usernamesList.size() - 1
                    wrongAttempts++;
                    if (wrongAttempts >= 3) {
                        //terminates=true;
                        result = "Error, too many attempts have been made";
                        error_message.setText("Error, too many attempts have been made");
                        error_message.setVisible(true);
                        System.out.println(result);
                        break;
                    } else {
                        attemptsLeft--;
                        result = "Wrong password, " + attemptsLeft + " attempts left";
                        error_message.setText("Wrong password, " + attemptsLeft + " attempts left");
                        error_message.setVisible(true);
                        System.out.println(result);
                        break;

                    }
                }
            } else if (!usernamesList.get(i).equals(usernameVar) && i == usernamesList.size() - 1) {
                result = "Username is incorrect! "+i;
                error_message.setText("Username is incorrect! ");
                error_message.setVisible(true);
                System.out.println(result);
            }

        }
    }

     @FXML
    public void validateAdminCredentials(ActionEvent event) throws IOException {
        // this.getUsersList();
        Stage stage = null;
        Parent root = null;
        usernameVar = admin_username.getText();
        usernameVar = usernameVar.toLowerCase();
        passwordVar = admin_password.getText();

        for (int i = 0; i < adminusernamesList.size(); i++) {
            if (adminusernamesList.get(i).equals(usernameVar)) {
                System.out.print("admin username exists "+i);
                if (adminpasswordsList.get(i).equals(passwordVar)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Login successful!");
                    Optional<ButtonType> loginSuccess = alert.showAndWait();
                    //after OK button is clicked it is redirected to the create property menu
                    if (loginSuccess.get() == ButtonType.OK) {
                        stage = (Stage) signin.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
                        root = loader.load();
                        MenuController menu = loader.<MenuController>getController();
                        menu.obtainRepo(new Repo(true));
                        stage.setScene(new Scene(root));
                        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
                        stage.setMaximized(true);
                        stage.show();
                    }
                    result = "You're logged in";
                    System.out.println(result);
                } else if (!adminpasswordsList.get(i).equals(passwordVar)) {
                    //&& (i == usernamesList.size() - 1
                    wrongAttempts++;
                    if (wrongAttempts >= 3) {
                        //terminates=true;
                        result = "Error, too many attempts have been made";
                        admin_error_message.setText("Error, too many attempts have been made");
                        admin_error_message.setVisible(true);
                        System.out.println(result);
                        break;
                    } else {
                        attemptsLeft--;
                        result = "Wrong password, " + attemptsLeft + " attempts left";
                        admin_error_message.setText("Wrong password, " + attemptsLeft + " attempts left");
                        admin_error_message.setVisible(true);
                        System.out.println(result);
                        break;

                    }
                }
            } else if (!adminusernamesList.get(i).equals(usernameVar) && i == adminusernamesList.size() - 1) {
                result = "Username is incorrect! "+i;
                admin_error_message.setText("Username is incorrect! ");
                admin_error_message.setVisible(true);
                System.out.println(result);
            }

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
