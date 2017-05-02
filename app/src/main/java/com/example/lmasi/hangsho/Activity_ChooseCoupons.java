package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-09-18.
 */
public class Activity_ChooseCoupons extends Activity {

    RelativeLayout main;
    RelativeLayout blackscrenn;
    TouchImageView backBtn;
    TextView title;

    UpdatedScrollView scrollView;
    ArrayList<Coupons> couponses = new ArrayList<>();
    ImageView checkBox;

    ArrayList<ShopInfo> infos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosecoupon);

        main = (RelativeLayout)findViewById(R.id.main);

        infos = (ArrayList<ShopInfo>) getIntent().getSerializableExtra("LIST");
        Log.e("INFOS", Boolean.toString(infos == null));

        checkBox = new ImageView(getApplicationContext());
        checkBox.setBackground(getResources().getDrawable(R.drawable.list_checkbutton));
        checkBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        main.addView(checkBox);
        checkBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    SocketService.sendmessage("COUPON");
                    startActivity(new Intent(Activity_ChooseCoupons.this, Activity_timeLine.class).putExtra("LIST", infos).putExtra("TIME", getIntent().getIntExtra("TIME", 0)));
                    finish();
                }

                return true;
            }
        });
        Handler COUPON = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if(DbResource.COUPON)
                {
                    startActivity(new Intent(Activity_ChooseCoupons.this, Activity_timeLine.class).putExtra("LIST", infos).putExtra("TIME", getIntent().getIntExtra("TIME", 0)));
                    DbResource.COUPON = false;
                    finish();
                }

                sendEmptyMessageDelayed(0, 100);
            }
        };
        COUPON.sendEmptyMessage(0);
        checkBox.setId(checkBox.hashCode());

        scrollView = new UpdatedScrollView(getApplicationContext());
        //scrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.BELOW, title.getId()).setMargin(0, 50 * ScreenParameter.getScreenparam_y(), 0, 0));
        scrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.ABOVE, checkBox.getId()));
        main.addView(scrollView);

        backBtn = new TouchImageView(getApplicationContext());
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        scrollView.addObject(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(Activity_ChooseCoupons.this, Activity_shopList.class));
                finish();

                return false;
            }
        });

        title = new TextView(getApplicationContext());
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("매장리스트");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        scrollView.addObject(title);
        title.setId(title.hashCode());


        for(int i=0; i<Resources.coupons.length; i++)
        {
            Coupons coupons = new Coupons(getApplicationContext(), Resources.coupons[i], i);
            coupons.setId(coupons.hashCode());

            if(i%2 == 0) {

                coupons.setMargin(10 * ScreenParameter.getScreenparam_x(), 0, 5 * ScreenParameter.getScreenparam_y(), 0);

                if(i/2 == 0)
                {
                    coupons.setMargin(0, 40 * ScreenParameter.getScreenparam_y(), 0, 0);
                    coupons.addRules(RelativeLayout.BELOW, title.getId());
                }

                else
                {
                    coupons.addRules(RelativeLayout.BELOW, couponses.get(i-2).getId());
                }
            }
            else
            {
                coupons.setMargin(ScreenParameter.getScreen_x()/2, 0, 0, 0);

                if(i/2 != 0) {
                    coupons.addRules(RelativeLayout.BELOW, couponses.get(i - 2).getId());
                }

                else
                {
                    coupons.setMargin(ScreenParameter.getScreen_x()/2, 40 * ScreenParameter.getScreenparam_y(), 0, 0);
                    coupons.addRules(RelativeLayout.BELOW, title.getId());
                }
            }

            couponses.add(coupons);
            scrollView.addObject(coupons);
        }
        scrollView.showScroll();


        blackscrenn = new RelativeLayout(getApplicationContext());
        int scrennTimer = 2000;
        blackscrenn.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        main.addView(blackscrenn);
        blackscrenn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        blackscrenn.setBackgroundColor(Color.argb(156, 63, 63, 63));
        TextView textView = new TextView(getApplicationContext());
        textView.setText("잠깐! \n" +
                "\n" +
                "커플끼리 서로 합의된 쇼핑시간이\n" +
                "초과되었을 시에는 남성분에게,\n" +
                "잘 지켜졌을 시에는 여성분에게\n" +
                "다음화면에서 선택한 쿠폰 3개 중 1개가\n" +
                "랜덤으로 지급됩니다.");
        textView.setTextColor(Color.argb(255, 255, 255, 255));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(35 * ScreenParameter.getScreenparam_y()));
        textView.setTypeface(FontResource.AppleSDGothicNeoB00);
        textView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_IN_PARENT));
        textView.setGravity(Gravity.CENTER);
        blackscrenn.addView(textView);
        final Handler handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                blackscrenn.setBackground(null);
                blackscrenn.removeAllViews();
                main.removeView(blackscrenn);
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, scrennTimer);
    }
}
