package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lmasi on 2016-09-25.
 */
public class SpeechBubble extends RelativeLayout {

    TextView textView;

    public SpeechBubble(Context context) {
        super(context);

        setBackground(getResources().getDrawable(R.drawable.timer_ballon));

        textView = new TextView(getContext());
        textView.setTextColor(Color.argb(255, 102, 102, 102));
        textView.setTypeface(FontResource.AppleSDGothicNeoB00);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(22 * ScreenParameter.getScreenparam_y()));
        textView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(CENTER_HORIZONTAL).setMargin(0, 23 * ScreenParameter.getScreenparam_y(), 0, 0 * ScreenParameter.getScreenparam_y()));
        this.addView(textView);


    }

    public void setWidth()
    {
        textView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams)getLayoutParams();
        param.width = textView.getMeasuredWidth() + (int)(40 * ScreenParameter.getScreenparam_x());
        setLayoutParams(param);
    }

    public void setText(String text)
    {
        textView.setText(text);
    }
}
