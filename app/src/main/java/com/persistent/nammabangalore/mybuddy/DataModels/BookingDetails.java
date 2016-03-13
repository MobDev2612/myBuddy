package com.persistent.nammabangalore.mybuddy.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class BookingDetails implements Parcelable {
    int Id;
    int RoomNo;
    String Title;
    String RoomName;
    int EmployeeId;
    String EmployeeName;
    String Description;
    long BookedDate;
    long StartDate;
    long EndDate;

    public BookingDetails() {
    }

    protected BookingDetails(Parcel in) {
        Id = in.readInt();
        RoomNo = in.readInt();
        Title = in.readString();
        RoomName = in.readString();
        EmployeeId = in.readInt();
        EmployeeName = in.readString();
        Description = in.readString();
        BookedDate = in.readLong();
        StartDate = in.readLong();
        EndDate = in.readLong();
    }

    public BookingDetails(int id, int roomNo, String title, String roomName, int employeeId, String employeeName, String description, long bookedDate, long startDate, long endDate) {
        Id = id;
        RoomNo = roomNo;
        Title = title;
        RoomName = roomName;
        EmployeeId = employeeId;
        EmployeeName = employeeName;
        Description = description;
        BookedDate = bookedDate;
        StartDate = startDate;
        EndDate = endDate;
    }

    public static final Creator<BookingDetails> CREATOR = new Creator<BookingDetails>() {
        @Override
        public BookingDetails createFromParcel(Parcel in) {
            return new BookingDetails(in);
        }

        @Override
        public BookingDetails[] newArray(int size) {
            return new BookingDetails[size];
        }
    };

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(int roomNo) {
        RoomNo = roomNo;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getBookedDate() {
        return BookedDate;
    }

    public void setBookedDate(long bookedDate) {
        BookedDate = bookedDate;
    }

    public long getStartDate() {
        return StartDate;
    }

    public void setStartDate(long startDate) {
        StartDate = startDate;
    }

    public long getEndDate() {
        return EndDate;
    }

    public void setEndDate(long endDate) {
        EndDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeInt(RoomNo);
        dest.writeString(Title);
        dest.writeString(RoomName);
        dest.writeInt(EmployeeId);
        dest.writeString(EmployeeName);
        dest.writeString(Description);
        dest.writeLong(BookedDate);
        dest.writeLong(StartDate);
        dest.writeLong(EndDate);
    }
}
