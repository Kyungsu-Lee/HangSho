package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2016-10-02.
 */
public class ResultShop extends RelativeLayout {

    private HangShowLayoutParam params;

    private ImageView icon;
    private TextView shopName;
    private TextView shopName_English;
    private String type;

    public ResultShop(Context context, String shopName, String  shopNameEnglish, DbResource.GENDER gender, String type) {
        super(context);

        params = new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);

        this.type = type;

        int image;

        if(gender == DbResource.GENDER.MAN)
        {
            image = R.drawable.result_boyeye + (type.equals("eye") ? 0 : 1);
        }

        else
        {
            image = R.drawable.result_girleye + (type.equals("eye") ? 0 : 1);
        }

        icon = new ImageView(getContext());
        icon.setLayoutParams(new HangShowLayoutParam(86 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y()));
        icon.setBackground(getResources().getDrawable(image));
        addView(icon);
        icon.setId(icon.hashCode());

        this.shopName = new TextView(getContext());
        this.shopName.setTextColor(Color.argb(255, 255, 255, 255));
        this.shopName.setTypeface(FontResource.AppleSDGothicNeoB00);
        this.shopName.setText(shopName);
        this.shopName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        this.shopName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(BELOW, icon.getId()).addRules(ALIGN_LEFT, icon.getId()).setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        addView(this.shopName);
        this.shopName.setId(this.shopName.hashCode());

        this.shopName_English = new TextView(getContext());
        this.shopName_English.setTextColor(Color.argb(255, 255, 255, 255));
        this.shopName_English.setTypeface(FontResource.AppleSDGothicNeoB00);
        this.shopName_English.setText(shopNameEnglish);
        this.shopName_English.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(12 * ScreenParameter.getScreenparam_y()));
        this.shopName_English.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(BELOW, this.shopName.getId()).addRules(ALIGN_LEFT, icon.getId()).setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        addView(this.shopName_English);
        this.shopName_English.setId(this.shopName_English.hashCode());

    }


    public void addRules(int verb)
    {
        params.addRules(verb);
    }

    public void addRules(int verbs, int subject)
    {
        params.addRules(verbs, subject);
    }

    public void setMargin(double left, double top, double right, double bottom)
    {
        params.setMargin(left, top, right, bottom);
        this.setLayoutParams(params);
    }
}
