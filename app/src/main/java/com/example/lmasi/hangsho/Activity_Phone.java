package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by lmasi on 2016-09-26.
 */
public class Activity_Phone extends Activity {

    RelativeLayout main;
    TouchImageView cloasBtn;
    onTextView textView;

    RelativeLayout back_box;
    TouchImageView profile_circle;

    RelativeLayout mine;
    RelativeLayout partner;

    TextView myPhoneNum;
    TextView otherPhoneNum;

    ImageView line1;
    ImageView line2;

    EditText myNum;
    EditText partnerNum;

    TextView explain;
    TextView chcekBox;

    String[] gets;

    boolean serverCheck = false;

    private Socket socket;
    BufferedReader socket_in;
    PrintWriter socket_out;
    Thread work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        cloasBtn = new TouchImageView(this);
        cloasBtn.setBackground(R.drawable.login_xicon);
        cloasBtn.setSize(32 * ScreenParameter.getScreenparam_x(), 32 * ScreenParameter.getScreenparam_y());
        cloasBtn.setLocation(43 * ScreenParameter.getScreenparam_x(), 42 * ScreenParameter.getScreenparam_y());
        cloasBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                finish();

                return false;
            }
        });
        main.addView(cloasBtn);


        gets = getIntent().getStringArrayExtra("ID");

        textView = new onTextView(this);
        textView.setLocation(93 * ScreenParameter.getScreenparam_x(), 37 * ScreenParameter.getScreenparam_y());
        textView.setText(" 상대방과 연결하기");
        textView.setTextColor(Color.rgb(34, 30 , 31));
        textView.setTypeface(FontResource.AppleSDGothicNeoB00);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        main.addView(textView);
        textView.setId(textView.hashCode());



        back_box = new RelativeLayout(this);
        back_box.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 445 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, textView.getId()).setMargin(0, 15 * ScreenParameter.getScreenparam_y(), 0, 0));
        back_box.setBackground(getResources().getDrawable(R.drawable.grad));
        main.addView(back_box);
        back_box.setId(back_box.hashCode());

        profile_circle = new TouchImageView(this);
        profile_circle.setSize(199 * ScreenParameter.getScreenparam_x(), 199 * ScreenParameter.getScreenparam_y());
        profile_circle.setLocation(275 * ScreenParameter.getScreenparam_x(), 126 * ScreenParameter.getScreenparam_y());
        ( (RelativeLayout.LayoutParams)profile_circle.getLayoutParams()).addRule(RelativeLayout.CENTER_IN_PARENT);
        BitmapDrawable drawable = new BitmapDrawable(DbResource.profile);
        profile_circle.setBackground(drawable);
        back_box.addView(profile_circle);

        mine = new RelativeLayout(getApplicationContext());
        mine.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 158 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, back_box.getId()));
        main.addView(mine);
        mine.setId(mine.hashCode());

        line1 = new ImageView(getApplicationContext());
        line1.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 2 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        line1.setBackgroundColor(Color.argb(153, 198, 198, 197));
        mine.addView(line1);

        partner = new RelativeLayout(getApplicationContext());
        partner.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 158 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, mine.getId()));
        main.addView(partner);
        partner.setId(partner.hashCode());

        line2 = new ImageView(getApplicationContext());
        line2.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 2 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        line2.setBackgroundColor(Color.argb(153, 198, 198, 197));
        partner.addView(line2);

        myPhoneNum = new TextView(getApplicationContext());
        myPhoneNum.setText("내 전화번호");
        myPhoneNum.setTextColor(Color.argb(255, 126, 145, 201));
        myPhoneNum.setTypeface(FontResource.AppleSDGothicNeoB00);
        myPhoneNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(29 * ScreenParameter.getScreenparam_y()));
        myPhoneNum.setGravity(Gravity.CENTER);
        myPhoneNum.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).setMargin(50 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y(), 0, 0));
        mine.addView(myPhoneNum);
        myPhoneNum.setId(myPhoneNum.hashCode());

        otherPhoneNum = new TextView(getApplicationContext());
        otherPhoneNum.setText("소중한 분의 전화번호");
        otherPhoneNum.setTextColor(Color.argb(255, 126, 145, 201));
        otherPhoneNum.setTypeface(FontResource.AppleSDGothicNeoB00);
        otherPhoneNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(29 * ScreenParameter.getScreenparam_y()));
        otherPhoneNum.setGravity(Gravity.CENTER);
        otherPhoneNum.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).setMargin(50 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y(), 0, 0));
        partner.addView(otherPhoneNum);
        otherPhoneNum.setId(otherPhoneNum.hashCode());

        myNum = new EditText(getApplicationContext());
        mine.addView(myNum);
        myNum.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, myPhoneNum.getId()).addRules(RelativeLayout.ALIGN_LEFT, myPhoneNum.getId()).setMargin(0, 25 * ScreenParameter.getScreenparam_y(), 0, 0));
        myNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        myNum.setTypeface(FontResource.AppleSDGothicNeoR00);
        myNum.setTextColor(Color.rgb(93, 94, 94));
        myNum.setPaintFlags(myNum.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        myNum.setBackground(null);
        myNum.setSingleLine();

        partnerNum = new EditText(getApplicationContext());
        partner.addView(partnerNum);
        partnerNum.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.BELOW, otherPhoneNum.getId()).addRules(RelativeLayout.ALIGN_LEFT, otherPhoneNum.getId()).setMargin(0, 25 * ScreenParameter.getScreenparam_y(), 0, 0));
        partnerNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        partnerNum.setTypeface(FontResource.AppleSDGothicNeoR00);
        partnerNum.setTextColor(Color.rgb(93, 94, 94));
        partnerNum.setPaintFlags(partnerNum.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        partnerNum.setBackground(null);
        partnerNum.setSingleLine();

        explain = new TextView(getApplicationContext());
        explain.setText("전화번호는 두 분을 연결하기 위해 필요합니다. 정확히 입력했는지\n" +
                "확인해주세요. 행쇼는 전화번호를 수집하지 않습니다.");
        explain.setTextColor(Color.argb(255, 149, 149, 149));
        explain.setTypeface(FontResource.AppleSDGothicNeoB00);
        explain.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(22 * ScreenParameter.getScreenparam_y()));
        explain.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.BELOW, partner.getId()).setMargin(0, 60 * ScreenParameter.getScreenparam_y(), 0, 0));
        explain.setGravity(Gravity.CENTER);
        main.addView(explain);

        chcekBox = new TextView(this);
