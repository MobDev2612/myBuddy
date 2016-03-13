package com.persistent.nammabangalore.mybuddy.DataModels;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class EventDetails {

    int Id;
    String Title;
    String Description;
    String Location;
    long StartTime;
    long EndTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }
}
