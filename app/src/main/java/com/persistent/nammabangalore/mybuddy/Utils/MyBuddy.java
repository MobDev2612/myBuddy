package com.persistent.nammabangalore.mybuddy.Utils;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.persistent.nammabangalore.mybuddy.Activity.HomeActivity;
import com.persistent.nammabangalore.mybuddy.Networking.LruBitmapCache;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Service.BeaconScannerService;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by swamy_hariharan on 3/12/2016.
 */
public class MyBuddy extends Application {
    public static final String TAG = MyBuddy.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyBuddy mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Constants.eventsList = new ArrayList<>();
        Intent intent = new Intent(this, BeaconScannerService.class);
        startService(intent);
    }

    public static synchronized MyBuddy getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
