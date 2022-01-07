/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
public class TransactionDashboardController implements Initializable, CheckTextField, ItemQuery {
    
    private final JDBConnection dbLink = new JDBConnection("supermarket");
    private final Connection con = dbLink.getConnection();
    private final FxmlLoader loader = new FxmlLoader(); 
    
    private String query;
    private ResultSet rs;
    private Stage stage;
    private String gid;
    private String gname;
    private String mid;
    private String mfname;
    private String mlname;
    private String mphoneNo;
    private String mbdate;
    private String mjdate;
    private String mpoints;
    private Integer i = 0;
    private Double grandTotal = 0.0;
    private Integer points = 0;

    @FXML
    private Label cashierIdLabel;
    @FXML
    private Label cashierNameLabel;
    @FXML
    private Label membershipIdLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneNoLabel;
    @FXML
    private Label birthDateLabel;
    @FXML
    private Label joinDateLabel;
    @FXML
    private Label pointsLabel;
    @FXML
    private JFXButton backBtn;
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
    private Label inventoryIdLabel;
    @FXML
    private Label inventoryNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private JFXTextField qtyField;
    @FXML
    private JFXTextField inventoryNameSearchField;
    @FXML
    private JFXButton searchBtn;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton voidBtn;
    @FXML
    private JFXButton clearBtn;
    @FXML
    private TextArea receipt;
    @FXML
    private JFXComboBox<String> paymentComb;
    @FXML
    private JFXTextField cashField;
    @FXML
    private JFXButton checkoutBtn;
    @FXML
    private JFXButton finishBtn;
    @FXML
    private Label receiveField;
    @FXML
    private Label stockLabel;
    @FXML
    private Label amountField;
    

    public void getCashier(String id, String name){
        cashierIdLabel.setText(id);
        cashierNameLabel.setText(name);
        gid = id;
        gname = name;
    }
    
    public void getMember(String id, String fname, String lname, String phoneNo, String bDate, String jDate, String points){
        membershipIdLabel.setText(id);
        firstNameLabel.setText(fname);
        lastNameLabel.setText(lname);
        phoneNoLabel.setText(phoneNo);
        birthDateLabel.setText(bDate);
        joinDateLabel.setText(jDate);
        pointsLabel.setText(points);
        mid = id;
        mfname = fname;
        mlname = lname;
        mphoneNo = phoneNo;
        mbdate = bDate;
        mjdate = jDate; 
        mpoints = points;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEmpty();
        ObservableList<String> categoryList = FXCollections.observableArrayList("Debit Card", "Credit Card", "Cash");
        paymentComb.setItems(categoryList);
        
        showItemList("");
    }
    
    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader ldr = loader.loadStage("/supermarketFinal/fxml/askMembership.fxml");
        Parent root = ldr.load();
        
        AskMembershipController controller = ldr.getController();
        
