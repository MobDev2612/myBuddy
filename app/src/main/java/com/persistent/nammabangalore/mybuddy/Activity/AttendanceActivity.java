package com.persistent.nammabangalore.mybuddy.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Adapters.EventsAdapter;
import com.persistent.nammabangalore.mybuddy.Adapters.TableData;
import com.persistent.nammabangalore.mybuddy.DataModels.AttendenceObj;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowColorizers;

public class AttendanceActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONArray> {
    TableView tableView;
    private static final String[][] DATA_TO_SHOW = { { "2/1/12", "9:00AM", "IN" },
            { "2/1/12", "9:03AM", "OUT" } };
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialiseComponents();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAttendance();
    }

    private void initialiseComponents() {
        tableView = (TableView) findViewById(R.id.tableView);
        tableView.setColumnCount(4);
        String[] strings = new String[]{"Date", "Time", "IN/OUT"};
        SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(this, strings);
        tableView.setHeaderAdapter(simpleTableHeaderAdapter);
//        int colorEvenRows = getResources().getColor(R.color.white);
//        int colorOddRows = getResources().getColor(R.color.grey);
//        tableView.setDataRowColoriser(TableDataRowColorizers.alternatingRows(colorEvenRows, colorOddRows));
//        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
//        tableView.setDataAdapter(new SimpleTableDataAdapter(this, DATA_TO_SHOW));
//        tableView.setColumnComparator(0, new Comparable<String>() {
//            @Override
//            public int compareTo(String another) {
//                return 0;
//            }
//        });
    }

    private void getAttendance(){
        customProgressDialog = CustomProgressDialog.getInstance();
        customProgressDialog.showProgress("Please wait", this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://10.66.41.45:8030/api/Attendence",this,this);
        MyBuddy.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        customProgressDialog.dismissProgress();
        Toast.makeText(this, "Error in fetching Attendance data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray jsonArray) {
        customProgressDialog.dismissProgress();
        Gson gson = new Gson();
        AttendenceObj[] attendenceObjs= gson.fromJson(jsonArray.toString(), AttendenceObj[].class);
        List<AttendenceObj> list = new ArrayList<>();

        for(AttendenceObj attendenceObj : attendenceObjs){
            if(attendenceObj.getEmployeeId() == 15449)
            list.add(attendenceObj);
        }
        TableData tableData = new TableData(this,list);
        tableView.setDataAdapter(tableData);
    }

}
