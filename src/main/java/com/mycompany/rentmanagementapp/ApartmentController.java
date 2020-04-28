/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class ApartmentController implements Initializable {

    @FXML
    private ImageView pic;

    @FXML
    private TextField address;

    @FXML
    private TextField city;

    @FXML
    private TextField state;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField amount;

    @FXML
    private Button back;

    private Button delete;

    @FXML
    private Button edit;

    private Repo repo = null;
    private int index;
    @FXML
    private TextArea description;
    @FXML
    private TextField lease;
    @FXML
    private TextField bed;
    @FXML
    private TextField bath;
    private boolean allPropflag = false;
    private boolean readonly = false;

    //when Back button is clicked, it redirects to the Apartments list/All prperties list based on the previous Scene
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        String fxml = "apartments";
        if (this.allPropflag) {
            fxml = "allProperties";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        if (this.allPropflag) {
            AllPropertiesController mc = loader.<AllPropertiesController>getController();
            mc.obtainRepo(repo);
        } else {
            ApartmentsController mc = loader.<ApartmentsController>getController();
            mc.obtainRepo(repo);
        }
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setMaximized(true);
        stage.show();
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //method to load the selected property from the list
    public void obtainRepoFromAllProps(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        this.allPropflag = true;
        loadPropertyFromAllProps();
    }

    //method to load the selected apartment from the list
    public void obtainRepo(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        loadProperty();
    }

    //method to load the selected property from the list and display it on the form
    private void loadPropertyFromAllProps() {
        Property prop = repo.allProperties().get(index);
        if (prop instanceof Apartment) {
            Apartment apt = (Apartment) prop;
            description.setText(apt.getDescription());
            address.setText(apt.getAddress());
            city.setText(apt.getCity());
            state.setText(apt.getState());
            postalCode.setText(apt.getPostalCode());
            bed.setText(String.valueOf(apt.getNumbOfBedrooms()));
            bath.setText(String.valueOf(apt.getNumbOfBathrooms()));
            lease.setText(String.valueOf(apt.getMinimumLeasePeriod()));
            amount.setText(String.valueOf(apt.getMonthlyFee()));
            pic.setImage(apt.getPropertyPic());
        }
    }

    //method to load the selected apartment from the list in readonly mode
    public void obtainRepoReadOnly(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        this.readonly = true;
        edit.setVisible(false);
        loadProperty();
        setReadOnly();
    }

    //method to load the selected property from the list in readonly mode
    public void obtainRepoReadOnlyFromAllprop(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        this.allPropflag = true;
        this.readonly = true;
        edit.setVisible(false);
        loadPropertyFromAllProps();
        setReadOnly();
    }

    //method to load the selected apartment from the list and display it on the form
    private void loadProperty() {
        Apartment prop = repo.allApartment().get(index);
        description.setText(prop.getDescription());
        address.setText(prop.getAddress());
        city.setText(prop.getCity());
        state.setText(prop.getState());
        postalCode.setText(prop.getPostalCode());
        bed.setText(String.valueOf(prop.getNumbOfBedrooms()));
        bath.setText(String.valueOf(prop.getNumbOfBathrooms()));
        lease.setText(String.valueOf(prop.getMinimumLeasePeriod()));
        amount.setText(String.valueOf(prop.getMonthlyFee()));
        pic.setImage(prop.getPropertyPic());

    }

    //sets all the textfields on the view to readonly
    public void setReadOnly() {
        if (this.readonly) {
            description.setEditable(false);
            bath.setEditable(false);
            bed.setEditable(false);
            amount.setEditable(false);
            postalCode.setEditable(false);
            city.setEditable(false);
            address.setEditable(false);
            lease.setEditable(false);
            state.setEditable(false);
        }
    }

    //handler method for Save button
    @FXML
    public void modify(ActionEvent event) throws IOException {
        //when the Save button is clicked, it saves the updated values of the Apartment
        if (event.getSource().equals(delete)) {
            Apartment condo = repo.allApartment().get(index);
            repo.deleteProperty(condo);
            Stage stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("apartments.fxml"));
            Parent root = loader.load();
            ApartmentsController mc = loader.<ApartmentsController>getController();
            mc.obtainRepo(repo);
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
            stage.setMaximized(true);
            stage.show();
        } else if (event.getSource().equals(edit)) {
            try {
                Apartment condo = null;
                if (this.allPropflag) {
                    condo = (Apartment) repo.allProperties().get(index);
                } else {
                    condo = repo.allApartment().get(index);
                }
                for (int i = 0; i < repo.allProperties().size(); i++) {
                    if (repo.allProperties().get(i).equals(condo)) {
                        condo.setDescription(description.getText());
                        condo.setMinimumLeasePeriod(Integer.parseInt(lease.getText()));
                        condo.setNumbOfBathrooms(Integer.parseInt(bath.getText()));
                        condo.setNumbOfBedrooms(Integer.parseInt(bed.getText()));
                        condo.setAddress(address.getText());
                        condo.setCity(city.getText());
                        condo.setState(state.getText());
                        condo.setPostalCode(postalCode.getText());
                        condo.setMonthlyFee(Double.parseDouble(amount.getText()));
                        repo.updateProperty(i, condo);
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Apartment is updated successfully");
                alert.show();
            } catch (Exception ex) {
                //displays an alert with the stack trace of any exception during Apartment update
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String s = sw.toString();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Apartment is not updated");
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
}
