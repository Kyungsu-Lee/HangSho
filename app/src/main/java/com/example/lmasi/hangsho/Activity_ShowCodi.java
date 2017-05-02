package com.example.lmasi.hangsho;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Activity_ShowCodi extends Activity {

    RelativeLayout main;
    TouchImageView backgrad;
    TouchImageView backBtn;

    TextView title;
    TextView shopName;

    ImageView backg;

    ArrayList<ShopInfo> infos = new ArrayList<>();
    int index;


    ImageView leftArrow;
    ImageView rightArrow;

    RelativeLayout[] relativeLayouts ;
    SlideView slideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityshowcodi);

        main = (RelativeLayout)findViewById(R.id.main);

        backgrad = new TouchImageView(this);
        backgrad.setBackground(R.drawable.grad);
        backgrad.setSize(750 * ScreenParameter.getScreenparam_x(), 148 * ScreenParameter.getScreenparam_y());
        backgrad.setId(backgrad.hashCode());
        main.addView(backgrad);
        backgrad.setId(backgrad.hashCode());
        backgrad.setId(backgrad.hashCode());

        backBtn = new TouchImageView(this);
        backBtn.setBackground(R.drawable.home_back);
        backBtn.setSize(80 * ScreenParameter.getScreenparam_x(), 80 * ScreenParameter.getScreenparam_y());
        backBtn.setLocation(32 * ScreenParameter.getScreenparam_x(), 42 * ScreenParameter.getScreenparam_y());
        backBtn.setId(backBtn.hashCode());
        main.addView(backBtn);
        backBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                finish();

                return false;
            }
        });

        title = new TextView(this);
        title.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_HORIZONTAL).addRules(RelativeLayout.ALIGN_TOP, backBtn.getId()).setMargin(0, 20 *ScreenParameter.getScreenparam_y(), 0, 0));
        title.setText("코디보기");
        title.setTextColor(Color.argb(255, 255, 255, 255));
        title.setTypeface(FontResource.AppleSDGothicNeoB00);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(32 * ScreenParameter.getScreenparam_y()));
        main.addView(title);
        title.setId(title.hashCode());

        infos = (ArrayList<ShopInfo>)getIntent().getSerializableExtra("LIST");
        index = getIntent().getIntExtra("index", 0);
        String shop_name = infos.get(index).getShopName();

        relativeLayouts = new RelativeLayout[infos.size()];
        slideView = new SlideView(getApplicationContext()) {
            @Override
            public void BeforeAction() {

            }

            @Override
            public void AfterAction() {
                shopName.setText(infos.get(getIndex()).getShopName());
                index = getIndex();
            }

            @Override
            public void onDrag() {

            }
        };
        slideView.setSize((int)(750 * ScreenParameter.getScreenparam_x()), (int)(1185 * ScreenParameter.getScreenparam_y()));
        slideView.setLocation(0, (int)(148 * ScreenParameter.getScreenparam_y()));
        slideView.setSpeed(1000);
        for(int i=0; i<infos.size(); i++)
        {
            relativeLayouts[i] = new RelativeLayout(getApplicationContext());
            relativeLayouts[i].setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            relativeLayouts[i].setBackground(getResources().getDrawable(R.drawable.codi1 + i%3));
            slideView.addLayout(relativeLayouts[i]);
        }
        main.addView(slideView);

//        backg = new ImageView(getApplicationContext());
//        backg.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).addRules(RelativeLayout.BELOW, backgrad.getId()));
//        backg.setBackground(getResources().getDrawable(R.drawable.codi1 + index%3));
//        main.addView(backg);

        shopName = new TextView(this);
        shopName.setText(shop_name);
        shopName.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 120 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_PARENT_BOTTOM));
        shopName.setBackgroundColor(Color.argb(179, 255, 255, 255));
        shopName.setTextColor(Color.argb(255, 108, 116, 183));
        shopName.setTypeface(FontResource.AppleSDGothicNeoB00);
        shopName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(40 * ScreenParameter.getScreenparam_y()));
        shopName.setGravity(Gravity.CENTER);
        main.addView(shopName);

        if(index > 0) {
            leftArrow = new ImageView(getApplicationContext());
            leftArrow.setLayoutParams(new HangShowLayoutParam(81 * ScreenParameter.getScreenparam_x(), 116 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_LEFT).setMargin(50 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
            leftArrow.setBackground(getResources().getDrawable(R.drawable.codi_leftbutton));
            main.addView(leftArrow);
            leftArrow.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                        shopName.setText(infos.get(index - 1).getShopName());

//                        if(index == 1)
//                        {
//                            main.removeView(leftArrow);
//                        }
//
//                        if(index == infos.size()-1)
//                        {
//                            main.addView(rightArrow);
//                        }


                        index--;
                        //backg.setBackground(getResources().getDrawable(R.drawable.codi1 + index%3));
                        if(index<0)
                            index = infos.size()-1;
                        slideView.setPage(index);
                    }

                    return true;
                }
            });
        }

        if(index < infos.size()) {
            rightArrow = new ImageView(getApplicationContext());
            rightArrow.setLayoutParams(new HangShowLayoutParam(81 * ScreenParameter.getScreenparam_x(), 116 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.CENTER_VERTICAL).addRules(RelativeLayout.ALIGN_PARENT_RIGHT).setMargin(0, 0, 50 * ScreenParameter.getScreenparam_x(), 0));
            rightArrow.setBackground(getResources().getDrawable(R.drawable.codi_rightbutton));
            main.addView(rightArrow);
            rightArrow.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    if(motionEvent.getAction() ==MotionEvent.ACTION_UP)
                    {
                    //    shopName.setText(infos.get(index+1).getShopName());

//                        if(index == infos.size()-2)
//                            main.removeView(rightArrow);
//
//                        if(index == 0)
//                            main.addView(leftArrow);


                        index++;
                        //backg.setBackground(getResources().getDrawable(R.drawable.codi1 + index%3));
                        if(index == infos.size())
                            index = 0;
                        slideView.setPage(index);
                    }

                    return true;
                }
            });
        }


    }
}