//        chcekBox.setSize(ScreenParameter.getScreen_x(), 120 * ScreenParameter.getScreenparam_y());
//        chcekBox.setLocation(0, (int)(1242 * ScreenParameter.getScreenparam_y()));
        chcekBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        chcekBox.setBackgroundColor(Color.argb(255, 108, 116, 183));
        main.addView(chcekBox);
        chcekBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP && ((TextView)view).getText().toString().equals("연결하기") && !serverCheck)
                {
                    DbResource.db.execSQL("update PersonalData set myPhoneNum = '" + myNum.getText().toString() + "'  where ID='" + DbResource.ID + "';");
                    DbResource.db.execSQL("update PersonalData set partnerPhoneNum = '" + partnerNum.getText().toString() +"' where ID='" + DbResource.ID + "';");

                    Cursor c = DbResource.db.rawQuery("select * from PersonalData where ID='" + DbResource.ID + "';", null);
                    c.moveToNext();
                    DbResource.myPhoneNum = c.getString(c.getColumnIndex("myPhoneNum"));
                    DbResource.partnerPhoneNum = c.getString(c.getColumnIndex("partnerPhoneNum"));

                    ((TextView)view).setText("대기중");
                    work.start();

                }

                else if(motionEvent.getAction() == MotionEvent.ACTION_UP  && serverCheck)
                {
                    startActivity(new Intent(Activity_Phone.this, Activity_Login.class).putExtra("ID", gets));
                }


                return true;
            }
        });
        chcekBox.setId(chcekBox.hashCode());
        chcekBox.setText("연결하기");
        chcekBox.setTypeface(FontResource.AppleSDGothicNeoB00);
        chcekBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        chcekBox.setGravity(Gravity.CENTER);
        chcekBox.setTextColor(Color.WHITE);


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

                    socket_out.println("register");

                    Cursor c = DbResource.db.rawQuery("select * from PersonalData where ID='" + DbResource.ID + "';", null);
                    c.moveToNext();

                    socket_out.println(c.getString(c.getColumnIndex("ID")));
                    socket_out.println(c.getString(c.getColumnIndex("UserName")));
                    socket_out.println(c.getString(c.getColumnIndex("myPhoneNum")));
                    socket_out.println(c.getString(c.getColumnIndex("Birthday")));
                    socket_out.println(c.getString(c.getColumnIndex("Gender")));

                    Cursor d = DbResource.db.rawQuery("select * from Login_info where  ID='" + DbResource.ID + "';", null);
                    d.moveToNext();

                    /////////BitMAP
