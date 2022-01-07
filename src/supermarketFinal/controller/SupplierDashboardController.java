/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
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
import supermarketFinal.classes.FxmlLoader;
import supermarketFinal.classes.Inventory;
import supermarketFinal.classes.JDBConnection;
import supermarketFinal.classes.Supplier;
import supermarketFinal.interfaces.CheckTextField;
import supermarketFinal.interfaces.ItemQuery;

/**
 * FXML Controller class
 *
 * @author Michael Christopher
 */
public class SupplierDashboardController implements Initializable, CheckTextField, ItemQuery {
    
    private final JDBConnection dbLink = new JDBConnection("supermarket");
    private final Connection con = dbLink.getConnection();
    private final FxmlLoader loader = new FxmlLoader();
    
    private String query;
    private ResultSet rs;

    @FXML
    private TableView<Supplier> supplierTb;
    @FXML
    private TableColumn<Supplier, Integer> supplierId;
    @FXML
    private TableColumn<Supplier, String> supplierName;
    @FXML
    private TableColumn<Supplier, String> address;
    @FXML
    private TableColumn<Supplier, String> phoneNo;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private Label supplierIdLabel;
    @FXML
    private JFXTextField supplierNameField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField phoneNoField;
    @FXML
    private JFXTextField suppliernameSearchField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showItemList("");
    }    

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        searchItem();
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
        // Check if searchTitle() == true
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
    private void supplierTableClicked(MouseEvent event) {
        Supplier supplier = supplierTb.getSelectionModel().getSelectedItem();
        supplierIdLabel.setText("" + supplier.getSupplier_id());
        supplierNameField.setText(supplier.getSupplier_name());
        addressField.setText(supplier.getAddress());
        phoneNoField.setText(supplier.getPhone_no());
    }

    @Override
    public boolean textFieldIsEmpty() {
        return (supplierNameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneNoField.getText().isEmpty());
    }

    @Override
    public ObservableList getItemList(String q) {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
        query = "SELECT * FROM supplier" + q;
        rs = dbLink.queryResult(query);
        
        try{
            while(rs.next()){
                Supplier supplier = new Supplier(rs.getInt("supplier_id"), rs.getString("supplier_name"), rs.getString("address"), rs.getString("phone_no"));
                supplierList.add(supplier);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return supplierList;
    }

    @Override
    public void showItemList(String q) {
        // Create list by calling getItemList() 
        ObservableList<Supplier> list = getItemList(q);
        // Set cell value factory of each table's cell
        supplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        supplierName.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNo.setCellValueFactory(new PropertyValueFactory<>("phone_no"));  
        // Set items in the table
        supplierTb.setItems(list);
    }

    @Override
    public void insertItem() {
        query = "INSERT INTO supplier VALUES (NULL, '" + supplierNameField.getText() + "','" + addressField.getText() + "','" 
                       + phoneNoField.getText() + "')";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void updateItem() {
        query = "UPDATE supplier SET supplier_name  = '" + supplierNameField.getText() + "', address = '" + addressField.getText()+ "', phone_no = '" + 
                phoneNoField.getText() + "' WHERE supplier_id = " + supplierIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void deleteItem() {
        query = "DELETE FROM supplier WHERE supplier_id = " + supplierIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void searchItem() {
        showItemList(" WHERE supplier_name LIKE '%" + suppliernameSearchField.getText() + "%'");
    }

    @Override
    public boolean isExist() {
        query = "SELECT count(1) FROM supplier WHERE supplier_name = '" + supplierNameField.getText() + "' AND address = '" + addressField.getText() + "'";
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
    public void setEmpty() {
        supplierIdLabel.setText("");
        supplierNameField.setText("");
        addressField.setText("");
        phoneNoField.setText("");
    }
}
