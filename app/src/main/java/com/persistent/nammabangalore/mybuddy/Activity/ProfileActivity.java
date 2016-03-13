package com.persistent.nammabangalore.mybuddy.Activity;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Adapters.EventsAdapter;
import com.persistent.nammabangalore.mybuddy.DataModels.Employee;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.Networking.VolleyRequest;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;
import com.persistent.nammabangalore.mybuddy.Utils.CustomProgressDialog;
import com.persistent.nammabangalore.mybuddy.Utils.FontView;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements Response.Listener<Employee>, Response.ErrorListener {

    FontView profileName,profileEmployeeId,employeeDesignation,profileBU,profileEmail,profilePhone;
    CircleImageView imageView;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initialiseComponents();
        getEmployeeDetails();
    }

    private void initialiseComponents(){
        profileName = (FontView) findViewById(R.id.profile_name);
        profileEmployeeId = (FontView) findViewById(R.id.profile_employee_id);
        employeeDesignation = (FontView) findViewById(R.id.profile_designation);
        profileBU = (FontView) findViewById(R.id.profile_bu);
        profileEmail = (FontView) findViewById(R.id.profile_email);
        profilePhone = (FontView) findViewById(R.id.profile_phone);
        imageView = (CircleImageView) findViewById(R.id.profile_image);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile, menu);
//        return true;
//    }

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

    public void getEmployeeDetails(){
        customProgressDialog = CustomProgressDialog.getInstance();
        customProgressDialog.showProgress("Please wait", this);
        VolleyRequest<Employee> jsonArrayRequest = new VolleyRequest<Employee>(Request.Method.GET,"http://10.66.41.45:8030/api/Employee/15449",
                Employee.class,null,this,this);
        MyBuddy.getInstance().addToRequestQueue(jsonArrayRequest);
    }


    @Override
    public void onErrorResponse(VolleyError volleyError) {
        customProgressDialog.dismissProgress();
        Toast.makeText(this, "Error in fetching Profile Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Employee employee) {
        customProgressDialog.dismissProgress();
        setData(employee);
    }

    private void setData(Employee employee){
        profileName.setText(employee.getEmployeeName());
        profileEmployeeId.setText(employee.getEmployeeId()+"");
        employeeDesignation.setText(employee.getDesignation());
        profileBU.setText(employee.getBusinessUnit());
        profileEmail.setText(employee.getEmailId());
        profilePhone.setText(employee.getMobileNo());
    }

}
