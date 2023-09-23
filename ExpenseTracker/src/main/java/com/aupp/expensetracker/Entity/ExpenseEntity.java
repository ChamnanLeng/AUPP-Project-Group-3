package com.aupp.expensetracker.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenseTransaction")
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expTransactionId")
    private int expTransactionId;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;
    @ManyToOne
    @JoinColumn(name = "itemId")
    private ItemEntity itemId;
    @ManyToOne
    @JoinColumn(name = "currencyId")
    private CurrencyEntity currencyId;
    @Column(name = "expenseAmount")
    private double expenseAmount;
    @Column(name = "expenseDate")
    private String expenseDate;

    public ExpenseEntity(){}

    public ExpenseEntity(int expTransactionId, UserEntity userId, ItemEntity itemId, CurrencyEntity currencyId, double expenseAmount, String expenseDate) {
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

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public ItemEntity getItemId() {
        return itemId;
    }

    public void setItemId(ItemEntity itemId) {
        this.itemId = itemId;
    }

    public CurrencyEntity getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(CurrencyEntity currencyId) {
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
