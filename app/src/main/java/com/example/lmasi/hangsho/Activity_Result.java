package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by lmasi on 2016-09-29.
 */
public class Activity_Result extends Activity {

    RelativeLayout main;

    ImageView topbar;
    RelativeLayout body;
    TextView title;

    ImageView logo;
    TextView shopTime;
    TextView Time;

    Wave topWave;
    Wave aboveWave;
    Wave belowWave;
    Wave bottomWave;

    Shark upShark;
    Shark downShark;

    TextView shop_time;
    TextView shop_get_time;

    RelativeLayout leftPage;
    RelativeLayout rightPage;

    int time_shop = 80;
    int time_get_shop = 40;

    UpdatedScrollView updatedScrollView;

    ImageView happyMan;

    ImageView leftArm;
    RotateAnimation leftArmAnimation;

    ImageView rightArm;
    RotateAnimation rightArmAnimation;

    ImageView insta;
    ImageView facebook;
    ImageView twitter;
    ImageView complete;

    RelativeLayout todayPlaceLayout;
    ImageView todayPlaceIcon;
    TextView todayPlace;
    TextView todayPlcaName;

    RelativeLayout numOfShopsLayout;
    ImageView numOfShopsIcon;
    TextView numOfShops;
    TextView numOfShopsNumber;

    int numOfShop;
    String location;

    RelativeLayout coupleTicketLayout;
    ImageView coupleTicketIcon;
    TextView coupleTicket;
    TextView coupleTicketcheck;

    ArrayList<ShopInfo> infos = new ArrayList<>();

    int sumTime;
    int spentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityresult);

        main = (RelativeLayout)findViewById(R.id.main);

        infos = (ArrayList<ShopInfo>)getIntent().getSerializableExtra("LIST");
        sumTime = getIntent().getIntExtra("TIME", 0);
        spentTime = getIntent().getIntExtra("SPENT", 0);

        boolean mission = getIntent().getBooleanExtra("misson", false);
        numOfShop = infos.size();
        location = getIntent().getStringExtra("location");
        int time = getIntent().getIntExtra("Time", 0);
        long currentTime = System.currentTimeMillis();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd");
        Random random = new Random();
