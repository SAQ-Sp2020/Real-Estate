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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class CreateCondoController implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private Label fileName;

    @FXML
    private TextField state;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField bathrooms;

    @FXML
    private TextField bedrooms;

    @FXML
    private TextField purchasePrice;

    @FXML
    private Button upload;

    @FXML
    private Button back;

    @FXML
    private Button create;

    private Repo repo = null;
    File file;
    @FXML
    private TextField fee;
    @FXML
    private TextField taxes;
    @FXML
    private TextArea description;

    //utility function to validate the key entered is only numbers
    //if an non numeric key is entered, it deletes that character and sets the textfield value to old value
    @FXML
    public void inputHandler(KeyEvent event) {
        TextField origin = (TextField) event.getSource();
        if (!isValidInput(event.getText())) {
            int cursorPos = origin.getText().length();
            if (cursorPos > 0) {
                origin.setText(origin.getText().substring(0, origin.getLength() - 1));
            }
            origin.positionCaret(cursorPos - 1);
        }
    }

    //utility function to validate the input entered is decimals
    //throws an exception if an invalid value is entered
    @FXML
    public void decimalHandler(KeyEvent event) {
        TextField origin = (TextField) event.getSource();
        String character = event.getCharacter();
        String text = origin.getText();

        if (!isValid_forDouble(text, character, 99.99)) {
            event.consume();
        }
    }

    public static boolean isValid_forDouble(String oldText, String newChar, double limit) {
        boolean valid = false;
        String newText = oldText + newChar;
        int maxDecimals = getDecimals(String.valueOf(limit));
        int decimals = 0;

        if (newChar.matches("[0-9]")) {
            decimals = getDecimals(newText);
            if (Double.valueOf(newText) <= limit) {
                if (decimals <= maxDecimals) {
                    valid = true;
                }
            }
        }

        if (newChar.equals(".")) {
            if (!oldText.contains(".")) {
                valid = true;
            }
        }

        return valid;
    }

    //get number of decimals
    private static int getDecimals(String value) {
        int integerPlaces = 0;
        int decimalPlaces = 0;

        if (value.contains(".")) {
            integerPlaces = value.indexOf('.');
            decimalPlaces = value.length() - integerPlaces - 1;
        }

        return decimalPlaces;
    }

    //regex to check for only numbers
    public boolean isValidInput(String s) {
        String regex = "[0-9]+";
        return s.matches(regex);//returns true if input and regex matches otherwise false;
    }

    //handler method defined for all the events during condominium creation
    @FXML
    public void createCondoHandler(ActionEvent event) throws IOException {
        //when upload button is clicked
        if (event.getSource().equals(upload)) {
            //enables user to choose an image files from his computer
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    //restricts user to only choose it from these file types
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
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
            //when create button is clicked, creates a new condominium and adds it to the repo
        } else {
            try {
                Condominiums house = new Condominiums();
                house.setTypeOfProp("Condominium");
                house.setDescription(description.getText());
                house.setAddress(address.getText());
                house.setCity(city.getText());
                house.setState(state.getText());
                house.setPostalCode(postalCode.getText());
                house.setNumbOfBathrooms(Integer.parseInt(bathrooms.getText()));
                house.setNumbOfBedrooms(Integer.parseInt(bedrooms.getText()));
                house.setPropertyPic(new Image(file.toURI().toString()));
                house.setPurchasePrice(Double.parseDouble(purchasePrice.getText()));
                house.setAnnualTaxes(Double.parseDouble(taxes.getText()) * 0.07);
                house.setManagementFee(Double.parseDouble(fee.getText()) * 0.05);
                repo.createProperty(house);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Condominium is added");
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
            } catch (Exception ex) {
                //displays an alert with the stack trace of any execption during condominium creation
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String s = sw.toString();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Condominium is not added");
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

}
