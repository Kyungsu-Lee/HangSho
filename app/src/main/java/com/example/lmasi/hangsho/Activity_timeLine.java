package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lmasi on 2016-09-25.
 */
public class Activity_timeLine extends Activity {


    RelativeLayout main;
    TouchImageView backBtn;
    TextView title;

    ImageView hangMan;

    Wave topWave;
    Wave aboveWave;
    Wave belowWave;
    Wave bottomWave;

    Shark upShark;
    Shark downShark;

    SpeechBubble speechBubble;

    ImageView timer_bar_left;
    ImageView timer_bar_right;
    ImageView failIcon;

    ImageView loop;

    HangShoTimer timer;

    int numShop;

    ImageView maps;
    ImageView stop;
    ImageView pause;
    ImageView start;

    Handler stateHandler;

    int sumTime;
    int spentTime;

    ArrayList<ShopInfo> infos = new ArrayList<>();

    Timer_Shop_List timer_shop_list;
    TimeLineParam[] timeLineLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


        setContentView(R.layout.testlayout);

        //stopService(new Intent(this, SocketService.class));
       // startService(new Intent(this, SocketService.class));

        main = (RelativeLayout)findViewById(R.id.main);

        numShop = getIntent().getIntExtra("numShop", 0);
        infos = (ArrayList<ShopInfo>)getIntent().getSerializableExtra("LIST");
        if(infos == null)
            infos = new ArrayList<>();
        numShop = infos.size();


        sumTime = getIntent().getIntExtra("TIME", 0);
        spentTime = 0;
        Log.e("INFOS", Boolean.toString(infos == null));

        backBtn = new TouchImageView(getApplicationContext());
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
       // main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(Activity_timeLine.this, Activity_Main.class));
                finish();

