package com.davidroach.assignment3paint;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;




public class PaintActivity extends AppCompatActivity {

    private ImageButton pencilButton;
    private ImageButton brushButton;
    private ImageButton bucketButton;
    private ImageButton eraseButton;
    private ImageButton openButton;
    private ImageButton saveButton;

    private DrawArea customView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);


        pencilButton = (ImageButton) findViewById(R.id.pencilButton);
        brushButton = (ImageButton) findViewById(R.id.brushButton);
        bucketButton = (ImageButton) findViewById(R.id.bucketButton);
        eraseButton = (ImageButton) findViewById(R.id.eraseButton);
        openButton = (ImageButton) findViewById(R.id.openButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);

        customView = (DrawArea) findViewById(R.id.draw_area_view);

        //set button transparency
        pencilButton.getBackground().setAlpha(100);
        brushButton.getBackground().setAlpha(100);
        bucketButton.getBackground().setAlpha(100);
        eraseButton.getBackground().setAlpha(100);
        openButton.getBackground().setAlpha(100);
        saveButton.getBackground().setAlpha(100);


        //setup image button onClickListeners
        pencilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setEraseButtonPressed(false);
                customView.changeBrushSize(10);
            }
        });

        //Thicken stroke for brush size.
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setEraseButtonPressed(false);
                customView.changeBrushSize(30);
            }
        });

        bucketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setEraseButtonPressed(false);
                customView.changeBackgroundColor();

            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.setEraseButtonPressed(true);
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToGallery();
            }
        });

    } //end oncreate


    
    public void saveToGallery() {
        try {
            customView.setDrawingCacheEnabled(true);
            Bitmap b = customView.getDrawingCache();
            MediaStore.Images.Media.insertImage(this.getContentResolver(), b, "Drawing", "Random Drawing");
            Toast.makeText(this, "Image Saved To Gallery.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("EXECPTION", e.toString());
        }
    }


        public void openFromGallery(){

      }

    }