//                    FileInputStream fileInputStream = openFileInput(d.getString(d.getColumnIndex("PassWd")) + ".png");
//                    DataInputStream dataInputStream = new DataInputStream(fileInputStream);
//                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//
//                    byte[] buf = new byte[1024];
//
////                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                    DbResource.profile.compress(Bitmap.CompressFormat.JPEG, 99, stream);
////                    buf = stream.toByteArray();
//
//                    int data = 0;
//                    int counnn;
//
//                    int totals = 0;
//                    while((counnn = dataInputStream.read(buf)) != -1)
//                    {
//                        data++;
//                        totals += counnn;
//                    }
//
//                    socket_out.println(Integer.toString(totals));
//
//                    fileInputStream = openFileInput(d.getString(d.getColumnIndex("PassWd")) + ".png");
//                    dataInputStream = new DataInputStream(fileInputStream);
//
////                    for(int i=0; i<buf.length; i++)
////                    {
////                        dataOutputStream.write(buf[i]);
////                        dataOutputStream.flush();
////
////                        Log.e("OUTPUT", "//////" + (counnn++) + "////////");
////                    }
//
//                    int total = 0;
//
//
//                    while((counnn = dataInputStream.read(buf)) != -1)
//                    {
//                        socket.getOutputStream().write(buf, 0, counnn);
//                        socket.getOutputStream().flush();
//                        Log.e("asdasdas", Integer.toString(counnn));
//                        total += counnn;
//                    }
//
//                    Log.e("END SOCKET", "WELL " + total);
//
//
//                    /////////////BITMAP END?////////////////

//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    DbResource.profile = Bitmap.createScaledBitmap(DbResource.profile, DbResource.profile.getWidth()/4, DbResource.profile.getHeight()/4, true);
//                    DbResource.profile.compress(Bitmap.CompressFormat.PNG, 20, byteArrayOutputStream);
//                    byte[] bytes = byteArrayOutputStream.toByteArray();
//
//                    socket_out.println(Integer.toString(bytes.length));
//                    socket.getOutputStream().write(bytes);
//
//
//                    Log.e("WELL", "WRLLLL + " + bytes.length);



                    while (true)
                    {
                        String PartnerID = socket_in.readLine();
                        DbResource.db.execSQL("update PersonalData set PartnerID = '" + PartnerID + "' where ID='" + DbResource.ID + "';");
                        String PartnerName = socket_in.readLine();
                        DbResource.db.execSQL("update PersonalData set PartnerName = '" + PartnerName + "' where ID='" + DbResource.ID + "';");
                        String partnerPhoneNum = socket_in.readLine();
                        DbResource.db.execSQL("update PersonalData set partnerPhoneNum = '" + partnerPhoneNum + "' where ID='" + DbResource.ID + "';");
                        String PartnerBirthday = socket_in.readLine();
                        DbResource.db.execSQL("update PersonalData set PartnerBirthday = '" + PartnerBirthday + "' where ID='" + DbResource.ID + "';");

                        String str = socket_in.readLine();
                        Log.e("FROMSERVER", str);
                        serverCheck = true;

                        chcekBox.setText("시작하기");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("SOCKET ERROR", "//");
                }
            }
        };


    }

    //비트맵의 byte배열을 얻는다
    public byte[] getImageByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        return out.toByteArray();
    }
    //숫자를 byte형태로 바꾼다
    private byte[] getByte(int num) {
        byte[] buf = new byte[4];
        buf[0] = (byte)( (num >>> 24) & 0xFF );
        buf[1] = (byte)( (num >>> 16) & 0xFF );
        buf[2] = (byte)( (num >>>  8) & 0xFF );
        buf[3] = (byte)( (num >>>  0) & 0xFF );

        return buf;
    }



    @Override
    protected void onStop() {

        super.onStop();
    }
}
