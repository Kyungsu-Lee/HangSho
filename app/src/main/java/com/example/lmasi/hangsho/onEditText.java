package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class onEditText extends EditText {

    private RelativeLayout.LayoutParams params;
    protected Drawable background;
    private int WIDTH;
    private int HEIGHT;

    private int LX;
    private int LY;

    protected Object who;

    static public double screenparam_x;
    static public double screenparam_y;

    static public int screen_x;
    static public int screen_y;

    static public int narrawSize;
    static public int wideSize;



    public onEditText(Context context)
    {

        super(context);

        who = this;

        screen_x = (int)ScreenParameter.getScreen_x();
        screen_y = (int)ScreenParameter.getScreen_y();

    }

    public void setLX(double x){this.LX = (int)x;}
    public void setLY(double y){this.LY = (int)y;}

    public int getLX(){return this.LX;}
    public int getLY(){return this.LY;}



    public int setWIDTH(int width)
    {
        this.WIDTH = width;
        return width;
    }

    public int SETHEIGHT(int height)
    {
        this.HEIGHT = height;
        return height;
    }

    public int getWIDTH() {return this.WIDTH;}
    public int getHEIGHT() {return this.HEIGHT;}

    private void setsize(double width, double height)
    {
        setWidth((int)width);
        setHeight((int)height);
    }

    public void setSize(double width, double height)
    {
        params = new RelativeLayout.LayoutParams((int)width, (int)height);
        this.setLayoutParams(params);

        setsize(width, height);
    }

    public void setLocation(double x, double y)
    {
        if(params != null)
            params.setMargins((int)x,(int)y,-1000,-1000);

        this.LX = (int)x;
        this.LY = (int)y;

    }


    public void setBackground(int id)
    {
        background = getResources().getDrawable(id);
        super.setBackground(background);
    }

}
