package com.example.lmasi.hangsho;

import android.app.Activity;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by lmasi on 2016-09-14.
 */
public class Activity_Main extends Activity  {

    private static final long serialVersionUID = 1L;

    RelativeLayout main;

    TouchImageView backBox;
    TouchImageView logo;
    TouchImageView backBtn;
    TouchImageView settings;
    TouchImageView startDate;
    TouchImageView profile_man;
    TouchImageView profile_woman;
    TouchImageView profile_cover_man;
    TouchImageView progile_cover_woman;

    ScrollView scrollView;
    RelativeLayout scrollRelative;

    TextView histoeyBox;
    TextView startDate_text;
    TextView fromDate;
    TextView dateNum;

    TextView name_man;
    TextView name_woman;
    TextView local_man;
    TextView local_woman;

    Vector<Pair<String, Boolean>> history;
    Vector<HistroryCard> histroryCards = new Vector<>();

    TextView recommand_codi;

    static Activity_Main getActivity;

    TextView showall;

    Handler call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        startService(new Intent(this, SocketService.class));

        main = (RelativeLayout)findViewById(R.id.main);
        main.setBackgroundColor(Color.WHITE);

        getActivity = this;

        scrollView = new ScrollView(this);
        scrollRelative = new RelativeLayout(this);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
        scrollView.setLayoutParams(param);

        backBox = new TouchImageView(this);
        backBox.setBackground(R.drawable.grad);
        backBox.setSize(ScreenParameter.getScreen_x(), 660 * ScreenParameter.getScreenparam_y() );
        backBox.setId(backBox.hashCode());
        scrollRelative.addView(backBox);

        logo = new TouchImageView(this);
        logo.setBackground(R.drawable.home_logo);
        logo.setSize(190 * ScreenParameter.getScreenparam_x(), 75 * ScreenParameter.getScreenparam_y());
        logo.addRule(RelativeLayout.CENTER_HORIZONTAL);
        logo.setLocation(0, 65 * ScreenParameter.getScreenparam_y());
        logo.setId(logo.hashCode());
        scrollRelative.addView(logo);

        fromDate = new TextView(this);
        RelativeLayout.LayoutParams fromDate_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        fromDate_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        fromDate_params.setMargins(0, (int)(160 * ScreenParameter.getScreenparam_y()), 0, 0);
        fromDate.setLayoutParams(fromDate_params);
        fromDate.setTypeface(FontResource.AppleSDGothicNeoB00);
        fromDate.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        fromDate.setText("연애한지 365일");
        fromDate.setTextColor(Color.argb(255, 236, 220, 156));
        fromDate.setId(fromDate.hashCode());
        scrollRelative.addView(fromDate);

        dateNum = new TextView(this);
        RelativeLayout.LayoutParams dateNum_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dateNum_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dateNum_params.addRule(RelativeLayout.BELOW, fromDate.getId());
        dateNum_params.setMargins(0, (int)(2 * ScreenParameter.getScreenparam_y()), 0, 0);
        dateNum.setLayoutParams(dateNum_params);
        dateNum.setTypeface(FontResource.AppleSDGothicNeoB00);
        dateNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        dateNum.setText("~2016. 05. 05");
        dateNum.setTextColor(Color.argb(255, 255, 255, 255));
        scrollRelative.addView(dateNum);

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.addRule(RelativeLayout.ALIGN_TOP, logo.getId());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), -5 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        scrollRelative.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                finish();

                return false;
            }
        });

        settings = new TouchImageView(this);
        settings.setBackground(R.drawable.home_setting);
        settings.setSize(44 * ScreenParameter.getScreenparam_x(), 43 * ScreenParameter.getScreenparam_y());
        settings.addRule(RelativeLayout.ALIGN_TOP, logo.getId());
        settings.setLocation(651 * ScreenParameter.getScreenparam_x(), 10 * ScreenParameter.getScreenparam_y());
        scrollRelative.addView(settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Activity_Main.this, Activity_Setting.class));
                finish();

            }
        });

