package com.example.lmasi.hangsho;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-09-18.
 */
public class Activity_shopList extends Activity {

    ArrayList<ShopInfo> infos;
    ArrayList<ShopInfo> infos_other = new ArrayList<>();

    RelativeLayout main;

    TouchImageView backBtn;

    TextView title;

    RelativeLayout maps;
    ImageView map;
    ImageView menu;
    TextView textView;

    TouchImageView profile_man;
    TouchImageView profile_woman;
    TouchImageView profile_cover_man;
    TouchImageView progile_cover_woman;

    TouchImageView toNext;
    TouchImageView toNextPage;

    TextView numOfShopMan;
    TextView numOfShopWoman;
    
    ScrollView scrollView;
    RelativeLayout scrollLayout;

    TextView[] manShopNames;
    RelativeLayout manShop;


    ScrollView scrollView_woman;
    RelativeLayout scrollLayout_woman;
    Handler handler;

    TextView[] womanShopNames;
    RelativeLayout womanShop;

    int numOfShops = 0;
    ShopDot[] shopDot;

    ArrayList<Point> shopListLocation = new ArrayList<>();

    String[] womanShopName;
    String[] womanShopSubName;
    String[] womanShopType;

    ImageView checkBox;

    ProgressDialog progressDialog;

    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    Thread work;
    Thread work2;

    PmTimePicker timePicker;

    TextView myTime;
    TextView partnerTime;

    TextView mT;
    TextView pT;

    String partTime;

    Handler partnertimnechecker;
    Handler sumTimeHandler;

