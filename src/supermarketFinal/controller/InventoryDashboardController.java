/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import supermarketFinal.classes.FxmlLoader;
import supermarketFinal.classes.Inventory;
import supermarketFinal.classes.JDBConnection;
import supermarketFinal.interfaces.CheckTextField;
import supermarketFinal.interfaces.ItemQuery;

/**
 * FXML Controller class
 *
 * @author Michael Christopher
 */
public class InventoryDashboardController implements Initializable, CheckTextField, ItemQuery {
    
    private final JDBConnection dbLink = new JDBConnection("supermarket");
    private final Connection con = dbLink.getConnection();
    private final FxmlLoader loader = new FxmlLoader();
    
    private String query;
    private ResultSet rs;

    @FXML
    private TableView<Inventory> inventoryTb;
    @FXML
    private TableColumn<Inventory, Integer> inventoryId;
    @FXML
    private TableColumn<Inventory, String> inventoryName;
    @FXML
    private TableColumn<Inventory, Float> price;
    @FXML
    private TableColumn<Inventory, Integer> stock;
    @FXML
    private TableColumn<Inventory, String> category;
    @FXML
    private TableColumn<Inventory, Integer> supplierId;
    @FXML
    private JFXComboBox<String> categoryComb;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private JFXTextField inventoryNameField;
     @FXML
    private JFXTextField priceField;
    @FXML
    private JFXTextField stockField;
    @FXML
    private JFXTextField inventoryNameSearchField;
    @FXML
    private JFXComboBox<String> supplierComb;
    @FXML
    private Label inventoryIdLabel;
    
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> categoryList = FXCollections.observableArrayList("Vegetable", "Fruit", "Meat", "Beverages", "Canned Foods", "Dairy", "Frozen Foods", "Dry Foods");
        categoryComb.setItems(categoryList);
        
        ObservableList<String> supplierList = FXCollections.observableArrayList();
        
        query = "SELECT supplier_id, supplier_name FROM supplier";
        rs = dbLink.queryResult(query);
        
        try {
            while(rs.next()){
                supplierList.add(rs.getInt("supplier_id") + " -- " + rs.getString("supplier_name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        supplierComb.setItems(supplierList);
        showItemList("");
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        searchItem();
        inventoryNameSearchField.setText("");
    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        // Check if searchTitle() == true
        else if(isExist()){
            // Throw alert
            loader.showAlert("Item already exist!!");
        }
        else{
            // Insert item into table by calling insertItem()
            insertItem();
            // Throw alert
            loader.showAlert("Item successfully inserted");
        }  
        setEmpty();
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        else{
            // Insert item into table by calling insertItem()
            updateItem();
            // Throw alert
            loader.showAlert("Item successfully updated");
        }  
        setEmpty();
    }

    @FXML
    private void removeButtonClicked(ActionEvent event) {
         // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        else{
            // Insert item into table by calling insertItem()
            deleteItem();
            // Throw alert
            loader.showAlert("Item successfully deleted");
        }  
        setEmpty();
    }

    
    @FXML
    private void inventoryTableClicked(MouseEvent event) {
        Inventory inventory = inventoryTb.getSelectionModel().getSelectedItem();
        inventoryIdLabel.setText("" + inventory.getInventory_id());
        inventoryNameField.setText(inventory.getInventory_name());
        priceField.setText("" + inventory.getPrice());
        stockField.setText("" + inventory.getStock());
    }
    
    @Override
    public boolean textFieldIsEmpty() {
        return (inventoryNameField.getText().isEmpty() || stockField.getText().isEmpty() || categoryComb.getSelectionModel().getSelectedItem() == null || supplierComb.getSelectionModel().getSelectedItem() == null);
    }

    @Override
    public ObservableList getItemList(String q) {
        
        ObservableList<Inventory> inventoryList = FXCollections.observableArrayList();
        query = "SELECT * FROM inventory" + q;
        rs = dbLink.queryResult(query);
        
        try{
            while(rs.next()){
                Inventory inventory = new Inventory(rs.getInt("inventory_id"), rs.getString("inventory_name"), rs.getFloat("price"), rs.getInt("stock"), rs.getString("category"), rs.getInt("Supplier_id"));
                inventoryList.add(inventory);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return inventoryList;
    }

    @Override
    public void showItemList(String q) {
        // Create list by calling getItemList() 
        ObservableList<Inventory> list = getItemList(q);
        // Set cell value factory of each table's cell
        inventoryId.setCellValueFactory(new PropertyValueFactory<>("inventory_id"));
        inventoryName.setCellValueFactory(new PropertyValueFactory<>("inventory_name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        supplierId.setCellValueFactory(new PropertyValueFactory<>("Supplier_id"));   
        // Set items in the table
        inventoryTb.setItems(list);
    }

    @Override
    public void insertItem() {
        query = "INSERT INTO inventory VALUES (NULL, '" + inventoryNameField.getText() + "'," + priceField.getText() + "," + stockField.getText() + ",'" 
                       + categoryComb.getSelectionModel().getSelectedItem() + "'," + supplierComb.getSelectionModel().getSelectedItem().substring(0,1) +  ")";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void updateItem() {
        query = "UPDATE inventory SET inventory_name  = '" + inventoryNameField.getText() + "', price = " + priceField.getText() + ", stock = " + stockField.getText()+ ", category = '" + 
                categoryComb.getSelectionModel().getSelectedItem() + "', supplier_id = " + supplierComb.getSelectionModel().getSelectedItem().substring(0,1) + " WHERE inventory_id = " + inventoryIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void deleteItem() {
        query = "DELETE FROM inventory WHERE inventory_id = " + inventoryIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void searchItem() {
        showItemList(" WHERE inventory_name LIKE '%" + inventoryNameSearchField.getText() + "%'");
    }
    
    @Override
    public boolean isExist(){
        query = "SELECT count(1) FROM inventory WHERE inventory_name = '" + inventoryNameField.getText() + "' AND supplier_id = " + supplierComb.getSelectionModel().getSelectedItem().substring(0,1);
        rs = dbLink.queryResult(query);
        
        try {
            while(rs.next()){
                if(rs.getInt(1) != 0){
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void setEmpty(){
        inventoryIdLabel.setText("Id");
        inventoryNameField.setText("");
        priceField.setText("");
        stockField.setText("");
    }
}
