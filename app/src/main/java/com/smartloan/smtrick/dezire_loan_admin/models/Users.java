package com.smartloan.smtrick.dezire_loan_admin.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private String name;
    private String mobilenumber;
    private String agentId;
    private String fathername;
    private String address;
    private String city;
    private String state;
    private String birthdate;
    private String pincode;
    private String email;
    private String altmobilenumber;
    private String accountname;
    private String bankname;
    private String accountnumber;
    private String accounttype;
    private String pannumber;
    private String branchname;
    private String ifsc;
    private String userid;

    public Users() {
    }


    public Users(String name, String mobilenumber, String agentId, String fathername, String address, String city,
                 String state, String birthdate, String pincode, String email, String altmobilenumber,
                 String accountname, String bankname, String accountnumber, String accounttype, String pannumber,
                 String branchname, String ifsc, String userid) {
        this.name = name;
        this.mobilenumber = mobilenumber;
        this.agentId = agentId;
        this.fathername = fathername;
        this.address = address;
        this.city = city;
        this.state = state;
        this.birthdate = birthdate;
        this.pincode = pincode;
        this.email = email;
        this.altmobilenumber = altmobilenumber;
        this.accountname = accountname;
        this.bankname = bankname;
        this.accountnumber = accountnumber;
        this.accounttype = accounttype;
        this.pannumber = pannumber;
        this.branchname = branchname;
        this.ifsc = ifsc;
        this.userid = userid;
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

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAltmobilenumber() {
        return altmobilenumber;
    }

    public void setAltmobilenumber(String altmobilenumber) {
        this.altmobilenumber = altmobilenumber;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getPannumber() {
        return pannumber;
    }

    public void setPannumber(String pannumber) {
        this.pannumber = pannumber;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getIfsc() {
        return ifsc;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    @Exclude
    public Map<String, Object> getUpdateUserMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("agentId", agentId);
        result.put("name", name);
        result.put("email", email);
        result.put("mobilenumber", mobilenumber);
        result.put("address", address);
        result.put("fathername", fathername);
        result.put("city", city);
        result.put("state", state);
        result.put("birthdate", birthdate);
        result.put("pincode", pincode);
        result.put("accountname", accountname);
        result.put("bankname", bankname);
        result.put("accounttype", accounttype);
        result.put("accountnumber", accountnumber);
        result.put("pannumber", pannumber);
        result.put("branchname", branchname);
        result.put("ifsc", ifsc);
        result.put("altmobilenumber", altmobilenumber);
        result.put("userid", userid);
        return result;
    }
}
