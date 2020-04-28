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
public class CondoController implements Initializable {

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
    private TextField bath;
    @FXML
    private TextField bed;
    @FXML
    private TextField taxes;
    @FXML
    private TextField fee;
    @FXML
    private TextArea description;
    private boolean allPropflag = false;
    private boolean readonly = false;

    //when Back button is clicked, it redirects to the condominiums list/All prperties list based on the previous Scene
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        String fxml = "condos";
        if (this.allPropflag) {
            fxml = "allProperties";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
        Parent root = loader.load();
        if (this.allPropflag) {
            AllPropertiesController mc = loader.<AllPropertiesController>getController();
            mc.obtainRepo(repo);
        } else {
            CondosController mc = loader.<CondosController>getController();
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

    //method to load the selected condominium from the list
    public void obtainRepo(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        loadProperty();
    }

    //method to load the selected property from the list and display it on the form
    private void loadPropertyFromAllProps() {
        Property prop = repo.allProperties().get(index);
        if (prop instanceof Condominiums) {
            Condominiums condo = (Condominiums) prop;
            fee.setText(String.valueOf(condo.getManagementFee()));
            taxes.setText(String.valueOf(condo.getAnnualTaxes()));
            bed.setText(String.valueOf(condo.getNumbOfBedrooms()));
            bath.setText(String.valueOf(condo.getNumbOfBathrooms()));
            description.setText(condo.getDescription());
            address.setText(condo.getAddress());
            city.setText(condo.getCity());
            state.setText(condo.getState());
            postalCode.setText(condo.getPostalCode());
            amount.setText(String.valueOf(condo.getPurchasePrice()));
            pic.setImage(condo.getPropertyPic());
        }
    }

    //method to load the selected condominium from the list and display it on the form
    private void loadProperty() {
        Condominiums prop = repo.allCondos().get(index);
        fee.setText(String.valueOf(prop.getManagementFee()));
        taxes.setText(String.valueOf(prop.getAnnualTaxes()));
        bed.setText(String.valueOf(prop.getNumbOfBedrooms()));
        bath.setText(String.valueOf(prop.getNumbOfBathrooms()));
        description.setText(prop.getDescription());
        address.setText(prop.getAddress());
        city.setText(prop.getCity());
        state.setText(prop.getState());
        postalCode.setText(prop.getPostalCode());
        amount.setText(String.valueOf(prop.getPurchasePrice()));
        pic.setImage(prop.getPropertyPic());

    }

    //method to load the selected condominium from the list in readonly mode
    public void obtainRepoReadOnly(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        this.readonly = true;
        edit.setVisible(false);//hides Save button in preview mode
        loadProperty();
        setReadOnly();
    }

    //method to load the selected property from the list in readonly mode
    public void obtainRepoReadOnlyFromAllprop(int index, Repo repo) {
        this.repo = repo;
        this.index = index;
        this.allPropflag = true;
        this.readonly = true;
        edit.setVisible(false);//hides the Save button in preview mode
        loadPropertyFromAllProps();
        setReadOnly();
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
            taxes.setEditable(false);
            state.setEditable(false);
            fee.setEditable(false);
        }
    }

    //handler method for Save button
    @FXML
    public void modify(ActionEvent event) throws IOException {
        //when the Save button is clicked, it saves the updated values of the condominium
        if (event.getSource().equals(edit)) {
            try {
                Condominiums condo = null;
                if (this.allPropflag) {
                    condo = (Condominiums) repo.allProperties().get(index);
                } else {
                    condo = repo.allCondos().get(index);
                }
                for (int i = 0; i < repo.allProperties().size(); i++) {
                    if (repo.allProperties().get(i).equals(condo)) {
                        condo.setDescription(description.getText());
                        condo.setManagementFee(Double.parseDouble(fee.getText()));
                        condo.setNumbOfBathrooms(Integer.parseInt(bath.getText()));
                        condo.setNumbOfBedrooms(Integer.parseInt(bed.getText()));
                        condo.setAnnualTaxes(Double.parseDouble(taxes.getText()));
                        condo.setAddress(address.getText());
                        condo.setCity(city.getText());
                        condo.setState(state.getText());
                        condo.setPostalCode(postalCode.getText());
                        condo.setPurchasePrice(Double.parseDouble(amount.getText()));
                        repo.updateProperty(i, condo);
                    }
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Condomimium is updated Successfully");
                alert.show();
            } catch (Exception ex) {
                //displays an alert with the stack trace of any exception during Condominium update
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                String s = sw.toString();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Condominium is not updated");
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
