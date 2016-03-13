package com.persistent.nammabangalore.mybuddy.Service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.persistent.nammabangalore.mybuddy.Activity.BookRoomActivity;
import com.persistent.nammabangalore.mybuddy.Activity.BookRoomFormActivity;
import com.persistent.nammabangalore.mybuddy.Activity.HomeActivity;
import com.persistent.nammabangalore.mybuddy.DataModels.AttendenceObj;
import com.persistent.nammabangalore.mybuddy.DataModels.BookingDetails;
import com.persistent.nammabangalore.mybuddy.Networking.VolleyRequest;
import com.persistent.nammabangalore.mybuddy.R;
import com.persistent.nammabangalore.mybuddy.Utils.MyBuddy;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Collection;


public class BeaconScannerService extends Service implements BeaconConsumer, Response.ErrorListener, Response.Listener<AttendenceObj> {
    private BeaconManager beaconManager;
    Region region;
        //    List<String> beaconList1;
    enum STATE {IN, OUT, UNDETERMINED}
    Beacon  inBeacon,outBeacon, meetBeacon,meetBeacon2;
    boolean inBeaconFlag, outBeaconFlag;

    @Override
    public void onCreate() {
        inBeaconFlag = true;
        outBeaconFlag = false;
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        region = new Region("com.example.myapp.boostrapRegion", null, null, null);
        beaconManager.bind(this);
//        beaconList1 = new ArrayList<>();
        outBeacon = new Beacon.Builder()
                .setId1("f7826da6-4fa2-4e98-8024-bc5b71e0893e")
                .setId2("1")
                .setId3("10")
                .build();
        inBeacon = new Beacon.Builder()
                .setId1("f7826da6-4fa2-4e98-8024-bc5b71e0893f")
                .setId2("1")
                .setId3("4")
                .build();
        meetBeacon = new Beacon.Builder()
                .setId1("f7826da6-4fa2-4e98-8024-bc5b71e0893a")
                .setId2("1")
                .setId3("4")
                .build();
        meetBeacon2 = new Beacon.Builder()
                .setId1("f7826da6-4fa2-4e98-8024-bc5b71e0893a")
                .setId2("1")
                .setId3("1")
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                try {
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                try {
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int i, Region region) {

            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(final Collection<Beacon> beacons, Region region) {
                for (Beacon oneBeacon : beacons) {
                    if (oneBeacon.getDistance() <= 0.5) {
                        saveBeacon(oneBeacon);
//                        STATE notif = saveBeacon(oneBeacon);
//                        if( notif== STATE.IN || notif == STATE.OUT){
//                            showNotification(notif);
//                        }
                    }
                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void saveBeacon(Beacon beacon) {
        // ckeck which beacon
        // if inbeacon -> post Type=I        // if outBeacon -> post Type=O
        // Toggle flag


        String id = beacon.getId1().toString();
        if(id.equals(outBeacon.getId1().toString())){

            if(outBeaconFlag) {
                sendRequest(false);
                inBeaconFlag = !inBeaconFlag;
                outBeaconFlag = !outBeaconFlag;

            }
            showNotification(false);
        }

        if(id.equals(inBeacon.getId1().toString())){


            if(inBeaconFlag) {
                // Post request with Type=I
                sendRequest(true);
                inBeaconFlag = !inBeaconFlag;
                outBeaconFlag = !outBeaconFlag;
            }
            showNotification(true);
        }

        if(id.equals(meetBeacon.getId1().toString())){
            if(beacon.getId3().toString().equals("1")){
                notifyUser(false);
            }
            if(beacon.getId3().toString().equals("4")){
                notifyUser(true);
            }

        }


//        else if(id.equals(inBeacon.getId1().toString()) && outBeaconFlag) {
//            inBeaconFlag = true;
//            outBeaconFlag = false;
//            showNotification(false);
//        }
//        if (!beaconList1.contains(id)) {
//            beaconList1.add(id);
//            processBeacon(beacon);
//        }
//        if (beaconList1.size() >= 2) {
//            return direction();
//        } else {
//            return STATE.UNDETERMINED;
//        }
    }

//    private STATE direction() {
//        if(inBeaconFlag && outBeaconFlag) {
//            if (beaconList1.get(0).equals(outBeacon.getId1().toString()) && beaconList1.get(1).equals(inBeacon.getId1().toString())) {
//                return STATE.IN;
//            } else if (beaconList1.get(0).equals(inBeacon.getId1().toString()) && beaconList1.get(1).equals(outBeacon.getId1().toString())) {
//                return STATE.OUT;
//            } else {
//                return STATE.UNDETERMINED;
//            }
//        } else {
//            return STATE.UNDETERMINED;
//        }
//    }
//
    private void showNotification(boolean value) {
//        beaconList1.clear();
        String message=null;
//        inBeaconFlag = false;
//        outBeaconFlag = false;
        if (!value) {
//            inBeaconFlag = true;
//            outBeaconFlag = false;
            message = "Thank You!! Have a Nice Day !!!";
        } else if(value ){
//            inBeaconFlag = false;
//            outBeaconFlag = true;
            message = "Welcome to Persistent";
        }
        if(message!=null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("myBuddy")
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }

    }
//
//    private void processBeacon(Beacon beacon){
//        if(outBeacon.getId1().equals(beacon.getId1())){
////            inBeaconFlag = true;
//            notifyUser(false);
//        }
//        if (inBeacon.getId1().equals(beacon.getId1())){
////            outBeaconFlag = true;
//            notifyUser(false);
//        }
//        if (meetBeacon.getId1().equals(beacon.getId1())) {
//            notifyUser(true);
//        }
//
//    }
//
    private void notifyUser(boolean isMeeting){
        String message;
        Intent intent;
        if(!isMeeting){
            message = "This meeting room is not available";
            intent = new Intent(this, BookRoomActivity.class);
        } else {
            message = "This meeting room is available. Do you want to book it?";
            intent = new Intent(this, BookRoomFormActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("myBuddy")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

    private void sendRequest(boolean isIn){
        Calendar calendar = Calendar.getInstance();
        String type;
        if(isIn){
            type ="I";
        } else {
            type ="O";
        }
        AttendenceObj attendenceObj = new AttendenceObj(calendar.getTimeInMillis()/1000,calendar.getTimeInMillis()/1000,type,15449);
        Gson gson = new Gson();
        String jsonObject = gson.toJson(attendenceObj,AttendenceObj.class);
        try {
            JSONObject jsonObject1 = new JSONObject(jsonObject);

        VolleyRequest<AttendenceObj> volleyRequest = new VolleyRequest<AttendenceObj>(Request.Method.POST,"http://10.66.41.45:8030/api/Attendence",
                AttendenceObj.class,null,this,this,jsonObject1);
            MyBuddy.getInstance().addToRequestQueue(volleyRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    @Override
    public void onResponse(AttendenceObj attendenceObj) {

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +100, restartServicePI);
    }

    @Override
    public void onDestroy() {
        beaconManager.unbind(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
