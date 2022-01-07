/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import supermarketFinal.classes.JDBConnection;
import supermarketFinal.classes.StaffCategory;
import supermarketFinal.classes.Supplier;
import supermarketFinal.interfaces.CheckTextField;
import supermarketFinal.interfaces.ItemQuery;

/**
 * FXML Controller class
 *
 * @author Michael Christopher
 */
public class CashierCatDashboardController implements Initializable, CheckTextField, ItemQuery {

    private final JDBConnection dbLink = new JDBConnection("supermarket");
    private final Connection con = dbLink.getConnection();
    private final FxmlLoader loader = new FxmlLoader();
    
    private String query;
    private ResultSet rs;
    
    @FXML
    private TableView<StaffCategory> cashierCategoryTb;
    @FXML
    private TableColumn<StaffCategory, Integer> staffCategoryId;
    @FXML
    private TableColumn<StaffCategory, String> categoryName;
    @FXML
    private TableColumn<StaffCategory, Float> hourlySalary;
    @FXML
    private TableColumn<StaffCategory, String> workingDays;
    @FXML
    private TableColumn<StaffCategory, String> workingHours;
    @FXML
    private JFXComboBox<String> workingDaysComb;
    @FXML
    private JFXComboBox<String> workingHoursComb;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton updateBtn;
    @FXML
    private JFXButton removeBtn;
    @FXML
    private Label staffCategoryIdLabel;
    @FXML
    private JFXTextField hourlySalaryField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showItemList("");
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
            updateItem();
        // Throw alert
            loader.showAlert("Item successfully inserted");
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
            deleteItem();
            // Throw alert
            loader.showAlert("Item successfully inserted");
        }  
        setEmpty();
    }

    @FXML
    private void cashierCategoryTableClicked(MouseEvent event) {
        StaffCategory staffCategory = cashierCategoryTb.getSelectionModel().getSelectedItem();
        staffCategoryId.setText("" + staffCategory.getStaff_category_id());
        categoryName.setText(staffCategory.getCategory_name());
        hourlySalary.setText("" + staffCategory.getHourly_salary());
        workingDays.setText(staffCategory.getWorking_days());
        workingHours.setText(staffCategory.getWorking_hours());
    }

    @Override
    public boolean textFieldIsEmpty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList getItemList(String q) {
        ObservableList<StaffCategory> staffCategoryList = FXCollections.observableArrayList();
        query = "SELECT * FROM staffcategory" + q;
        rs = dbLink.queryResult(query);
        
        try{
            while(rs.next()){
                StaffCategory staffCategory = new StaffCategory(rs.getInt("staff_category_id"), rs.getString("category_name"), rs.getFloat("hourly_salary"), rs.getString("working_days"), rs.getString("working_hours"));
                staffCategoryList.add(staffCategory);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return staffCategoryList;
    }

    @Override
    public void showItemList(String q) {
        // Create list by calling getItemList() 
        ObservableList<StaffCategory> list = getItemList(q);
        // Set cell value factory of each table's cell
        staffCategoryId.setCellValueFactory(new PropertyValueFactory<>("staff_category_id"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        hourlySalary.setCellValueFactory(new PropertyValueFactory<>("hourly_salary"));
        workingDays.setCellValueFactory(new PropertyValueFactory<>("working_days"));  
        workingHours.setCellValueFactory(new PropertyValueFactory<>("working_hours"));  
        // Set items in the table
        cashierCategoryTb.setItems(list);
    }

    @Override
    public void insertItem() {
        query = "INSERT INTO staffcategory VALUES (NULL, 'Cashier'," + hourlySalaryField.getText() + ",'" 
                       + workingDaysComb.getSelectionModel().getSelectedItem() + "','" + workingHoursComb.getSelectionModel().getSelectedItem() + "')";
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void updateItem() {
        query = "UPDATE staffcategory SET category_name  = 'Cashier', hourly_salary = " + hourlySalaryField.getText()+ ", working_days = '" + 
                workingDaysComb.getSelectionModel().getSelectedItem() + "', working_days = '" + workingHoursComb.getSelectionModel().getSelectedItem() + "' WHERE staff_category_id = " + staffCategoryIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void deleteItem() {
        query = "DELETE FROM staffcategory WHERE staff_category_id = " + staffCategoryIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void searchItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isExist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setEmpty() {
        staffCategoryId.setText("");
        categoryName.setText("");
        hourlySalary.setText("");
        workingDays.setText("");
        workingHours.setText("");
    }
    
}
