package com.davidroach.assignment3paint;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import java.lang.reflect.Method;




public class PaintActivity extends AppCompatActivity {

     Dialog color_dialog; //color picker dialog.


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
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(Color.parseColor("#DCDCDC"));
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        customView = (DrawArea) findViewById(R.id.draw_area_view);

        //color picker dialog.
        color_dialog = new Dialog(this);
        color_dialog.setContentView(R.layout.color_picker);

    } //end oncreate


    //Handles imaged returned from openGalleryImage
    //requestCode =1 for success
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            try {
                Uri returnedUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), returnedUri);
                customView.appCanvas.drawBitmap(bitmap, 0, 0, customView.canvasPaint);
                customView.invalidate();
                Toast.makeText(getApplicationContext(), "Image opened successfully.", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Error opening image.",Toast.LENGTH_LONG).show();
                Log.i("ERROR",e.toString());
            }
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
            case R.id.action_color:
                showColorPicker();
                return true;


            default:
                //handle unknown.
                //this should never be called.
                return super.onOptionsItemSelected(item);

        }

    }


    public void showColorPicker(){
        color_dialog.setTitle("Title...");
        color_dialog.show();
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


    //called when one of the color pick buttons are clicked.
    public void colorClick(View viewIn){
        int  newColor = Color.parseColor(viewIn.getTag().toString());
        customView.setCurrentColor(newColor);

        //close color dialog
        color_dialog.dismiss();

        Log.i("Color Tag:", Integer.toString(newColor));
        Log.i("ColorClick","Active");
    }


    }