        controller.getCashier(gid, gname);
        
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        
        Stage stage = (Stage) backBtn.getScene().getWindow();
        stage.close();  
    }

    @FXML
    private void inventoryTableClicked(MouseEvent event) {
        Inventory inventory = inventoryTb.getSelectionModel().getSelectedItem();
        inventoryIdLabel.setText("" + inventory.getInventory_id());
        inventoryNameLabel.setText(inventory.getInventory_name());
        priceLabel.setText("" + inventory.getPrice());
        stockLabel.setText("" + inventory.getStock());
    }

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        searchItem();
    }

    @FXML
    private void addButtonClicked(ActionEvent event) throws SQLException {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        else if (!isNum(qtyField.getText())){
            loader.showAlert("Enter valid input!!");
        }
        else if (belowZero(qtyField.getText())){
            loader.showAlert("Number can't be negative!!");
        }
        else if (Integer.parseInt(qtyField.getText()) > Integer.parseInt(stockLabel.getText())){
            loader.showAlert("Insufficient stock!!");
        }
        else{
            con.setAutoCommit(false);
            if (i==0){
                receipt.setText(receipt.getText() + "========WELCOME TO OWL MARKET========\n");
                receipt.setText(receipt.getText() + "===================================\n");
                receipt.setText(receipt.getText() + "\tCashier\t\t       : " + gname + "\n");
                if (mid == null){
                    receipt.setText(receipt.getText() + "\tMember\t\t       : -\n");
                }
                else{
                    receipt.setText(receipt.getText() + "\tMember\t\t       : " + mfname + " " + mlname + "\n");
                }
                receipt.setText(receipt.getText() + "\tDate\t\t\t       : " + LocalDate.now() + "\n");
                receipt.setText(receipt.getText() + "===================================\n");
                updateItem();
                i++;
                Double total = Double.parseDouble(priceLabel.getText()) * Double.parseDouble(qtyField.getText());
                total = Math.round(total*100)/100.0;
                grandTotal = grandTotal + total;
                points = grandTotal.intValue()/10;
                receipt.setText(receipt.getText() + "\tNo\t\t               : " + i + "\n");
                receipt.setText(receipt.getText() + "\tInventory Name(id): " + inventoryNameLabel.getText() + "(" + inventoryIdLabel.getText() + ")\n");
                receipt.setText(receipt.getText() + "\tPrice\t\t\t       : " + priceLabel.getText() + "\n");
                receipt.setText(receipt.getText() + "\tQty\t\t               : " + qtyField.getText() + "\n");
                receipt.setText(receipt.getText() + "\tTotal\t\t       : " + total + "\n");
                receipt.setText(receipt.getText() + "===================================\n"); 
            }
            else{
                updateItem();
                i++;
                Double total = Double.parseDouble(priceLabel.getText()) * Double.parseDouble(qtyField.getText());
                total = Math.round(total*100)/100.0;
                grandTotal = grandTotal + total;
                points = grandTotal.intValue()/10;
                receipt.setText(receipt.getText() + "\tNo\t\t               : " + i + "\n");
                receipt.setText(receipt.getText() + "\tInventory Name(id): " + inventoryNameLabel.getText() + "(" + inventoryIdLabel.getText() + ")\n");
                receipt.setText(receipt.getText() + "\tPrice\t\t\t       : " + priceLabel.getText() + "\n");
                receipt.setText(receipt.getText() + "\tQty\t\t               : " + qtyField.getText() + "\n");
                receipt.setText(receipt.getText() + "\tTotal\t\t       : " + total + "\n");
                receipt.setText(receipt.getText() + "===================================\n");  
            }   
        }
        setEmpty();
        showPoints();
        showTotal();
    }

    @FXML
    private void voidButtonClicked(ActionEvent event) throws SQLException {
        // Check if textFieldIsEmpty() == true
        if(textFieldIsEmpty()){
            // Throw alert
            loader.showAlert("Fill in required data!!");
        }
        else if (!isNum(qtyField.getText())){
            loader.showAlert("Enter valid input!!");
        }
        else if (belowZero(qtyField.getText())){
            loader.showAlert("Number can't be negative!!");
        }
        else if (Integer.parseInt(qtyField.getText()) > Integer.parseInt(stockLabel.getText())){
            loader.showAlert("Insufficient stock!!");
        }
        else{
            con.setAutoCommit(false);
            deleteItem();
            i++;
            Double total = Double.parseDouble(priceLabel.getText()) * -Double.parseDouble(qtyField.getText());
            total = Math.round(total*100)/100.0;
            grandTotal = grandTotal + total;
            points = grandTotal.intValue()/10;
            receipt.setText(receipt.getText() + "\tNo\t\t               : VOID\n");
            receipt.setText(receipt.getText() + "\tInventory Name(id): " + inventoryNameLabel.getText() + "(" + inventoryIdLabel.getText() + ")\n");
            receipt.setText(receipt.getText() + "\tPrice\t\t\t       : " + priceLabel.getText() + "\n");
            receipt.setText(receipt.getText() + "\tQty\t\t               : " + qtyField.getText() + "\n");
            receipt.setText(receipt.getText() + "\tTotal\t\t       : " + total + "\n");
            receipt.setText(receipt.getText() + "===================================\n");
        }
        setEmpty();
        showPoints();
        showTotal();
    }

    @FXML
    private void clearButtonClicked(ActionEvent event) throws SQLException {
        receipt.setText("");
        con.rollback();
        i = 0;
        grandTotal = 0.0;
        points = 0;
        showItemList("");
        setEmpty();
        showPoints();
        showTotal();
    }
    
        @FXML
    private void checkoutButtonClicked(ActionEvent event) {
        Double cash = 0.0;
        Double change = 0.0;
        if(i < 1){
            loader.showAlert("No transaction!!");
        }
        else if(paymentComb.getSelectionModel().getSelectedItem() == null){
            loader.showAlert("Select payment method!!");
        }
        else{
            if(paymentComb.getSelectionModel().getSelectedItem() == "Debit Card" || paymentComb.getSelectionModel().getSelectedItem() == "Credit Card"){
                cash = grandTotal;
                change = cash - grandTotal;
                points = grandTotal.intValue()/10;
                receipt.setText(receipt.getText() + "\tPayment\t\t       : " + paymentComb.getSelectionModel().getSelectedItem() + "\n");
                receipt.setText(receipt.getText() + "\tGrand Total\t       : " + grandTotal + "\n");
                receipt.setText(receipt.getText() + "\tCash\t\t\t       : " + cash + "\n");
                receipt.setText(receipt.getText() + "\tChange\t\t       : " + change + "\n");
                receipt.setText(receipt.getText() + "\tPoints\t\t       : " + points + "\n");
                receipt.setText(receipt.getText() + "===================================\n");
                showPoints();
                showTotal();
            }
            else{
                if (!isNum(cashField.getText())){
                    loader.showAlert("Enter valid input!!");
                }
                else if (belowZero(cashField.getText())){
                    loader.showAlert("Number can't be negative!!");
                }
                else if (grandTotal > Integer.parseInt(cashField.getText())){
                    loader.showAlert("Insufficient cash!!");
                }
                else{
                    cash = Double.parseDouble(cashField.getText());
                    change = cash - grandTotal;
                    change = Math.round(change*100)/100.0;
                    points = grandTotal.intValue()/10;
                    receipt.setText(receipt.getText() + "\tPayment\t\t       : " + paymentComb.getSelectionModel().getSelectedItem() + "\n");
                    receipt.setText(receipt.getText() + "\tGrand Total\t       : " + grandTotal + "\n");
                    receipt.setText(receipt.getText() + "\tCash\t\t\t       : " + cash + "\n");
                    receipt.setText(receipt.getText() + "\tChange\t\t       : " + change + "\n");
                    receipt.setText(receipt.getText() + "\tPoints\t\t       : " + points + "\n");
                    receipt.setText(receipt.getText() + "===================================\n");
                    showPoints();
                    showTotal();
                }
            }
        }   
    }

    @FXML
    private void finishButtonClicked(ActionEvent event) throws SQLException {
        con.commit();
        con.setAutoCommit(true);
        insertItem();
        if (mid != null){
            addPoints();
        }
        loader.showAlert("Transaction completed!!");
    }

    @Override
    public boolean textFieldIsEmpty() {
        return (inventoryIdLabel.getText().equals("Id"));
    }
    
    private boolean belowZero(String text){
        return (Integer.parseInt(text) <= 0);
    }
    
    private boolean isNum(String text){
        try{
            Float.parseFloat(text);
        }
        catch(NumberFormatException e){
            return false;
        }
        catch(NullPointerException e) {
            return false;
        }
        return true;
    }
    
    private void showPoints(){
        receiveField.setText("Points received: " + points);
    }
    
    private void showTotal(){
        amountField.setText("Total Amount: " + grandTotal);
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
        if (mid == null){
            query = "INSERT INTO invoice VALUES (NULL, CURRENT_DATE(), '" + paymentComb.getSelectionModel().getSelectedItem() + "', " + grandTotal + ", " + gid + ", NULL)";
        }
        else{
            query = "INSERT INTO invoice VALUES (NULL, CURRENT_DATE(), '" + paymentComb.getSelectionModel().getSelectedItem() + "', " + grandTotal + ", " + gid + ", " + mid + ")";
        }
        
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void updateItem() {
        query = "UPDATE inventory SET stock = stock - " + qtyField.getText() + " WHERE inventory_id = " + inventoryIdLabel.getText();
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }
    
    private void addPoints(){
        query = "UPDATE memberships SET  points = points + " + points + " WHERE membership_id = " + mid;
        // Execute the query by calling executeQuery() from JDBConnection
        dbLink.executeQuery(query);
        // Show the table by calling showItemList() 
        showItemList("");
    }

    @Override
    public void deleteItem() {
        query = "UPDATE inventory SET stock = stock + " + qtyField.getText() + " WHERE inventory_id = " + inventoryIdLabel.getText();
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
    public boolean isExist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setEmpty() {
        inventoryIdLabel.setText("Id");
        inventoryNameLabel.setText("Inventory Name");
        priceLabel.setText("Price");
        stockLabel.setText("Stock");
        qtyField.setText("0");
    }
}