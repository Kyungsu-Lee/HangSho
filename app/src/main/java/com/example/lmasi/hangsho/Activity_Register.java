package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Activity_Register extends Activity {

    RelativeLayout main;

    TouchImageView cloasBtn;
    onTextView textView;

    TouchImageView back_box;
    TouchImageView[] lines;

    ScrollView mainBody;
    RelativeLayout scrollLayout;

    TouchImageView profile_circle;

    onTextView name;
    onTextView email;
    onTextView gender;
    onTextView passWord;
    onTextView birthday;

    TouchImageView chcekBox;

    EditText userName;
    EditText email_text;
    EditText passwd_text;
    EditText birthday_text;

    TouchImageView checkGender_female;
    TouchImageView checkGender_male;

    TouchImageView male;
    TouchImageView female;

    DbResource.GENDER genderCheck = null;

    Bitmap bm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        main = (RelativeLayout)findViewById(R.id.main);

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



        textView = new onTextView(this);
        textView.setLocation(93 * ScreenParameter.getScreenparam_x(), 37 * ScreenParameter.getScreenparam_y());
        textView.setText(" 가입하기");
        textView.setTextColor(Color.rgb(34, 30 , 31));
        textView.setTypeface(FontResource.AppleSDGothicNeoB00);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        main.addView(textView);

        mainBody = new ScrollView(this);
        scrollLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)ScreenParameter.getScreen_x(), (int)(1118 * ScreenParameter.getScreenparam_y()));
        params.setMargins(0, (int)(96 * ScreenParameter.getScreenparam_y()), 0, 0);
        mainBody.setLayoutParams(params);
        main.addView(mainBody);


        back_box = new TouchImageView(this);
        back_box.setSize(750 * ScreenParameter.getScreenparam_x(), 445 * ScreenParameter.getScreenparam_y());
        back_box.setBackground(R.drawable.grad);
        scrollLayout.addView(back_box);

        profile_circle = new TouchImageView(this);
        profile_circle.setSize(199 * ScreenParameter.getScreenparam_x(), 199 * ScreenParameter.getScreenparam_y());
        profile_circle.setLocation(275 * ScreenParameter.getScreenparam_x(), 126 * ScreenParameter.getScreenparam_y());
        ( (RelativeLayout.LayoutParams)profile_circle.getLayoutParams()).addRule(RelativeLayout.CENTER_HORIZONTAL);
        profile_circle.setBackground(R.drawable.login_addicon);
        scrollLayout.addView(profile_circle);
        profile_circle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 100);
                }

                return true;
            }
        });

        lines = new TouchImageView[4];
        for(int i=0; i<4; i++)
        {
            lines[i] = new TouchImageView(this);
            lines[i].setBackground(R.drawable.login_line);
            lines[i].setSize(ScreenParameter.getScreen_x(), 2 * ScreenParameter.getScreenparam_y());
            lines[i].setLocation(0, (604 + 158 * i) * ScreenParameter.getScreenparam_y());
            scrollLayout.addView(lines[i]);
        }

//        Geocoder c = new Geocoder(this, Locale.getDefault());
//        try {
//           List<Address> addr =  c.getFromLocation(36.0775, 128.3888, 1);
//            Log.e("//////", addr.get(0).getThoroughfare().toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        name = new onTextView(this);
        name.setLocation(42 * ScreenParameter.getScreenparam_x(), 500 * ScreenParameter.getScreenparam_y());
        name.setText("Name");
        name.setTextColor(Color.argb(140, 94, 94, 94));
        name.setTypeface(FontResource.GothamRounded_Book);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        scrollLayout.addView(name);

        email = new onTextView(this);
        email.setLocation(42 * ScreenParameter.getScreenparam_x(), 651 * ScreenParameter.getScreenparam_y());
        email.setText("Email");
        email.setTextColor(Color.argb(140, 94, 94, 94));
        email.setTypeface(FontResource.GothamRounded_Book);
        email.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        scrollLayout.addView(email);

        gender = new onTextView(this);
        gender.setLocation(42 * ScreenParameter.getScreenparam_x(), 804 * ScreenParameter.getScreenparam_y());
        gender.setText("Gender");
        gender.setTextColor(Color.argb(140, 94, 94, 94));
        gender.setTypeface(FontResource.GothamRounded_Book);
        gender.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        scrollLayout.addView(gender);

        passWord = new onTextView(this);
        passWord.setLocation(42 * ScreenParameter.getScreenparam_x(), 967 * ScreenParameter.getScreenparam_y());
        passWord.setText("Password");
        passWord.setTextColor(Color.argb(140, 94, 94, 94));
        passWord.setTypeface(FontResource.GothamRounded_Book);
        passWord.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        scrollLayout.addView(passWord);

        birthday = new onTextView(this);
        birthday.setLocation(42 * ScreenParameter.getScreenparam_x(), 1126 * ScreenParameter.getScreenparam_y());
        birthday.setText("Birthday");
        birthday.setTextColor(Color.argb(140, 94, 94, 94));
        birthday.setTypeface(FontResource.GothamRounded_Book);
        birthday.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(26 * ScreenParameter.getScreenparam_y()));
        scrollLayout.addView(birthday);

        chcekBox = new TouchImageView(this);
