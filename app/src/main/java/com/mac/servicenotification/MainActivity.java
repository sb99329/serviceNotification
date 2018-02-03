package com.mac.servicenotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.R.attr.description;
import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION_CODES.N;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;


    @Override
    protected void onResume() {
        super.onResume();

        Log.d("main","onResume!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);


        Log.d("main","onCreate!");



        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Service Start
                startService(new Intent(getApplicationContext(),NotificationService.class));

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("main","onDestory!");
    }
}
