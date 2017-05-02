package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_CheckCoupons extends Activity {

    RelativeLayout main;

    TouchImageView backgrad;
    TouchImageView backBtn;

    TextView title;

    RelativeLayout mid;
    ImageView backImage;
    ImageView blackBox;

    ImageView checkBox;

    ImageView checkCoupons;
    TextView text;

    Coupons coupons;

    static ScaleAnimation bigger;
    static ScaleAnimation smaller;
    static ScaleAnimation setAnim;

    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlocal);

        main = (RelativeLayout)findViewById(R.id.main);

        bigger = new ScaleAnimation(0.95f, 1.05f, 0.95f, 1.05f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        smaller = new ScaleAnimation(1.05f, 1f, 1.05f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //setAnim = new ScaleAnimation(1.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        bigger.setFillAfter(true);
        smaller.setFillAfter(true);
//        setAnim.setFillAfter(true);

        backgrad = new TouchImageView(this);
        backgrad.setBackground(R.drawable.grad);
        backgrad.setSize(750 * ScreenParameter.getScreenparam_x(), 148 * ScreenParameter.getScreenparam_y());
        backgrad.setId(backgrad.hashCode());
        main.addView(backgrad);
        backgrad.setId(backgrad.hashCode());

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                finish();

                return false;
            }
        });

        title = new TextView(this);
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 *ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("쿠폰확인");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);

        checkBox = new ImageView(getApplicationContext());
        checkBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        checkBox.setBackground(getResources().getDrawable(R.drawable.login_button));
        main.addView(checkBox);
        checkBox.setId(checkBox.hashCode());
        checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                finish();

                return false;
            }
        });

        mid = new RelativeLayout(getApplicationContext());
        mid.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.BELOW, backgrad.getId()).addRules(RelativeLayout.ABOVE, checkBox.getId()));
        main.addView(mid);

        backImage = new ImageView(getApplicationContext());
        backImage.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mid.addView(backImage);
        backImage.setBackground(getResources().getDrawable(R.drawable.irin));

        blackBox = new ImageView(getApplicationContext());
        blackBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mid.addView(blackBox);
        blackBox.setBackgroundColor(Color.argb(153, 32, 33, 51));

        checkCoupons = new ImageView(getApplicationContext());
        checkCoupons.setLayoutParams(new HangShowLayoutParam(390 * ScreenParameter.getScreenparam_x(), 302 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_IN_PARENT));
        checkCoupons.setBackground(getResources().getDrawable(R.drawable.checkcoupons));
        mid.addView(checkCoupons);
        checkCoupons.setId(checkCoupons.hashCode());
        checkCoupons.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    clicked = true;
                    mid.removeView(view);
                    mid.addView(coupons);
                    coupons.setCanClickable(false);

                    final int time = 80;

                    bigger.setDuration(time);
                    smaller.setDuration(time);
//                    setAnim.setDuration(time);
                    coupons.setAnimation(bigger);
                    coupons.setAnimation(smaller);
                    coupons.setAnimation(setAnim);

                    Handler handler = new Handler()
                    {
                        int count = 0;

                        @Override
                        public void handleMessage(Message msg) {
                            if(count == 0)
                                coupons.startAnimation(bigger);
                            else if(count == time)
                                coupons.startAnimation(smaller);
//                            else if (count == time * 8 / 9)
//                                coupons.startAnimation(setAnim);
                            else if(count > 600)
                                removeMessages(0);

                            count++;

                            sendEmptyMessageDelayed(0, 1);
                        }
                    };
                    handler.sendEmptyMessage(0);

                }


                return true;
            }
        });

        String str = "";
        str = DbResource.Gender == DbResource.GENDER.MAN ? "오늘 쇼핑계획을 성실하게 지킨 \n" +
                "아이린님에게 박보검님이 커플쿠폰을 드립니다!\n" : "오늘은 쇼핑이 게획대로 안됐나보네요.\n" +
                " 박보검님에게 아이린님이 커플쿠폰을 드립니다!\n";

        text = new onTextView(this);
        text.setText(str);
        text.setTextColor(Color.WHITE);
        text.setTypeface(FontResource.GothamRounded_Book);
        text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        text.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).setMargin(0, 650 * ScreenParameter.getScreenparam_y(), 0, 0).addRules(RelativeLayout.CENTER_HORIZONTAL));
        mid.addView(text);
        text.setGravity(Gravity.CENTER);
        text.setLineSpacing((float)(3 * ScreenParameter.getScreenparam_y()), 1.5f);

        Random random = new Random();
        if(DbResource.couponses.size() != 0)
            coupons = new Coupons(getApplicationContext(), DbResource.couponses.get( Math.abs(random.nextInt()) % DbResource.couponses.size()));
        else
            coupons = new Coupons(getApplicationContext(), "쿠폰이 없습니다", 0);
        coupons.addRules(RelativeLayout.CENTER_IN_PARENT);
        checkCoupons.setId(coupons.hashCode());

        final Handler time = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                {
                    if (!clicked) {
                        mid.removeView(checkCoupons);
                        mid.addView(coupons);
                        coupons.setCanClickable(false);

                        final int time = 80;

                        bigger.setDuration(time);
                        smaller.setDuration(time);
//                    setAnim.setDuration(time);
                        coupons.setAnimation(bigger);
                        coupons.setAnimation(smaller);
                        coupons.setAnimation(setAnim);

                        Handler handler = new Handler() {
                            int count = 0;

                            @Override
                            public void handleMessage(Message msg) {
                                if (count == 0)
                                    coupons.startAnimation(bigger);
                                else if (count == time)
                                    coupons.startAnimation(smaller);
//                            else if (count == time * 8 / 9)
//                                coupons.startAnimation(setAnim);
                                else if (count > 600)
                                    removeMessages(0);

                                count++;

                                sendEmptyMessageDelayed(0, 1);
                            }
                        };
                        handler.sendEmptyMessage(0);

                    }
                }
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                time.sendEmptyMessage(0);
            }
        }, 3000);
    }


}
