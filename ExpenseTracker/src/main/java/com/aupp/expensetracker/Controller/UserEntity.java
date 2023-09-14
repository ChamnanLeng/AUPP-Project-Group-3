package com.aupp.expensetracker.Controller;

import jakarta.persistence.*;

@Entity
@Table(name = "userInformation")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    @Column
    private String userName;
    @Column
    private String userEmail;
    @Column
    private String userPassword;
    @Column
    private Boolean isVerify;
    @Column
    private String createDate;
    @Column
    private String lastUpdate;
    @Column
    private String removeDate;

    public UserEntity(){}

    public UserEntity(String userId, String userName, String userEmail, String userPassword, Boolean isVerify, String createDate, String lastUpdate, String removeDate) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.isVerify = isVerify;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
        this.removeDate = removeDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Boolean getVerify() {
        return isVerify;
    }

    public void setVerify(Boolean verify) {
        isVerify = verify;
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

    public String getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(String removeDate) {
        this.removeDate = removeDate;
    }
}
