package com.davidroach.assignment3paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class PaintActivity extends AppCompatActivity {

    private ImageButton pencilButton;
    private ImageButton brushButton;
    private ImageButton bucketButton;
    private ImageButton eraseButton;
    private ImageButton openButton;
    private ImageButton saveButton;

    private DrawArea customView;

    //will be passed to onTouchEvent
    boolean eraseButtonPressed;



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

        eraseButtonPressed = false;

        //setup image button onClickListeners
        pencilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.changeBrushSize(10);
            }
        });

        //Thicken stroke for brush size.
        brushButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.changeBrushSize(30);
            }
        });

        bucketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customView.changeBackgroundColor();

            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });



    }



}
