package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2016-09-16.
 */
public class HistoryRoot extends RelativeLayout {

    private TouchImageView icon;
    private TextView placeName;

    private RelativeLayout.LayoutParams layoutParams;

    public HistoryRoot(Context context, boolean gender, String place_name) {
        super(context);

        icon = new TouchImageView(context);
        placeName = new TextView(context);

        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 30, 0);
        this.setLayoutParams(layoutParams);

        icon.setSize(26 * ScreenParameter.getScreenparam_x(), 26 * ScreenParameter.getScreenparam_x());
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)icon.getLayoutParams();
        param.addRule(CENTER_HORIZONTAL);
        icon.setLayoutParams(param);
        icon.setBackground(gender ? R.drawable.home_bluemap : R.drawable.home_pinkmap);
        this.addView(icon);

        RelativeLayout.LayoutParams textParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParam.addRule(CENTER_HORIZONTAL);
        textParam.setMargins(0, (int)(31 * ScreenParameter.getScreenparam_x()), 0, 0);
        placeName.setLayoutParams(textParam);
        placeName.setText(place_name);
        placeName.setTextColor(Color.argb(77, 34, 30, 31));
        placeName.setTypeface(FontResource.AppleSDGothicNeoB00);
        placeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        this.addView(placeName);

    }

    public HistoryRoot setLocation(double x, double y)
    {
        this.layoutParams.setMargins((int)x, (int)y, 0, 0);
        this.setLayoutParams(this.layoutParams);

        return this;
    }

    public RelativeLayout.LayoutParams getparams()
    {
        return this.layoutParams;
    }

    public void SetParams(RelativeLayout.LayoutParams layoutParams)
    {
        this.layoutParams = layoutParams;
    }


}
