package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class Activity_Start extends Activity {

    RelativeLayout main;
    TouchImageView logo;

    boolean isLogged;

    String ID;
    String passWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        main = (RelativeLayout)findViewById(R.id.main);

        ScreenParameter.setScreenparam_x(ScreenSize().x/ScreenParameter.getDefaultsizeX());
        ScreenParameter.setScreenparam_y(ScreenSize().y/ScreenParameter.getDefaultsizeY());
        ScreenParameter.setScreen_x(ScreenSize().x);
        ScreenParameter.setScreen_y(ScreenSize().y);

        FontResource.AppleSDGothicNeoB00 = Typeface.createFromAsset(getAssets(), "AppleSDGothicNeoB00.ttf");
        FontResource.AppleSDGothicNeoR00 = Typeface.createFromAsset(getAssets(), "AppleSDGothicNeoR00.ttf");
        FontResource.GothamRounded_Book = Typeface.createFromAsset(getAssets(), "GothamRounded_Book.ttf");
        FontResource.GothamRounded_Light = Typeface.createFromAsset(getAssets(), "GothamRounded_Light.ttf");
        FontResource.GothamRounded_Medium = Typeface.createFromAsset(getAssets(), "GothamRounded_Medium.ttf");
        FontResource.GothamRounded_Medium_2 = Typeface.createFromAsset(getAssets(), "GothamRounded_Medium_2.ttf");


        try {
            logo = new TouchImageView(this);
            logo.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            logo.setSize(315 * ScreenParameter.getScreenparam_x(), 252 * ScreenParameter.getScreenparam_y());
            logo.setLocation(ScreenParameter.getScreen_x() / 2 - 315 * ScreenParameter.getScreenparam_x() / 2, ScreenParameter.getScreen_y() / 2 - 252 * ScreenParameter.getScreenparam_y() / 2);
            logo.setBackground(R.drawable.login_logo);
            main.addView(logo);
        }
        catch (OutOfMemoryError e)
        {
            Log.e("OOM", e.toString());
            return;
        }

        try
        {
            DbResource.conn = new DBConect(this, "HangSho.db", null, 1);

            DbResource.db = DbResource.conn.getWritableDatabase();


            //DbResource.db.execSQL("delete from login_info");
            //DbResource.db.execSQL("delete from PersonalData");
            //DbResource.db.execSQL("delete from HistoryCard");
            //DbResource.db.execSQL("delete from History");

            //DbResource.db.execSQL("insert into login_info values ('lmasi@naver.com', 'passwd', 1);");
            //DbResource.db.execSQL("insert into PersonalData values ('lmasi@naver.com', '21400509@handong.edu' , '박보검', '조현영', '1994.08.16','1994.11.25' , 0, '경로', '01056691533', '01090379325');");
        }
        catch (Exception e)
        {
            Log.d("SQLEXCEPTION", e.toString());
        }
        finally
        {
            Cursor c = DbResource.db.rawQuery("select * from login_info", null);

            try
            {
                while(c.moveToNext())
                {
                    isLogged = (c.getInt(c.getColumnIndex("isLogged")) == 1);

                    if(isLogged)
                    {
                        DbResource.ID = c.getString(c.getColumnIndex("ID"));
                        DbResource.PassWd = c.getString(c.getColumnIndex("PassWd"));

                        Cursor info = DbResource.db.rawQuery("select * from PersonalData where id='" + c.getString(c.getColumnIndex("ID")) + "';", null);

                        while(info.moveToNext())
                        {
                            Log.e("asdasdasd", "asdasdasda");

                            DbResource.UserName = info.getString(info.getColumnIndex("UserName"));
                            DbResource.PartnerID = info.getString(info.getColumnIndex("PartnerID"));
                            DbResource.PartnerName = info.getString(info.getColumnIndex("PartnerName"));
                            DbResource.Birthday = info.getString(info.getColumnIndex("Birthday"));
                            DbResource.PartnerBirthday = info.getString(info.getColumnIndex("PartnerBirthday"));
                            DbResource.Gender = info.getString(info.getColumnIndex("Gender")).equals("0") ? DbResource.Gender.MAN : DbResource.Gender.WOMAN;
                            DbResource.myPhoneNum = info.getString(info.getColumnIndex("myPhoneNum"));
                            DbResource.partnerPhoneNum = info.getString(info.getColumnIndex("partnerPhoneNum"));

                            try{
                                String imgpath = "data/data/com.example.lmasi.hangsho/files/" + DbResource.PassWd + ".png";
                                Bitmap bm = BitmapFactory.decodeFile(imgpath);
                                DbResource.profile = bm;
                            }
                            catch(Exception e)
                            {
                            }
                        }

                        DbResource.Loggin = true;
                        break;
                    }
                }

            }
            catch (Exception e)
            {
                Log.e("ERROR", e.toString());

                Toast.makeText(getApplicationContext(),"Cannot check log-in info", Toast.LENGTH_SHORT).show();

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 200);
            }

//            try
//            {
//                //////
//                ////
//                /////
//                ////
//                //
//                File f = new File("a.png");
//                FileInputStream fileInputStream = openFileInput("super.png");
//                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
//
//
//                for(int i=0; i<1000; i++)
//                    Log.e("///////", "/..........");
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//            }


            if(isLogged)
            {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Activity_Start.this, Activity_Main.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);   /// intent to main-page;
            }

            else
            {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Activity_Start.this, Activity_Login.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1500);   /// intent to login page
            }
        }




    }

    @Override
    protected void onDestroy() {

//        for(int i=0; i<main.getChildCount(); i++)
//            if(main.getChildAt(i) instanceof TouchImageView && main.getChildAt(i) != null)
//                ((TouchImageView) main.getChildAt(i)).recycle();

        main.removeView(logo);
        main = null;
        logo = null;

        Log.e("Destroy LOG", "WLEEDONE");

        super.onDestroy();
    }

    public Point ScreenSize() { //현재 스크린의 사이즈를 가져오는 메서드. 정형화된 틀이다.

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        Point size = new Point(width, height);

        return size;

    }

    public int narrowSize() {
        int x = ScreenSize().x; //현재 스크린의 사이즈; 가로, 세로 화면.
        int y = ScreenSize().y;

        return (x > y ? y : x);
    }

    public int wideSize() {
        int x = ScreenSize().x;
        int y = ScreenSize().y;

        return (x < y ? y : x);
    }
}
