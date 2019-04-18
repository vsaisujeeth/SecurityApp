package com.example.android.securityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.pusher.pushnotifications.PushNotifications;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushNotifications.start(getApplicationContext(), "34215f76-aae8-4584-8643-a1a72e605faa");
        PushNotifications.addDeviceInterest("hello");

    }


    public void signInButtons(View view) {
        if(view.getId() == R.id.button_admin){

            Intent admin_intent= new Intent(this,Admin.class);
            startActivity(admin_intent);
        }
        else if(view.getId() == R.id.button_res){

        }
        else if(view.getId() == R.id.button_sec){

            Intent intent = new Intent(this,Security.class);
            startActivity(intent);

        }
    }
}
