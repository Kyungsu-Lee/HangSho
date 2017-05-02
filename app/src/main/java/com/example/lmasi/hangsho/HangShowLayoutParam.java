package com.example.lmasi.hangsho;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class HangShowLayoutParam extends RelativeLayout.LayoutParams {


    public HangShowLayoutParam(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public HangShowLayoutParam(ViewGroup.LayoutParams source) {
        super(source);
    }

    public HangShowLayoutParam(RelativeLayout.LayoutParams source) {
        super(source);
    }

    public HangShowLayoutParam(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    public HangShowLayoutParam(int w, int h) {
        super(w, h);
    }

    public HangShowLayoutParam(double w, double h) {
        super((int)w, (int)h);
    }

    public HangShowLayoutParam setMargin(double left, double top, double right, double bottom)
    {
        super.setMargins((int)left, (int)top, (int)right, (int)bottom);
        return this;
    }


    public HangShowLayoutParam addRules(int verb) {
        super.addRule(verb);

        return this;
    }


    public HangShowLayoutParam addRules(int verb, int subject) {
        super.addRule(verb, subject);

        return this;
    }
}