//        startDate = new TouchImageView(this);
//        startDate.setBackground(R.drawable.home_button1);
//        startDate.setSize(585 * ScreenParameter.getScreenparam_x(), 76 * ScreenParameter.getScreenparam_y());
//        startDate.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        startDate.setLocation(0, 287 * ScreenParameter.getScreenparam_y());
//        startDate.setId(startDate.hashCode());
//        main.addView(startDate);

        startDate_text = new TextView(this);
        RelativeLayout.LayoutParams startDate_text_params = new RelativeLayout.LayoutParams((int)(585 * ScreenParameter.getScreenparam_x()), (int)(76 * ScreenParameter.getScreenparam_y()));
        startDate_text_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        startDate_text_params.setMargins(0, (int)(300 * ScreenParameter.getScreenparam_y()), 0, 0);
        startDate_text.setLayoutParams(startDate_text_params);
        startDate_text.setBackground(getResources().getDrawable(R.drawable.home_button1));
        startDate_text.setGravity(Gravity.CENTER);
        startDate_text.setText("쇼핑데이트 시작하기");
        startDate_text.setTypeface(FontResource.AppleSDGothicNeoB00);
        startDate_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        startDate_text.setTextColor(Color.WHITE);
        scrollRelative.addView(startDate_text);
        startDate_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(DbResource.iscalled)
                    {
                        startActivity(new Intent(Activity_Main.this, Activity_ChooseStore.class));
                    }
                    else
                        startActivity(new Intent(Activity_Main.this, Activity_Local.class));
                    finish();
                }

                return true;
            }
        });

        call = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                if(DbResource.iscalled)
                {
                    startDate_text.setText("시작하기");
                    startActivity(new Intent(getApplicationContext(), Activity_Popup.class));
                    DbResource.iscalled = false;
                }
                sendEmptyMessageDelayed(0, 1000);
            }
        };
        call.sendEmptyMessage(0);

        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.gum_1);
        Bitmap bitmap = DbResource.profile;
        bitmap = getRoundedBitmap(bitmap);
        bitmapDrawable = new BitmapDrawable(bitmap);

        profile_man = new TouchImageView(this);
        profile_man.setBackground(bitmapDrawable);
        profile_man.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_man.setLocation(39 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        profile_man.setId(profile_man.hashCode());
        scrollRelative.addView(profile_man);


        profile_cover_man = new TouchImageView(this);
        profile_cover_man.setBackground(R.drawable.home_photoline);
        profile_cover_man.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setLocation(39 * ScreenParameter.getScreenparam_x(), 494 * ScreenParameter.getScreenparam_y());
        profile_cover_man.setId(profile_cover_man.hashCode());
        scrollRelative.addView(profile_cover_man);
//
        BitmapDrawable bitmapDrawable2 = (BitmapDrawable)getResources().getDrawable(R.drawable.irine_1);
        Bitmap bitmap2 = bitmapDrawable2.getBitmap();
        bitmap2 = getRoundedBitmap(bitmap2);
        bitmapDrawable2 = new BitmapDrawable(bitmap2);
//
        profile_woman = new TouchImageView(this);
        profile_woman.setBackground(bitmapDrawable2);
        profile_woman.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        profile_woman.addRule(RelativeLayout.ALIGN_TOP, profile_man.getId());
        profile_woman.setLocation(599 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
        profile_woman.setId(profile_woman.hashCode());
        scrollRelative.addView(profile_woman);

        progile_cover_woman = new TouchImageView(this);
        progile_cover_woman.setBackground(R.drawable.home_photoline);
        progile_cover_woman.setSize(118 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.addRule(RelativeLayout.ALIGN_TOP, profile_man.getId());
        progile_cover_woman.setLocation(599 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
        progile_cover_woman.setId(progile_cover_woman.hashCode());
        scrollRelative.addView(progile_cover_woman);

        name_man = new TextView(this);
        RelativeLayout.LayoutParams name_man_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        name_man_params.addRule(RelativeLayout.ALIGN_TOP, profile_cover_man.getId());
        //name_man_params.addRule(RelativeLayout.RIGHT_OF, profile_man.getId());
        name_man_params.setMargins((int)(168 * ScreenParameter.getScreenparam_x()), (int)(10 * ScreenParameter.getScreenparam_y()), 0, 0);
        name_man.setLayoutParams(name_man_params);
        name_man.setText(DbResource.UserName);
        name_man.setTypeface(FontResource.AppleSDGothicNeoB00);
        name_man.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        name_man.setTextColor(Color.rgb(192, 227, 224));
        name_man.setId(name_man.hashCode());
        scrollRelative.addView(name_man);

        local_man = new TextView(this);
        RelativeLayout.LayoutParams local_man_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        local_man_params.addRule(RelativeLayout.BELOW, name_man.getId());
        //name_man_params.addRule(RelativeLayout.RIGHT_OF, profile_man.getId());
        local_man_params.setMargins((int)(168 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_y()), 0, 0);
        local_man.setLayoutParams(local_man_params);
        local_man.setText(DbResource.Birthday + "\n" + DbResource.myPhoneNum);
        local_man.setTypeface(FontResource.AppleSDGothicNeoB00);
        local_man.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        local_man.setTextColor(Color.rgb(255, 255, 255));
        local_man.setId(local_man.hashCode());
        scrollRelative.addView(local_man);


        name_woman = new TextView(this);
        RelativeLayout.LayoutParams name_woman_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        name_woman_params.addRule(RelativeLayout.ALIGN_TOP, name_man.getId());
        //name_man_params.addRule(RelativeLayout.RIGHT_OF, profile_man.getId());
        name_woman_params.setMargins((int)(520 * ScreenParameter.getScreenparam_x()), (int)(0 * ScreenParameter.getScreenparam_y()), 0, 0);
        name_woman.setLayoutParams(name_woman_params);
        name_woman.setText(DbResource.PartnerName);
        name_woman.setTypeface(FontResource.AppleSDGothicNeoB00);
        name_woman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        name_woman.setTextColor(Color.rgb(245, 187, 208));
        name_woman.setId(name_woman.hashCode());
        scrollRelative.addView(name_woman);

        local_woman = new TextView(this);
        RelativeLayout.LayoutParams local_woman_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        local_woman_params.addRule(RelativeLayout.ALIGN_TOP, local_man.getId());
        local_woman_params.addRule(RelativeLayout.ALIGN_RIGHT, name_woman.getId());
        //local_woman_params.setMargins((int)(168 * ScreenParameter.getScreenparam_x()), (int)(20 * ScreenParameter.getScreenparam_y()), 0, 0);
        local_woman.setLayoutParams(local_woman_params);
        local_woman.setText(DbResource.PartnerBirthday + "\n" + DbResource.partnerPhoneNum);
        local_woman.setTypeface(FontResource.AppleSDGothicNeoB00);
        local_woman.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        local_woman.setTextColor(Color.rgb(255, 255, 255));
        local_woman.setGravity(Gravity.RIGHT);
        local_woman.setId(local_woman.hashCode());
        scrollRelative.addView(local_woman);
        local_man.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    //startActivity(new Intent(getApplicationContext(), Activity_Popup.class));
                }

                return true;
            }
        });


        histoeyBox = new TextView(this);
        histoeyBox.setBackgroundColor(Color.argb(127, 255, 255, 255));
        RelativeLayout.LayoutParams historyBox_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(79 * ScreenParameter.getScreenparam_y()));
        historyBox_params.addRule(RelativeLayout.BELOW, backBox.getId());
        histoeyBox.setLayoutParams(historyBox_params);
        histoeyBox.setText("쇼핑데이트 히스토리");
        histoeyBox.setBackgroundColor(Color.argb(255, 240, 248, 248));
        histoeyBox.setGravity(Gravity.CENTER);
        histoeyBox.setTypeface(FontResource.AppleSDGothicNeoB00);
        histoeyBox.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        histoeyBox.setTextColor(Color.rgb(137, 204, 198));
        histoeyBox.setId(Integer.parseInt("26865"));
        scrollRelative.addView(histoeyBox);
        histoeyBox.setId(histoeyBox.hashCode());

        showall = new TextView(this);
        RelativeLayout.LayoutParams showall_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(79 * ScreenParameter.getScreenparam_y()));
        showall_params.addRule(RelativeLayout.BELOW, backBox.getId());
        showall.setLayoutParams(showall_params);
        showall.setText(Html.fromHtml("<u>모두보기</u>"));
        showall.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        histoeyBox.setTextColor(Color.rgb(137, 204, 198));
        showall.setPadding(0, 0, (int)(10 * ScreenParameter.getScreenparam_x()), 0);
        showall.setTypeface(FontResource.AppleSDGothicNeoB00);
        showall.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        showall.setTextColor(Color.rgb(137, 204, 198));
        scrollRelative.addView(showall);
        showall.setId(showall.hashCode());
        showall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(getApplicationContext(), Activity_CheckHistory.class));
                }

                return true;
            }
        });




