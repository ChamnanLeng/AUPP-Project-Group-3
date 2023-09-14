package com.aupp.expensetracker.Controller;

import jakarta.persistence.*;

@Entity
@Table(name = "expenseTransaction")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String expTransactionId;
    @Column
    private String userId;
    @Column
    private String itemId;
    @Column
    private String currencyId;
    @Column
    private double expenseAmount;
    @Column
    private String expenseDate;

    public ExpenseEntity(){}

    public ExpenseEntity(String expTransactionId, String userId, String itemId, String currencyId, double expenseAmount, String expenseDate) {
        this.expTransactionId = expTransactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.currencyId = currencyId;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    public String getExpTransactionId() {
        return expTransactionId;
    }

    public void setExpTransactionId(String expTransactionId) {
        this.expTransactionId = expTransactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
