/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import supermarketFinal.classes.FxmlLoader;

/**
 * FXML Controller class
 *
 * @author Michael Christopher
 */
public class ManagerDashboardController {
    
    private final FxmlLoader loader = new FxmlLoader();

    @FXML
    private JFXButton inventoryBtn;
    @FXML
    private JFXButton supplierBtn;
    @FXML
    private JFXButton membershipsBtn;
    @FXML
    private JFXButton cashierBtn;
    @FXML
    private JFXButton cashierCatBtn;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private BorderPane dashboardManagerPane;
    
    @FXML
    private void inventoryButtonCLicked(ActionEvent event) throws IOException {
        Pane view = loader.getView("/supermarketFinal/fxml/inventoryDashboard.fxml");    
        dashboardManagerPane.setCenter(view);
    }

    @FXML
    private void supplierButtonClicked(ActionEvent event) throws IOException {
        Pane view = loader.getView("/supermarketFinal/fxml/supplierDashboard.fxml");    
        dashboardManagerPane.setCenter(view);
    }

    @FXML
    private void membershipsButtonClicked(ActionEvent event) throws IOException {
        Pane view = loader.getView("/supermarketFinal/fxml/membershipsDashboard.fxml");    
        dashboardManagerPane.setCenter(view);
    }

    @FXML
    private void cashierButtonClicked(ActionEvent event) throws IOException {
        Pane view = loader.getView("/supermarketFinal/fxml/cashierDashboard.fxml");    
        dashboardManagerPane.setCenter(view);
    }

    @FXML
    private void cashierCatButtonClicked(ActionEvent event) throws IOException {
        Pane view = loader.getView("/supermarketFinal/fxml/cashierCatDashboard.fxml");    
        dashboardManagerPane.setCenter(view);
    }

    @FXML
    private void homeBtnClicked(ActionEvent event) throws IOException {
        Stage stage = (Stage) homeBtn.getScene().getWindow();
        stage.close();    
        loader.showStage("/supermarketFinal/fxml/Login.fxml");
    }
    
}