                return false;
            }
        });

        hangMan = new ImageView(getApplicationContext());
        hangMan.setBackground(getResources().getDrawable(R.drawable.hangman));
        hangMan.setLayoutParams(new HangShowLayoutParam(93 * ScreenParameter.getScreenparam_x(), 456 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, -2 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(hangMan);
        hangMan.setId(hangMan.hashCode());

        loop = new ImageView(getApplicationContext());
        loop.setBackground(getResources().getDrawable(R.drawable.timer_loop));
        loop.setLayoutParams(new HangShowLayoutParam(9 * ScreenParameter.getScreenparam_x(), 0).addRules(RelativeLayout.ALIGN_PARENT_TOP).setMargin(372* ScreenParameter.getScreenparam_x(), 0, 0, 0));
        main.addView(loop);

        title = new TextView(getApplicationContext());
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("쇼핑 타임라인");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);
        title.setId(title.hashCode());

//        timer_bar_left = new ImageView(getApplicationContext());
//        timer_bar_left.setBackground(getResources().getDrawable(R.drawable.timer_bar_left));
//        timer_bar_left.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 645 * ScreenParameter.getScreenparam_y()).setMargin(78 * ScreenParameter.getScreenparam_x(), 342 * ScreenParameter.getScreenparam_y(), 0, 0));
//        main.addView(timer_bar_left);

        timer_bar_right = new ImageView(getApplicationContext());
        timer_bar_right.setBackground(getResources().getDrawable(R.drawable.timer_bar_right));
        timer_bar_right.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 645 * ScreenParameter.getScreenparam_y()).setMargin(644 * ScreenParameter.getScreenparam_x(), 342 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(timer_bar_right);

        failIcon = new TouchImageView(getApplicationContext());
        failIcon.setBackground(getResources().getDrawable(R.drawable.failicon));
        failIcon.setLayoutParams(new HangShowLayoutParam(111 * ScreenParameter.getScreenparam_x(), 111 * ScreenParameter.getScreenparam_y()).setMargin(602 * ScreenParameter.getScreenparam_x(), 903 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(failIcon);

        maps = new ImageView(getApplicationContext());
        maps.setBackground(getResources().getDrawable(R.drawable.timer_map));
        maps.setLayoutParams(new HangShowLayoutParam(86 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y()).setMargin(607 * ScreenParameter.getScreenparam_x(), 150 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(maps);
        maps.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("geo:0, 0?q=" + DbResource.Location));
                    startActivity(intent);
                }

                return true;
            }
        });

        start = new ImageView(getApplicationContext());
        start.setBackground(getResources().getDrawable(R.drawable.timer_start));
        start.setLayoutParams(new HangShowLayoutParam(86 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y()).setMargin(525 * ScreenParameter.getScreenparam_x(), 150 * ScreenParameter.getScreenparam_y(), 0, 0));
        start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    SocketService.sendmessage("RESTART");
                    main.addView(stop);
                    main.addView(pause);
                    main.removeView(start);
                    timer.resumeTimer();
                }

                return true;
            }
        });

        stop = new ImageView(getApplicationContext());
        stop.setBackground(getResources().getDrawable(R.drawable.timer_stop));
        stop.setLayoutParams(new HangShowLayoutParam(86 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y()).setMargin(525 * ScreenParameter.getScreenparam_x(), 150 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(stop);
        stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    SocketService.sendmessage("STOP");
                    startActivity(new Intent(Activity_timeLine.this, Activity_Result.class).putExtra("misson", true).putExtra("LIST", infos).putExtra("TIME", sumTime).putExtra("SPENT", spentTime));
                    timer.stopTimer();
                    finish();
                }

                return true;
            }
        });

        pause = new ImageView(getApplicationContext());
        pause.setBackground(getResources().getDrawable(R.drawable.timer_pause));
        pause.setLayoutParams(new HangShowLayoutParam(86 * ScreenParameter.getScreenparam_x(), 86 * ScreenParameter.getScreenparam_y()).setMargin(443 * ScreenParameter.getScreenparam_x(), 150 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(pause);
        pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    SocketService.sendmessage("PAUSE");
                    main.removeView(stop);
                    main.removeView(pause);
                    main.addView(start);
                    timer.stopTimer();
                }

                return true;
            }
        });



        timer = new HangShoTimer(getApplicationContext()) {
            @Override
            public void action(int time)
            {
                int down = (int)((300 * ScreenParameter.getScreenparam_y()) / sumTime);
                spentTime++;

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hangMan.getLayoutParams();
                params.topMargin += down * ScreenParameter.getScreenparam_y();
                hangMan.setLayoutParams(params);

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) loop.getLayoutParams();
                params1.height += down * ScreenParameter.getScreenparam_y();
                loop.setLayoutParams(params1);
            }

            @Override
            public void timeOver() {
                startActivity(new Intent(Activity_timeLine.this, Activity_Result.class).putExtra("misson", false).putExtra("LIST", infos).putExtra("TIME", sumTime).putExtra("SPENT", spentTime));
                finish();
            }
        };
        timer.setTextColor(Color.argb(255, 255, 255, 255));
        timer.setTypeface(FontResource.AppleSDGothicNeoB00);
        timer.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        timer.setLayoutParams(new HangShowLayoutParam(103 * ScreenParameter.getScreenparam_x(), 103 * ScreenParameter.getScreenparam_y()).setMargin(598 * ScreenParameter.getScreenparam_x(), 301 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(timer);
        timer.setTime(sumTime);
        timer.startTimer();


        speechBubble = new SpeechBubble(getApplicationContext());
        speechBubble.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, 61 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, hangMan.getId()).setMargin(ScreenParameter.getScreen_x()/2 , 10 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(speechBubble);
        speechBubble.setText("오늘은 안빠지길...");
        speechBubble.setWidth();

        Handler handler = new Handler()
        {
            boolean isOn = true;

            @Override
            public void handleMessage(Message msg) {

                if(isOn)
                {
                    main.removeView(speechBubble);
                }

                else
                {
                    Random rand = new Random();
                    speechBubble.setText(Resources.speechBubble[Math.abs(rand.nextInt()) % Resources.speechBubble.length]);
                    speechBubble.setWidth();
                    main.addView(speechBubble);
                }

                isOn = !isOn;

                sendEmptyMessageDelayed(0, 5000);
            }
        };
        handler.sendEmptyMessage(0);

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


        Log.e("NUMSHOP", Integer.toString(numShop));




        final ArrayList<TimeLinePlace> timeLinePlaces = new ArrayList<>();
        for(int i=0; i<infos.size(); i++) {
            Random random = new Random();
            TimeLinePlace timeline = new TimeLinePlace(getApplicationContext())
            {
                @Override
                public void clickDelete(View view, View child) {
                    super.clickDelete(view, child);

                    timer_shop_list.removeAllViews();
                    main.removeView(timer_shop_list);
                    timer_shop_list = null;
                    timeLinePlaces.remove(timeLinePlaces.indexOf(child));

                    for(int i=0; i<infos.size(); i++)
                    {
                        if(infos.get(i).equals(new ShopInfo(((TimeLinePlace)child).getType(), ((TimeLinePlace)child).getshopName(), ((TimeLinePlace)child).getSubName())))
                            infos.remove(infos.get(i));
                    }

                    timer_shop_list = new Timer_Shop_List(getApplicationContext(), timeLinePlaces);
                    Log.e("///", Integer.toString(infos.size()));
                    main.addView(timer_shop_list);
                    timer_shop_list.invalidate();
                }

                @Override
                public void clickShirt(View view) {
                    super.clickShirt(view);

                    startActivity(new Intent(Activity_timeLine.this, Activity_ShowCodi.class).putExtra("LIST",infos).putExtra("index", timeLinePlaces.indexOf(view)));
                }
            };
            timeline.setName(infos.get(i).getShopName());
            timeline.setSubName(infos.get(i).getSubName());
            timeline.setType(infos.get(i).getType());
            timeline.setIcon(infos.get(i).getType().equals("shop") ?  (Math.abs(random.nextInt())%2 == 0 ? R.drawable.timer_girlshop : R.drawable.timer_boyshop) : (Math.abs(random.nextInt())%2 == 0 ? R.drawable.timer_girleye : R.drawable.timer_boyeye));
            timeLinePlaces.add(timeline);

        }
        timer_shop_list = new Timer_Shop_List(getApplicationContext(), timeLinePlaces);
        main.addView(timer_shop_list);

        stateHandler = new Handler()
        {

            @Override
            public void handleMessage(Message msg) {
               if(DbResource.pause)
               {
                   DbResource.pause = false;
                   main.removeView(stop);
                   main.removeView(pause);
                   main.addView(start);
                   timer.stopTimer();
               }
                if(DbResource.stop)
                {
                    DbResource.stop = false;
                    startActivity(new Intent(Activity_timeLine.this, Activity_Result.class).putExtra("misson", true).putExtra("LIST", infos).putExtra("TIME", sumTime).putExtra("SPENT", spentTime));
                    timer.stopTimer();
                    finish();
                }
                if(DbResource.restart)
                {
                    DbResource.restart = false;
                    main.addView(stop);
                    main.addView(pause);
                    main.removeView(start);
                    timer.resumeTimer();
                }

                sendEmptyMessageDelayed(0, 300);
            }
        };
        stateHandler.sendEmptyMessage(0);

    }

    @Override
    public void onBackPressed() {
    }
}
