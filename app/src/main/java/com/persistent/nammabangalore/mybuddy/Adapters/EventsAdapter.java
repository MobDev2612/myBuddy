package com.persistent.nammabangalore.mybuddy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.persistent.nammabangalore.mybuddy.DataModels.EventDetails;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.Font;
import com.persistent.nammabangalore.mybuddy.Utils.FontView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class EventsAdapter extends ArrayAdapter<EventDetails> {
    private Context mContext;
    private int mResource;
    List<EventDetails> dataList;
    boolean isHome;

    public EventsAdapter(Context context, int resource, List<EventDetails> objects,boolean isHome) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
        dataList = objects;
        this.isHome = isHome;
    }

    public class ViewHolder {
        public FontView mName, mDescription,mDateTime;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(mResource, null);
            viewHolder = new ViewHolder();
            viewHolder.mName = (FontView) convertView.findViewById(R.id.event_title);
            viewHolder.mDescription = (FontView) convertView.findViewById(R.id.event_description);
            viewHolder.mDateTime = (FontView) convertView.findViewById(R.id.event_date_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mName.setText(dataList.get(position).getTitle());
        viewHolder.mDescription.setText(dataList.get(position).getDescription());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dataList.get(position).getStartTime()*1000);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        viewHolder.mDateTime.setText(dataList.get(position).getLocation()+" "+dateFormatter.format(calendar.getTime()));
        return convertView;
    }

    @Override
    public int getCount() {
        if(isHome){
            return 2;
        }
        return dataList.size();
    }

    @Override
    public EventDetails getItem(int position) {
        return dataList.get(position);
    }
}
