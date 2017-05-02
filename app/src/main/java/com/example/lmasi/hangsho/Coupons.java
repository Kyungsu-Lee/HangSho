package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

/**
 * Created by lmasi on 2016-09-18.
 */
public class Coupons extends RelativeLayout {

    HangShowLayoutParam params;
    TextView name;
    TextView number;
    TextView dueDate;

    int index;

    Drawable background;

    boolean canClickable = true;

    public boolean isCanClickable() {
        return canClickable;
    }

    public void setCanClickable(boolean canClickable) {
        this.canClickable = canClickable;
    }

    @Override
    public Drawable getBackground() {
        return background;
    }

    public TextView getDueDate() {
        return dueDate;
    }

    public int getIndex() {
        return index;
    }

    public TextView getName() {
        return name;
    }

    public TextView getNumber() {
        return number;
    }

    public HangShowLayoutParam getParams() {
        return params;
    }

    public Coupons(Context context, Coupons other)
    {
        super(context);

        String nameText = other.getName().getText().toString();
        int index = other.getIndex();

        background = index%2 == 0 ? getResources().getDrawable(R.drawable.coupons) : getResources().getDrawable(R.drawable.coupons2);

        params = new HangShowLayoutParam(372 * ScreenParameter.getScreenparam_x(), 247 * ScreenParameter.getScreenparam_y());
        setBackground(background);
        this.setLayoutParams(params);

        this.index = index;

        name = new TextView(getContext());
        name.setText(nameText);
        name.setTextColor(Color.argb(255, 255, 255, 255));
        name.setTypeface(FontResource.AppleSDGothicNeoB00);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        name.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(CENTER_IN_PARENT).setMargin(-10 * ScreenParameter.getScreenparam_x(), -10 * ScreenParameter.getScreenparam_y(), 10 * ScreenParameter.getScreenparam_x(), 10 * ScreenParameter.getScreenparam_y()));
        name.setGravity(Gravity.CENTER);
        this.addView(name);

        DecimalFormat decimalFormat = new DecimalFormat("000");

        number = new TextView(getContext());
        number.setText("COUPLE COUPON NO." + decimalFormat.format(index+1));
        number.setTextColor(Color.argb(255, 255, 255, 255));
        number.setTypeface(FontResource.AppleSDGothicNeoB00);
        number.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(10 * ScreenParameter.getScreenparam_y()));
        number.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(ALIGN_PARENT_RIGHT).setMargin(0, 30 * ScreenParameter.getScreenparam_y(), 60 * ScreenParameter.getScreenparam_x(), 0));
        number.setGravity(Gravity.CENTER);
        this.addView(number);

        dueDate = new TextView(getContext());
        dueDate.setText("1회 이용권 (2016.12.31까지)");
        dueDate.setTextColor(Color.argb(255, 255, 255, 255));
        dueDate.setTypeface(FontResource.AppleSDGothicNeoB00);
        dueDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(10 * ScreenParameter.getScreenparam_y()));
        dueDate.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(ALIGN_PARENT_RIGHT).addRules(ALIGN_PARENT_BOTTOM).setMargin(0, 0, 60 * ScreenParameter.getScreenparam_x(), 55 * ScreenParameter.getScreenparam_y()));
        dueDate.setGravity(Gravity.CENTER);
        this.addView(dueDate);


        setOnTouchListener(new OnTouchListener() {

            boolean condition = true;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP && canClickable)
                {
                    if(condition)//클릭 시도
                    {
                        setBackground(getResources().getDrawable(R.drawable.coupons_click));
                        DbResource.couponses.add(getCoupones());

                    }
                    else
                    {
                        setBackground(background);
                        DbResource.couponses.remove(getCoupones());
                    }

                    condition = !condition;
                }

                return true;
            }
        });
    }

    public Coupons(Context context, String nameText, int index) {
        super(context);

        background = index%2 == 0 ? getResources().getDrawable(R.drawable.coupons) : getResources().getDrawable(R.drawable.coupons2);

        params = new HangShowLayoutParam(372 * ScreenParameter.getScreenparam_x(), 247 * ScreenParameter.getScreenparam_y());
        setBackground(background);
        this.setLayoutParams(params);

        this.index = index;

        name = new TextView(getContext());
        name.setText(nameText);
        name.setTextColor(Color.argb(255, 255, 255, 255));
        name.setTypeface(FontResource.AppleSDGothicNeoB00);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        name.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(CENTER_IN_PARENT).setMargin(-10 * ScreenParameter.getScreenparam_x(), -10 * ScreenParameter.getScreenparam_y(), 10 * ScreenParameter.getScreenparam_x(), 10 * ScreenParameter.getScreenparam_y()));
        name.setGravity(Gravity.CENTER);
        this.addView(name);

        DecimalFormat decimalFormat = new DecimalFormat("000");

        number = new TextView(getContext());
        number.setText("COUPLE COUPON NO." + decimalFormat.format(index+1));
        number.setTextColor(Color.argb(255, 255, 255, 255));
        number.setTypeface(FontResource.AppleSDGothicNeoB00);
        number.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(10 * ScreenParameter.getScreenparam_y()));
        number.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(ALIGN_PARENT_RIGHT).setMargin(0, 30 * ScreenParameter.getScreenparam_y(), 60 * ScreenParameter.getScreenparam_x(), 0));
        number.setGravity(Gravity.CENTER);
        this.addView(number);

        dueDate = new TextView(getContext());
        dueDate.setText("1회 이용권 (2016.12.31까지)");
        dueDate.setTextColor(Color.argb(255, 255, 255, 255));
        dueDate.setTypeface(FontResource.AppleSDGothicNeoB00);
        dueDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(10 * ScreenParameter.getScreenparam_y()));
        dueDate.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(ALIGN_PARENT_RIGHT).addRules(ALIGN_PARENT_BOTTOM).setMargin(0, 0, 60 * ScreenParameter.getScreenparam_x(), 55 * ScreenParameter.getScreenparam_y()));
        dueDate.setGravity(Gravity.CENTER);
        this.addView(dueDate);


        setOnTouchListener(new OnTouchListener() {

            boolean condition = true;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(condition)//클릭 시도
                    {
                        setBackground(getResources().getDrawable(R.drawable.coupons_click));
                        DbResource.couponses.add(getCoupones());

                    }
                    else
                    {
                        setBackground(background);
                        DbResource.couponses.remove(getCoupones());
                    }

                    condition = !condition;
                }

                return true;
            }
        });


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

    private Coupons getCoupones()
    {
        return this;
    }
}
