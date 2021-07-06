package com.bkenterprises.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="inventory")
public class Inventory {

    @Column(name="uuid")
    private @Id String uuid;

    @Column(name="product_uuid")
    private String productUUID;

    @Column(name="vendor_uuid")
    private String vendorUUID;

    @Column(name="quantity")
    private int quantity;

    @Column(name="total_cost")
    private float totalCost;

    @Column(name="delivered_on")
    private LocalDateTime deliveredOn;

    @Column(name="modified_on")
    private LocalDateTime modifiedOn;

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setVendorUUID(String vendorUUID) {
        this.vendorUUID = vendorUUID;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getDeliveredOn() {
        return deliveredOn;
    }

    public String getUuid() {
        return uuid;
    }

    public String getVendorUUID() {
        return vendorUUID;
    }

    public void setDeliveredOn(LocalDateTime deliveredOn) {
        this.deliveredOn = deliveredOn;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return quantity == inventory.quantity && Float.compare(inventory.totalCost, totalCost) == 0 &&
                Objects.equals(uuid, inventory.uuid) && Objects.equals(productUUID, inventory.productUUID) &&
                Objects.equals(vendorUUID, inventory.vendorUUID) && Objects.equals(deliveredOn, inventory.deliveredOn) &&
                Objects.equals(modifiedOn, inventory.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, productUUID, vendorUUID, quantity, totalCost, deliveredOn, modifiedOn);
    }

}
