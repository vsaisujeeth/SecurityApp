package com.example.android.securityapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    FirebaseDatabase db;
    EditText text;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
     List<Admin_List_Item> admin_listItems = new ArrayList<Admin_List_Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {



         db=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =db.getReference();
        databaseReference.child("college").child("iit patna").child("vehicles").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Toast.makeText(getApplicationContext(),"fsdakjg",
                //        Toast.LENGTH_SHORT).show();
                /*for(int i=0;i<=10;i++){
                    Admin_List_Item admin_list_item=new Admin_List_Item("Vehicle"+ (i+1),"yoyoyoyoyoyoyo");
                    admin_listItems.add(admin_list_item);
                }*/
                // specify an adapter (see also next example)
                //  ArrayAdapter<item> arrayAdapter=new ArrayAdapter<>()

                for(DataSnapshot dataSnap:dataSnapshot.getChildren()){

                    Admin_List_Item item = dataSnap.getValue(Admin_List_Item.class);

                    admin_listItems.add(item);
                }
                mAdapter = new MyAdapter(admin_listItems,getApplicationContext());
                recyclerView.setAdapter(mAdapter);
//                Iterable<DataSnapshot> children =dataSnapshot.getChildren();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"fsdakjg",
                        Toast.LENGTH_SHORT).show();
            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        recyclerView = (RecyclerView) findViewById(R.id.vehicle_log);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



  /*      for(int i=0;i<=10;i++){
            Admin_List_Item admin_list_item=new Admin_List_Item("Vehicle"+ (i+1),"yoyoyoyoyoyoyo");
            admin_listItems.add(admin_list_item);
        }
        // specify an adapter (see also next example)
      //  ArrayAdapter<item> arrayAdapter=new ArrayAdapter<>()
        mAdapter = new MyAdapter(admin_listItems,this);
        recyclerView.setAdapter(mAdapter);
    */
    }
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/
    /*public void Add(View view) {

    }*/

}
