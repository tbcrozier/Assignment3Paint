package com.davidroach.assignment3paint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by droach-dev on 3/8/17.
 */

public class DrawArea extends View {

    //Brush atrributes set in this class for now.
    //May move to main  class if the need arises.

    private int currentColor;
    private Paint paint;



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
        canvas.drawPaint(paint);
    }



    //sets color variable to be used in brushes and fonts.
    private void setCurrentColor(int colorIn){
        currentColor = colorIn;

    }


    //
    public void setup(AttributeSet attrs){
        currentColor = Color.BLACK;
        paint = new Paint();

    }

    //fill background with color
    public void changeBackgroundColor(int colorIn){
        currentColor = colorIn;
        paint.setColor(currentColor);
        Log.i("CHANGED", "Filled Canvas with color.");
        invalidate();

    }



}
