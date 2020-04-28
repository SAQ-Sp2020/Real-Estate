/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class CreateHouseController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private TextField state;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField bathrooms;

    @FXML
    private TextField bedrooms;

    @FXML
    private Button upload;

    @FXML
    private Label fileName;

    @FXML
    private TextField purchasePrice;

    @FXML
    private Button create;

    @FXML
    private Button back;

    private Repo repo = null;
    File file;
    @FXML
    private TextField taxes;
    @FXML
    private TextArea description;
    @FXML
    private AnchorPane anchorpane;

    //utility function to validate the input entered is numbers. 
    //this method adds a pattern to the text field to accept only numbers
    @FXML
    public void inputHandler(KeyEvent event) {
        TextField origin = (TextField) event.getSource();
        Pattern decimalPattern = Pattern.compile("\\d*");
        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(filter);
        origin.setTextFormatter(formatter);
    }

    //utility function to validate the input entered is decimals. 
    //this method adds a pattern to the text field to accept only decimal numbers up to 3 places
    @FXML
    public void decimalHandler(KeyEvent event) {
        TextField origin = (TextField) event.getSource();
        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,3})?");
        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };
        TextFormatter<Double> formatter = new TextFormatter<>(filter);
        origin.setTextFormatter(formatter);
    }

    //handler method defined for all the events during house creation
    @FXML
    public void createHouseHandler(ActionEvent event) throws IOException {
        //if upload button is clicked
        if (event.getSource().equals(upload)) {
            //enables user to choose an image files from his computer
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    //restricts user to only choose it from these file types
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
            file = fileChooser.showOpenDialog((Stage) upload.getScene().getWindow());
            fileName.setText(file.getName());
            //when button clicked is back, it loads the create property menu scene
        } else if (event.getSource().equals(back)) {
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createPropertyMenu.fxml"));
            Parent root = loader.load();
            CreatePropertyMenuController createmenu = loader.<CreatePropertyMenuController>getController();
            createmenu.obtainRepo(repo);
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
            stage.setMaximized(true);
            stage.show();
            //when create button is clicked, creates a new house and adds it to the repo
        } else {
            try {
                //validates if all the text fields and image field is populated
                if (validateAllFields().length() == 0) {
                    House house = new House();
                    house.setTypeOfProp("House");
                    house.setDescription(description.getText());
                    house.setAddress(address.getText());
                    house.setCity(city.getText());
                    house.setState(state.getText());
                    house.setPostalCode(postalCode.getText());
                    house.setNumbOfBathrooms(Integer.parseInt(bathrooms.getText()));
                    house.setNumbOfBedrooms(Integer.parseInt(bedrooms.getText()));
                    house.setPropertyPic(new Image(file.toURI().toString()));
                    house.setPurchasePrice(Double.parseDouble(purchasePrice.getText()));
                    house.setAnnualTaxes(Double.parseDouble(taxes.getText()));
                    repo.createProperty(house);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("House added Successfully");
                    //alert.setContentText("House added");
                    Optional<ButtonType> result = alert.showAndWait();
                    //after OK button is clicked it is redirected to the create property menu
                    if (result.get() == ButtonType.OK) {
                        Stage stage = (Stage) create.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("createPropertyMenu.fxml"));
                        Parent root = loader.load();
                        CreatePropertyMenuController createmenu = loader.<CreatePropertyMenuController>getController();
                        createmenu.obtainRepo(repo);
                        stage.setScene(new Scene(root));
                        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
                        stage.setMaximized(true);
                        stage.show();
                    }
                } else {
                    //alerts the user with all the ids that are not populated
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("House is not added");
                    TextArea textArea = new TextArea();
                    textArea.setText(validateAllFields() + " are not populated");
                    VBox dialogPaneContent = new VBox();
                    dialogPaneContent.getChildren().addAll(textArea);
                    // Set content for Dialog Pane
                    alert.getDialogPane().setContent(dialogPaneContent);
                    alert.show();
                }
            } catch (Exception ex) {
                //displays an alert with the stack trace of any exception during house creation
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String s = sw.toString();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("House is not added");
                VBox dialogPaneContent = new VBox();
                Label label = new Label("Stack Trace:");
                TextArea textArea = new TextArea();
                textArea.setText(s);
                dialogPaneContent.getChildren().addAll(label, textArea);
                // Set content for Dialog Pane
                alert.getDialogPane().setContent(dialogPaneContent);
                alert.show();
            }
        }
    }

    //sets the variable repo
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

    //validates if all the text fields and image is populated
    private String validateAllFields() {
        String fields = "";
        for (Node node : anchorpane.getChildren()) {
            if (node instanceof TextField) {
                if (((TextField) node).getText().isEmpty()) {
                    if (fields.isEmpty()) {
                        fields = node.getId();
                    } else {
                        fields += ", " + node.getId();
                    }
                }
            }
        }
        if (fileName.getText().isEmpty()) {
            if (fields.isEmpty()) {
                fields = "image";
            } else {
                fields += ", image";
            }
        }
        return fields;

    }

}
