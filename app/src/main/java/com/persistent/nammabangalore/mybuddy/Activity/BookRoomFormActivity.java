package com.persistent.nammabangalore.mybuddy.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.DataModels.AttendenceObj;
import com.persistent.nammabangalore.mybuddy.DataModels.BookingDetails;
import com.persistent.nammabangalore.mybuddy.Networking.VolleyRequest;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.Font;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BookRoomFormActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<BookingDetails> {

    EditText meetingName, meetingDate, meetingStartTime, meetingEndTime, meetingDescription;
    TextInputLayout meetingNameLayout, meetingDateLayout, meetingStartTimeLayout, meetingEndTimeLayout, meetingDescriptionLayout;
    Button bookRoomButton;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private TimePickerDialog mStartTimePicker, mEndTimePicker;
    long date, start, end;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room_form);
        initialiseComponents();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initialiseComponents() {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        meetingName = (EditText) findViewById(R.id.meeting_form_name);
        meetingDate = (EditText) findViewById(R.id.meeting_form_date);
        meetingStartTime = (EditText) findViewById(R.id.meeting_form_start_time);
        meetingEndTime = (EditText) findViewById(R.id.meeting_form_end_time);
        meetingDescription = (EditText) findViewById(R.id.meeting_form_description);

//        meetingNameLayout = (TextInputLayout) findViewById(R.id.meeting_form_name_)
        bookRoomButton = (Button) findViewById(R.id.meeting_form_book);
        bookRoomButton.setOnClickListener(this);
        meetingDate.setInputType(InputType.TYPE_NULL);
        meetingStartTime.setInputType(InputType.TYPE_NULL);
        meetingEndTime.setInputType(InputType.TYPE_NULL);

        meetingName.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        meetingDate.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        meetingStartTime.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        meetingEndTime.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        meetingDescription.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        bookRoomButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));

        setDateTimeField();
        setStartTimeField();
        setEndTimeField();
    }

    private void setDateTimeField() {
        meetingDate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date = newDate.getTimeInMillis();
                meetingDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setStartTimeField() {
        meetingStartTime.setOnClickListener(this);
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        mStartTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, hour, minute, selectedHour, selectedMinute);
                meetingStartTime.setText(selectedHour + ":" + selectedMinute);
                start = calendar.getTimeInMillis();
            }
        }, hour, minute, true);
        mStartTimePicker.setTitle("Select Time");
//        mStartTimePicker.show();
    }

    private void setEndTimeField() {
        meetingEndTime.setOnClickListener(this);
        Calendar mcurrentTime = Calendar.getInstance();
        final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mcurrentTime.get(Calendar.MINUTE);
        mEndTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                meetingEndTime.setText(selectedHour + ":" + selectedMinute);
                Calendar calendar = Calendar.getInstance();
                calendar.set(2016, hour, minute, selectedHour, selectedMinute);
                end = calendar.getTimeInMillis();
            }
        }, hour, minute, true);
        mEndTimePicker.setTitle("Select Time");
//        mStartTimePicker.show();
    }

    @Override
    public void onClick(View v) {
        if (v == meetingDate) {
            fromDatePickerDialog.show();
        } else if (v == meetingStartTime) {
            mStartTimePicker.show();
        } else if (v == meetingEndTime) {
            mEndTimePicker.show();
        } else if (v == bookRoomButton) {
            sendRequest();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendRequest() {
        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setEmployeeId(15449);
        bookingDetails.setEmployeeName("Hari Haran Swamy");
        bookingDetails.setRoomNo(2);
        bookingDetails.setRoomName("Shishir");
        if (!meetingName.getText().toString().isEmpty())
            bookingDetails.setTitle(meetingName.getText().toString());
        if (!meetingDescription.getText().toString().isEmpty())
            bookingDetails.setDescription(meetingDescription.getText().toString());
        if (!meetingDate.getText().toString().isEmpty() && !meetingStartTime.getText().toString().isEmpty() && !meetingEndTime.getText().toString().isEmpty()) {
            bookingDetails.setBookedDate(date / 1000);
            bookingDetails.setStartDate(start / 1000);
            bookingDetails.setEndDate(end / 1000);
        }
        customProgressDialog = CustomProgressDialog.getInstance();
        customProgressDialog.showProgress("Please wait", this);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(bookingDetails, BookingDetails.class);
        try {
            JSONObject jsonObject1 = new JSONObject(jsonObject);

            VolleyRequest<BookingDetails> volleyRequest = new VolleyRequest<BookingDetails>(Request.Method.POST, "http://10.66.41.45:8030/api/ConferenceRoom",
                    BookingDetails.class, null, this, this, jsonObject1);
            MyBuddy.getInstance().addToRequestQueue(volleyRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        customProgressDialog.dismissProgress();
        Toast.makeText(this, "Error in creating meeting", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(BookingDetails bookingDetails) {
        customProgressDialog.dismissProgress();
        onBackPressed();
    }
}
