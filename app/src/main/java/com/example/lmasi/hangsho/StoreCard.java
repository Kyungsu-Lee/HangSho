package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public abstract class StoreCard extends RelativeLayout {

     private RelativeLayout main;

    private ImageView shopping;
    private ImageView eyes;

    private TextView mainNmae;
    private TextView subName;

    private ImageView arrow;

    ShopImageView shopImage;

    private  boolean shop_condition = false;
    private  boolean eye_condition = false;

    int index;
    private boolean like = false;

    public int getIndex() {
        return index;
    }

    public StoreCard(Context context, String mainName, String subName, int index)
    {
        super(context);

        main = new RelativeLayout(getContext());
        main.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 96 * ScreenParameter.getScreenparam_y()));
        addView(main);
        main.setId(main.hashCode());
        main.setBackgroundColor(Color.argb(255, 255, 255, 255));

        this.setOnTouchListener(new OnTouchListener() {

            boolean condition = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(condition)
                    {
                        unclickCard();
                        arrow.setBackground(null);
                    }

                    else
                    {
                        arrow.setBackground(getResources().getDrawable(R.drawable.list_arrow_shop));
                        clickCard();
                    }

                    condition = !condition;
                }

                return true;
            }
        });

        this.index = index;

        shopping = new ImageView(context);
        shopping.setBackground(getResources().getDrawable(R.drawable.list_shoppingicon));
        shopping.setLayoutParams(new HangShowLayoutParam(54 * ScreenParameter.getScreenparam_x(), 54 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(38 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        shopping.setId(shopping.hashCode());
        main.addView(shopping);
        shopping.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(shop_condition)
                    {
                        unclickEventShop();
                        shopUnClick();
                    }

                    else
                    {
                        shopClick();
                        clickEventShop();
                    }

                }

                return true;
            }
        });

        eyes = new ImageView(context);
        eyes.setBackground(getResources().getDrawable(R.drawable.list_eyeshoppingicon));
        eyes.setLayoutParams(new HangShowLayoutParam(54 * ScreenParameter.getScreenparam_x(), 54 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 0, 0).addRules(RelativeLayout.RIGHT_OF, shopping.getId()));
        eyes.setId(eyes.hashCode());
        main.addView(eyes);
        eyes.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(eye_condition)
                    {
                        unclickEventEyes();
                        eyeUnClick();
                    }

                    else
                    {
                        clickEventEyes();
                        eyeClick();
                    }

                }

                return true;
            }
        });

        this.mainNmae = new TextView(context);
        this.mainNmae.setText(mainName);
        this.mainNmae.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, eyes.getId()).setMargin(30 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        main.addView(this.mainNmae);
        this.mainNmae.setTypeface(FontResource.AppleSDGothicNeoB00);
        this.mainNmae.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        this.mainNmae.setTextColor(Color.argb(255, 65, 64, 66));
        this.mainNmae.setId(this.mainNmae.hashCode());

        this.subName = new TextView(context);
        this.subName.setText(subName);
        this.subName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, this.mainNmae.getId()).setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 0, 0).addRules(RelativeLayout.ALIGN_BASELINE, this.mainNmae.getId()));
        main.addView(this.subName);
        this.subName.setTypeface(FontResource.AppleSDGothicNeoB00);
        this.subName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        this.subName.setTextColor(Color.argb(255, 65, 64, 66));
        this.subName.setId(this.subName.hashCode());

        arrow = new ImageView(context);
        arrow.setLayoutParams(new HangShowLayoutParam(22 * ScreenParameter.getScreenparam_x(), 12 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(20 * ScreenParameter.getScreenparam_x(), 0, 0, 0).addRules(RelativeLayout.RIGHT_OF, this.subName.getId()));
        arrow.setId(arrow.hashCode());
        main.addView(arrow);
    }

    public String getShopName()
    {
        return this.mainNmae.getText().toString();
    }

    public abstract void clickEventShop();
    public abstract void clickEventEyes();
    public abstract void unclickEventShop();
    public abstract void unclickEventEyes();
    public abstract void clickCard();
    public abstract void unclickCard();

    public void eyeClick()
    {
        eyes.setBackground(getResources().getDrawable(R.drawable.list_eyeshoppinglike));
        eye_condition = !eye_condition;

    }

    public void eyeUnClick()
    {
        eyes.setBackground(getResources().getDrawable(R.drawable.list_eyeshoppingicon));
        eye_condition = !eye_condition;
    }

    public void shopClick()
    {
        shopping.setBackground(getResources().getDrawable(R.drawable.list_shoppinglike));
        shop_condition = !shop_condition;
    }

    public void shopUnClick()
    {
        shopping.setBackground(getResources().getDrawable(R.drawable.list_shoppingicon));
        shop_condition = !shop_condition;
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

    public void Like()
    {
        this.like = true;
    }

    public boolean isLike(){return this.like;}

    public RelativeLayout getMain(){return main;}

    public String getsubName(){return this.subName.getText().toString();}

}
