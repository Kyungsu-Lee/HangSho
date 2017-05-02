package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.sax.RootElement;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2016-09-18.
 */
public abstract class LikeIcon extends RelativeLayout {


    ImageView icon;
    ImageView close;
    TextView shopName;

    String type;

    public LikeIcon(Context context, String type, String name) {
        super(context);

        this.type = type;

        icon = new ImageView(getContext());
        icon.setBackground(type.equals("eye") ? getResources().getDrawable(R.drawable.list_shop) : getResources().getDrawable(R.drawable.list_shop2));
        icon.setLayoutParams(new HangShowLayoutParam(84 * ScreenParameter.getScreenparam_x(), 84 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL));
        icon.setId(icon.hashCode());
        this.addView(icon);

        close = new ImageView(getContext());
        close.setBackground(getResources().getDrawable(R.drawable.list_delete));
        close.setLayoutParams(new HangShowLayoutParam(34 * ScreenParameter.getScreenparam_x(), 34 * ScreenParameter.getScreenparam_y()).addRules(ALIGN_BOTTOM, icon.getId()).addRules(ALIGN_LEFT,icon.getId()).setMargin(55 * ScreenParameter.getScreenparam_x(), 0,0, -10 * ScreenParameter.getScreenparam_y()));
        close.setId(close.hashCode());
        this.addView(close);
        close.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    clickCloseButton(LikeIcon.this);
                }

                return true;
            }
        });

        shopName = new TextView(getContext());
        shopName.setText(name);
        shopName.setTextColor(Color.argb(255, 60, 60, 59));
        shopName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        shopName.setTypeface(FontResource.AppleSDGothicNeoB00);
        shopName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, close.getId()).addRules(CENTER_HORIZONTAL).setMargin(0, 10 * ScreenParameter.getScreenparam_y(), 0, 0));
        this.addView(shopName);
    }

    public void addRule(int verb)
    {
        if(this.getLayoutParams() instanceof RelativeLayout.LayoutParams)
        {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
            params.addRule(verb);
        }
    }

    public void addRule(int verb, int subject)
    {
        if(this.getLayoutParams() instanceof RelativeLayout.LayoutParams)
        {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
            params.addRule(verb, subject);
        }
    }

    public void removeRule(int verb)
    {
        if(this.getLayoutParams() instanceof RelativeLayout.LayoutParams)
        {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();
            params.removeRule(verb);
        }
    }


    public abstract void clickCloseButton(View view);
}
