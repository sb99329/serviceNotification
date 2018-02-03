package com.mac.servicenotification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;



public class NotificationService extends Service {





    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //timer 3sec
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                setNotification();

            }
        };

        timer.schedule(timerTask,3000);



        return START_STICKY;
    }

    public void setNotification(){

        NotificationManager mNotificationManager;
        int notificationId = 1;

        Log.d("main","Notification Service");

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(getApplicationContext(),
                        0,resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        String CHANNEL_ID = "my_channel_01";// The id of the channel.


        Intent buttonIntent = new Intent(getApplicationContext(),ButtonReceiver.class);
        buttonIntent.putExtra("button","cancel");
        buttonIntent.putExtra("notificationID",notificationId);
        buttonIntent.putExtra("channelID",CHANNEL_ID);

        PendingIntent buttonPedingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,buttonIntent,PendingIntent.FLAG_CANCEL_CURRENT);




        Notification mBuilder = null;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int notifyID = 1;

            CharSequence name = CHANNEL_ID;// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            mBuilder =
                    new Notification.Builder(getApplicationContext())
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("NOTIFICATION")
                            .addAction(0,"Button1",resultPendingIntent)
                            .addAction(0,"Button2",buttonPedingIntent)
                            .setAutoCancel(true)
                            .setContentTitle("My notification")
                            .setChannelId(CHANNEL_ID)
                            .setContentText("Hello World!").build();

            mNotificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.createNotificationChannel(mChannel);
        }else{

            mBuilder =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setTicker("NOTIFICATION")
                            .addAction(0,"Button1",resultPendingIntent)
                            .addAction(0,"Button2",buttonPedingIntent)
                            .setAutoCancel(true)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!").build();

            mNotificationManager =
                    (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        }

        mNotificationManager.notify(notificationId, mBuilder);

        stopSelf();

    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("main","Service Destory");
    }
}
