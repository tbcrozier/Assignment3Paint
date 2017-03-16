package com.davidroach.assignment3paint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;


public class PaintActivity extends AppCompatActivity {

    /*
    private ImageButton pencilButton;
    private ImageButton brushButton;
    private ImageButton bucketButton;
    private ImageButton eraseButton;
    private ImageButton openButton;
    private ImageButton saveButton;
    */

    private DrawArea customView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Set overflow menubar icons to visible.
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

/*
        pencilButton = (ImageButton) findViewById(R.id.pencilButton);
        brushButton = (ImageButton) findViewById(R.id.brushButton);
        bucketButton = (ImageButton) findViewById(R.id.bucketButton);
        eraseButton = (ImageButton) findViewById(R.id.eraseButton);
        openButton = (ImageButton) findViewById(R.id.openButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);

  */

        customView = (DrawArea) findViewById(R.id.draw_area_view);
/*
        //set button transparency
        pencilButton.getBackground().setAlpha(100);
        brushButton.getBackground().setAlpha(100);
        bucketButton.getBackground().setAlpha(100);
        eraseButton.getBackground().setAlpha(100);
        openButton.getBackground().setAlpha(100);
        saveButton.getBackground().setAlpha(100);
*/
        /*Options Moved to Menubar  MAY BE REMOVED*/

/*
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

        */

    } //end oncreate






    /* Moved to BrushOPtionActivit.java   MAY BE REMOVED */
    public void saveToGallery() {
        try {
            customView.setDrawingCacheEnabled(true);
            Bitmap b = customView.getDrawingCache();
            MediaStore.Images.Media.insertImage(this.getContentResolver(), b, "Drawing", "Random Drawing");
            Toast.makeText(this, "Image Saved To Gallery.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.i("EXCEPTION", e.toString());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_pencil:
                customView.setEraseButtonPressed(false);
                customView.changeBrushSize(10);
                return true;

            case R.id.action_brush:
                customView.setEraseButtonPressed(false);
                customView.changeBrushSize(30);
                return true;

            case R.id.action_bucket:
                customView.setEraseButtonPressed(false);
                customView.changeBackgroundColor();
                return true;

            case R.id.action_eraser:
                customView.setEraseButtonPressed(true);
                return true;

            case R.id.action_save:
                saveToGallery();
                return true;

            case R.id.action_open:

                return true;

            case R.id.action_quit:
                quit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void quit(){

    }


    }




