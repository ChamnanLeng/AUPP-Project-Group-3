package com.aupp.expensetracker.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminId")
    private int adminId;
    @Column(name = "adminName")
    private String adminName;
    @Column(name = "adminEmail")
    private String adminEmail;
    @Column(name = "adminPassword")
    private String adminPassword;
    public AdminEntity(){}

    public AdminEntity(int adminId, String adminName, String adminEmail, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
