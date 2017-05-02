package com.example.lmasi.hangsho;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class onTextView extends TextView {

    RelativeLayout.LayoutParams params;
    private int HEIGHT;
    private int WIDTH;

    onTextView(Context context)
    {
        super(context);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    public int setWIDTH(int width) {
        this.WIDTH = width;
        return width;
    }

    public int setHEIGHT(int height) {
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
        setWIDTH((int) width);
        setHEIGHT((int) height);
    }

    public void setSize(double width, double height) {
        params = new RelativeLayout.LayoutParams((int) width, (int) height);
        this.setLayoutParams(params);

        setsize(width, height);
    }


    public void setLocation(double x, double y)
    {
        params.setMargins((int)x,(int)y,0,0);
    }

}
