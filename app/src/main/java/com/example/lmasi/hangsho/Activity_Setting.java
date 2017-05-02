package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lmasi on 2016-09-19.
 */
public class Activity_Setting extends Activity {

    RelativeLayout main;
    UpdatedScrollView scrollView;

    TouchImageView backBtn;

    TextView title;

    ImageView coverimage;

    TextView name;
    ImageView blank1;
    TextView birthday;
    TextView birthday_number;
    ImageView blank2;
    TextView gender_left;
    TextView gender_right;
    TextView id_info;
    TextView myId;
    TextView partner_id_info;
    TextView partnerId;

    RelativeLayout logout_layout;
    ImageView line;
    RelativeLayout partnerCut_layout;

    TextView logout;
    TextView cutting;

    RelativeLayout copyrightLayout;
    ImageView copyright;


    TouchImageView profile_man;
    TouchImageView profile_woman;
    TouchImageView profile_cover_man;
    TouchImageView progile_cover_woman;


    Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysettings);

        main = (RelativeLayout)findViewById(R.id.main);
        
        scrollView = new UpdatedScrollView(getApplicationContext());
        scrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

                startActivity(new Intent(Activity_Setting.this, Activity_Main.class));
                finish();

                return false;
            }
        });


        title = new TextView(getApplicationContext());
        title.setLayoutParams(new HangShowLayoutParam(180 * ScreenParameter.getScreenparam_x(), 64 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 10 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setBackground(getResources().getDrawable(R.drawable.home_logo));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        scrollView.addObject(title);

        coverimage = new ImageView(getApplicationContext());
        coverimage.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 460 * ScreenParameter.getScreenparam_y()).setMargin(0, 148 * ScreenParameter.getScreenparam_y(), 0, 0));
        coverimage.setBackground(getResources().getDrawable(R.drawable.setting_background2));
        coverimage.setId(coverimage.hashCode());
        scrollView.addObject(coverimage);

        name = new TextView(getApplicationContext());
        name.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, coverimage.getId()));
        name.setText(DbResource.UserName);
        name.setTextColor(Color.argb(255, 0, 0, 0));
        name.setBackgroundColor(Color.argb(255, 255, 255, 255));
        name.setTypeface(FontResource.AppleSDGothicNeoB00);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        name.setGravity(Gravity.CENTER_VERTICAL);
        name.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        name.setId(name.hashCode());
        scrollView.addObject(name);

        blank1 = new ImageView(getApplicationContext());
        blank1.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 61 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW,name.getId()));
        blank1.setBackgroundColor(Color.argb(255, 235, 235, 235));
        blank1.setId(blank1.hashCode());
        scrollView.addObject(blank1);

        birthday = new TextView(getApplicationContext());
        birthday.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, blank1.getId()));
        birthday.setText("생일");
        birthday.setTextColor(Color.argb(255, 0, 0, 0));
        birthday.setBackgroundColor(Color.argb(255, 255, 255, 255));
        birthday.setTypeface(FontResource.AppleSDGothicNeoB00);
        birthday.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        birthday.setGravity(Gravity.CENTER_VERTICAL);
        birthday.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        birthday.setId(birthday.hashCode());
        scrollView.addObject(birthday);

        birthday_number = new TextView(getApplicationContext());
        birthday_number.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, blank1.getId()).addRules(RelativeLayout.RIGHT_OF, birthday.getId()));
        birthday_number.setText(DbResource.Birthday);
        birthday_number.setTextColor(Color.argb(255, 137, 204, 198));
        birthday_number.setBackgroundColor(Color.argb(255, 255, 255, 255));
        birthday_number.setTypeface(FontResource.AppleSDGothicNeoB00);
        birthday_number.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        birthday_number.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        birthday_number.setPadding(0, 0, (int)(20 * ScreenParameter.getScreenparam_x()), 0);
        birthday_number.setId(birthday_number.hashCode());
        scrollView.addObject(birthday_number);

        blank2 = new ImageView(getApplicationContext());
        blank2.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 61 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW,birthday_number.getId()));
        blank2.setBackgroundColor(Color.argb(255, 235, 235, 235));
        blank2.setId(blank2.hashCode());
        scrollView.addObject(blank2);

        gender_left = new TextView(getApplicationContext());
        gender_left.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, blank2.getId()));
        gender_left.setText("성별");
        gender_left.setTextColor(Color.argb(255, 0, 0, 0));
        gender_left.setBackgroundColor(Color.argb(255, 255, 255, 255));
        gender_left.setTypeface(FontResource.AppleSDGothicNeoB00);
        gender_left.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        gender_left.setGravity(Gravity.CENTER_VERTICAL);
        gender_left.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        gender_left.setId(gender_left.hashCode());
        scrollView.addObject(gender_left);

        gender_right = new TextView(getApplicationContext());
        gender_right.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, blank2.getId()).addRules(RelativeLayout.RIGHT_OF, gender_left.getId()));
        gender_right.setText(DbResource.Gender == DbResource.GENDER.MAN ? "남성" : "여성");
        gender_right.setTextColor(Color.argb(255, 137, 204, 198));
        gender_right.setBackgroundColor(Color.argb(255, 255, 255, 255));
        gender_right.setTypeface(FontResource.AppleSDGothicNeoB00);
        gender_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        gender_right.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        gender_right.setPadding(0, 0, (int)(20 * ScreenParameter.getScreenparam_x()), 0);
        gender_right.setId(gender_right.hashCode());
        scrollView.addObject(gender_right);

        id_info = new TextView(getApplicationContext());
        id_info.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, gender_left.getId()));
        id_info.setText("내 계정 정보");
        id_info.setTextColor(Color.argb(255, 0, 0, 0));
        id_info.setBackgroundColor(Color.argb(255, 235, 235, 235));
        id_info.setTypeface(FontResource.AppleSDGothicNeoB00);
        id_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        id_info.setGravity(Gravity.CENTER_VERTICAL);
        id_info.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        id_info.setId(id_info.hashCode());
        scrollView.addObject(id_info);

        myId = new TextView(getApplicationContext());
        myId.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, id_info.getId()));
        myId.setText(DbResource.ID);
        myId.setTextColor(Color.argb(255, 137, 204, 198));
        myId.setBackgroundColor(Color.argb(255, 255, 255, 255));
        myId.setTypeface(FontResource.AppleSDGothicNeoB00);
        myId.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        myId.setGravity(Gravity.CENTER_VERTICAL);
        myId.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        myId.setId(myId.hashCode());
        scrollView.addObject(myId);

        partner_id_info = new TextView(getApplicationContext());
        partner_id_info.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, myId.getId()));
        partner_id_info.setText("상대방 계정 정보");
        partner_id_info.setTextColor(Color.argb(255, 0, 0, 0));
        partner_id_info.setBackgroundColor(Color.argb(255, 235, 235, 235));
        partner_id_info.setTypeface(FontResource.AppleSDGothicNeoB00);
        partner_id_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        partner_id_info.setGravity(Gravity.CENTER_VERTICAL);
        partner_id_info.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        partner_id_info.setId(partner_id_info.hashCode());
        scrollView.addObject(partner_id_info);

        partnerId = new TextView(getApplicationContext());
        partnerId.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 81 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, partner_id_info.getId()));
        partnerId.setText(DbResource.PartnerID);
        partnerId.setTextColor(Color.argb(255, 137, 204, 198));
        partnerId.setBackgroundColor(Color.argb(255, 255, 255, 255));
        partnerId.setTypeface(FontResource.AppleSDGothicNeoB00);
        partnerId.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        partnerId.setGravity(Gravity.CENTER_VERTICAL);
        partnerId.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        partnerId.setId(partnerId.hashCode());
        scrollView.addObject(partnerId);

        logout_layout = new RelativeLayout(getApplicationContext());
        logout_layout.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 163 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, partnerId.getId()));
        logout_layout.setBackgroundColor(Color.argb(255, 235, 235, 235));
        logout_layout.setId(logout_layout.hashCode());
        scrollView.addObject(logout_layout);

        line = new ImageView(getApplicationContext());
        line.setLayoutParams(new HangShowLayoutParam(434 * ScreenParameter.getScreenparam_x(), 2 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        line.setBackgroundColor(Color.argb(39, 58, 41, 30));
        line.setId(line.hashCode());
        logout_layout.addView(line);

        partnerCut_layout = new RelativeLayout(getApplicationContext());
        partnerCut_layout.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 163 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, logout_layout.getId()));
        partnerCut_layout.setBackgroundColor(Color.argb(255, 235, 235, 235));
        partnerCut_layout.setId(partnerCut_layout.hashCode());
        scrollView.addObject(partnerCut_layout);

        logout = new TextView(getApplicationContext());
        logout.setLayoutParams(new HangShowLayoutParam(695 * ScreenParameter.getScreenparam_x(), 90 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_IN_PARENT));
        logout.setText("로그아웃");
        logout.setTextColor(Color.argb(255, 255, 255, 255));
        logout.setBackground(getResources().getDrawable(R.drawable.setting_btn));
        logout.setTypeface(FontResource.AppleSDGothicNeoB00);
        logout.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        logout.setGravity(Gravity.CENTER);
        logout.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        logout.setId(logout.hashCode());
        logout_layout.addView(logout);
        logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    DbResource.db.execSQL("update Login_info set isLogged = 0;");
                    startActivity(new Intent(getApplicationContext(), Activity_Start.class));
                    finish();
                    Activity_Main.getActivity.finish();
                }

                return true;
            }
        });

        cutting = new TextView(getApplicationContext());
        cutting.setLayoutParams(new HangShowLayoutParam(695 * ScreenParameter.getScreenparam_x(), 90 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_IN_PARENT));
        cutting.setText("상대방과 연결 끊기");
        cutting.setTextColor(Color.argb(255, 255, 255, 255));
        cutting.setBackground(getResources().getDrawable(R.drawable.setting_btn));
        cutting.setTypeface(FontResource.AppleSDGothicNeoB00);
        cutting.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        cutting.setGravity(Gravity.CENTER);
        cutting.setPadding((int)(20 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        cutting.setId(cutting.hashCode());
        partnerCut_layout.addView(cutting);
        cutting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    Toast.makeText(getApplicationContext(), "안됩니다!", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        copyrightLayout = new RelativeLayout(getApplicationContext());
        copyrightLayout.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 99 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW,partnerCut_layout.getId()));
        copyrightLayout.setBackgroundColor(Color.argb(255, 235, 235, 235));
        copyrightLayout.setId(copyrightLayout.hashCode());
        scrollView.addObject(copyrightLayout);

        copyright = new ImageView(getApplicationContext());
        copyright.setLayoutParams(new HangShowLayoutParam(274 * ScreenParameter.getScreenparam_x(), 29 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_IN_PARENT));
        copyright.setBackground(getResources().getDrawable(R.drawable.copyright));
        copyrightLayout.addView(copyright);


        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.gum_1);
        Bitmap bitmap = DbResource.profile;
        bitmap = getRoundedBitmap(bitmap);
        bitmapDrawable = new BitmapDrawable(bitmap);

        profile_man = new TouchImageView(this);
        profile_man.setBackground(bitmapDrawable);
        profile_man.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_man.setLocation(39 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        profile_man.setId(profile_man.hashCode());
        scrollView.addObject(profile_man);
//        profile_man.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
//                {
//                    try {
//
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//                        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(intent, 100);
//
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//
//                return  true;
//            }
//        });


        profile_cover_man = new TouchImageView(this);
        profile_cover_man.setBackground(R.drawable.home_photoline);
        profile_cover_man.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setLocation(39 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setId(profile_cover_man.hashCode());
        scrollView.addObject(profile_cover_man);

        scrollView.showScroll();
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


    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //if(requestCode == 100)
        {
            //  if(resultCode==Activity.RESULT_OK)
            {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = getRoundedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                    bm = image_bitmap;
                    //ImageView image = profile_circle;

                    BitmapDrawable drawable = new BitmapDrawable(bm);
                    profile_man.setBackground(drawable);


                    File file = new File(DbResource.PassWd + ".png");
                    FileOutputStream fos = openFileOutput(DbResource.PassWd + ".png", Context.MODE_WORLD_READABLE);
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();

                    startActivity(new Intent(getApplicationContext(), Activity_Start.class));
                    finish();
//
//                    //배치해놓은 ImageView에 set
//                    // image.setImageBitmap(image_bitmap);
//                    image.setBackground(null);
//                    image.setBackground(new BitmapDrawable(bm));



                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
