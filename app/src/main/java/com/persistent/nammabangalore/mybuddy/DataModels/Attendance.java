package com.persistent.nammabangalore.mybuddy.DataModels;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class Attendance {
    int Id;
    long Date;
    long InTime;
    long OutTime;
    int EmployeeId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public long getInTime() {
        return InTime;
    }

    public void setInTime(long inTime) {
        InTime = inTime;
    }

    public long getOutTime() {
        return OutTime;
    }

    public void setOutTime(long outTime) {
        OutTime = outTime;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }
}
