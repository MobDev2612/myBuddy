package com.persistent.nammabangalore.mybuddy.Activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Adapters.EventsAdapter;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.Networking.VolleyRequest;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.Font;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, Response.ErrorListener, Response.Listener<JSONArray> {

    Button profileButton, attendanceButton, bookRoomButton, eventButton, viewMoreButton;
    ListView homeEvent;
    CustomProgressDialog customProgressDialog;
    List<EventDetails> list;
//    private BeaconManager beaconManager;
//    Region region;
//    List<String> beaconList1;
//    enum STATE {IN, OUT, UNDETERMINED}
//    Beacon beacon1, beacon2;
//    boolean inBoolean,outBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialiseComponents();
        setListeners();
        if(Constants.eventsList.isEmpty()) {
            getEvents();
        }
    }

    private void initialiseComponents() {
        profileButton = (Button) findViewById(R.id.profile_button);
        attendanceButton = (Button) findViewById(R.id.attendence_button);
        bookRoomButton = (Button) findViewById(R.id.book_room_button);
        eventButton = (Button) findViewById(R.id.events_button);
        viewMoreButton = (Button) findViewById(R.id.view_more_events);
        homeEvent = (ListView) findViewById(R.id.home_events);

        profileButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        attendanceButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        bookRoomButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        eventButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
        viewMoreButton.setTypeface(Font.getTypeface(this, Font.FONTAWESOME));
    }

    private void setListeners() {
        profileButton.setOnClickListener(this);
        attendanceButton.setOnClickListener(this);
        bookRoomButton.setOnClickListener(this);
        eventButton.setOnClickListener(this);
        viewMoreButton.setOnClickListener(this);
        homeEvent.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Activity activity = null;
        if (v == profileButton) {
            activity = new ProfileActivity();
        } else if (v == attendanceButton) {
            activity = new AttendanceActivity();
        } else if (v == bookRoomButton) {
            activity = new BookRoomActivity();
        } else if (v == eventButton) {
            activity = new EventsActivity();
        } else if (v == viewMoreButton) {
            activity = new EventsActivity();
        }
        if (activity != null) {
            Intent intent = new Intent(this, activity.getClass());
            startActivity(intent);
//            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void getEvents(){
        customProgressDialog = CustomProgressDialog.getInstance();
        customProgressDialog.showProgress("Please wait", this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://10.66.41.45:8030/api/Event",this,this);
        MyBuddy.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        customProgressDialog.dismissProgress();
        Toast.makeText(this,"Error in fetching Events",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        customProgressDialog.dismissProgress();
        Gson gson = new Gson();
        EventDetails[] eventDetailses= gson.fromJson(jsonArray.toString(), EventDetails[].class);
        list = Arrays.asList(eventDetailses);
        Constants.eventsList = list;
        setData();
    }

    private void setData(){
        EventsAdapter eventsAdapter = new EventsAdapter(this,R.layout.events_list_item,list,true);
        homeEvent.setAdapter(eventsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Constants.eventsList.isEmpty()){
            list = Constants.eventsList;
            setData();
        }
    }
}
