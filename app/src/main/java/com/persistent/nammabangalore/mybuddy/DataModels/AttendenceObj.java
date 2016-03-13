package com.persistent.nammabangalore.mybuddy.DataModels;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class AttendenceObj {
    int Id;
    long Date;
    long InOutTime;
    String Type;
    int EmployeeId;

    public AttendenceObj(long date, long inOutTime, String type, int employeeId) {
        Date = date;
        InOutTime = inOutTime;
        Type = type;
        EmployeeId = employeeId;
    }

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

    public long getInOutTime() {
        return InOutTime;
    }

    public void setInOutTime(long inOutTime) {
        InOutTime = inOutTime;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }
}