    TextView sum;
    int sumTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoplist);


        progressDialog = ProgressDialog.show(Activity_shopList.this, "", "잠시만 기다려주세요.", true);

        main = (RelativeLayout)findViewById(R.id.main);
        infos = (ArrayList<ShopInfo>) getIntent().getSerializableExtra("LIST");


        work = new Thread() {

            public void run() {
                try {
                    socket = new Socket(DbResource.IPADDR, DbResource.port);
                    socket_out = new PrintWriter(socket.getOutputStream(), true);
                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e("SOCKET", "SUCC");

                } catch (IOException e) {
                    Log.e("ERROR", e.toString());
                }

                try {

                    socket_out.println("SHOPLIST");

                    socket_out.println(infos.size());

                    String check;

                    while(true) {
                        check = socket_in.readLine();

                        if(check.equals("WELL"))
                            break;
                        else
                            finish();

                    }

                    Log.e("WELL", check);


                    for(int i=0; i<infos.size(); i++)
                    {
                        socket_out.println(infos.get(i).getShopName());
                        socket_out.println(infos.get(i).getSubName());
                        socket_out.println(infos.get(i).getType());
                    }

                    int numOfOthers = -1;

                    while(true)
                    {
                        numOfOthers = Integer.parseInt(socket_in.readLine());
                        Log.e("NUMBER OF OTHER SHOP", Integer.toString(numOfOthers));

                        if(numOfOthers != -1)
                            break;
                    }

                    womanShopName = new String [numOfOthers];
                    womanShopSubName = new String[numOfOthers];
                    womanShopType = new String[numOfOthers];
                    for(int i=0; i<numOfOthers; i++)
                    {
                        while(womanShopName[i] == null)
                        {
                            womanShopName[i] = socket_in.readLine();
                            womanShopSubName[i] = socket_in.readLine();
                            womanShopType[i] = socket_in.readLine();
                        }
                    }

                    while(true)
                    {
                        String flag = socket_in.readLine();

                        if(flag.equals("SUCC"))
                        {
                            //progressDialog.dismiss();
                            progressDialog.cancel();
                            Thread.sleep(200);
                            handler.sendEmptyMessage(0);
                            break;
                        }
                    }

                } catch (Exception e) {
                }
            }
        };

        work2 = new Thread() {

            public void run() {
                try {
                    socket = new Socket(DbResource.IPADDR, DbResource.port);
                    socket_out = new PrintWriter(socket.getOutputStream(), true);
                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e("SOCKET", "SUCC");

                } catch (IOException e) {
                    Log.e("ERROR", e.toString());
                }

                try {

                    socket_out.println("TIME");

                    String check;

                    while(true) {
                        check = socket_in.readLine();

                        if(check.equals("WELL"))
                            break;
                        else
                            finish();

                    }

                    Log.e("WELL", check);


                    socket_out.println(mT.getText().toString());

                    String str;
                    while(true) {
                       str  = socket_in.readLine();
                        Log.e("STRLOG", str);

                        if(str.substring(6).equals("분"))
                            break;
                    }

                    while(true)
                    {
                        String flag = socket_in.readLine();

                        if(flag.equals("SUCC"))
                        {
                            //progressDialog.dismiss();
                            progressDialog.cancel();
                            Thread.sleep(200);
                            partTime = str;
                            partnertimnechecker.sendEmptyMessage(0);
                           // handler.sendEmptyMessage(0);
                            break;
                        }
                    }

                } catch (Exception e) {
                }
            }
        };

        work.start();

        sumTimeHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                int m = Integer.parseInt(pT.getText().toString().substring(0,2)) * 60 + Integer.parseInt(pT.getText().toString().substring(4,6));
                int w = Integer.parseInt(mT.getText().toString().substring(0,2)) * 60 + Integer.parseInt(mT.getText().toString().substring(4,6));

                sumTime = (m+w)/2;

                sum.setText("오늘의 최종 예상 쇼핑시간은 " + sumTime/60 + "시간 " + sumTime%60 + "분입니다.");
                textView.setText("최종 예상 쇼핑시간이 상대방과 동기화되어 계산되어집니다\n" +
                        "(내가 원하는 시간 + 상대방이 원하는 시간 ÷2)");
                textView.setGravity(Gravity.CENTER);
                main.addView(sum);
                main.removeView(pT);
                main.removeView(mT);
            }
        };

        partnertimnechecker = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                pT.setText(partTime);
                pT.invalidate();
                main.addView(pT);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        sumTimeHandler.sendEmptyMessage(0);
                    }
                }, 1000);
            }
        };

        backBtn = new TouchImageView(getApplicationContext());
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(Activity_shopList.this, Activity_ChooseStore.class));
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
        main.addView(title);

        map = new ImageView(getApplicationContext());
        map.setLayoutParams(new HangShowLayoutParam(696 * ScreenParameter.getScreenparam_x(), 454 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 174 * ScreenParameter.getScreenparam_y(), 0, 0));
        map.setBackground(getResources().getDrawable(R.drawable.time_map));
        main.addView(map);

        maps = new RelativeLayout(getApplicationContext());
        maps.setLayoutParams(new HangShowLayoutParam(696 * ScreenParameter.getScreenparam_x(), 454 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 174 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(maps);


//        menu = new ImageView(getApplicationContext());
//        menu.setLayoutParams(new HangShowLayoutParam(99 * ScreenParameter.getScreenparam_x(), 99 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 675 * ScreenParameter.getScreenparam_y(), 0, 0));
//        menu.setBackground(getResources().getDrawable(R.drawable.time_icon2));
//        main.addView(menu);

        textView = new TextView(getApplicationContext());
        textView.setText("본인과 상대방의 매장리스트가 보여집니다");
        textView.setTextColor(Color.argb(255, 255, 255, 255));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        textView.setTypeface(FontResource.AppleSDGothicNeoB00);
        textView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 730 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(textView);
        textView.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), (int)(15 * ScreenParameter.getScreenparam_y()), (int)(20 * ScreenParameter.getScreenparam_x()), (int)(15 * ScreenParameter.getScreenparam_y()));
        textView.setBackground(getResources().getDrawable(R.drawable.back));
        textView.setId(textView.hashCode());

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.gum_1);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmap = getRoundedBitmap(bitmap);
        bitmapDrawable = new BitmapDrawable(bitmap);

        profile_man = new TouchImageView(getApplicationContext());
        profile_man.setBackground(bitmapDrawable);
        profile_man.setSize(156 * ScreenParameter.getScreenparam_x(), 157 * ScreenParameter.getScreenparam_y());
        profile_man.setLocation(110 * ScreenParameter.getScreenparam_x(), 885 * ScreenParameter.getScreenparam_y());
        profile_man.setId(profile_man.hashCode());
        main.addView(profile_man);

        profile_cover_man = new TouchImageView(getApplicationContext());
        profile_cover_man.setBackground(R.drawable.home_photoline);
        profile_cover_man.setSize(156 * ScreenParameter.getScreenparam_x(), 157 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setLocation(110 * ScreenParameter.getScreenparam_x(), 885 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setId(profile_cover_man.hashCode());
        main.addView(profile_cover_man);

        BitmapDrawable bitmapDrawable2 = (BitmapDrawable)getResources().getDrawable(R.drawable.irine_1);
        Bitmap bitmap2 = bitmapDrawable2.getBitmap();
        bitmap2 = getRoundedBitmap(bitmap2);
        bitmapDrawable2 = new BitmapDrawable(bitmap2);
//
        profile_woman = new TouchImageView(getApplicationContext());
        profile_woman.setBackground(bitmapDrawable2);
        profile_woman.setSize(156 * ScreenParameter.getScreenparam_x(), 157 * ScreenParameter.getScreenparam_y());
        profile_woman.addRule(RelativeLayout.ALIGN_TOP, profile_man.getId());
        profile_woman.setLocation(480 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
        profile_woman.setId(profile_woman.hashCode());
        main.addView(profile_woman);

        progile_cover_woman = new TouchImageView(getApplicationContext());
        progile_cover_woman.setBackground(R.drawable.home_photoline);
        progile_cover_woman.setSize(156 * ScreenParameter.getScreenparam_x(), 157 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.addRule(RelativeLayout.ALIGN_TOP, profile_man.getId());
        progile_cover_woman.setLocation(480 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.setId(progile_cover_woman.hashCode());
        main.addView(progile_cover_woman);


        numOfShopMan = new TextView(getApplicationContext());
        numOfShopMan.setText("선택 매장 갯수 : " + (infos.size()) + "개");
        numOfShopMan.setTextColor(Color.argb(255, 255, 255, 255));
        numOfShopMan.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        numOfShopMan.setTypeface(FontResource.AppleSDGothicNeoB00);
        numOfShopMan.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, profile_cover_man.getId()).setMargin(0, 1055 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(numOfShopMan);
        numOfShopMan.setId(numOfShopMan.hashCode());


        ///man
        scrollView = new HangShoScrollView(getApplicationContext());
        scrollView.setLayoutParams(new HangShowLayoutParam(375 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.BELOW, numOfShopMan.getId()).setMargin(0 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y(), 0, 30 * ScreenParameter.getScreenparam_y()));
        main.addView(scrollView);
        scrollLayout = new RelativeLayout(getApplicationContext());

        manShop = new RelativeLayout(getApplicationContext());
        manShop.setLayoutParams(new HangShowLayoutParam(156 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        scrollLayout.addView(manShop);
        manShopNames = new TextView[infos.size()];
        for(int i=0; i<manShopNames.length; i++)
        {
            manShopNames[i] = new TextView(getApplicationContext());
            manShopNames[i].setText(infos.get(i).getShopName());
            manShopNames[i].setTextColor(Color.argb(255, 255, 255, 255));
            manShopNames[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
            manShopNames[i].setTypeface(FontResource.AppleSDGothicNeoB00);
            manShopNames[i].setId(manShopNames[i].hashCode());
            if(i != 0)
                manShopNames[i].setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.BELOW, manShopNames[i-1].hashCode()).setMargin(0, 10 * ScreenParameter.getScreenparam_y(), 0, 0));
            else
                manShopNames[i].setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 0 * ScreenParameter.getScreenparam_y(), 0, 0));
            manShop.addView(manShopNames[i]);
        }

        scrollView.addView(scrollLayout);
        scrollView.setVerticalScrollBarEnabled(false);


        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {



                if (infos == null) {
                    infos = new ArrayList<>();
                }

                numOfShops = infos.size() + womanShopName.length;

                for(int i=0; i<numOfShops; i++)
                    shopListLocation.add(new Point((int)(Resources.shopDotLocation[i].x * ScreenParameter.getScreenparam_x()), ((int)(Resources.shopDotLocation[i].y * ScreenParameter.getScreenparam_y()))));

                for(int i=0; i<womanShopType.length; i++)
                {
                    infos.add(new ShopInfo(womanShopType[i], womanShopName[i], womanShopSubName[i]));
                }

                timePicker = (PmTimePicker)findViewById(R.id.timepicker);
                timePicker.setIs24HourView(true);
                timePicker.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 780 * ScreenParameter.getScreenparam_y(), 0, 0));
                main.removeView(timePicker);
                timePicker.setCurrentHour(0);
                timePicker.setCurrentMinute(0);


                toNext = new TouchImageView(getApplicationContext());
                toNext.setSize(27 * ScreenParameter.getScreenparam_x(), 37 * ScreenParameter.getScreenparam_y());
                toNext.setLocation(685 * ScreenParameter.getScreenparam_x(), 944 * ScreenParameter.getScreenparam_y());
                toNext.setBackground(R.drawable.time_next);
                main.addView(toNext);
                toNext.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        {
                            main.addView(timePicker);
                            main.addView(checkBox);
                            main.removeView(profile_cover_man);
                            main.removeView(profile_man);
                            main.removeView(profile_woman);
                            main.removeView(progile_cover_woman);
                            main.removeView(scrollView);
                            main.removeView(scrollView_woman);
                            main.removeView(toNext);
                            main.removeView(numOfShopMan);
                            main.removeView(numOfShopWoman);
                        }

                        return true;
                    }
                });

                toNextPage = new TouchImageView(getApplicationContext());
                toNextPage.setSize(27 * ScreenParameter.getScreenparam_x(), 37 * ScreenParameter.getScreenparam_y());
                toNextPage.setLocation(685 * ScreenParameter.getScreenparam_x(), 944 * ScreenParameter.getScreenparam_y());
                toNextPage.setBackground(R.drawable.time_next);
                //main.addView(toNextPage);
                toNextPage.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        {
                            SocketService.sendmessage("LSI");
                            Log.e("NUMBER", Integer.toString(infos.size()));
                            startActivity(new Intent(Activity_shopList.this, Activity_ChooseCoupons.class).putExtra("LIST",infos).putExtra("TIME", sumTime));
                            finish();
                        }

                        return true;
                    }
                });

                Handler LSI = new Handler()
                {
                    boolean check = true;

                    @Override
                    public void handleMessage(Message msg) {
                        if(DbResource.LSI && check)
                        {
                            Log.e("NUMBER", Integer.toString(infos.size()));
                            startActivity(new Intent(Activity_shopList.this, Activity_ChooseCoupons.class).putExtra("LIST",infos).putExtra("TIME", sumTime));
                            check = false;
                            DbResource.LSI = false;
                            finish();
                        }

                        sendEmptyMessageDelayed(0, 100);
                    }
                };
                LSI.sendEmptyMessage(0);

                partnerTime = new TextView(getApplicationContext());
                partnerTime.setText("내가 원하는 쇼핑시간");
                partnerTime.setTextColor(Color.argb(255, 255, 255, 255));
                partnerTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(15 * ScreenParameter.getScreenparam_y()));
                partnerTime.setTypeface(FontResource.AppleSDGothicNeoB00);
                partnerTime.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, profile_cover_man.getId()).setMargin(20 * ScreenParameter.getScreenparam_x(), 1055 * ScreenParameter.getScreenparam_y(), 0, 0));
                //main.addView(numOfShopMan);
                partnerTime.setId(partnerTime.hashCode());


                myTime = new TextView(getApplicationContext());
                myTime.setText("상대방이 원하는 쇼핑시간");
                myTime.setTextColor(Color.argb(255, 255, 255, 255));
                myTime.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(15 * ScreenParameter.getScreenparam_y()));
                myTime.setTypeface(FontResource.AppleSDGothicNeoB00);
                myTime.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, progile_cover_woman.getId()).setMargin(20 * ScreenParameter.getScreenparam_x(), 1055 * ScreenParameter.getScreenparam_y(), 0, 0));
                //main.addView(myTime);
                myTime.setId(myTime.hashCode());

                mT = new TextView(getApplicationContext());
                mT.setText("");
                mT.setTextColor(Color.argb(255, 255, 255, 255));
                mT.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(40 * ScreenParameter.getScreenparam_y()));
                mT.setTypeface(FontResource.AppleSDGothicNeoB00);
                mT.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, profile_cover_man.getId()).setMargin(-20 * ScreenParameter.getScreenparam_x(), 1140 * ScreenParameter.getScreenparam_y(), 0, 0));
                //main.addView(myTime);
                mT.setId(mT.hashCode());

                sum = new TextView(getApplicationContext());
                sum.setText("");
                sum.setTextColor(Color.argb(255, 255, 255, 255));
                sum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
                sum.setTypeface(FontResource.AppleSDGothicNeoB00);
                sum.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(-20 * ScreenParameter.getScreenparam_x(), 1140 * ScreenParameter.getScreenparam_y(), 0, 0));
                //main.addView(myTime);
                sum.setId(sum.hashCode());

                pT = new TextView(getApplicationContext());
                pT.setText("");
                pT.setTextColor(Color.argb(255, 255, 255, 255));
                pT.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(40 * ScreenParameter.getScreenparam_y()));
                pT.setTypeface(FontResource.AppleSDGothicNeoB00);
                pT.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, progile_cover_woman.getId()).setMargin(-20 * ScreenParameter.getScreenparam_x(), 1140 * ScreenParameter.getScreenparam_y(), 0, 0));
                //main.addView(myTime);
                pT.setId(pT.hashCode());

                numOfShopWoman = new TextView(getApplicationContext());
                numOfShopWoman.setText("선택 매장 갯수 : " + (womanShopName.length) + "개");
                numOfShopWoman.setTextColor(Color.argb(255, 255, 255, 255));
                numOfShopWoman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
                numOfShopWoman.setTypeface(FontResource.AppleSDGothicNeoB00);
                numOfShopWoman.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.ALIGN_LEFT, progile_cover_woman.getId()).setMargin(0, 1055 * ScreenParameter.getScreenparam_y(), 0, 0));
                main.addView(numOfShopWoman);
                numOfShopWoman.setId(numOfShopWoman.hashCode());

                checkBox = new ImageView(getApplicationContext());
                checkBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
                checkBox.setBackground(getResources().getDrawable(R.drawable.login_button));
                //main.addView(checkBox);
                checkBox.setId(checkBox.hashCode());
                checkBox.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        {
                            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
                            nf.setMinimumIntegerDigits(2);

                            mT.setText(nf.format(timePicker.getCurrentHour().intValue()) + "시간" + nf.format(timePicker.getCurrentMinute()) + "분");

                            main.addView(toNextPage);
                            main.addView(profile_cover_man);
                            main.addView(profile_man);
                            main.addView(profile_woman);
                            main.addView(progile_cover_woman);
                            main.removeView(timePicker);
                            main.addView(myTime);
                            main.addView(partnerTime);
                            main.addView(mT);
                            main.removeView(checkBox);

                            progressDialog = ProgressDialog.show(Activity_shopList.this, "", "잠시만 기다려주세요.", true);
                            work2.start();
                        }

                        return true;
                    }
                });



                ///woman
                scrollView_woman = new HangShoScrollView(getApplicationContext());
                scrollView_woman.setLayoutParams(new HangShowLayoutParam(375 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.BELOW, numOfShopWoman.getId()).setMargin(375 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y(), 0, 30 * ScreenParameter.getScreenparam_y()));
                main.addView(scrollView_woman);
                scrollLayout_woman = new RelativeLayout(getApplicationContext());

                womanShop = new RelativeLayout(getApplicationContext());
                womanShop.setLayoutParams(new HangShowLayoutParam(156 * ScreenParameter.getScreenparam_x(), ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
                scrollLayout_woman.addView(womanShop);
                womanShopNames = new TextView[womanShopName.length];
                for(int i=0; i<womanShopName.length; i++)
                {
                    womanShopNames[i] = new TextView(getApplicationContext());
                    womanShopNames[i].setText(womanShopName[i]);
                    womanShopNames[i].setTextColor(Color.argb(255, 255, 255, 255));
                    womanShopNames[i].setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(28 * ScreenParameter.getScreenparam_y()));
                    womanShopNames[i].setTypeface(FontResource.AppleSDGothicNeoB00);
                    womanShopNames[i].setId(womanShopNames[i].hashCode());
                    if(i != 0)
                        womanShopNames[i].setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.BELOW, womanShopNames[i-1].hashCode()).setMargin(0, 10 * ScreenParameter.getScreenparam_y(), 0, 0));
                    else
                        womanShopNames[i].setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).setMargin(0, 0 * ScreenParameter.getScreenparam_y(), 0, 0));
                    womanShop.addView(womanShopNames[i]);
                }

                scrollView_woman.addView(scrollLayout_woman);
                scrollView_woman.setVerticalScrollBarEnabled(false);

                shopDot = new ShopDot[numOfShops];
                for(int i=0; i<numOfShops; i++)
                {
                    Random random = new Random();
                    int num = Math.abs(random.nextInt(1000)) % shopListLocation.size();
                    Point location = shopListLocation.get(num);
                    shopListLocation.remove(num);

                    shopDot[i] = new ShopDot(getApplicationContext(), Math.abs(random.nextInt()) % 2);
                    shopDot[i].setLayoutParams(new HangShowLayoutParam(52 * ScreenParameter.getScreenparam_x(), 52 * ScreenParameter.getScreenparam_y()).setMargin(location.x, location.y, 0, 0));
                    maps.addView(shopDot[i]);

                }

                if(numOfShops > 2)
                {
                    final ImageView arrow = new ImageView(getApplicationContext());
                    arrow.setLayoutParams(new HangShowLayoutParam(427 * ScreenParameter.getScreenparam_x(), 190 * ScreenParameter.getScreenparam_y()).setMargin(103 * ScreenParameter.getScreenparam_x(), 109 * ScreenParameter.getScreenparam_y(), 0, 0));
                    maps.addView(arrow);
                    Handler handler = new Handler()
                    {
                        int i = R.drawable.arrow1_01 - R.drawable.arrow1_62;

                        @Override
                        public void handleMessage(Message msg) {
                            if(i <= 0)
                            {
                                arrow.setBackground(getResources().getDrawable(R.drawable.arrow1_01 - i));
                            }

                            i++;

                            sendEmptyMessageDelayed(0, 1);
                        }
                    };
                    handler.sendEmptyMessage(0);
                }
                else if(numOfShops == 2)
                {
                    final ImageView arrow = new ImageView(getApplicationContext());
                    arrow.setLayoutParams(new HangShowLayoutParam(40 * ScreenParameter.getScreenparam_x(), 101 * ScreenParameter.getScreenparam_y()).setMargin(95 * ScreenParameter.getScreenparam_x(), 123 * ScreenParameter.getScreenparam_y(), 0, 0));
                    maps.addView(arrow);
                    Handler handler = new Handler()
                    {
                        int i = R.drawable.arrow3_1 - R.drawable.arrow3_9;
                        int time = 0;

                        @Override
                        public void handleMessage(Message msg) {
                            if(time > 500) {
                                if (i <= 0) {
                                    arrow.setBackground(getResources().getDrawable(R.drawable.arrow3_1 - i));
                                }

                                i++;
                            }

                            time++;
                            sendEmptyMessageDelayed(0, 1);

                        }
                    };
                    handler.sendEmptyMessage(0);
                }
            }
        };



    }

    public static Bitmap getRoundedBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.GRAY;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }
}
