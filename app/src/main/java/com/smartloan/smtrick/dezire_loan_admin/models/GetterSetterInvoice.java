package com.smartloan.smtrick.dezire_loan_admin.models;

public class GetterSetterInvoice {
    private String name = "";
    private String cityState = "";
    private String phone = "";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCityState(String cityState) {
        this.cityState = cityState;
    }

    public String getCityState() {
        return cityState;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
