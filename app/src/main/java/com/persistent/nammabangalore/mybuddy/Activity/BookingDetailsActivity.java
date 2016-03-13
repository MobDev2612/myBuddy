package com.persistent.nammabangalore.mybuddy.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.persistent.nammabangalore.mybuddy.DataModels.BookingDetails;
import com.persistent.nammabangalore.mybuddy.DataModels.Employee;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.FontView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookingDetailsActivity extends AppCompatActivity {

    FontView meetingTitle,bookedBy,meetingDate,meetingStartTime,meetingEndTime,meetingDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialiseComponents();
    }

    private void initialiseComponents(){
        meetingTitle = (FontView) findViewById(R.id.meeting_details_name);
        bookedBy = (FontView) findViewById(R.id.meeting_details_booked_by);
        meetingDate = (FontView) findViewById(R.id.meeting_details_date);
        meetingStartTime = (FontView) findViewById(R.id.meeting_details_start_time);
        meetingEndTime = (FontView) findViewById(R.id.meeting_details_end_time);
        meetingDescription = (FontView) findViewById(R.id.meeting_details_description);
        Intent intent = getIntent();
        BookingDetails bookingDetails = intent.getParcelableExtra("data");
        setData(bookingDetails);
    }

    private void setData(BookingDetails bookingDetails){
        meetingTitle.setText(bookingDetails.getTitle());
        bookedBy.setText(bookingDetails.getEmployeeName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bookingDetails.getBookedDate()*1000);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        meetingDate.setText(dateFormatter.format(calendar.getTime()));
        dateFormatter = new SimpleDateFormat("HH:mm", Locale.US);
        calendar.setTimeInMillis(bookingDetails.getStartDate()*1000);
        meetingStartTime.setText(dateFormatter.format(calendar.getTime()));
        calendar.setTimeInMillis(bookingDetails.getEndDate()*1000);
        meetingEndTime.setText(dateFormatter.format(calendar.getTime()));
        meetingDescription.setText(bookingDetails.getDescription());
    }
}
