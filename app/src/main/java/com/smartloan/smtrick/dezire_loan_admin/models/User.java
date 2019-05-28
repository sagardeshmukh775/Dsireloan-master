package com.smartloan.smtrick.dezire_loan_admin.models;


import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String regId;
    private String userName;
    private String password;
    private String email;
    private String mobileNumber;
    private String aadhaarNumber;
    private String userProfileImageLarge;
    private String userProfileImageSmall;
    private String userId;
    private String registrationToken;
    private Boolean isTermsAndConditionsAccepted, isBlocked;
    private Long createdDateTime, updatedDateTime;
    private String imeiNumber;
    private String designation;
    private String department;
    private String role;
    private String address;
    private String gender;
    private String agentId;

    private String father="";
    private String city="";
    private String state="";
    private String birthdate="";
    private String pincode="";
    private String accountname="";
    private String bank="";
    private String accounttype="";
    private String accountno="";
    private String pan="";
    private String branch="";
    private String ifsc="";
    private String altmobileno="";

    //empty constructor is neaded
    public User() {
    }


    public void setAltmobileno(String altmobileno) {
        this.altmobileno = altmobileno;
    }
    public String getAltmobileno() {
        return altmobileno;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }
    public String getIfsc() {
        return ifsc;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    public String getBranch() {
        return branch;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getPan() {
        return pan;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }
    public String getAccountno() {
        return accountno;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }
    public String getAccounttype() {
        return accounttype;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBank() {
        return bank;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }
    public String getAccountname() {
        return accountname;
    }


    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getPincode() {
        return pincode;
    }


    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
    public String getBirthdate() {
        return birthdate;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getState() {
        return state;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setFather(String father) {
        this.father = father;
    }
    public String getFather() {
        return father;
    }





    @Exclude
    public Long getCreatedDateTimeLong() {
        return createdDateTime;
    }

    public Map<String, String> getCreatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setCreatedDateTime(Long createdDateTime) {
        this.createdDateTime = (Long) createdDateTime;
    }

    @Exclude
    public Long getUpdatedDateTimeLong() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Long updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public Map<String, String> getUpdatedDateTime() {
        return ServerValue.TIMESTAMP;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getUserProfileImageLarge() {
        return userProfileImageLarge;
    }

    public void setUserProfileImageLarge(String userProfileImageLarge) {
        this.userProfileImageLarge = userProfileImageLarge;
    }

    public String getUserProfileImageSmall() {
        return userProfileImageSmall;
    }

    public void setUserProfileImageSmall(String userProfileImageSmall) {
        this.userProfileImageSmall = userProfileImageSmall;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public Boolean getTermsAndConditionsAccepted() {
        return isTermsAndConditionsAccepted;
    }

    public void setTermsAndConditionsAccepted(Boolean termsAndConditionsAccepted) {
        isTermsAndConditionsAccepted = termsAndConditionsAccepted;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("regId", regId);
        result.put("userName", userName);
        result.put("email", email);
        result.put("mobileNumber", mobileNumber);
        result.put("userId", userId);
        result.put("registrationToken", registrationToken);
        result.put("termsAndConditionsAccepted", true);
        result.put("createdDateTime", getCreatedDateTime());
        result.put("updatedDateTime", getUpdatedDateTime());
        result.put("password", password);
        result.put("address", address);
        result.put("gender", gender);
        result.put("agentId", agentId);
        result.put("father", father);
        result.put("city", city);
        result.put("state", state);
        result.put("birthdate", birthdate);
        result.put("pincode", pincode);
        result.put("accountname", accountname);
        result.put("bank", bank);
        result.put("accounttype", accounttype);
        result.put("accountno", accountno);
        result.put("pan", pan);
        result.put("branch", branch);
        result.put("ifsc", ifsc);
        result.put("altmobileno", altmobileno);
        return result;

        }

    @Exclude
    public Map<String, Object> getUpdateUserMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("email", email);
        result.put("mobileNumber", mobileNumber);
        result.put("updatedDateTime", getUpdatedDateTime());
        result.put("address", address);
        result.put("gender", gender);
        result.put("userProfileImageLarge", userProfileImageLarge);
        result.put("userProfileImageSmall", userProfileImageSmall);
        result.put("father", father);
        result.put("city", city);
        result.put("state", state);
        result.put("birthdate", birthdate);
        result.put("pincode", pincode);
        result.put("accountname", accountname);
        result.put("bank", bank);
        result.put("accounttype", accounttype);
        result.put("accountno", accountno);
        result.put("pan", pan);
        result.put("branch", branch);
        result.put("ifsc", ifsc);
        result.put("altmobileno", altmobileno);
        return result;
    }
}