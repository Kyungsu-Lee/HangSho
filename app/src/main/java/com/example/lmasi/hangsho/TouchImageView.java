package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class TouchImageView extends ImageView {

    private RelativeLayout.LayoutParams params;
    protected Drawable background;
    private int WIDTH;
    private int HEIGHT;

    private int LX;
    private int LY;

    protected TouchImageView who;

    static public double screenparam_x;
    static public double screenparam_y;

    static public int screen_x;
    static public int screen_y;

    static public int narrawSize;
    static public int wideSize;


    public TouchImageView(Context context) {

        super(context);

        who = this;

        screen_x = (int) ScreenParameter.getScreen_x();
        screen_y = (int) ScreenParameter.getScreen_y();

    }

    public void setLX(double x) {
        this.LX = (int) x;
    }

    public void setLY(double y) {
        this.LY = (int) y;
    }

    public int getLX() {
        return this.LX;
    }

    public int getLY() {
        return this.LY;
    }


    public int setWidth(int width) {
        this.WIDTH = width;
        return width;
    }

    public int setHeight(int height) {
        this.HEIGHT = height;
        return height;
    }

    public int getWIDTH() {
        return this.WIDTH;
    }

    public int getHEIGHT() {
        return this.HEIGHT;
    }

    private void setsize(double width, double height) {
        setWidth((int) width);
        setHeight((int) height);
    }

    public void setSize(double width, double height) {
        params = new RelativeLayout.LayoutParams((int) width, (int) height);
        this.setLayoutParams(params);

        setsize(width, height);
    }

    public void setLocation(double x, double y) {
        if (params != null)
            params.setMargins((int) x, (int) y, -1000, -1000);

        this.LX = (int) x;
        this.LY = (int) y;

    }


    public void setBackground(int id) {

        try {
            background = (BitmapDrawable) getResources().getDrawable(id);
            super.setBackground(background);
        }
        catch (Exception e)
        {
            super.setBackground(getResources().getDrawable(id));
            Log.e("BITMAPEXCEPTION", e.toString());
        }
        finally {
           if(!(getResources().getDrawable(id) instanceof BitmapDrawable))
             Log.e("CLASS", "class :  "+ getResources().getDrawable(id).getClass() );
        }

    }

    public Bitmap getbackground()
    {
         return ((BitmapDrawable)background).getBitmap();
    }

    public void recycle()
    {
        if(background instanceof BitmapDrawable)
        {
            Bitmap bitmap = ((BitmapDrawable)background).getBitmap();
            bitmap.recycle();
        }

        background.setCallback(null);
    }

    public void addRule(int verb)
    {
        this.params.addRule(verb);
        this.setLayoutParams(params);
    }

    public void addRule(int verb, int subject)
    {
        this.params.addRule(verb, subject);
        this.setLayoutParams(params);
    }

}
