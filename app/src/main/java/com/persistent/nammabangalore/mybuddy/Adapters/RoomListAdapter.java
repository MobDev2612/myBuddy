package com.persistent.nammabangalore.mybuddy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.persistent.nammabangalore.mybuddy.DataModels.BookingDetails;
import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.FontView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class RoomListAdapter extends ArrayAdapter<BookingDetails> {
    private Context mContext;
    private int mResource;
    List<BookingDetails> dataList;

    public RoomListAdapter(Context context, int resource, List<BookingDetails> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        dataList = objects;
    }

    public class ViewHolder {
        public FontView mName, mBookedBy, mStartTime,mEndTime;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(mResource, null);
            viewHolder = new ViewHolder();
            viewHolder.mName = (FontView) convertView.findViewById(R.id.meeting_name);
            viewHolder.mBookedBy = (FontView) convertView.findViewById(R.id.meeting_booked);
            viewHolder.mStartTime = (FontView) convertView.findViewById(R.id.meeting_start_time);
            viewHolder.mEndTime = (FontView) convertView.findViewById(R.id.meeting_end_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mName.setText(dataList.get(position).getTitle());
        viewHolder.mBookedBy.setText(dataList.get(position).getEmployeeName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataList.get(position).getStartDate()*1000);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(dataList.get(position).getEndDate()*1000);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        viewHolder.mStartTime.setText(dateFormatter.format(calendar.getTime()) + " " + dateFormatter.format(calendar2.getTime()));
        return convertView;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public BookingDetails getItem(int position) {
        return dataList.get(position);
    }
}
