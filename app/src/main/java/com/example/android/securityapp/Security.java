package com.example.android.securityapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.w3c.dom.Text;

public class Security extends AppCompatActivity {


    ImageView imageView;
    Bitmap bitmap;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        Button camera = (Button)findViewById(R.id.but_clickPhoto);
        text = (EditText)findViewById(R.id.editText_regno);
        imageView = (ImageView)findViewById(R.id.imageView2);


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

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock x = items.valueAt(i);
                stringBuilder.append(x.getValue());
                stringBuilder.append("\n");
            }

            text.setText(stringBuilder.toString());
        }
    }
}
