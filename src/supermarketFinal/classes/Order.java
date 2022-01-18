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
public class Order {
    private Integer order_id;
    private Integer inventory_id;
    private Float price;
    private Integer quantity;
    private Float total;
    private Integer invoice_id;

    public Order(Integer order_id, Integer inventory_id, Float price, Integer quantity, Float total, Integer invoice_id) {
        this.order_id = order_id;
        this.inventory_id = inventory_id;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.invoice_id = invoice_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Integer getInventory_id() {
        return inventory_id;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Float getTotal() {
        return total;
    }

    public Integer getInvoice_id() {
        return invoice_id;
    }  
}