//        for(int i=0; i<3; i++)
//        {
//            history = new Vector<>();
//            history.add(new Pair<String, Boolean>("찰스앤키스", true));
//            history.add(new Pair<String, Boolean>("질스튜어스", false));
//            history.add(new Pair<String, Boolean>("라코스테", false));
//            history.add(new Pair<String, Boolean>("에잇세컨즈", true));
//            history.add(new Pair<String, Boolean>("비이커", false));
//            history.add(new Pair<String, Boolean>("비이커", true));
//
//            HistroryCard histroryCard = new HistroryCard(this, !(i==1), "서울 - 명동 쇼핑데이트", "2016.08.03 | 6코스 | 02:37", history);
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(900 * ScreenParameter.getScreenparam_x()), (int)(309 * ScreenParameter.getScreenparam_y()));
//            //param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
//            params.setMargins(0, (int)((737 + 312 * i) * ScreenParameter.getScreenparam_y()), 0, 0);
//            histroryCard.setLayoutParams(params);
//            scrollRelative.addView(histroryCard);
//
//            histroryCards.add(histroryCard);
//        }
//
//        DbResource.db.execSQL("insert into HistoryCard values(7, 1, '한동 - 명동 쇼핑데이트', '2016.10.10', 3, '30:37');");
//        DbResource.db.execSQL("insert into History values(7, '찰스앤키스', 2)");
//        DbResource.db.execSQL("insert into History values(7, '비이커', 2)");
//        DbResource.db.execSQL("insert into History values(7, '비이커', 2)");

