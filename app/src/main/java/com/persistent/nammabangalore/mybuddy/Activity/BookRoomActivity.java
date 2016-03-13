package com.persistent.nammabangalore.mybuddy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Adapters.EventsAdapter;
import com.persistent.nammabangalore.mybuddy.Adapters.RoomListAdapter;
import com.persistent.nammabangalore.mybuddy.DataModels.BookingDetails;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class BookRoomActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, Response.ErrorListener, Response.Listener<JSONArray> {
    FloatingActionButton fab;
    Spinner spinner;
    ListView recyclerView;
    List<BookingDetails> list;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialiseComponents();
//        getBookingDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBookingDetails();
    }

    private void initialiseComponents() {
        fab = (FloatingActionButton) findViewById(R.id.add_meeting);
        spinner = (Spinner) findViewById(R.id.book_room_meeting_room);
        recyclerView = (ListView) findViewById(R.id.room_bookings_list);
        recyclerView.setOnItemClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, BookRoomFormActivity.class);
            startActivity(intent);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,BookingDetailsActivity.class);
        intent.putExtra("data",list.get(position));
        startActivity(intent);
    }

    public void getBookingDetails(){
        customProgressDialog = CustomProgressDialog.getInstance();
        customProgressDialog.showProgress("Please wait", this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://10.66.41.45:8030/api/ConferenceRoom",this,this);
        MyBuddy.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        customProgressDialog.dismissProgress();
        Toast.makeText(this, "Error in fetching Conference Bookings", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        customProgressDialog.dismissProgress();
        Gson gson = new Gson();
        BookingDetails[] eventDetailses= gson.fromJson(jsonArray.toString(), BookingDetails[].class);
        list = Arrays.asList(eventDetailses);
//        Constants.eventsList = list;
        RoomListAdapter eventsAdapter = new RoomListAdapter(this,R.layout.meeting_list_item,list);
        recyclerView.setAdapter(eventsAdapter);
    }

}
