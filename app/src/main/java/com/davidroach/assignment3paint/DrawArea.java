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
    private Paint paintBrush;
    private Canvas appCanvas;
    private Paint canvasPaint;
    private Path linePath;
    private Bitmap appBitmap;



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
    private void setCurrentColor(int colorIn){
        currentColor = colorIn;
        //paintBrush.setColor(colorIn);

    }


    //
    public void setup(AttributeSet attrs){
        setCurrentColor(Color.BLACK);

        //
        appCanvas = new Canvas();

        linePath = new Path();

        //Set paint brush initial settings
        paintBrush = new Paint();
        paintBrush.setAntiAlias(true);
        paintBrush.setStrokeWidth(50);
        paintBrush.setStrokeJoin(Paint.Join.ROUND);
        paintBrush.setStrokeCap(Paint.Cap.ROUND);
        paintBrush.setStyle(Paint.Style.STROKE);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }



    //fill background with color
    public void changeBackgroundColor(int colorIn){
        currentColor = colorIn;
        paintBrush.setColor(currentColor);
        Log.i("CHANGED", "Filled Canvas with color.");
        invalidate();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xCord = event.getX();
        float yCord = event.getY();


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


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        appBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        appCanvas = new Canvas(appBitmap);

    }

}
