package com.davidroach.assignment3paint;

/*
    Assignment3 Paint;
    CSCI 4020
    Thomas Crozier
    David Roach
    March 27th, 2017
 */


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;
import java.lang.reflect.Method;
import android.graphics.PorterDuff;


public class PaintActivity extends AppCompatActivity implements View.OnClickListener{

    /* List of Features
    * - Erase
    *   Class:      DrawArea.java
    *   Variables:  private int eraseColor; /  private int currentColor;  /   eraseColor = Color.WHITE;
    *                boolean eraseButtonPressed;
    *   Methods:    public void setEraseButtonPressed(boolean flagIn)
    *
    * - Fill
    *   Class:      DrawArea.java
    *   Variables:  private int currentColor;
    *   Methods:    public void changeBackgroundColor()  /  public void setCurrentColor(int colorIn)
    *
    * - New Sheet
    *   Class:      DrawArea.java
    *   Variables:  0 , 0, PorterDuff.Mode.CLEAR
    *   Methods:    public void blanksheet()  /  invalidate();
    *
    * - Open File
    *   Class:
    *   Variables:
    *   Methods:    openGalleryImage();
    *
    * - Save File
    *   Class:      PaintActivity.java
    *   Variables:  Bitmap b
    *   Methods:    saveToGallery()
    *
    * */

    Dialog color_dialog; //color picker dialog.

    private DrawArea customView;

    //Brush Picker Size Variables
    private float smallBrush, mediumBrush, largeBrush;

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

        //Brush Size Value Instantiation
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        customView.setBrushSize(smallBrush);
        //End Brush Size Value Instantiation

        //color picker dialog.
        color_dialog = new Dialog(this);
        color_dialog.setContentView(R.layout.color_picker);

    } //end oncreate


    @Override
    public void onBackPressed(){
        SaveDialogFragment saveDialog = new SaveDialogFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        saveDialog.show(ft,"Save");




    }

    public static class SaveDialogFragment extends DialogFragment {
        

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You are exiting.  Are you sure you want to quit without saving first?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i("Quit", "Without Saving");

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);


                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.i("Not quitting", "You chose no.");

                        }

                    });
            // Create the AlertDialog object and return it
            return builder.create();

        }

    }


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
                customView.resetShapeFlags();
                customView.setEraseButtonPressed(false);
                customView.changeBrushSize(10);
                return true;

            case R.id.action_brush:
                customView.resetShapeFlags();
                customView.setEraseButtonPressed(false);
                //customView.changeBrushSize(30);
                final Dialog brushDialog = new Dialog(this);
                brushDialog.setTitle("Brush size:");
                brushDialog.setContentView(R.layout.brush_picker);

                ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
                smallBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        customView.setBrushSize(smallBrush);
                        customView.setLastBrushSize(smallBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
                mediumBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        customView.setBrushSize(mediumBrush);
                        customView.setLastBrushSize(mediumBrush);
                        brushDialog.dismiss();
                    }
                });

                ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
                largeBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        customView.setBrushSize(largeBrush);
                        customView.setLastBrushSize(largeBrush);
                        brushDialog.dismiss();
                    }
                });

                brushDialog.show();
                return true;

            case R.id.action_bucket:
                customView.resetShapeFlags();
                customView.setEraseButtonPressed(false);
                customView.changeBackgroundColor();
                return true;

            case R.id.action_eraser:
                customView.resetShapeFlags();
                customView.setEraseButtonPressed(true);
                return true;

            case R.id.action_save:
                customView.resetShapeFlags();
                saveToGallery();
                return true;

            case R.id.action_open:
                customView.resetShapeFlags();
                openGalleryImage();
                return true;

            case R.id.action_quit:
                quit();
                return true;

            case R.id.action_about:
                about();
                return true;

            case R.id.action_new:
                customView.blanksheet();
                return true;

            case R.id.action_color:
                customView.resetShapeFlags();
                showColorPicker();
                return true;


            case R.id.action_square:
                customView.resetShapeFlags();
                customView.squareFlag = true;
                return true;


            default:
                //handle unknown.
                //this should never be called.
                return super.onOptionsItemSelected(item);

        }

    }


    public void showColorPicker(){
        color_dialog.setTitle("");
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
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void about(){
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
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

//Draw_btn Onclick that will give brush size options
    @Override
    public void onClick(View view) {

    }
}




