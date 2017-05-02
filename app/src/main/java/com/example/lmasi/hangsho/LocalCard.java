package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;


public class LocalCard extends TextView {

    boolean isClicked = false;

    public LocalCard(Context context, String text, int index) {
        super(context);

        this.setBackgroundColor(Color.argb(255, 234, 234, 234));
        this.setLayoutParams(new HangShowLayoutParam(288 * ScreenParameter.getScreenparam_x(), 99 * ScreenParameter.getScreenparam_y()).setMargin(0, 99 * index * ScreenParameter.getScreenparam_y(), 0, 0));
        this.setGravity(Gravity.CENTER);
        this.setText(text);
        this.setTypeface(FontResource.AppleSDGothicNeoB00);
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        this.setTextColor(Color.argb(255, 65, 64, 66));
    }

    public void Click()
    {
        this.setBackgroundColor(Color.WHITE);
        this.setTextColor(Color.argb(255, 128,202,196));
        isClicked = true;
    }

    public void unClick()
    {
        this.setBackgroundColor(Color.argb(255, 234, 234, 234));
        this.setTextColor(Color.argb(255, 65, 64, 66));
        isClicked = false;
    }


}
