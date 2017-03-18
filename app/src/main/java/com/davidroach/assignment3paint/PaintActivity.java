package com.davidroach.assignment3paint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.PersistableBundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        //rotation code starts here
        if (savedInstanceState == null) {  // no rotation - total new start
            //Place data that will be saved through rotation
            //DrawArea customView = savedInstanceState.();
            int currentColor = 0;
            int eraseColor = 0;
            Bitmap appBitmap = null;
            //Canvas appCanvas = savedInstanceState.getClassLoader();

        } else { // possibly a rotation - may have data
            //Place data that will be saved through rotation
            //DrawArea customView = savedInstanceState.();
            int currentColor = savedInstanceState.getInt("color");
            int eraseColor = savedInstanceState.getInt("eraseColor");
            Bitmap appBitmap;


        }
        //rotation code ends here

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

            }
        });

    }

    //rotation code continued
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);



        }


}
