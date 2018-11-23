package com.example.neighbor07.mobe;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import static com.estimote.sdk.EstimoteSDK.getApplicationContext;

public class BeaconManage extends Activity {
    public BeaconManager beaconManager;

    public BeaconManage(final Context applicationContext){
        beaconManager = new BeaconManager(applicationContext);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), 40001,16975));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("비콘 연결되었습니다.","비콘번호:"+UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"));
                getApplicationContext().startActivity(new Intent(getApplicationContext(),
                        MainActivity.class).putExtra("비콘이름" ,
                        String.valueOf(list.get(0).getProximityUUID())).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification("비콘 연결 해제되었습니다.","비콘번호:"+UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this,MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this,0, new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }
}
