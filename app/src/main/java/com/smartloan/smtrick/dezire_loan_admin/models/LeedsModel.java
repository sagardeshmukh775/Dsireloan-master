package com.smartloan.smtrick.dezire_loan_admin.models;

import android.view.View;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeedsModel implements Serializable {
    private String customerName;
    private String mobileNumber;
    private String address;
    private String gender;
    private String agentId;
    private Long createdDateTime, updatedDateTime;
    private String loanType;
    private String panCardNumber;
    private String email;
    private String dateOfBirth;
    private String expectedLoanAmount;
    private String occupation;
    private String agentName;
    private String leedId;
    private String status;
    private String customerImageSmall;
    private String customerImagelarg;
    private String leedNumber;
    private String bankName;
    private String payout;
    private String createdBy;
    private Map<String, ImagesModel> documentImages;
    private String approvedLoan;
    private String gst;
    private String officeAdderess;
    private String propertyAddress;
    private String description;
    private String dissbussloan;
    private String approvedDate;
    private String adharNo;
    private String altmobile;
    private String income;
    private String parents;
    private String recidential;
    private String peraddress;
    private String mincome;
    private String yincome;
    private String flatno;
    private String projectname;
    private String propaddress;
    private String propage;
    private String proparea;
    private String pincode;
    private String pcity;
    private String pstate;
    private String note;
    private String paymentDate;
    private String commission;
    ArrayList<String> notes;
    ArrayList<String> disbussAmounts;


    private View.OnClickListener requestBtnClickListener;

    public LeedsModel() {
    }

    public LeedsModel(int id) {


        this.flatno = "Prateek Patel";
        this.projectname = "84121211";
        this.propaddress = "Prateek Patel";
        this.propage = "84121211";
        this.proparea = "Pune";
        this.pincode = "Prateek Patel";
        this.pcity = "84121211";
        this.pstate = "Pune";
        this.mincome = "Prateek Patel";
        this.yincome = "84121211";
        this.parents = "Prateek Patel";
        this.recidential = "84121211";
        this.peraddress = "Pune";
        this.customerName = "Prateek Patel";
        this.mobileNumber = "84121211";
        this.address = "Pune";
        this.gender = "Male";
        this.agentId = "Ag-56465";
        this.loanType = "Home Loan";
        this.panCardNumber = "jds45";
        this.email = "kjsdj@jhjdf.sdf";
        this.expectedLoanAmount = "2565656";
        this.occupation = "vdvf";
        this.agentName = "Aikk";
        this.leedId = "dfgdfg";
        this.status = "Generated";
        this.leedNumber = "LD_56654";
        this.bankName = "SBI";
        this.payout = "325454";
        this.approvedLoan = "3564545";
        this.dissbussloan = "3564545";
        this.gst = "2%";
        this.officeAdderess = "na";
        this.propertyAddress = "na";
        this.description = "na";
        this.altmobile = "na";
        this.adharNo = "na";
        this.income = "na";
        this.approvedDate = "na";
        this.note = "na";
        this.commission = "123456";
        this.paymentDate = "321654684";

        this.notes = new ArrayList<String>();
        this.disbussAmounts = new ArrayList<String>();
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

    public String getofficeAdderess() {
        return officeAdderess;
    }

    public void setOfficeAdderess(String officeAdderess) {
        this.officeAdderess = officeAdderess;
    }

    public String getpropertyAddress() {
        return propertyAddress;
    }

    public String getdescription() {
        return description;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setAltmobile(String altmobile) {
        this.altmobile = altmobile;
    }

    public String getaltmobile() {
        return altmobile;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getadharNo() {
        return adharNo;
    }

    public String getincome() {
        return income;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public void setgst(String gst) {
        this.gst = gst;
    }

    public String getPanCardNumber() {
        return panCardNumber;
    }

    public void setPanCardNumber(String panCardNumber) {
        this.panCardNumber = panCardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }


    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }


    public String getPropaddress() {
        return propaddress;
    }

    public void setPropaddress(String propaddress) {
        this.propaddress = propaddress;
    }


    public String getPropage() {
        return propage;
    }

    public void setPropage(String propage) {
        this.propage = propage;
    }

    public String getProparea() {
        return proparea;
    }

    public void setProparea(String proparea) {
        this.proparea = proparea;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }


    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }


    public String getMincome() {
        return mincome;
    }

    public void setMincome(String mincome) {
        this.mincome = mincome;
    }

    public String getYincome() {
        return yincome;
    }

    public void setYincome(String yincome) {
        this.yincome = yincome;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getExpectedLoanAmount() {
        return expectedLoanAmount;
    }

    public void setdissbussloan(String dissbussloan) {
        this.dissbussloan = dissbussloan;
    }

    public String getDissbussloan() {
        return dissbussloan;
    }

    public void setExpectedLoanAmount(String expectedLoanAmount) {
        this.expectedLoanAmount = expectedLoanAmount;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerImageSmall() {
        return customerImageSmall;
    }

    public void setCustomerImageSmall(String customerImageSmall) {
        this.customerImageSmall = customerImageSmall;
    }

    public String getCustomerImagelarg() {
        return customerImagelarg;
    }

    public void setCustomerImagelarg(String customerImagelarg) {
        this.customerImagelarg = customerImagelarg;
    }

    public String getLeedId() {
        return leedId;
    }

    public void setLeedId(String leedId) {
        this.leedId = leedId;
    }

    public String getLeedNumber() {
        return leedNumber;
    }

    public void setLeedNumber(String leedNumber) {
        this.leedNumber = leedNumber;
    }

    public String getPeraddress() {
        return peraddress;
    }

    public void setPeraddress(String peraddress) {
        this.peraddress = peraddress;
    }


    public String getRecidential() {
        return recidential;
    }

    public void setRecidential(String recidential) {
        this.recidential = recidential;
    }


    public String getParents() {
        return parents;
    }

    public void setParents(String bankName) {
        this.parents = parents;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    public Map<String, ImagesModel> getDocumentImages() {
        return documentImages;
    }

    public void setDocumentImages(Map<String, ImagesModel> documentImages) {
        this.documentImages = documentImages;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getApprovedLoan() {
        return approvedLoan;
    }

    public void setApprovedLoan(String approvedLoan) {
        this.approvedLoan = approvedLoan;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public ArrayList<String> getDisbussAmounts() {
        return disbussAmounts;
    }

    public void setDisbussAmounts(ArrayList<String> disbussAmounts) {
        this.disbussAmounts = disbussAmounts;
    }

    public static ArrayList<LeedsModel> getLeeds() {
        ArrayList<LeedsModel> leedsModelArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LeedsModel leedsModel = new LeedsModel(i);
            leedsModelArrayList.add(leedsModel);
        }
        return leedsModelArrayList;
    }

    @Exclude
    public Map getLeedStatusMap() {
        Map<String, Object> leedMap = new HashMap<>();
        //leedMap.put("status", getStatus());
        leedMap.put("updatedDateTime", getUpdatedDateTime());
        leedMap.put("customerName", getCustomerName());
        leedMap.put("address", getAddress());
        leedMap.put("agentId", getAgentId());
        leedMap.put("loanType", getLoanType());
        leedMap.put("mobileNumber", getMobileNumber());
        leedMap.put("panCardNumber", getPanCardNumber());
        leedMap.put("bankName", getBankName());
        leedMap.put("officeAdderess", getofficeAdderess());
        leedMap.put("propertyAddress", getpropertyAddress());
        leedMap.put("description", getdescription());
        leedMap.put("altmobile", getaltmobile());
        leedMap.put("adharNo", getadharNo());
        leedMap.put("expectedLoanAmount", getExpectedLoanAmount());
        leedMap.put("parents", getParents());
        leedMap.put("recidential", getRecidential());
        leedMap.put("peraddress", getPeraddress());
        leedMap.put("mincome", getMincome());
        leedMap.put("yincome", getYincome());
        leedMap.put("flatno", getFlatno());
        leedMap.put("projectname", getProjectname());
        leedMap.put("propage", getPropage());
        leedMap.put("proparea", getProparea());
        leedMap.put("pincode", getPincode());
        leedMap.put("pcity", getPcity());
        leedMap.put("pstate", getPstate());
        leedMap.put("propaddress", getPropaddress());
        leedMap.put("approvedDate", getApprovedDate());
        leedMap.put("note", getNote());
        leedMap.put("paymentDate", getPaymentDate());
        leedMap.put("commission", getCommission());
        leedMap.put("approvedLoan", getApprovedLoan());
        leedMap.put("dissbussloan", getDissbussloan());
        leedMap.put("email", getEmail());
        leedMap.put("notes", getNotes());
        leedMap.put("disbussAmounts", getDisbussAmounts());

        return leedMap;
    }

    @Exclude
    public Map getLeedStatusMap1() {
        Map<String, Object> leedMap = new HashMap<>();
        leedMap.put("status", getStatus());
        leedMap.put("updatedDateTime", getUpdatedDateTime());

        return leedMap;
    }
}
