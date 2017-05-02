package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Login extends Activity {

    RelativeLayout main;
    TouchImageView logo;
    TouchImageView textBox;
    com.example.lmasi.hangsho.onTextView button;

    onTextView onTextView;
    onTextView register;
    onTextView login_info;
    onTextView text_uername;
    onTextView text_password;

    EditText userName;
    EditText passWd;

    Handler handler;

    String[] ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setId(0);

        ID = getIntent().getStringArrayExtra("ID");

        logo = new TouchImageView(this);
        logo.setSize(315 * ScreenParameter.getScreenparam_x(), (252-0) * ScreenParameter.getScreenparam_y());
        logo.setLocation(205 * ScreenParameter.getScreenparam_x(), 234 * ScreenParameter.getScreenparam_y());
        logo.setBackground(R.drawable.login_logo);
        main.addView(logo);

        onTextView = new onTextView(this);
        onTextView.setLocation(131 * ScreenParameter.getScreenparam_x(), (617-0) * ScreenParameter.getScreenparam_y());
        onTextView.setText("행복한  쇼핑데이트를 시작하세요!");
        onTextView.setTypeface(FontResource.AppleSDGothicNeoB00);
        onTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(37 * ScreenParameter.getScreenparam_y()));
        onTextView.setTextColor(Color.WHITE);
        main.addView(onTextView);

        textBox = new TouchImageView(this);
        textBox.setBackground(R.drawable.login_textbox);
        textBox.setSize(ScreenParameter.getScreen_x(), (553-0) * ScreenParameter.getScreenparam_y());
        textBox.setLocation(0, (783-0) * ScreenParameter.getScreenparam_y());
        main.addView(textBox);

        button = new onTextView(this);
//        button.setSize(ScreenParameter.getScreen_x(), (120-0) * ScreenParameter.getScreenparam_y());
//        button.setLocation(0 , (1088 -0)* ScreenParameter.getScreenparam_y());
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(120 * ScreenParameter.getScreenparam_y()));
        param.setMargins(0, (int)(1088 * ScreenParameter.getScreenparam_y()), 0, 0);
        button.setLayoutParams(param);
        button.setBackgroundColor(Color.rgb(108,116,182));
        main.addView(button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    if(((TextView)view).getText().toString().equals("가입하기"))
                        startActivity(new Intent(Activity_Login.this, Activity_Register.class));


                    else    //로그인
                    {
                        DbResource.db = DbResource.conn.getWritableDatabase();

                        Cursor c = DbResource.db.rawQuery("select * from login_info", null);
                        String ID = userName.getText().toString();

                        Log.e("SQL", Integer.toString(c.getCount()));

                        boolean isLogged = false;

                        while(c.moveToNext() && !isLogged)
                        {
                            isLogged |= ID.equals(c.getString(c.getColumnIndex("ID")));
                            Log.e("SQLINFO" , ID + "  " + c.getString(c.getColumnIndex("ID")) + " " + c.getString(c.getColumnIndex("PassWd")));
                        }

                        c.close();

                        try {
                            c = DbResource.db.rawQuery("select * from login_info where ID='" + ID + "';", null);
                            c.moveToNext();
                            isLogged &= passWd.getText().toString().equals(c.getString(c.getColumnIndex("PassWd")));

                            if (!isLogged) {
                                throw new Exception("WRONG");
                            }
                        }catch (Exception e)
                        {
                            isLogged = false;
                            Toast.makeText(getApplicationContext(), "Cannot check log-in info", Toast.LENGTH_SHORT).show();
                            return true;
                        }


                        if(isLogged) {

                            DbResource.db.execSQL("update login_info set isLogged = 1 where ID='" + ID + "';");
                            c = DbResource.db.rawQuery("select * from login_info", null);

                            try {
                                while (c.moveToNext()) {
                                    isLogged = (c.getInt(c.getColumnIndex("isLogged")) == 1);

                                    if (isLogged) {
                                        DbResource.ID = c.getString(c.getColumnIndex("ID"));
                                        DbResource.PassWd = c.getString(c.getColumnIndex("PassWd"));

                                        Cursor info = DbResource.db.rawQuery("select * from PersonalData where id='" + c.getString(c.getColumnIndex("ID")) + "';", null);

                                        while (info.moveToNext()) {

                                            DbResource.UserName = info.getString(info.getColumnIndex("UserName"));
                                            DbResource.PartnerID = info.getString(info.getColumnIndex("PartnerID"));
                                            DbResource.PartnerName = info.getString(info.getColumnIndex("PartnerName"));
                                            DbResource.Birthday = info.getString(info.getColumnIndex("Birthday"));
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

                                        c.close();
                                        DbResource.Loggin = true;
                                        break;
                                    }
                                }

                            } catch (Exception e) {
                                Log.e("ERROR", e.toString());

                                Toast.makeText(getApplicationContext(), "Cannot check log-in info", Toast.LENGTH_SHORT).show();

                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 200);
                            }

                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Activity_Login.this, Activity_Main.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 200);   /// intent to main-page;
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(),"입력된 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                return true;
            }
        });
        button.setId(button.hashCode());
       // button.setText("가입하기");
        button.setTypeface(FontResource.AppleSDGothicNeoB00);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        button.setGravity(Gravity.CENTER);
        button.setTextColor(Color.WHITE);

