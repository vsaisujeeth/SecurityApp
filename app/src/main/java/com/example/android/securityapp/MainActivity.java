package com.example.android.securityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
