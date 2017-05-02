package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class ShopImageView extends GridLayout {

    RelativeLayout.LayoutParams layoutParams;

    public ShopImageView(Context context, int index) {
        super(context);

        this.setBackgroundColor(Color.argb(255, 255, 255, 255));

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(500 * ScreenParameter.getScreenparam_y()));
        setLayoutParams(layoutParams);
        this.setBackground(getResources().getDrawable(R.drawable.list_01 + index));
    }

    public void addRule(int verb)
    {
        layoutParams.addRule(verb);
    }

    public void addRule(int verb, int subject)
    {
        layoutParams.addRule(verb, subject);
    }

    public void removeRule(int verb)
    {
        layoutParams.removeRule(verb);
    }

}
