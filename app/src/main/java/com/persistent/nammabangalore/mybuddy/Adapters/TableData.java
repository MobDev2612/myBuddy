package com.persistent.nammabangalore.mybuddy.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.persistent.nammabangalore.mybuddy.DataModels.AttendenceObj;
import com.persistent.nammabangalore.mybuddy.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class TableData extends TableDataAdapter<AttendenceObj> {
    Context mContext;
    List<AttendenceObj> dataList;

    public TableData(Context context, List<AttendenceObj> data) {
        super(context, data);
        mContext = context;
        dataList = data;
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        AttendenceObj car = getRowData(rowIndex);
        View renderedView = null;

        switch (columnIndex) {
            case 0: {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(car.getDate()*1000);
                TextView textView = new TextView(mContext);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                textView.setText(simpleDateFormat.format(calendar.getTime()));
                textView.setPadding(5,5,5,5);
                renderedView = textView;
            }
                break;
            case 1:
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(car.getInOutTime()*1000);
                TextView textView = new TextView(mContext);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
                textView.setText(simpleDateFormat.format(calendar.getTime()));
                textView.setPadding(5, 5, 5, 5);
                renderedView = textView;
            }
                break;
            case 2:
            {
                TextView textView = new TextView(mContext);
                String type;
                if (car.getType().equals("I")){
                    type= "IN";
                } else {
                    type= "OUT";
                }
                textView.setText(type);
                textView.setPadding(5,5,5,5);
                renderedView = textView;
            }
                break;

        }

        return renderedView;
    }
}
