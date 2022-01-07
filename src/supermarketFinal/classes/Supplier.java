/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermarketFinal.classes;

/**
 *
 * @author Michael Christopher
 */
public class Supplier {
    
    private Integer supplier_id;
    private String supplier_name;
    private String address;
    private String phone_no;

    public Supplier(Integer supplier_id, String supplier_name, String address, String phone_no) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.address = address;
        this.phone_no = phone_no;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone_no() {
        return phone_no;
    }
    
    
}
