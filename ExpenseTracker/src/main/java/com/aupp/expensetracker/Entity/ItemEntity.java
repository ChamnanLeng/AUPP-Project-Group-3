package com.aupp.expensetracker.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "itemInformation")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String itemId;
    @Column
    private String itemName;
    @Column
    private String createDate;
    @Column
    private String lastUpdate;

    public ItemEntity(){}

    public ItemEntity(String itemId, String itemName, String createDate, String lastUpdate) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
