package com.davidroach.assignment3paint;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.WHITE;


public class PaintActivity extends AppCompatActivity {


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


    //Handles imaged returned from openGalleryImage
    //requestCode =1 for success
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1  && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            

            File myFile = new File(selectedImage.getPath());
            String absPath = myFile.getPath();
            Bitmap bitmap;

    try {
        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(absPath));
        customView.appCanvas.drawBitmap(bitmap, 0, 0, customView.canvasPaint);
        customView.invalidate();
    }
    catch (Exception e){
        Log.i("ERROR",e.toString());
    }



int l=1;

            //customView.invalidate();




        }
    }





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
        /*
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        tartActivityForResult(Intent.createChooser(galleryIntent, ""),1);

        */
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
       //galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(Intent.createChooser(galleryIntent, ""),1);
    }

         public void quit(){

        }


    }