//        register = new onTextView(this);
//        register.setText("가입하기");
//        register.setTypeface(FontResource.AppleSDGothicNeoB00);
//        register.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
//        //register.setLocation(325 * ScreenParameter.getScreenparam_x(), (1136-0) * ScreenParameter.getScreenparam_y());
//        RelativeLayout.LayoutParams param = register.params;
//        param.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        param.addRule(RelativeLayout.ALIGN_TOP, button.getId());
//        param.setMargins(0, 0,0, 0);
//        register.setLayoutParams(param);
//        main.addView(register);
//        register.setTextColor(Color.WHITE);

        login_info = new onTextView(this);
        login_info.setText("로그인 정보를 잊으셨나요?");
        login_info.setTypeface(FontResource.AppleSDGothicNeoB00);
        login_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        login_info.setLocation(221 * ScreenParameter.getScreenparam_x(), (1259-0) * ScreenParameter.getScreenparam_y());
        login_info.setTextColor(Color.WHITE);
        main.addView(login_info);

        text_uername = new onTextView(this);
        text_uername.setLocation(51* ScreenParameter.getScreenparam_x(), 833 * ScreenParameter.getScreenparam_y());
        text_uername.setText("Username");
        text_uername.setTextColor(Color.argb(140, 255, 255, 255));
        text_uername.setTypeface(FontResource.GothamRounded_Book);
        main.addView(text_uername);

        text_password = new onTextView(this);
        text_password.setLocation(51* ScreenParameter.getScreenparam_x(), 980 * ScreenParameter.getScreenparam_y());
        text_password.setText("Password");
        text_password.setTextColor(Color.argb(140, 255, 255, 255));
        text_password.setTypeface(FontResource.GothamRounded_Book);
        main.addView(text_password);

        userName = new EditText(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int)(51 * ScreenParameter.getScreenparam_x()), (int)(870 * ScreenParameter.getScreenparam_y()), 0, 0);
        userName.setLayoutParams(params);
        main.addView(userName);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        userName.setTypeface(FontResource.GothamRounded_Book);
        userName.setTextColor(Color.WHITE);
        userName.setPaintFlags(userName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        userName.setBackground(null);
        userName.setSingleLine();

        passWd = new EditText(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins((int)(51 * ScreenParameter.getScreenparam_x()), (int)(1015 * ScreenParameter.getScreenparam_y()), 0, 0);
        passWd.setLayoutParams(params1);
        main.addView(passWd);
        passWd.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        passWd.setTypeface(FontResource.GothamRounded_Book);
        passWd.setTextColor(Color.WHITE);
        passWd.setPaintFlags(passWd.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        passWd.setBackground(null);
        passWd.setSingleLine();
        passWd.setTransformationMethod(new PasswordTransformationMethod());

        if(ID != null) {
            passWd.setText(ID[1]);
            userName.setText(ID[0]);
        }

        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                if(( !userName.getText().toString().equals("")  && !passWd.getText().toString().equals("") ))
                    button.setText("로그인");
                else
                    button.setText("가입하기");

                button.invalidate();

                sendEmptyMessageDelayed(0, 100);
            }
        };
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onDestroy() {

        handler.removeMessages(0);
        super.onDestroy();
    }
}
