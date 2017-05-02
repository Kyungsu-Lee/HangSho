package com.example.lmasi.hangsho;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Vector;

/**
 * Created by lmasi on 2016-10-19.
 */
public class Activity_CheckHistory extends Activity {

    RelativeLayout main;


    TouchImageView backBox;
    TouchImageView logo;
    TouchImageView backBtn;

    ScrollView scrollView;
    RelativeLayout scrollRelative;


    TextView histoeyBox;

    Vector<Pair<String, Boolean>> history;
    Vector<HistroryCard> histroryCards = new Vector<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkhistory);

        main = (RelativeLayout)findViewById(R.id.main);

        backBox = new TouchImageView(this);
        backBox.setBackground(R.drawable.grad);
        backBox.setSize(ScreenParameter.getScreen_x(), 184 * ScreenParameter.getScreenparam_y() );
        backBox.setId(backBox.hashCode());
        main.addView(backBox);
        backBox.setId(backBox.getId());

        logo = new TouchImageView(this);
        logo.setBackground(R.drawable.home_logo);
        logo.setSize(190 * ScreenParameter.getScreenparam_x(), 75 * ScreenParameter.getScreenparam_y());
        logo.addRule(RelativeLayout.CENTER_HORIZONTAL);
        logo.setLocation(0, 65 * ScreenParameter.getScreenparam_y());
        logo.setId(logo.hashCode());
        main.addView(logo);

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.addRule(RelativeLayout.ALIGN_TOP, logo.getId());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), -5 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                finish();

                return false;
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
        main.addView(histoeyBox);
        histoeyBox.setId(histoeyBox.hashCode());

        scrollView = new ScrollView(this);
        scrollRelative = new RelativeLayout(this);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
        scrollView.setLayoutParams(param);


        try {
            Cursor c = DbResource.db.rawQuery("select * from HistoryCard", null);
            Log.e("./........///", Integer.toString(c.getCount()));

            c.moveToLast();
            for (int i = 0; i < c.getCount(); i++) {

                history = new Vector<>();
                Cursor d = DbResource.db.rawQuery("select * from History where idx = " + c.getLong(c.getColumnIndex("idx")), null);
                while (d.moveToNext()) {
                    history.add(new Pair<String, Boolean>(d.getString(d.getColumnIndex("place")), d.getInt(d.getColumnIndex("man")) == 0));
                }

                HistroryCard histroryCard = new HistroryCard(getApplicationContext(), c.getInt(c.getColumnIndex("good")) == 1, c.getString(c.getColumnIndex("title")), c.getString(c.getColumnIndex("dateDay")) + " | " + c.getInt(c.getColumnIndex("courseNum")) + "코스" + " | " + c.getString(c.getColumnIndex("timer")), history);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) (900 * ScreenParameter.getScreenparam_x()), (int) (309 * ScreenParameter.getScreenparam_y()));
                //param.addRule(RelativeLayout.BELOW, histoeyBox.getId());
                params.setMargins(0, (int) ((0 + 312 * i) * ScreenParameter.getScreenparam_y()), 0, 0);
                histroryCard.setLayoutParams(params);
                scrollRelative.addView(histroryCard);
                histroryCards.add(histroryCard);

                c.moveToPrevious();
            }


            scrollView.addView(scrollRelative);
            main.addView(scrollView);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }


}
