package com.aupp.expensetracker.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "expenseTransaction")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expTransactionId;
    @Column(name = "userId")
    private int userId;
    @Column(name = "itemId")
    private int itemId;
    @Column(name = "currencyId")
    private int currencyId;
    @Column(name = "expenseAmount")
    private double expenseAmount;
    @Column(name = "expenseDate")
    private String expenseDate;

    public ExpenseEntity(){}

    public ExpenseEntity(int expTransactionId, int userId, int itemId, int currencyId, double expenseAmount, String expenseDate) {
        this.expTransactionId = expTransactionId;
        this.userId = userId;
        this.itemId = itemId;
        this.currencyId = currencyId;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    public int getExpTransactionId() {
        return expTransactionId;
    }

    public void setExpTransactionId(int expTransactionId) {
        this.expTransactionId = expTransactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
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