try {
    Cursor c = DbResource.db.rawQuery("select * from HistoryCard", null);
    Log.e("./........///", Integer.toString(c.getCount()));
    if (c.getCount() < 4) {
        c.moveToLast();
        for (int i = 0; i < c.getCount(); i++) {
            history = new Vector<>();
            Cursor d = DbResource.db.rawQuery("select * from History where idx = " + c.getLong(c.getColumnIndex("idx")), null);
            while (d.moveToNext()) {
                history.add(new Pair<String, Boolean>(d.getString(d.getColumnIndex("place")), d.getInt(d.getColumnIndex("man")) == 0));
                Log.e("///////////", d.getString(d.getColumnIndex("place")));
            }

            HistroryCard histroryCard = new HistroryCard(getApplicationContext(), c.getInt(c.getColumnIndex("good")) == 1, c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("dateDay")) + " | " + c.getInt(c.getColumnIndex("courseNum")) + "코스" + " | " + c.getString(c.getColumnIndex("timer")), history);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (900 * ScreenParameter.getScreenparam_x()), (int) (309 * ScreenParameter.getScreenparam_y()));
            //param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
            params.setMargins(0, (int) ((737 + 312 * i) * ScreenParameter.getScreenparam_y()), 0, 0);
            histroryCard.setLayoutParams(params);
            scrollRelative.addView(histroryCard);
            histroryCards.add(histroryCard);
            c.moveToPrevious();
        }
    } else {
        c.moveToLast();
        for (int i = 0; i < 3; i++) {

            history = new Vector<>();
            Cursor d = DbResource.db.rawQuery("select * from History where idx = " + c.getLong(c.getColumnIndex("idx")), null);
            while (d.moveToNext()) {
                history.add(new Pair<String, Boolean>(d.getString(d.getColumnIndex("place")), d.getInt(d.getColumnIndex("man")) == 0));
            }

            HistroryCard histroryCard = new HistroryCard(getApplicationContext(), c.getInt(c.getColumnIndex("good")) == 1, c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("dateDay")) + " | " + c.getInt(c.getColumnIndex("courseNum")) + "코스" + " | " + c.getString(c.getColumnIndex("timer")), history);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (900 * ScreenParameter.getScreenparam_x()), (int) (309 * ScreenParameter.getScreenparam_y()));
            //param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
            params.setMargins(0, (int) ((737 + 312 * i) * ScreenParameter.getScreenparam_y()), 0, 0);
            histroryCard.setLayoutParams(params);
            scrollRelative.addView(histroryCard);
            histroryCards.add(histroryCard);

            c.moveToPrevious();
        }

    }

}
catch (Exception e)
{
    e.printStackTrace();
}



        recommand_codi = new TextView(this);
        RelativeLayout.LayoutParams recommand_codi_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(79 * ScreenParameter.getScreenparam_y()));
        recommand_codi_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        if(histroryCards != null && histroryCards.size() != 0) {
            histroryCards.lastElement().setId(histroryCards.hashCode());
            recommand_codi_params.addRule(RelativeLayout.BELOW, histroryCards.hashCode());
        }
        else
        recommand_codi_params.addRule(RelativeLayout.BELOW, histoeyBox.getId());
        //recommand_codi_params.setMargins(0, (int)(300 * ScreenParameter.getScreenparam_y()), 0, 0);
        recommand_codi.setLayoutParams(recommand_codi_params);
        recommand_codi.setBackgroundColor(Color.argb(255, 240, 248, 248));
        recommand_codi.setGravity(Gravity.CENTER);
        recommand_codi.setText("추천 코디");
        recommand_codi.setTypeface(FontResource.AppleSDGothicNeoB00);
        recommand_codi.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        recommand_codi.setTextColor(Color.rgb(137, 204, 198));
        recommand_codi.setId(recommand_codi.hashCode());
        scrollRelative.addView(recommand_codi);




        CodiImageView codiImageView = new CodiImageView(this, R.drawable.th_codi_10a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 10, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView2 = new CodiImageView(this, R.drawable.th_codi_12c, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 530).setMargin(ScreenParameter.getScreen_x()/2, 10, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView3 = new CodiImageView(this, R.drawable.th_codi_14d, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 1020, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView4 = new CodiImageView(this, R.drawable.th_codi_10a+3, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2, 1000).setMargin(ScreenParameter.getScreen_x()/2, 550, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView5 = new CodiImageView(this, R.drawable.th_codi_13b, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 700).setMargin(0, 2030, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView6 = new CodiImageView(this, R.drawable.th_codi_10a+4, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 900).setMargin(ScreenParameter.getScreen_x()/2, 1570, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView7 = new CodiImageView(this, R.drawable.th_codi_12a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 2750, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView8 = new CodiImageView(this, R.drawable.th_codi_11b, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 2490, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView9 = new CodiImageView(this, R.drawable.th_codi_14e, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 300).setMargin(0, 3770, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView10 = new CodiImageView(this, R.drawable.th_codi_13a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 3510, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView11 = new CodiImageView(this, R.drawable.th_codi_19a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 900).setMargin(0, 4090, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView12 = new CodiImageView(this, R.drawable.th_codi_19c, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 4530, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView13 = new CodiImageView(this, R.drawable.th_codi_20b, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 5010, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView14 = new CodiImageView(this, R.drawable.th_codi_21a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 5550, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView15 = new CodiImageView(this, R.drawable.th_codi_22a, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 6030, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView16 = new CodiImageView(this, R.drawable.th_codi_22b, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 6570, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView17 = new CodiImageView(this, R.drawable.th_codi_22c, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(0, 7050, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView18 = new CodiImageView(this, R.drawable.th_codi_23c, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 1000).setMargin(ScreenParameter.getScreen_x()/2, 7590, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));
        CodiImageView codiImageView19 = new CodiImageView(this, R.drawable.th_codi_16c, new HangShowLayoutParam(ScreenParameter.getScreen_x()/2-10, 500).setMargin(0, 8070, 0, 0).addRules(RelativeLayout.BELOW, recommand_codi.getId()));


        scrollRelative.addView(codiImageView);
        scrollRelative.addView(codiImageView2);
        scrollRelative.addView(codiImageView3);
        scrollRelative.addView(codiImageView4);
        scrollRelative.addView(codiImageView5);
        scrollRelative.addView(codiImageView6);
        scrollRelative.addView(codiImageView7);
        scrollRelative.addView(codiImageView8);
        scrollRelative.addView(codiImageView9);
        scrollRelative.addView(codiImageView10);
        scrollRelative.addView(codiImageView11);
        scrollRelative.addView(codiImageView12);
        scrollRelative.addView(codiImageView13);
        scrollRelative.addView(codiImageView14);
        scrollRelative.addView(codiImageView15);
        scrollRelative.addView(codiImageView16);
        scrollRelative.addView(codiImageView17);
        scrollRelative.addView(codiImageView18);
        scrollRelative.addView(codiImageView19);





        scrollView.addView(scrollRelative);
        main.addView(scrollView);



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
