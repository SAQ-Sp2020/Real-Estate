/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rentmanagementapp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author VarnithaPuli
 */
public class AllPropertiesController implements Initializable {

    @FXML
    private TableView<PropEntity> allProperties;

    @FXML
    private TableColumn<PropEntity, String> state;

    @FXML
    private TableColumn<PropEntity, String> city;

    @FXML
    private Button back;

    private Repo repo = null;
    @FXML
    private TableColumn<PropEntity, String> type;
    @FXML
    private TableColumn<PropEntity, String> price;
    @FXML
    private TableColumn<PropEntity, ImageView> image;
    @FXML
    private Button delete;
    @FXML
    private Button edit;
    @FXML
    private Button view;
    private int selectedIndex = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //sets the table view with list of all houses, apartments, condominiums
    private void loadTable() {
        List<PropEntity> allProps = new ArrayList<>();
        for (Property prop : repo.allProperties()) {
            if (prop instanceof House) {
                ImageView imageview = new ImageView(prop.getPropertyPic());
                imageview.setFitHeight(25);
                imageview.setFitWidth(80);
                allProps.add(new PropEntity(prop.getTypeOfProp(), String.valueOf(((House) prop).getPurchasePrice()), prop.getCity(), prop.getState(), prop.getPostalCode(), imageview));
            }
            if (prop instanceof Apartment) {
                ImageView imageview = new ImageView(prop.getPropertyPic());
                imageview.setFitHeight(25);
                imageview.setFitWidth(80);
                allProps.add(new PropEntity(prop.getTypeOfProp(), String.valueOf(((Apartment) prop).getMonthlyFee()), prop.getCity(), prop.getState(), prop.getPostalCode(), imageview));
            }
            if (prop instanceof Condominiums) {
                ImageView imageview = new ImageView(prop.getPropertyPic());
                imageview.setFitHeight(25);
                imageview.setFitWidth(80);
                allProps.add(new PropEntity(prop.getTypeOfProp(), String.valueOf(((Condominiums) prop).getPurchasePrice()), prop.getCity(), prop.getState(), prop.getPostalCode(), imageview));
            }
        }
        ObservableList<PropEntity> data = FXCollections.observableList(allProps);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        state.setCellValueFactory(new PropertyValueFactory<>("state"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        allProperties.setItems(data);
        allProperties.getSelectionModel().clearSelection();
    }

    //handler method to handle preview, edit, delete and back events for properties list
    @FXML
    public void allPropertiesHandler(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        //when Edit button is clicked, displays a new scene with all the information of a property for update
        if (event.getSource().equals(edit)) {
            String fxml = "";
            int i = allProperties.getSelectionModel().getSelectedIndex();
            System.out.println("all prop size " + repo.allProperties.size());
            this.selectedIndex = i;
            //alerts the user when there is no selection made
            if (i == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please make a selection");
                alert.show();
            } else {
                Property prop = repo.allProperties().get(i);
                stage = (Stage) edit.getScene().getWindow();
                //when the property selected is house
                if (prop instanceof House) {
                    fxml = "house";
                }
                //when the property selected is apartment
                if (prop instanceof Apartment) {
                    fxml = "apartment";
                }
                //when the property selected is condominium
                if (prop instanceof Condominiums) {
                    fxml = "condo";
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
                root = loader.load();
                if (fxml == "house") {
                    HouseController chc = loader.<HouseController>getController();
                    chc.obtainRepoFromAllProps(i, repo);
                } else if (fxml == "apartment") {
                    ApartmentController chc = loader.<ApartmentController>getController();
                    chc.obtainRepoFromAllProps(i, repo);
                } else if (fxml == "condo") {
                    CondoController chc = loader.<CondoController>getController();
                    chc.obtainRepoFromAllProps(i, repo);
                }
            }
            //when Preview button is clicked, displays a new scene with all the information of a property in read only mode
        } else if (event.getSource().equals(view)) {
            String fxml = "property";
            int i = allProperties.getSelectionModel().getSelectedIndex();
            this.selectedIndex = i;
            //alerts the user when there is no selection made
            if (i == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please make a selection");
                alert.show();
            } else {
                Property prop = repo.allProperties().get(i);
                stage = (Stage) view.getScene().getWindow();
                //when the property selected is house
                if (prop instanceof House) {
                    fxml = "house";
                }
                //when the property selected is apartment
                if (prop instanceof Apartment) {
                    fxml = "apartment";
                }
                //when the property selected is condominium
                if (prop instanceof Condominiums) {
                    fxml = "condo";
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
                root = loader.load();
                if (fxml == "house") {
                    HouseController chc = loader.<HouseController>getController();
                    chc.obtainRepoReadOnlyFromAllprop(i, repo);
                } else if (fxml == "apartment") {
                    ApartmentController chc = loader.<ApartmentController>getController();
                    chc.obtainRepoReadOnlyFromAllprop(i, repo);
                } else if (fxml == "condo") {
                    CondoController chc = loader.<CondoController>getController();
                    chc.obtainRepoReadOnlyFromAllprop(i, repo);
                }
            }
            //when Delete button is clicked, deletes the selected property from the list
        } else if (event.getSource().equals(delete)) {
            int i = allProperties.getSelectionModel().getSelectedIndex();
            this.selectedIndex = i;
            //alerts the user when there is no selection made
            if (i == -1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Please make a selection");
                alert.show();
            } else {
                Property property = repo.allProperties().get(i);
                repo.deleteProperty(property);
                stage = (Stage) delete.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("allProperties.fxml"));
                root = loader.load();
                AllPropertiesController mc = loader.<AllPropertiesController>getController();
                mc.obtainRepo(repo);
            }
        } else {
            //when Back button is clicked, redirects to the View/Edit Menu
            System.out.println("in the back function " + this.selectedIndex);
            stage = (Stage) back.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view_editProperty.fxml"));
            root = loader.load();
            View_editPropertyController chc = loader.<View_editPropertyController>getController();
            chc.obtainRepo(repo);
        }
        if (this.selectedIndex != -1 || event.getSource().equals(back)) {
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
            stage.setMaximized(true);
            stage.show();
        }
    }

    //sets the repo variable and calls the loadTable method to load the listings on to the Table view
    public void obtainRepo(Repo repo) {
        this.repo = repo;
        loadTable();
         if (!this.repo.admin) {
            edit.setVisible(false);
            delete.setVisible(false);
        }
    }

}
