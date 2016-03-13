package com.persistent.nammabangalore.mybuddy.DataModels;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class Employee {
    int EmployeeId;
    String EmployeeName;
    String EmailId;
    String MobileNo;
    String Designation;
    String BusinessUnit;
    String ImageUrl;
    boolean IsAdmin;

    public Employee(int employeeId, String employeeName, String emailId, String mobileNo, String designation, String businessUnit, String imageUrl, boolean isAdmin) {
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        EmailId = emailId;
        MobileNo = mobileNo;
        Designation = designation;
        BusinessUnit = businessUnit;
        ImageUrl = imageUrl;
        IsAdmin = isAdmin;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getBusinessUnit() {
        return BusinessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        BusinessUnit = businessUnit;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        IsAdmin = isAdmin;
    }
}
