package com.example.android.securityapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class log extends AppCompatActivity {
    FirebaseDatabase db;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    public List<Log_List_Item> log_list_items = new ArrayList<Log_List_Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_log);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        recyclerView = (RecyclerView) findViewById(R.id.logvec);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        db=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =db.getReference();
        databaseReference.child("college").child("iit patna").child("log").orderByChild("date").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnap:dataSnapshot.getChildren()){
               //     Toast.makeText(getApplicationContext(),"entered",Toast.LENGTH_LONG).show();
                    Log_List_Item item = dataSnap.getValue(Log_List_Item.class);
                    log_list_items.add(item);
                }

                Collections.reverse(log_list_items);
                mAdapter = new LogAdapter(log_list_items,getApplicationContext());
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"fsdakjg",
                        Toast.LENGTH_SHORT).show();
            }
        });




}
}
