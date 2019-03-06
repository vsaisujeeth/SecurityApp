package com.example.android.securityapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

public class Security extends AppCompatActivity {

    ImageView imageView;
    FirebaseDatabase db;
    Bitmap bitmap;
    EditText text;
    TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        db=FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        text = (EditText)findViewById(R.id.editText_regno);
        imageView = (ImageView)findViewById(R.id.imageView2);
        status = (TextView)findViewById(R.id.text_status);




    }

    public void Shoot(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if(!textRecognizer.isOperational())
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Couldn't Detect Text",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            if(items.size()==0)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Error! No text detected. please scan again",Toast.LENGTH_SHORT);
                toast.show();
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock x = items.valueAt(i);
                stringBuilder.append(x.getValue());

            }

            String x= stringBuilder.toString();
            x= enchance(x);
            text.setText(x);
            CheckText(x);
        }
    }


    private void CheckText(final String s)
    {


        Query query =db.getReference("college").child("iit patna").child("vehicles").orderByChild("head").equalTo(s);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                   // status.setText("OK");
                    status.setTextColor(Color.WHITE);
                    status.setTextSize(32);
                    status.setBackgroundColor(Color.GREEN);

                }
                else {
                    //status.setText("unauthorized");
                    status.setTextColor(Color.WHITE);
                    status.setTextSize(32);
                    status.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void submit(View view) {
        status.setText("waiting");
        status.setBackgroundColor(Color.WHITE);
        String s= text.getText().toString();
        s = enchance(s);
        CheckText(s);
        hideSoftKeyBoard();
    }

    public String enchance(String s)
    {
        status.setText(s);
        s = s.toUpperCase();
        s=s.replace(" ","").trim();
        s=s.replace("\n","");
        Approximate approximate= new Approximate();
        s=approximate.mainfn(s);
        text.setText(s);
        return s;
    }


}
