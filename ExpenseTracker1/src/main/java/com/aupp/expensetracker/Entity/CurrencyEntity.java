package com.aupp.expensetracker.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "currencyInformation")
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int currencyId;
    @Column(name = "currencyName")
    private String currencyName;
    @Column(name = "currencyValue")
    private int currencyValue;
    @Column(name = "currencyRateValue")
    private int currencyRateValue;

    public CurrencyEntity(){}

    public CurrencyEntity(int currencyId, String currencyName, int currencyValue, int currencyRateValue) {
        this.currencyId = currencyId;
        this.currencyName = currencyName;
        this.currencyValue = currencyValue;
        this.currencyRateValue = currencyRateValue;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(int currencyValue) {
        this.currencyValue = currencyValue;
    }

    public int getCurrencyRateValue() {
        return currencyRateValue;
    }

    public void setCurrencyRateValue(int currencyRateValue) {
        this.currencyRateValue = currencyRateValue;
    }
}
