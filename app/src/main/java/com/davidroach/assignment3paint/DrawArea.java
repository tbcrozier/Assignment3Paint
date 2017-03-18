package com.davidroach.assignment3paint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by droach-dev on 3/8/17.
 */

public class DrawArea extends View {

    //Brush atrributes set in this class for now.
    //May move to main  class if the need arises.



    private int currentColor;
    private int eraseColor;
    private Paint paintBrush;
    private Canvas appCanvas;
    private Paint canvasPaint;
    private Path linePath;
    private Bitmap appBitmap;

    //will be passed to onTouchEvent
    boolean eraseButtonPressed;



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

    //Getters and setters for savedInstanceState Data

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

        eraseButtonPressed = false;
        eraseColor = Color.WHITE;
        setCurrentColor(Color.BLACK);
        appCanvas = new Canvas();
        linePath = new Path();

        //Set paint brush initial settings
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStrokeWidth(10);
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

        //if erase flag is true change area touched to canvas background color Color.WHITE
        if(eraseButtonPressed == true){
            Log.i("ERASE_BUTTON: ",  "ACTIVE");

            paintBrush.setColor(eraseColor);
        }
        else{
            Log.i("ERASE_BUTTON: ",  "NOT ACTIVE");
            paintBrush.setColor(currentColor);
        }

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

        //redraw Canvas
        invalidate();
        return true;
    }

     public void setEraseButtonPressed(boolean flagIn){
         eraseButtonPressed = flagIn;
     }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        appBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        appCanvas = new Canvas(appBitmap);
    }

}