try {
//        DbResource.db.execSQL("insert into HistoryCard values(7, 1, '한동 - 명동 쇼핑데이트', '2016.10.10', 3, '30:37');");
    DbResource.db.execSQL("insert into HistoryCard values(" + currentTime + ", " + (mission ? 1 : 0) + ", '" + (DbResource.Location + " 데이트") + "', '" + sdf.format(d) + "', " + numOfShop + ", '" + spentTime + "' );");
    Log.e("SQLINSERT", "insert into HistoryCard values(" + currentTime + ", " + (mission ? 1 : 0) + ", '" + (DbResource.Location + " 데이트") + "', '" + sdf.format(d) + "', " + numOfShop + ", '" + spentTime + "' );");
    for (int i = 0; i < numOfShop; i++) {
        DbResource.db.execSQL("insert into History values(" + currentTime + ", '" + infos.get(i).getShopName() + "', " + random.nextInt() % 2 + ")");
        Log.e("SQLINSERT", "insert into History values(" + currentTime + ", '" + infos.get(i).getShopName() + "', " + random.nextInt() % 2 + ")");
    }
}
catch (Exception e)
{
    e.printStackTrace();
}

        DbResource.iscalled = false;

        topbar = new ImageView(getApplicationContext());
        topbar.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 141 *ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL));
        topbar.setBackground(getResources().getDrawable(R.drawable.result_topbar));
        main.addView(topbar);
        topbar.setId(topbar.hashCode());

        title = new TextView(getApplicationContext());
        title.setLayoutParams(new HangShowLayoutParam(180 * ScreenParameter.getScreenparam_x(), 64 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 32 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("쇼핑결과");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);

        updatedScrollView = new UpdatedScrollView(getApplicationContext());
        updatedScrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 950 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, topbar.getId()));

        body = new RelativeLayout(getApplicationContext());
        body.setLayoutParams(new HangShowLayoutParam(662 * ScreenParameter.getScreenparam_x(), (numOfShop < 6 ? 550 : 760) * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 50 * ScreenParameter.getScreenparam_y(), 0, 0));
        body.setBackground(getResources().getDrawable(R.drawable.result_backgroundsquare));
        updatedScrollView.addObject(body);
        body.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_x()));
        body.setId(body.hashCode());

        logo = new ImageView(getApplicationContext());
        logo.setLayoutParams(new HangShowLayoutParam(590 * ScreenParameter.getScreenparam_x(), 186 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 10 * ScreenParameter.getScreenparam_y(), 0, 0 ));
        logo.setBackground(getResources().getDrawable(R.drawable.result_logo));
        body.addView(logo);
        logo.setId(logo.hashCode());

        leftPage = new RelativeLayout(getApplicationContext());
        leftPage.setLayoutParams(new HangShowLayoutParam(331 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, logo.getId()));
        body.addView(leftPage);
        leftPage.setId(leftPage.hashCode());

        rightPage = new RelativeLayout(getApplicationContext());
        rightPage.setLayoutParams(new HangShowLayoutParam(331 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, logo.getId()).addRules(RelativeLayout.RIGHT_OF, leftPage.getId()));
        body.addView(rightPage);

        shopTime = new TextView(getApplicationContext());
        shopTime.setLayoutParams(new HangShowLayoutParam(269 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_x()).addRules(RelativeLayout.CENTER_HORIZONTAL));
        leftPage.addView(shopTime);
        shopTime.setBackground(getResources().getDrawable(R.drawable.result_plannedtimecloud));
        shopTime.setText("오늘의 쇼핑 계획 시간");
        shopTime.setPadding(0, (int)(-6 * ScreenParameter.getScreenparam_y()), 0, 0);
        shopTime.setGravity(Gravity.CENTER);
        shopTime.setTextColor(Color.argb(255, 87, 87, 86));
        shopTime.setTypeface(FontResource.AppleSDGothicNeoB00);
        shopTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        shopTime.setId(shopTime.hashCode());

        Time = new TextView(getApplicationContext());
        Time.setLayoutParams(new HangShowLayoutParam(269 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_x()).addRules(RelativeLayout.CENTER_HORIZONTAL));
        rightPage.addView(Time);
        Time.setBackground(getResources().getDrawable(R.drawable.result_plannedtimecloud));
        Time.setText("오늘의 쇼핑 시간");
        Time.setPadding(0, (int)(-6 * ScreenParameter.getScreenparam_y()), 0, 0);
        Time.setGravity(Gravity.CENTER);
        Time.setTextColor(Color.argb(255, 87, 87, 86));
        Time.setTypeface(FontResource.AppleSDGothicNeoB00);
        Time.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        Time.setId(Time.hashCode());

        time_get_shop = sumTime;

        shop_get_time = new TextView(getApplicationContext());
        shop_get_time.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, shopTime.getId()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        leftPage.addView(shop_get_time);
        shop_get_time.setText(time_get_shop/60 == 0 ? time_get_shop%60 + " 분" : time_get_shop/60 +"시간 " + time_get_shop%60 + " 분");
        shop_get_time.setGravity(Gravity.CENTER);
        shop_get_time.setTextColor(Color.argb(255, 255, 255, 255));
        shop_get_time.setTypeface(FontResource.AppleSDGothicNeoB00);
        shop_get_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));

        time_shop = spentTime;

        shop_time = new TextView(getApplicationContext());
        shop_time.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        rightPage.addView(shop_time);
        shop_time.setText(time_shop/60 == 0 ? time_shop%60 + " 분" : time_shop/60 +"시간 " + time_shop%60 + " 분");
        shop_time.setGravity(Gravity.CENTER);
        shop_time.setTextColor(Color.argb(255, 255, 255, 255));
        shop_time.setTypeface(FontResource.AppleSDGothicNeoB00);
        shop_time.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        shop_time.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, Time.getId()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 0, 0, 0));

        todayPlaceLayout = new RelativeLayout(getApplicationContext());
        todayPlaceLayout.setLayoutParams(new HangShowLayoutParam(558 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW,body.getId()).setMargin(100 * ScreenParameter.getScreenparam_x(), 40 * ScreenParameter.getScreenparam_y(), 0, 0));
        updatedScrollView.addObject(todayPlaceLayout);
        todayPlaceLayout.setId(todayPlaceLayout.hashCode());

        todayPlaceIcon = new ImageView(getApplicationContext());
        todayPlaceIcon.setBackground(getResources().getDrawable(R.drawable.result_placeicon));
        todayPlaceIcon.setLayoutParams(new HangShowLayoutParam(53 * ScreenParameter.getScreenparam_x(), 68 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(0, 0, 0, 0));
        todayPlaceLayout.addView(todayPlaceIcon);
        todayPlaceIcon.setId(todayPlaceIcon.hashCode());

        todayPlace = new TextView(getApplicationContext());
        todayPlace.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, todayPlaceIcon.getId()).setMargin(20 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        todayPlace.setGravity(Gravity.CENTER);
        todayPlace.setTextColor(Color.argb(255, 111, 111, 111));
        todayPlace.setTypeface(FontResource.AppleSDGothicNeoB00);
        todayPlace.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        todayPlace.setText("오늘의 쇼핑 장소");
        todayPlaceLayout.addView(todayPlace);
        todayPlace.setId(todayPlace.hashCode());

        todayPlcaName = new TextView(getApplicationContext());
        todayPlcaName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT));
        todayPlcaName.setGravity(Gravity.CENTER);
        todayPlcaName.setTextColor(Color.argb(255, 111, 111, 111));
        todayPlcaName.setTypeface(FontResource.AppleSDGothicNeoB00);
        todayPlcaName.setText(DbResource.Location);
        todayPlcaName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        todayPlaceLayout.addView(todayPlcaName);
        todayPlcaName.setId(todayPlcaName.hashCode());

        numOfShopsLayout = new RelativeLayout(getApplicationContext());
        numOfShopsLayout.setLayoutParams(new HangShowLayoutParam(558 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW,body.getId()).addRules(RelativeLayout.BELOW, todayPlaceLayout.getId()).addRules(RelativeLayout.ALIGN_LEFT, todayPlaceLayout.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        updatedScrollView.addObject(numOfShopsLayout);
        numOfShopsLayout.setId(numOfShopsLayout.hashCode());

        numOfShopsIcon = new ImageView(getApplicationContext());
        numOfShopsIcon.setBackground(getResources().getDrawable(R.drawable.result_shopicon));
        numOfShopsIcon.setLayoutParams(new HangShowLayoutParam(53 * ScreenParameter.getScreenparam_x(), 68 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(0, 0, 0, 0));
        numOfShopsLayout.addView(numOfShopsIcon);
        numOfShopsIcon.setId(numOfShopsIcon.hashCode());

        numOfShops = new TextView(getApplicationContext());
        numOfShops.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, numOfShopsIcon.getId()).setMargin(20 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        numOfShops.setGravity(Gravity.CENTER);
        numOfShops.setTextColor(Color.argb(255, 111, 111, 111));
        numOfShops.setTypeface(FontResource.AppleSDGothicNeoB00);
        numOfShops.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        numOfShops.setText("방문 매장 수");
        numOfShopsLayout.addView(numOfShops);
        numOfShops.setId(numOfShops.hashCode());

        numOfShopsNumber = new TextView(getApplicationContext());
        numOfShopsNumber.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT));
        numOfShopsNumber.setGravity(Gravity.CENTER);
        numOfShopsNumber.setTextColor(Color.argb(255, 111, 111, 111));
        numOfShopsNumber.setTypeface(FontResource.AppleSDGothicNeoB00);
        numOfShopsNumber.setText(Integer.toString(numOfShop)+"개");
        numOfShopsNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        numOfShopsLayout.addView(numOfShopsNumber);
        numOfShopsNumber.setId(numOfShopsNumber.hashCode());

        coupleTicketLayout = new RelativeLayout(getApplicationContext());
        coupleTicketLayout.setLayoutParams(new HangShowLayoutParam(558 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW,body.getId()).addRules(RelativeLayout.BELOW, numOfShopsLayout.getId()).addRules(RelativeLayout.ALIGN_LEFT, numOfShopsLayout.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        updatedScrollView.addObject(coupleTicketLayout);
        coupleTicketLayout.setId(coupleTicketLayout.hashCode());

        coupleTicketIcon = new ImageView(getApplicationContext());
        coupleTicketIcon.setBackground(getResources().getDrawable(R.drawable.result_ticketsicon));
        coupleTicketIcon.setLayoutParams(new HangShowLayoutParam(53 * ScreenParameter.getScreenparam_x(), 68 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(0, 0, 0, 0));
        coupleTicketLayout.addView(coupleTicketIcon);
        coupleTicketIcon.setId(coupleTicketIcon.hashCode());

        coupleTicket = new TextView(getApplicationContext());
        coupleTicket.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.RIGHT_OF, coupleTicketIcon.getId()).setMargin(20 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        coupleTicket.setGravity(Gravity.CENTER);
        coupleTicket.setTextColor(Color.argb(255, 111, 111, 111));
        coupleTicket.setTypeface(FontResource.AppleSDGothicNeoB00);
        coupleTicket.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        coupleTicket.setText("커플티켓");
        coupleTicketLayout.addView(coupleTicket);
        coupleTicket.setId(coupleTicket.hashCode());

        coupleTicketcheck = new TextView(getApplicationContext());
        coupleTicketcheck.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT));
        coupleTicketcheck.setGravity(Gravity.CENTER);
        coupleTicketcheck.setTextColor(Color.argb(255, 122, 143, 200));
        coupleTicketcheck.setTypeface(FontResource.AppleSDGothicNeoB00);
        coupleTicketcheck.setText("확인하기");
        coupleTicketcheck.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
        coupleTicketLayout.addView(coupleTicketcheck);
        coupleTicketcheck.setId(coupleTicketcheck.hashCode());
        coupleTicketcheck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Result.this, Activity_CheckCoupons.class));
                }

                return true;
            }
        });


        main.addView(updatedScrollView);
        updatedScrollView.showScroll();


        topWave = new Wave(getApplicationContext());
        topWave.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 263 * ScreenParameter.getScreenparam_y()).setMargin(0,1070 * ScreenParameter.getScreenparam_y(), 0, -1000 * ScreenParameter.getScreenparam_y() ));
        main.addView(topWave);

        upShark = new Shark(getApplicationContext());
        upShark.setRight(false);
        upShark.setLayoutParams(new HangShowLayoutParam(88 * ScreenParameter.getScreenparam_x(), 119 * ScreenParameter.getScreenparam_y()).setMargin(466 * ScreenParameter.getScreenparam_x(), 1098 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(upShark);
        upShark.setNormalLocation(1098);
        upShark.startAnimation(1000);

        aboveWave = new Wave(getApplicationContext());
        aboveWave.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 263 * ScreenParameter.getScreenparam_y()).setMargin(0,1139 * ScreenParameter.getScreenparam_y(), 0, -1000 * ScreenParameter.getScreenparam_y()));
        main.addView(aboveWave);


        if(mission)
        {
            happyMan = new ImageView(getApplicationContext());
            happyMan.setLayoutParams(new HangShowLayoutParam(360 * ScreenParameter.getScreenparam_x(), 360 * ScreenParameter.getScreenparam_y()).setMargin(202 * ScreenParameter.getScreenparam_x(), 892 * ScreenParameter.getScreenparam_y(), 0, 0));
            happyMan.setBackground(getResources().getDrawable(R.drawable.result_happyman));
            main.addView(happyMan);
        }

        else if(!mission)
        {
            int speed = 800;

            leftArm = new ImageView(getApplicationContext());
            leftArm.setLayoutParams(new HangShowLayoutParam(108  * ScreenParameter.getScreenparam_x(), 29 * ScreenParameter.getScreenparam_y()).setMargin(243 * ScreenParameter.getScreenparam_x(), 1137 * ScreenParameter.getScreenparam_y(), 0, 0));
            leftArm.setBackground(getResources().getDrawable(R.drawable.result_failmanleftarm));
            main.addView(leftArm);

            leftArmAnimation = new RotateAnimation(-10, 10, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
            leftArmAnimation.setDuration(speed);
            leftArm.setAnimation(leftArmAnimation);
            leftArmAnimation.setRepeatMode(Animation.REVERSE);
            leftArmAnimation.setRepeatCount(Animation.INFINITE);

            rightArm = new ImageView(getApplicationContext());
            rightArm.setLayoutParams(new HangShowLayoutParam(94  * ScreenParameter.getScreenparam_x(), 82 * ScreenParameter.getScreenparam_y()).setMargin(403 * ScreenParameter.getScreenparam_x(), 1068 * ScreenParameter.getScreenparam_y(), 0, 0));
            rightArm.setBackground(getResources().getDrawable(R.drawable.result_failmanrightarm));
            main.addView(rightArm);

            rightArmAnimation = new RotateAnimation(15, 0, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.85f);
            rightArmAnimation.setDuration(speed);
            rightArm.setAnimation(rightArmAnimation);
            rightArmAnimation.setRepeatMode(Animation.REVERSE);
            rightArmAnimation.setRepeatCount(Animation.INFINITE);


            happyMan = new ImageView(getApplicationContext());
            happyMan.setLayoutParams(new HangShowLayoutParam(127 * ScreenParameter.getScreenparam_x(), 282 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 955 * ScreenParameter.getScreenparam_y(), 0, 0));
            happyMan.setBackground(getResources().getDrawable(R.drawable.sadman));
            main.addView(happyMan);

            Handler mov = new Handler()
            {
                boolean condition = false;

                int n = 0;

                int y = 1;

                @Override
                public void handleMessage(Message msg) {

                    if(condition)
                    {
                        happyMan.setY(happyMan.getY() + y);
                        leftArm.setY(leftArm.getY() + y);
                        rightArm.setY(rightArm.getY() + y);

                        n++;
                    }
                    else
                    {
                        happyMan.setY(happyMan.getY() - y);
                        leftArm.setY(leftArm.getY() - y);
                        rightArm.setY(rightArm.getY() - y);

                        n++;
                    }

                    if(n > 10)
                    {
                        condition = !condition;
                        n = 0;
                    }


                    sendEmptyMessageDelayed(0, 70);
                }
            };

            mov.sendEmptyMessage(0);
        }



        belowWave = new Wave(getApplicationContext());
        belowWave.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 263 * ScreenParameter.getScreenparam_y()).setMargin(0,1208 * ScreenParameter.getScreenparam_y(), 0, -1000 * ScreenParameter.getScreenparam_y() ));
        main.addView(belowWave);

        downShark = new Shark(getApplicationContext());
        downShark.setRight(true);
        downShark.setLayoutParams(new HangShowLayoutParam(88 * ScreenParameter.getScreenparam_x(), 119 * ScreenParameter.getScreenparam_y()).setMargin(119 * ScreenParameter.getScreenparam_x(), 1230 * ScreenParameter.getScreenparam_y(), 0, -1000 * ScreenParameter.getScreenparam_y()));
        main.addView(downShark);
        downShark.setNormalLocation(1230);
        downShark.startAnimation(300);
        downShark.setSpeed(70);

        bottomWave = new Wave(getApplicationContext());
        bottomWave.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 263 * ScreenParameter.getScreenparam_y()).setMargin(0,1277 * ScreenParameter.getScreenparam_y(), 0, -1000 * ScreenParameter.getScreenparam_y() ));
        main.addView(bottomWave);

        insta = new ImageView(getApplicationContext());
        insta.setBackground(getResources().getDrawable(R.drawable.result_instagram));
        insta.setLayoutParams(new HangShowLayoutParam(94 * ScreenParameter.getScreenparam_x(), 94 * ScreenParameter.getScreenparam_y()).setMargin(359 * ScreenParameter.getScreenparam_x(), 1200 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(insta);
        insta.setId(insta.hashCode());

        facebook = new ImageView(getApplicationContext());
        facebook.setBackground(getResources().getDrawable(R.drawable.result_facebook));
        facebook.setLayoutParams(new HangShowLayoutParam(94 * ScreenParameter.getScreenparam_x(), 94 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_TOP, insta.getId()).addRules(RelativeLayout.RIGHT_OF, insta.getId()).setMargin(3 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        main.addView(facebook);
        facebook.setId(facebook.hashCode());
        facebook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
//                    Bitmap image = DbResource.profile;
//                    SharePhoto photo = new SharePhoto.Builder()
//                            .setBitmap(image)
//                            .build();
//                    SharePhotoContent content = new SharePhotoContent.Builder()
//                            .addPhoto(photo)
//                            .build();

                    getWindow().getDecorView().setDrawingCacheEnabled(true);

                    Bitmap screenshot = getWindow().getDecorView() .getDrawingCache();

                    String filename = "screenshot.png";

                    try {

                        File f = new File(Environment.getExternalStorageDirectory(), filename);
                        f.createNewFile();
                        OutputStream outStream = new FileOutputStream(f);
                        screenshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        outStream.close();

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                    getWindow().getDecorView().setDrawingCacheEnabled(false);

//
//                    Uri uri = Uri.fromFile(new File(filename));
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_SEND);
//                    intent.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent.setType("image/*");
//                    startActivity(Intent.createChooser(intent, "공유하기"));

                    //File dirName = new File(Environment.getExternalStoragePublicDirectory());  //디렉토리를 지정합니다.
                    String file = "screenshot.png"; //공유할 이미지 파일 명
                    File files = new File(Environment.getExternalStorageDirectory(), filename);
                    if(!files.exists()) Log.e("ERROE", "ERROR");
                    Uri mSaveImageUri = Uri.fromFile(files); //file의 경로를 uri로 변경합니다.
                    Intent intent = new Intent(Intent.ACTION_SEND); //전송 메소드를 호출합니다. Intent.ACTION_SEND
                    intent.setType("image/*"); //jpg 이미지를 공유 하기 위해 Type을 정의합니다.
                    intent.putExtra(Intent.EXTRA_STREAM, mSaveImageUri); //사진의 Uri를 가지고 옵니다.
                    startActivity(Intent.createChooser(intent, "Choose")); //Activity를 이용하여 호출 합니다.

                }

                return true;
            }
        });

        twitter = new ImageView(getApplicationContext());
        twitter.setBackground(getResources().getDrawable(R.drawable.result_twitter));
        twitter.setLayoutParams(new HangShowLayoutParam(94 * ScreenParameter.getScreenparam_x(), 94 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_TOP, facebook.getId()).addRules(RelativeLayout.RIGHT_OF, facebook.getId()).setMargin(3 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        main.addView(twitter);
        twitter.setId(twitter.hashCode());
        twitter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    SocketService.sendmessage("bye");
                }

                return true;
            }
        });

        complete = new ImageView(getApplicationContext());
        complete.setBackground(getResources().getDrawable(R.drawable.result_complete));
        complete.setLayoutParams(new HangShowLayoutParam(94 * ScreenParameter.getScreenparam_x(), 94 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_TOP, twitter.getId()).addRules(RelativeLayout.RIGHT_OF, twitter.getId()).setMargin(3 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        main.addView(complete);
        complete.setId(complete.hashCode());
        complete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Result.this, Activity_Start.class));
                    finish();
                }

                return true;
            }
        });


        Random rand = new Random();

        switch (numOfShop)
        {
            case 0:
            {

                break;
            }
            case 1:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(105 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                break;
            }
            case 2:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(),Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(380 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                break;
            }
            case 3:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(345 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);
                break;
            }
            case 4:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(265 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(350 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(427 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(510 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                break;
            }
            case 5:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(222 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(310 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(340 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(470 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line4 = new ImageView(getApplicationContext());
                line4.setBackgroundColor(Color.WHITE);
                line4.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(560 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line4);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                eee.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);

                break;
            }
            case 6:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(345 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);


                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line5 = new ImageView(getApplicationContext());
                line5.setBackgroundColor(Color.WHITE);
                line5.setLayoutParams(new HangShowLayoutParam(1 * ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_y()).setMargin(627 * ScreenParameter.getScreenparam_x(), 570 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line5);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(5).getShopName(), infos.get(5).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(5).getType());
                ddd.setMargin(104 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                eee.setMargin(345 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);

                ImageView line4 = new ImageView(getApplicationContext());
                line4.setBackgroundColor(Color.WHITE);
                line4.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line4);

                ResultShop fff = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                fff.setMargin(585 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(fff);

                break;
            }
            case 7:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(265 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(350 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(427 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(510 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);


                ImageView line5 = new ImageView(getApplicationContext());
                line5.setBackgroundColor(Color.WHITE);
                line5.setLayoutParams(new HangShowLayoutParam(1 * ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_y()).setMargin(627 * ScreenParameter.getScreenparam_x(), 570 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line5);

                ResultShop undera = new ResultShop(getApplicationContext(), infos.get(6).getShopName(), infos.get(6).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(6).getType());
                undera.setMargin(104 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(undera);

                ImageView underline1 = new ImageView(getApplicationContext());
                underline1.setBackgroundColor(Color.WHITE);
                underline1.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(underline1);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(5).getShopName(), infos.get(5).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(5).getType());
                eee.setMargin(345 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);

                ImageView underline2 = new ImageView(getApplicationContext());
                underline2.setBackgroundColor(Color.WHITE);
                underline2.setLayoutParams(new HangShowLayoutParam(150 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(underline2);

                ResultShop fff = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                fff.setMargin(585 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(fff);


                break;
            }
            case 8: {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt()) % 2 == 0 ? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt()) % 2 == 0 ? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(265 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(350 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt()) % 2 == 0 ? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(427 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(510 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt()) % 2 == 0 ? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

            }
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(7).getShopName(), infos.get(7).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(7).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(6).getShopName(), infos.get(6).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(6).getType());
                b.setMargin(265 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(350 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(5).getShopName(), infos.get(5).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(5).getType());
                ccc.setMargin(427 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(510 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                ddd.setMargin(585 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line5 = new ImageView(getApplicationContext());
                line5.setBackgroundColor(Color.WHITE);
                line5.setLayoutParams(new HangShowLayoutParam(1 * ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_y()).setMargin(627 * ScreenParameter.getScreenparam_x(), 570 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line5);

                break;
            }
            case 9:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(222 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(310 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(340 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(470 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line4 = new ImageView(getApplicationContext());
                line4.setBackgroundColor(Color.WHITE);
                line4.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(560 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line4);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                eee.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);
            }

            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(8).getShopName(), infos.get(8).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(8).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(7).getShopName(), infos.get(7).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(7).getType());
                b.setMargin(265 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(350 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(6).getShopName(), infos.get(6).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(6).getType());
                ccc.setMargin(427 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(75 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(510 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(5).getShopName(), infos.get(5).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(5).getType());
                ddd.setMargin(585 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line5 = new ImageView(getApplicationContext());
                line5.setBackgroundColor(Color.WHITE);
                line5.setLayoutParams(new HangShowLayoutParam(1 * ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_y()).setMargin(627 * ScreenParameter.getScreenparam_x(), 570 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line5);

                break;
            }
            case 10:
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(0).getShopName(), infos.get(0).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(0).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(1).getShopName(), infos.get(1).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(1).getType());
                b.setMargin(222 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(310 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(2).getShopName(), infos.get(2).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(2).getType());
                ccc.setMargin(340 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(3).getShopName(), infos.get(3).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(3).getType());
                ddd.setMargin(470 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line4 = new ImageView(getApplicationContext());
                line4.setBackgroundColor(Color.WHITE);
                line4.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(560 * ScreenParameter.getScreenparam_x(), 475 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line4);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(4).getShopName(), infos.get(4).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(4).getType());
                eee.setMargin(585 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);
            }
            {
                ResultShop a = new ResultShop(getApplicationContext(), infos.get(9).getShopName(), infos.get(9).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(9).getType());
                a.setMargin(104 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(a);

                ImageView line = new ImageView(getApplicationContext());
                line.setBackgroundColor(Color.WHITE);
                line.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(190 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line);

                ResultShop b = new ResultShop(getApplicationContext(), infos.get(8).getShopName(), infos.get(8).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(8).getType());
                b.setMargin(222 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(b);

                ImageView line2 = new ImageView(getApplicationContext());
                line2.setBackgroundColor(Color.WHITE);
                line2.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(310 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line2);

                ResultShop ccc = new ResultShop(getApplicationContext(), infos.get(7).getShopName(), infos.get(7).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(7).getType());
                ccc.setMargin(340 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ccc);

                ImageView line3 = new ImageView(getApplicationContext());
                line3.setBackgroundColor(Color.WHITE);
                line3.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(430 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line3);

                ResultShop ddd = new ResultShop(getApplicationContext(), infos.get(6).getShopName(), infos.get(6).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(6).getType());
                ddd.setMargin(470 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(ddd);

                ImageView line4 = new ImageView(getApplicationContext());
                line4.setBackgroundColor(Color.WHITE);
                line4.setLayoutParams(new HangShowLayoutParam(30 * ScreenParameter.getScreenparam_x(), 1 * ScreenParameter.getScreenparam_y()).setMargin(560 * ScreenParameter.getScreenparam_x(), 685 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line4);

                ResultShop eee = new ResultShop(getApplicationContext(), infos.get(5).getShopName(), infos.get(5).getSubName(), Math.abs(rand.nextInt())%2 == 0? DbResource.GENDER.WOMAN : DbResource.GENDER.MAN, infos.get(5).getType());
                eee.setMargin(585 * ScreenParameter.getScreenparam_x(), 640 * ScreenParameter.getScreenparam_y(), 0, 0);
                updatedScrollView.addObject(eee);

                ImageView line5 = new ImageView(getApplicationContext());
                line5.setBackgroundColor(Color.WHITE);
                line5.setLayoutParams(new HangShowLayoutParam(1 * ScreenParameter.getScreenparam_x(), 70 * ScreenParameter.getScreenparam_y()).setMargin(627 * ScreenParameter.getScreenparam_x(), 570 * ScreenParameter.getScreenparam_y(), 0, 0));
                updatedScrollView.addObject(line5);
                break;
            }
        }


    }
}
