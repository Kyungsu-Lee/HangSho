package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-09-17.
 */
public class CodiImageView extends RelativeLayout {

    Context context;

    ImageView imageView;
    RelativeLayout heart;

    Bitmap image;

    CodiImageView who;

    ImageView whiteHeart;

    boolean isLike = false;

    static ScaleAnimation bigger;
    static ScaleAnimation smaller;
    static ScaleAnimation setAnim;


    boolean condition = true;

    public CodiImageView(final Context context, final int image, LayoutParams layoutParams) {
        super(context);
        this.context = context;
        this.setId(this.hashCode());

        who = this;

        this.setLayoutParams(layoutParams);

        this.image = ((BitmapDrawable)getResources().getDrawable(image)).getBitmap();

        bigger = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        smaller = new ScaleAnimation(1.1f, 0.9f, 1.1f, 0.9f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        setAnim = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        bigger.setFillAfter(true);
        smaller.setFillAfter(true);
        setAnim.setFillAfter(true);
//        bigger.setFillBefore(false);
//        smaller.setFillBefore(false);
//        setAnim.setFillBefore(false);
//        smaller.setFillEnabled(true);
//        bigger.setFillEnabled(true);
//        setAnim.setFillEnabled(true);

        imageView = new ImageView(context);
        RelativeLayout.LayoutParams image_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(image_params);
        imageView.setImageBitmap(this.image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addView(imageView);

        heart = new RelativeLayout(context);
        RelativeLayout.LayoutParams heart_params = new RelativeLayout.LayoutParams((int)(63 * ScreenParameter.getScreenparam_x()), (int)(63 * ScreenParameter.getScreenparam_y()));
        heart_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        heart_params.setMargins(0, (int)(15 * ScreenParameter.getScreenparam_x()), (int)(0 * ScreenParameter.getScreenparam_x()), (int)(15 * ScreenParameter.getScreenparam_y()));
        heart.setLayoutParams(heart_params);
        heart.setBackground(getResources().getDrawable(R.drawable.home_likeicon));
        this.addView(heart);

        whiteHeart = new ImageView(getContext());
        whiteHeart.setBackground(null);
        whiteHeart.setLayoutParams(new HangShowLayoutParam(82 * ScreenParameter.getScreenparam_x(), 71 * ScreenParameter.getScreenparam_y()).addRules(CENTER_IN_PARENT));


        this.setOnTouchListener(new OnTouchListener() {

            ImageView coverBlack;

            RelativeLayout background;
            TouchImageView close_btn;

            ImageView line;
            ImageView imageView;
            RelativeLayout like;

            TextView storeName;
            TextView productName;



            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(condition)
                    {
                        coverBlack = new ImageView(context);
                        coverBlack.setBackgroundColor(Color.argb(72, 34, 30, 31));
                        coverBlack.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        ((Activity_Main)context).main.addView(coverBlack);
                        coverBlack.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                return true;
                            }
                        });

                        background = new RelativeLayout(context);
                        background.setBackgroundColor(Color.WHITE);
                        background.setLayoutParams(new HangShowLayoutParam((int)(640 * ScreenParameter.getScreenparam_x()), (int)(1058 * ScreenParameter.getScreenparam_y())).addRules(ALIGN_TOP, getId()).addRules(CENTER_IN_PARENT));
                        //background.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).setMargin(640 * ScreenParameter.getScreenparam_x(), 1058 * ScreenParameter.getScreenparam_y(), 0, 0).addRules(ALIGN_TOP, getId()).addRules(CENTER_IN_PARENT));
                        ((Activity_Main)context).main.addView(background);
                        background.setId(background.hashCode());

                        storeName = new TextView(context);
                        storeName.setLayoutParams(new HangShowLayoutParam(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).addRules(CENTER_HORIZONTAL).setMargin(0, (int)(50 * ScreenParameter.getScreenparam_y()), 0, 0));
                        storeName.setText(" WONDER PLACE");
                        storeName.setTypeface(FontResource.AppleSDGothicNeoB00);
                        storeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
                        storeName.setTextColor(Color.argb(255, 93, 94, 94));
                        background.addView(storeName);

                        line = new ImageView(context);
                        line.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, (int)(2 * ScreenParameter.getScreenparam_y())).setMargin(0, 127 * ScreenParameter.getScreenparam_y(), 0, 0));
                       // line.setImageBitmap(((BitmapDrawable)getResources().getDrawable(R.drawable.home_mapline)).getBitmap());
                        line.setBackgroundColor(Color.argb(255, 208, 210, 211));
                        background.addView(line);

                        productName = new TextView(context);
                        productName.setLayoutParams(new HangShowLayoutParam(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).addRules(CENTER_HORIZONTAL).setMargin(0, (int)(980 * ScreenParameter.getScreenparam_y()), 0, 0));
                        productName.setText("WPO MEN 폴리자이 반팔 티셔츠");
                        productName.setTypeface(FontResource.AppleSDGothicNeoB00);
                        productName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
                        productName.setTextColor(isLike ? Color.argb(255, 243, 132, 170) : Color.argb(255, 108, 116, 182));
                        background.addView(productName);

                        imageView = new ImageView(context);
                        imageView.setImageBitmap(who.image);
                        imageView.setScaleType(ImageView.ScaleType.FIT_START);
                        imageView.setLayoutParams(new HangShowLayoutParam(602 * ScreenParameter.getScreenparam_x(), 802 * ScreenParameter.getScreenparam_y()).addRules(CENTER_HORIZONTAL).setMargin(0, 147 * ScreenParameter.getScreenparam_y(), 0, 0));
                        //imageView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(CENTER_IN_PARENT).setMargin(0, 147 * ScreenParameter.getScreenparam_y(), 0, 0));
                        background.addView(imageView);
                        imageView.setId(imageView.hashCode());

                        like = new RelativeLayout(context);
                        like.setBackground(getResources().getDrawable(isLike ? R.drawable.heartbg : R.drawable.home_biglike));
                        like.setLayoutParams(new HangShowLayoutParam(143 * ScreenParameter.getScreenparam_x(), 143 * ScreenParameter.getScreenparam_y()).addRules(ALIGN_RIGHT, imageView.getId()).addRules(ALIGN_TOP, imageView.getId()).setMargin(0, 25 * ScreenParameter.getScreenparam_y(), 15 * ScreenParameter.getScreenparam_x(), 0));
                        background.addView(like);
                        like.addView(whiteHeart);


                        like.setOnTouchListener(new OnTouchListener() {

                            boolean clicked = isLike;

                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {

                                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                                {
                                    if(clicked)
                                    {
                                        like.setBackground(getResources().getDrawable(R.drawable.home_biglike));
                                        heart.setBackground(getResources().getDrawable(R.drawable.home_likeicon));
                                        whiteHeart.setBackground(null);
                                        productName.setTextColor(Color.argb(255, 108, 116, 182));

                                    }
                                    else
                                    {
                                        like.setBackground(getResources().getDrawable(R.drawable.heartbg));
                                        //heart.removeView(whiteHeart);
                                        heart.setBackground(getResources().getDrawable(R.drawable.home_likepressed));
                                        productName.setTextColor(Color.argb(255, 243, 132, 170));
                                        whiteHeart.setBackground(getResources().getDrawable(R.drawable.whiteheart));
                                        //like.addView(whiteHeart);

                                        final int time = 80;

                                        bigger.setDuration(time);
                                        smaller.setDuration(time);
                                        setAnim.setDuration(time);
                                        whiteHeart.setAnimation(bigger);
                                        whiteHeart.setAnimation(smaller);
                                        whiteHeart.setAnimation(setAnim);

                                       Handler handler = new Handler()
                                       {
                                           int count = 0;

                                           @Override
                                           public void handleMessage(Message msg) {
                                               if(count == 0)
                                                   whiteHeart.startAnimation(bigger);
                                               else if(count == time)
                                                   whiteHeart.startAnimation(smaller);
                                               else if (count == time * 8 / 9)
                                                   whiteHeart.startAnimation(setAnim);
                                               else if(count > 600)
                                                   removeMessages(0);

                                               count++;

                                               sendEmptyMessageDelayed(0, 1);
                                           }
                                       };
                                        handler.sendEmptyMessage(0);

                                    }

                                    clicked = !clicked;
                                    isLike = !isLike;
                                }

                                return true;
                            }
                        });

                        close_btn = new TouchImageView(context);
                        close_btn.setBackground(R.drawable.home_close);
                        close_btn.setSize(78 * ScreenParameter.getScreenparam_x(), 78 * ScreenParameter.getScreenparam_y());
                        close_btn.addRule(ALIGN_TOP, background.getId());
                        //close_btn.addRule(ALIGN_RIGHT, background.getId());
                        close_btn.setLocation(660 * ScreenParameter.getScreenparam_x(), -40 * ScreenParameter.getScreenparam_y());
                        ((Activity_Main)context).main.addView(close_btn);
                        close_btn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                like.removeAllViews();
                                background.removeAllViews();

                                ((Activity_Main)context).main.removeView(close_btn);
                                ((Activity_Main)context).main.removeView(background);
                                ((Activity_Main)context).main.removeView(coverBlack);
                            }
                        });

                    }


                }

                return true;
            }
        });
    }
}
