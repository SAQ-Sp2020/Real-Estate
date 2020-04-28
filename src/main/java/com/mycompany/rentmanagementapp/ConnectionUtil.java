/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 *
 * @author AbhinayReddy
 */
public class ConnectionUtil {

    Connection conn = null;

    public static Connection connectdb() {
        try {
            
            Class.forName("com.mysql.jdbc.driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rent management application"+ "", "root", "admin");
            return conn;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String s = sw.toString();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Could not connect to the Rent Management database");
                VBox dialogPaneContent = new VBox();
                Label label = new Label("Stack Trace:");
                TextArea textArea = new TextArea();
                textArea.setText(s);
                dialogPaneContent.getChildren().addAll(label, textArea);

                // Set content for Dialog Pane
                alert.getDialogPane().setContent(dialogPaneContent);
                alert.show();
            return null;
        }
    }
}
