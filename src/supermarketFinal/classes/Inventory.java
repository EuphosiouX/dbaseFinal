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
public class Inventory {
    
    private Integer inventory_id;
    private String inventory_name;
    private Float price;
    private Integer stock;
    private String category;
    private Integer supplier_id;

    public Inventory(Integer inventory_id, String inventory_name, Float price, Integer stock, String category, Integer supplier_id) {
        this.inventory_id = inventory_id;
        this.inventory_name = inventory_name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.supplier_id = supplier_id;
    }

    public Integer getInventory_id() {
        return inventory_id;
    }

    public String getInventory_name() {
        return inventory_name;
    }
    
     public Float getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategory() {
        return category;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }
    
    
}
