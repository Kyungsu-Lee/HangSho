package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by lmasi on 2016-09-17.
 */
public class Activity_Local extends Activity {

    RelativeLayout main;

    TouchImageView backgrad;
    TouchImageView backBtn;

    TextView title;

    ScrollView scrollView_local;
    RelativeLayout scrollLayout_local;

    ScrollView scrollView_name;
    RelativeLayout scrollLayout_name;

    
    TextView set_Location;

    Vector<LocalCard> localCards = new Vector<>();
    Vector<LocalName> localNames = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setlocal);

        main = (RelativeLayout)findViewById(R.id.main);

        backgrad = new TouchImageView(this);
        backgrad.setBackground(R.drawable.grad);
        backgrad.setSize(750 * ScreenParameter.getScreenparam_x(), 148 * ScreenParameter.getScreenparam_y());
        backgrad.setId(backgrad.hashCode());
        main.addView(backgrad);

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 18 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                startActivity(new Intent(Activity_Local.this, Activity_Main.class));
                finish();

                return false;
            }
        });

        title = new TextView(this);
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 * ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("지역설정");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);

        set_Location = new TextView(this);
        set_Location.setBackgroundColor(Color.argb(127, 255, 255, 255));
        set_Location.setLayoutParams(new HangShowLayoutParam(750 * ScreenParameter.getScreenparam_x(), 118 * ScreenParameter.getScreenparam_y()).setMargin(0, 1217 * ScreenParameter.getScreenparam_y(), 0, 0));
        set_Location.setText("현재위치로 설정");
        set_Location.setBackgroundColor(Color.argb(242, 108, 116, 182));
        set_Location.setGravity(Gravity.CENTER);
        set_Location.setTypeface(FontResource.AppleSDGothicNeoB00);
        set_Location.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        set_Location.setTextColor(Color.rgb(255, 255, 255));
        set_Location.setId(set_Location.hashCode());
        main.addView(set_Location);
        set_Location.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    startActivity(new Intent(Activity_Local.this, Activity_ChooseStore.class).putExtra("LocalName", "인사동/삼청동"));
                    finish();
                }

                return true;
            }
        });

        scrollView_local = new ScrollView(this);
        scrollView_local.setVerticalScrollBarEnabled(false);
        scrollView_local.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, 1068 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, backgrad.getId()));
        scrollLayout_local = new RelativeLayout(this);
        scrollView_local.setId(scrollLayout_local.hashCode());

        scrollView_name = new ScrollView(this);
        scrollView_name.setVerticalScrollBarEnabled(false);
        scrollView_name.setLayoutParams(new HangShowLayoutParam(462 * ScreenParameter.getScreenparam_x(), 1068 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, backgrad.getId()).setMargin(288 *ScreenParameter.getScreenparam_x(), 0, 0, 0));
        scrollLayout_name = new RelativeLayout(this);

        for(int i=0; i<Resources.locations.length; i++)
        {
            localCards.add(new LocalCard(this, Resources.locations[i], i));
            localCards.get(i).setId(i);
            localCards.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {



                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        for(int j=0; j<localCards.size(); j++)
                            if(localCards.get(j).isClicked)
                                localCards.get(j).unClick();

                        ((LocalCard)view).Click();

                        for(int j=0; j<localNames.size(); j++)
                            scrollLayout_name.removeView(localNames.get(j));

                        localNames.clear();


                        try {


                            for (int j = 0; j < Resources.specific_locations[view.getId()].length; j++) {
                                localNames.add(new LocalName(Activity_Local.this, Resources.specific_locations[view.getId()][j], j));
                                scrollLayout_name.addView(localNames.get(j));
                                localNames.get(j).setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View view, MotionEvent motionEvent) {

                                        if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                                        {
                                            startActivity(new Intent(Activity_Local.this, Activity_ChooseStore.class).putExtra("LocalName", ((LocalName)view).getName()));
                                            finish();
                                        }

                                        return true;
                                    }
                                });
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e("BOUNMDAYEXCEPTION", Integer.toString(view.getId()));
                        }
                    }

                    return true;
                }
            });
            scrollLayout_local.addView(localCards.get(i));
        }
        
        for(int i=0; i<Resources.specific_locations[0].length; i++)
        {
            localNames.add(new LocalName(this, Resources.specific_locations[0][i], i));
            scrollLayout_name.addView(localNames.get(i));
            localNames.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                    {
                        startActivity(new Intent(Activity_Local.this, Activity_ChooseStore.class).putExtra("LocalName", ((LocalName)view).getName()));
                        finish();
                    }

                    return true;
                }
            });
        }



        scrollView_local.addView(scrollLayout_local);
        main.addView(scrollView_local);
        scrollView_name.addView(scrollLayout_name);
        main.addView(scrollView_name);


        localCards.get(0).Click();
    }
}
