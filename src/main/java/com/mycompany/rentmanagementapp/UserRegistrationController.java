/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author AbhinayReddy
 */
public class UserRegistrationController implements Initializable {
   // public class UserRegistration implements java.sql.Driver{

    @FXML
    private Button userReg;
    @FXML
    private Button cancel;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    public void cancelRegistration(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        stage = (Stage) cancel.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
        root = loader.load();
        LoginPageController login = loader.<LoginPageController>getController();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void registerUser(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
         Class.forName("com.mysql.jdbc.driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rent management application"+ "", "root", "admin");
        //connection = ConnectionUtil.connectdb();
        String sql = "INSERT INTO users (First Name, Last Name, Email, password) VALUES (?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name.getText());
            preparedStatement.setString(2, last_name.getText());
            preparedStatement.setString(3, email.getText());
            preparedStatement.setString(4, password.getText());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(UserRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        PrintWriter fw = null;
        String separator = "++";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(first_name.getText());
            bw.write(separator);
            bw.write(last_name.getText());
            bw.write(separator);
            bw.write(email.getText());
            bw.write(separator);
            bw.write(password.getText());
            bw.newLine();
            bw.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("User Registration is successful!");
            Optional<ButtonType> result = alert.showAndWait();
            //after OK button is clicked it is redirected to the create property menu
            if (result.get() == ButtonType.OK) {
                Stage stage = (Stage) userReg.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("loginPage.fxml"));
                Parent root = loader.load();
                LoginPageController loginPage = loader.<LoginPageController>getController();
                loginPage.getUsersList();
                stage.setScene(new Scene(root));
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                stage.setMaximized(true);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    //}

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
