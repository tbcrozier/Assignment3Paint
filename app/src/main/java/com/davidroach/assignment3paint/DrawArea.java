package com.davidroach.assignment3paint;

/*
    Assignment3 Paint;
    CSCI 4020
    Thomas Crozier
    David Roach
    March 27th, 2017
 */


import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

//imports for brush sizes
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;


/**
 * Created by droach-dev on 3/8/17.
 */

public class DrawArea extends View {

    //Brush atrributes set in this class for now.
    //May move to main  class if the need arises.


    protected int currentColor;
    private int eraseColor;

    //used to determine if a shape should be drawn or a free line.
    public boolean squareFlag;
    public boolean triangleFlag;
    public boolean ovalFlag;
    public boolean lineFlag;
    boolean isSecondShapeTouch;


    //needed for onsizeChanged to get canvas dimensions.
    private int width;
    private int height;

    public float dpiPixels;

    private Paint paintBrush;
    public Canvas appCanvas;
    protected Paint canvasPaint;
    private Path linePath;
    protected Bitmap appBitmap;

    //will be passed to onTouchEvent
    boolean eraseButtonPressed;

    //var to hold brush sizes
    protected float brushSize;
    private float   lastBrushSize;

    float squareTopCornerX;
    float squareTopCornerY;

    float squareBottomCornerX;
    float squareBottomCornerY;





    //Constructors
    public DrawArea(Context context){
        super(context);
        setup(null);
    }


    public DrawArea(Context context, AttributeSet attrs){
        super(context, attrs);
        setup(attrs);
    }

    public DrawArea(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        setup(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(appBitmap, 0, 0, canvasPaint);
        canvas.drawPath(linePath, paintBrush);
    }





    //sets color variable to be used in brushes and fonts.
    public void setCurrentColor(int colorIn){
        currentColor = colorIn;
        //paintBrush.setColor(colorIn);

    }


    //
    public void setup(AttributeSet attrs){

        //for on touch event
        lineFlag = false;
        ovalFlag = false;
        squareFlag = false;
        triangleFlag = false;

        //Brush Size instantiation and setStrokeWidth to Brushsize
        brushSize = getResources().getInteger(R.integer.medium_size);
        lastBrushSize = brushSize;


        eraseButtonPressed = false;
        eraseColor = Color.WHITE;
        setCurrentColor(Color.BLACK);
        appCanvas = new Canvas();
        linePath = new Path();

        //Set paint brush initial settings
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStrokeWidth(brushSize);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeCap(Paint.Cap.ROUND);
        paintBrush.setStyle(Paint.Style.STROKE);
        paintBrush.setColor(currentColor);


        canvasPaint = new Paint(Paint.DITHER_FLAG);
        setBackgroundColor(Color.WHITE);

    }


    //change brush size
    public void changeBrushSize(int brushSizeIn){
        paintBrush.setStrokeWidth(brushSizeIn);
    }

    //fill background with color
    public void changeBackgroundColor(){
        appCanvas.drawColor(currentColor);
        Log.i("CHANGED", "Filled Canvas with color.");
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xCord = event.getX();
        float yCord = event.getY();

        //if any of the the shape flags are true do shape stuff
        if(squareFlag){
            Log.i("SHAPE","In shape section of code.");

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    squareTopCornerX = xCord;
                    squareTopCornerY = yCord;
                    Log.i("SHAPE CORD",Float.toString(squareTopCornerX) + " - " + Float.toString(squareTopCornerY));

                    break;
                case MotionEvent.ACTION_UP:
                    squareBottomCornerX = xCord;
                    squareBottomCornerY = yCord;
                    Log.i("SHAPE CORD",Float.toString(squareBottomCornerX) + " - " + Float.toString(squareBottomCornerY));

                    makeSquare();
                    resetShapeFlags();

                    linePath.reset();
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                default:
                    return false;
            }

        }
        else {

            //if erase flag is true change area touched to canvas background color Color.WHITE
            if (eraseButtonPressed) {
                //Log.i("ERASE_BUTTON: ", "ACTIVE");

                paintBrush.setColor(eraseColor);
            } else {
                //Log.i("ERASE_BUTTON: ", "NOT ACTIVE");
                paintBrush.setColor(currentColor);
            }

            //IF THERE ARE NO SHAPE FLAGS CHECKED
            //for free line draws.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    linePath.moveTo(xCord, yCord);
                    break;
                case MotionEvent.ACTION_UP:
                    appCanvas.drawPath(linePath, paintBrush);
                    linePath.reset();
                    break;
                case MotionEvent.ACTION_MOVE:
                    linePath.lineTo(xCord, yCord);
                    break;
                default:
                    return false;
            }
        }

        //redraw Canvas
        invalidate();
        return true;
    }

    public void setEraseButtonPressed(boolean flagIn){
         eraseButtonPressed = flagIn;
     }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;

        //called after size is calculated.  Need that width and height to make it work.
        appBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        appCanvas = new Canvas(appBitmap);
    }

    private void getDpi(float dpiSizeIn) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        dpiPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpiSizeIn, dm);
    }

    protected void resetShapeFlags(){
        lineFlag = false;
        ovalFlag = false;
        squareFlag = false;
        triangleFlag = false;
    }

    public void setBrushSize(float newSize){
        //New Size
        float pixelAmount;
        pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                newSize, getResources().getDisplayMetrics());
        brushSize=pixelAmount;
        paintBrush.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastBrush){
        lastBrushSize = lastBrush;
    }

    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void blanksheet(){
        //Blank sheet code here
        appCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }


    private void makeSquare(){
        Log.i("makeSquare()", "Running");

            appCanvas.drawRect(squareTopCornerX, squareTopCornerY, squareBottomCornerX, squareBottomCornerY, paintBrush);
            //reset cord variables for good measure
            squareBottomCornerX = 0;
            squareBottomCornerY = 0;
            squareTopCornerX = 0;
            squareTopCornerY = 0;



    }

}
