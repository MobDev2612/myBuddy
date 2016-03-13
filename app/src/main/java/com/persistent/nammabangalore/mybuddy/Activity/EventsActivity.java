package com.persistent.nammabangalore.mybuddy.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Adapters.EventsAdapter;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;

public class EventsActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray> {
    ListView listView;
    List<EventDetails> eventsList;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.events_list);
        if(!Constants.eventsList.isEmpty()){
            eventsList = Constants.eventsList;
            setData();
        } else {
            getEvents();
        }
    }

    private void setData() {
        EventsAdapter eventsAdapter = new EventsAdapter(this,R.layout.events_list_item,eventsList,false);
        listView.setAdapter(eventsAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        Toast.makeText(this, "Error in fetching Events", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        customProgressDialog.dismissProgress();
        Gson gson = new Gson();
        EventDetails[] eventDetailses= gson.fromJson(jsonArray.toString(), EventDetails[].class);
        List<EventDetails> list = Arrays.asList(eventDetailses);
        Constants.eventsList = list;
        eventsList = list;
        setData();
    }
}