//        chcekBox.setSize(ScreenParameter.getScreen_x(), 120 * ScreenParameter.getScreenparam_y());
//        chcekBox.setLocation(0, (int)(1242 * ScreenParameter.getScreenparam_y()));
        chcekBox.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        chcekBox.setBackground(R.drawable.login_button);
        main.addView(chcekBox);
        chcekBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if( (userName.getText().toString().equals("") || email_text.getText().toString().equals("") || genderCheck == null || passWord.getText().toString().equals("") || birthday_text.getText().toString().equals("") ))
                    {
                        Toast.makeText(getApplicationContext(),"빈칸을 모두 입력하세요..", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {

                        try {
                            DbResource.db.execSQL("insert into login_info values ('" + email_text.getText().toString() + "', '" + passwd_text.getText().toString() + "', 0);");
                            DbResource.db.execSQL("insert into PersonalData values ('" + email_text.getText().toString() + "', 'yet' , '" + userName.getText().toString() + "', 'yet', '" + birthday_text.getText().toString() + "', 'yet' , 0, '" + passwd_text.getText().toString() + "', 'yet', 'yet');");


                           // BitmapDrawable image  = (BitmapDrawable)profile_circle.getbackground();
                            //Bitmap bm = image.getBitmap();


                            try{

                                File file = new File(passwd_text.getText().toString() + ".png");
                                FileOutputStream fos = openFileOutput(passwd_text.getText().toString() + ".png" , Context.MODE_WORLD_READABLE);
                                bm.compress(Bitmap.CompressFormat.PNG, 100 , fos);
                                fos.flush();
                                fos.close();

                            }catch(Exception e) {
                                Log.e("FILE ERROR", "///////////");
                            }


                            if(bm == null)
                                bm = profile_circle.getbackground();


                            DbResource.profile = bm;
                            DbResource.ID = email_text.getText().toString();


                            startActivity(new Intent(Activity_Register.this, Activity_Phone.class).putExtra("ID", new String[] {email_text.getText().toString(), passwd_text.getText().toString()}));
                            finish();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"duplicated ID", Toast.LENGTH_SHORT).show();
                        }
                    }

                }


                return true;
            }
        });

        TouchImageView a = new TouchImageView(this);
        a.setSize(0,00);
        a.setLocation(0, 1880 * ScreenParameter.getScreenparam_y());
        a.setBackgroundColor(Color.YELLOW);
        scrollLayout.addView(a);

        userName = new EditText(this);
        RelativeLayout.LayoutParams username_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        username_param.setMargins((int)(26 * ScreenParameter.getScreenparam_x()), (int)(533 * ScreenParameter.getScreenparam_y()), 0, 0);
        userName.setLayoutParams(username_param);
        scrollLayout.addView(userName);
        userName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        userName.setTypeface(FontResource.AppleSDGothicNeoR00);
        userName.setTextColor(Color.rgb(93, 94, 94));
        userName.setPaintFlags(userName.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        userName.setBackground(null);
        userName.setSingleLine();

        email_text = new EditText(this);
        RelativeLayout.LayoutParams email_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        email_param.setMargins((int)(26 * ScreenParameter.getScreenparam_x()), (int)(691 * ScreenParameter.getScreenparam_y()), 0, 0);
        email_text.setLayoutParams(email_param);
        scrollLayout.addView(email_text);
        email_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        email_text.setTypeface(FontResource.AppleSDGothicNeoR00);
        email_text.setTextColor(Color.rgb(93, 94, 94));
        email_text.setPaintFlags(email_text.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        email_text.setBackground(null);
        email_text.setSingleLine();

        passwd_text = new EditText(this);
        RelativeLayout.LayoutParams passwd_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        passwd_param.setMargins((int)(26 * ScreenParameter.getScreenparam_x()), (int)(1007 * ScreenParameter.getScreenparam_y()), 0, 0);
        passwd_text.setLayoutParams(passwd_param);
        scrollLayout.addView(passwd_text);
        passwd_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        passwd_text.setTypeface(FontResource.AppleSDGothicNeoR00);
        passwd_text.setTextColor(Color.rgb(93, 94, 94));
        passwd_text.setPaintFlags(passwd_text.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        passwd_text.setBackground(null);
        passwd_text.setSingleLine();

        birthday_text = new EditText(this);
        RelativeLayout.LayoutParams birthday_param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        birthday_param.setMargins((int)(26 * ScreenParameter.getScreenparam_x()), (int)(1165 * ScreenParameter.getScreenparam_y()), 0, 0);
        birthday_text.setLayoutParams(birthday_param);
        scrollLayout.addView(birthday_text);
        birthday_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        birthday_text.setTypeface(FontResource.AppleSDGothicNeoR00);
        birthday_text.setTextColor(Color.rgb(93, 94, 94));
        birthday_text.setPaintFlags(birthday_text.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        birthday_text.setBackground(null);
        birthday_text.setSingleLine();

        checkGender_female = new TouchImageView(this);
        checkGender_female.setSize(36 * ScreenParameter.getScreenparam_x(), 36 * ScreenParameter.getScreenparam_y());
        checkGender_female.setLocation(51 * ScreenParameter.getScreenparam_x(), 856 * ScreenParameter.getScreenparam_y());
        checkGender_female.setBackground(R.drawable.login_checkbox);
        scrollLayout.addView(checkGender_female);
        checkGender_female.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    view.setBackground(getResources().getDrawable(R.drawable.login_albumcheckbox));
                    checkGender_male.setBackground(R.drawable.login_checkbox);
                    genderCheck = DbResource.GENDER.WOMAN;
                }

                return true;
            }
        });

        checkGender_male = new TouchImageView(this);
        checkGender_male.setSize(36 * ScreenParameter.getScreenparam_x(), 36 * ScreenParameter.getScreenparam_y());
        checkGender_male.setLocation(221 * ScreenParameter.getScreenparam_x(), 856 * ScreenParameter.getScreenparam_y());
        checkGender_male.setBackground(R.drawable.login_checkbox);
        scrollLayout.addView(checkGender_male);
        checkGender_male.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    view.setBackground(getResources().getDrawable(R.drawable.login_albumcheckbox));
                    checkGender_female.setBackground(R.drawable.login_checkbox);
                    genderCheck = DbResource.GENDER.MAN;
                }

                return true;
            }
        });

        female = new TouchImageView(this);
        female.setSize(30 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y());
        female.setLocation(101 * ScreenParameter.getScreenparam_x(), 854 * ScreenParameter.getScreenparam_y());
        female.setBackground(R.drawable.login_female);
        scrollLayout.addView(female);

        male = new TouchImageView(this);
        male.setSize(30 * ScreenParameter.getScreenparam_x(), 30 * ScreenParameter.getScreenparam_y());
        male.setLocation(266 * ScreenParameter.getScreenparam_x(), 854 * ScreenParameter.getScreenparam_y());
        male.setBackground(R.drawable.login_male);
        scrollLayout.addView(male);



        mainBody.addView(scrollLayout);
        mainBody.setVerticalScrollBarEnabled(false);
        mainBody.setOverScrollMode(View.OVER_SCROLL_NEVER);

        main.setFocusable(true);
        main.setFocusableInTouchMode(true);
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
                    ImageView image = profile_circle;

                    //배치해놓은 ImageView에 set
                   // image.setImageBitmap(image_bitmap);
                    image.setBackground(null);
                    image.setBackground(new BitmapDrawable(bm));



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
