package com.davidroach.assignment3paint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import java.io.File;
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
        customView = (DrawArea) findViewById(R.id.draw_area_view);

    } //end oncreate

/*
    //Handles imaged returned from openGalleryImage
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {

            if (requestCode == 1) {
                Uri uriReturned = data.getData();
                String imagePath= getRealPathFromURI(currImageURI);

                File file = new File(imagePath);

                if (file.exists()) {
                    Drawable d = Drawable.createFromPath(file.getAbsolutePath());
                    //drawView.setBackground(d);
                }
                else
                {
                    // file does not exist
                }

            }
        }
    }
    */




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
                openGalleryImage();
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

    public void  openGalleryImage()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, ""),1);
    }

         public void quit(){

        }


    }




