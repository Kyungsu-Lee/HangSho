package com.example.lmasi.hangsho;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-10-19.
 */
public class Activity_Popup extends Activity {

    RelativeLayout main;

    ImageView check;
    ImageView shallow;

    TouchImageView profile_man;
    TouchImageView profile_woman;
    TouchImageView profile_cover_man;
    TouchImageView progile_cover_woman;


    TextView name_man;
    TextView name_woman;
    TextView local_man;
    TextView local_woman;

    TextView hangsho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuop);

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.argb(204, 28, 28, 27));

        check = new ImageView(getApplicationContext());
        check.setLayoutParams(new HangShowLayoutParam(102 * ScreenParameter.getScreenparam_x(), 102 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_IN_PARENT));
        check.setBackground(getResources().getDrawable(R.drawable.check));
        main.addView(check);
        check.setId(check.hashCode());
        check.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    finish();
                }

                return  true;
            }
        });

        hangsho = new TextView(this);
        hangsho.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.BELOW, check.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        hangsho.setText("행쇼하세요!");
        hangsho.setTypeface(FontResource.AppleSDGothicNeoB00);
        hangsho.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        hangsho.setTextColor(Color.rgb(255, 255, 255));
        hangsho.setId(hangsho.hashCode());
        main.addView(hangsho);

        shallow = new ImageView(getApplicationContext());
        shallow.setBackground(getResources().getDrawable(R.drawable.shallow));
        shallow.setLayoutParams(new HangShowLayoutParam(273 * ScreenParameter.getScreenparam_x(), 62 * ScreenParameter.getScreenparam_y()).setMargin(340 * ScreenParameter.getScreenparam_x(), 430 * ScreenParameter.getScreenparam_y(), 0, 0));
        main.addView(shallow);

        BitmapDrawable bitmapDrawable2 = (BitmapDrawable)getResources().getDrawable(R.drawable.irine_1);
        Bitmap bitmap2 = bitmapDrawable2.getBitmap();
        bitmap2 = getRoundedBitmap(bitmap2);
        bitmapDrawable2 = new BitmapDrawable(bitmap2);
//
        profile_woman = new TouchImageView(this);
        profile_woman.setBackground(bitmapDrawable2);
        profile_woman.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_woman.setLocation(599 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        profile_woman.setId(profile_woman.hashCode());
        main.addView(profile_woman);

        progile_cover_woman = new TouchImageView(this);
        progile_cover_woman.setBackground(R.drawable.home_photoline);
        progile_cover_woman.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.setLocation(599 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.setId(progile_cover_woman.hashCode());
        main.addView(progile_cover_woman);

        name_woman = new TextView(this);
        RelativeLayout.LayoutParams name_woman_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        name_woman_params.addRule(RelativeLayout.ALIGN_TOP, progile_cover_woman.getId());
        name_woman_params.setMargins((int)(520 * ScreenParameter.getScreenparam_x()), (int)(10 * ScreenParameter.getScreenparam_y()), 0, 0);
        name_woman.setLayoutParams(name_woman_params);
        name_woman.setText(DbResource.PartnerName);
        name_woman.setTypeface(FontResource.AppleSDGothicNeoB00);
        name_woman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        name_woman.setTextColor(Color.rgb(245, 187, 208));
        name_woman.setId(name_woman.hashCode());
        main.addView(name_woman);

        local_woman = new TextView(this);
        RelativeLayout.LayoutParams local_woman_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        local_woman_params.addRule(RelativeLayout.BELOW, name_woman.getId());
        local_woman_params.addRule(RelativeLayout.ALIGN_RIGHT, name_woman.getId());
        local_woman_params.setMargins((int)(0 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_y()), 0, 0);
        local_woman.setLayoutParams(local_woman_params);
        local_woman.setText(DbResource.PartnerBirthday + "\n" + DbResource.partnerPhoneNum);
        local_woman.setTypeface(FontResource.AppleSDGothicNeoB00);
        local_woman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        local_woman.setTextColor(Color.rgb(255, 255, 255));
        local_woman.setGravity(Gravity.RIGHT);
        local_woman.setId(local_woman.hashCode());
        main.addView(local_woman);


    }


    public static Bitmap getRoundedBitmap(Bitmap bitmap) {

        Bitmap output = null;

        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
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
        }

        catch(Exception e)
        {

        }

        //  bitmap.recycle();

        return output;
    }
}
