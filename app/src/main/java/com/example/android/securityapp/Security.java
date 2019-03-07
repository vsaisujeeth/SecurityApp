package com.example.android.securityapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class Security extends AppCompatActivity {

    ImageView imageView;
    FirebaseDatabase db;
    Bitmap bitmap;
    EditText text;
    TextView status;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.security_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.log)
        {
            Intent intent=new Intent(getApplicationContext(),log.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.settings)
        {

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        db = FirebaseDatabase.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        text = (EditText) findViewById(R.id.editText_regno);
        imageView = (ImageView) findViewById(R.id.imageView2);
        status = (TextView) findViewById(R.id.text_status);


    }

    public void Shoot(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

        effects(bitmap);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Couldn't Detect Text", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            if (items.size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error! No text detected. please scan again", Toast.LENGTH_SHORT);
                toast.show();
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock x = items.valueAt(i);
                stringBuilder.append(x.getValue());

            }

            String x = stringBuilder.toString();
            x = enchance(x);
            text.setText(x);
            CheckText(x);
        }
    }


    private void CheckText(final String s) {


        Query query = db.getReference("college").child("iit patna").child("vehicles").orderByChild("head").equalTo(s);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // status.setText("OK");
                    status.setTextColor(Color.WHITE);
                    status.setTextSize(32);
                    status.setBackgroundColor(Color.GREEN);

                    addToLog(s);

                } else {
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

    private void addToLog(final String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("H:mm a");
        String Time = sdf.format(new Date());
        sdf = new SimpleDateFormat("yyyy/MM/dd,H:mm a");
        String Date = sdf.format(new Date());
        final Log_List_Item item=new Log_List_Item(Date,Time,"dummy",s);

        db = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = db.getReference();
        databaseReference.child("college").child("iit patna").child("log").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    databaseReference.child("college").child("iit patna").child("log").child(s).setValue(item);
                    Toast.makeText(getApplicationContext(),"Added Successfully to log ",Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext()," Failed to Add to log !!",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void submit(View view) {
        status.setText("waiting");
        status.setBackgroundColor(Color.WHITE);
        String s = text.getText().toString();
        s = enchance(s);
        CheckText(s);
        hideSoftKeyBoard();
    }

    public String enchance(String s) {

        s = s.toUpperCase();
        s = s.replace(" ", "").trim();
        s = s.replace("\n", "");
        Approximate approximate = new Approximate();
        s = approximate.mainfn(s);
        text.setText(s);
        return s;
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public Bitmap blackandwhite(Bitmap bitmap) {
        Bitmap bwBitmap = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565 );
        float[] hsv = new float[ 3 ];
        for( int col = 0; col < bitmap.getWidth(); col++ ) {
            for( int row = 0; row < bitmap.getHeight(); row++ ) {
                Color.colorToHSV( bitmap.getPixel( col, row ), hsv );
                if( hsv[ 2 ] > 0.2f ) {
                    bwBitmap.setPixel( col, row, 0xffffffff );
                } else {
                    bwBitmap.setPixel( col, row, 0xff000000 );
                }
            }
        }
        return bwBitmap;
    }

    public Bitmap effects(Bitmap bitmap) {

        //bitmap=changeBitmapContrastBrightness(bitmap,1,0);
        bitmap=blackandwhite(bitmap);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Couldn't Detect Text", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);

            if (items.size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error! No text detected. please scan again", Toast.LENGTH_SHORT);
                toast.show();
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock x = items.valueAt(i);
                stringBuilder.append(x.getValue());

            }

            String x = stringBuilder.toString();
            x = enchance(x);
            status.setText(x);
            CheckText(x);

        }
        ImageView imageView = (ImageView) findViewById(R.id.imageAdj);
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }
}