package com.smartloan.smtrick.dezire_loan_admin.models;

public class Users {

    private String name;
    private String mobilenumber;
    private String agentId;

    public Users() {
    }


    public Users(String name, String mobilenumber, String agentId) {
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
