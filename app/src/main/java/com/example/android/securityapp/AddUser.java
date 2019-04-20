package com.example.android.securityapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUser extends AppCompatActivity {
    FirebaseDatabase db;
    EditText ed_vehicleno;
    EditText ed_type;
    EditText ed_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
         ed_vehicleno = (EditText) findViewById(R.id.ed_vehicleno);
         ed_type = (EditText) findViewById(R.id.ed_type);
        ed_mobile=(EditText) findViewById(R.id.ed_mobile);
    }

        public void addButton (View view){
            final String vehicleno, type,mobile;
            vehicleno = ed_vehicleno.getText().toString().toUpperCase();
            type = ed_type.getText().toString();
            mobile=ed_mobile.getText().toString();
            db = FirebaseDatabase.getInstance();

            final DatabaseReference databaseReference = db.getReference();
            databaseReference.child("college").child("iit patna").child("vehicles").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(vehicleno.isEmpty()||type.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Empty Field",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Admin_List_Item admin_list_item = new Admin_List_Item(vehicleno, type,mobile);
                        databaseReference.child("college").child("iit patna").child("vehicles").child(admin_list_item.head).setValue(admin_list_item);
                        Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),Admin.class);
                        startActivity(intent);
                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext()," Failed to Add !!",Toast.LENGTH_LONG).show();
                }
            });
        }

}
