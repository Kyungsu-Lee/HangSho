package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lmasi on 2016-09-25.
 */
public class TimeLinePlace extends RelativeLayout {

    private ImageView icon;
    private TextView name;
    private TextView subname;

    boolean mov = false;

    double maxHeight;
    double minHeight;

    ImageView shirtIcon;
    ImageView deleteIcon;
    ImageView blackImage;

    Handler showHandler;
    Handler closeHandler;


    int stimer = 0;
    int ctimer = 0;


    public TimeLinePlace(Context context) {
        super(context);
        
        final float mv = (float)(11.8 * ScreenParameter.getScreenparam_x());

        showHandler = new Handler()
        {

            @Override
            public void handleMessage(Message msg) {

                if(stimer < 5)
                {
                    stimer++;

                    deleteIcon.setY(deleteIcon.getY() + mv);
                    deleteIcon.setX(deleteIcon.getX() + mv);

                    shirtIcon.setY(shirtIcon.getY() - mv);
                    shirtIcon.setX(shirtIcon.getX() + mv);

                    setLayoutParams(getLayoutParams());

                }

                sendEmptyMessageDelayed(0, 1);
            }

        };

        closeHandler = new Handler()
        {

            @Override
            public void handleMessage(Message msg) {

                if(ctimer < 5)
                {
                    ctimer++;

                    deleteIcon.setY(deleteIcon.getY() - mv);
                    deleteIcon.setX(deleteIcon.getX() - mv);

                    shirtIcon.setY(shirtIcon.getY() + mv);
                    shirtIcon.setX(shirtIcon.getX() - mv);
                }

                sendEmptyMessageDelayed(0, 1);
            }
        };

        ImageView blank = new ImageView(getContext());
        blank.setBackgroundColor(Color.argb(0, 255, 255, 255));
        blank.setLayoutParams(new HangShowLayoutParam(96 * ScreenParameter.getScreenparam_x(), 96 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_TOP, getId()).addRules(RelativeLayout.ALIGN_LEFT, getId()).setMargin(139 * ScreenParameter.getScreenparam_x(), 95 * ScreenParameter.getScreenparam_y(), 0, 0));
        addView(blank);

        ImageView blank2 = new ImageView(getContext());
        blank2.setBackgroundColor(Color.argb(0, 255, 255, 255));
        blank2.setLayoutParams(new HangShowLayoutParam(96 * ScreenParameter.getScreenparam_x(), 96 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.ALIGN_TOP, getId()).addRules(RelativeLayout.ALIGN_LEFT, getId()).setMargin(139 * ScreenParameter.getScreenparam_x(), -102 * ScreenParameter.getScreenparam_y(), 0, 0));
        addView(blank2);


        icon = new ImageView(getContext());
        icon.setBackground(getResources().getDrawable(R.drawable.timer_girleye));
        icon.setLayoutParams(new HangShowLayoutParam(96 * ScreenParameter.getScreenparam_x(), 96 * ScreenParameter.getScreenparam_y()).addRules(CENTER_VERTICAL));
        addView(icon);
        icon.setId(icon.hashCode());
        icon.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mov = true;

                Vibrator vibe = (Vibrator)getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(70);

                removeView(name);
                removeView(subname);

                return true;
            }
        });
        icon.setOnTouchListener(new OnTouchListener() {

            float X = 0;
            float Y = 0;

            boolean clicked = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_UP && !mov)
                {
                    final RelativeLayout main = (RelativeLayout)getParent().getParent();

                    if(!clicked)
                    {
                        if(blackImage == null) {
                            blackImage = new ImageView(getContext());
                            blackImage.setBackgroundColor(Color.argb(127, 188, 188, 188));
                            blackImage.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        }
                        main.addView(blackImage);

                        if(deleteIcon == null) {
                            deleteIcon = new ImageView(getContext());
                            deleteIcon.setBackground(getResources().getDrawable(R.drawable.timer_delete));
                            deleteIcon.setLayoutParams(new HangShowLayoutParam(61 * ScreenParameter.getScreenparam_x(), 61 * ScreenParameter.getScreenparam_y()).addRules(CENTER_VERTICAL).setMargin(15 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y(), 0, 0));
                            deleteIcon.setOnTouchListener(new OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                                    {
                                        clickDelete((View)getthis().getParent(), getthis());
                                        main.removeView(blackImage);
                                    }

                                    return true;
                                }
                            });
                        }
                        addView(deleteIcon);

                        if(shirtIcon == null) {
                            shirtIcon = new ImageView(getContext());
                            shirtIcon.setBackground(getResources().getDrawable(R.drawable.timer_codi));
                            shirtIcon.setLayoutParams(new HangShowLayoutParam(61 * ScreenParameter.getScreenparam_x(), 61 * ScreenParameter.getScreenparam_y()).addRules(CENTER_VERTICAL).setMargin(15 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y(), 0, 0));
                            shirtIcon.setOnTouchListener(new OnTouchListener() {
                                @Override
                                public boolean onTouch(View view, MotionEvent motionEvent) {
                                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                                        clickShirt(getthis());

                                    return true;
                                }
                            });
                        }
                        addView(shirtIcon);

                        icon.bringToFront();
                        ((RelativeLayout)getParent()).bringToFront();
                        bringToFront();

                        stimer = 0;
                        showHandler.sendEmptyMessage(0);
                        closeHandler.removeMessages(0);
                        invalidate();
                    }

                    else
                    {
                        final Handler handler = new Handler()
                        {
                            @Override
                            public void handleMessage(Message msg) {
                                main.removeView(blackImage);
                                removeView(shirtIcon);
                                removeView(deleteIcon);
                            }
                        };

                        ctimer = 0;
                        closeHandler.sendEmptyMessage(0);
                        showHandler.removeMessages(0);

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(0);
                            }
                        },180);
                    }

                    clicked = !clicked;

                }

                else if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && !mov)
                {
                    X = motionEvent.getRawX();
                    Y = motionEvent.getRawY();


                    Log.e("LOCATION", Float.toString(motionEvent.getRawY()) + " " + maxHeight);
                }

                else if(motionEvent.getAction() == MotionEvent.ACTION_UP && mov)
                {
                    mov = false;

                    addView(name);
                    addView(subname);
                }

                else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && motionEvent.getRawY() < maxHeight && motionEvent.getRawY() > minHeight)
                {
                    if(mov)
                    {
                        float y = motionEvent.getRawY() - Y;

                        //setY(getY() + y);

                        X = motionEvent.getRawX();
                        Y = motionEvent.getRawY();

                    }
                }

                return false;
            }
        });

        name = new TextView(getContext());
        name.setTextColor(Color.argb(255, 255, 255, 255));
        name.setTypeface(FontResource.AppleSDGothicNeoB00);
        name.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(20 * ScreenParameter.getScreenparam_y()));
        name.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RIGHT_OF, icon.getId()).setMargin(5 * ScreenParameter.getScreenparam_x(), 72 * ScreenParameter.getScreenparam_y(), 0, 0));
        addView(name);
        name.setId(name.hashCode());

        subname = new TextView(getContext());
        subname.setTextColor(Color.argb(255, 255, 255, 255));
        subname.setTypeface(FontResource.AppleSDGothicNeoB00);
        subname.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(15 * ScreenParameter.getScreenparam_y()));
        subname.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RIGHT_OF, icon.getId()).addRules(RelativeLayout.BELOW, name.getId()).setMargin(5 * ScreenParameter.getScreenparam_x(), 2 * ScreenParameter.getScreenparam_y(), 0, 0));
        addView(subname);

        setId(hashCode());
    }

    public void setIcon(int drawable)
    {
        icon.setBackground(getResources().getDrawable(drawable));
        icon.invalidate();
    }

    public void setName(String name)
    {
        this.name.setText(name);
    }

    public void setSubName(String subName)
    {
        this.subname.setText(subName);
    }

    public TimeLinePlace setLocation(double y)
    {
        this.setY(this.getY() + (int)(y * ScreenParameter.getScreenparam_y()));

        return this;
    }

    public TimeLinePlace getthis()
    {
        return this;
    }

    public TimeLinePlace setRange(int min, int max)
    {
        this.minHeight = min * ScreenParameter.getScreenparam_y();
        this.maxHeight = max * ScreenParameter.getScreenparam_y();

        return this;
    }

    public void clickShirt(View view)
    {

    }

    public void clickDelete(View view, View child)
    {
        RelativeLayout main = (RelativeLayout)getParent().getParent();
    }

    public String getshopName()
    {
            return name.getText().toString();
    }

    public String getSubName()
    {
        return subname.getText().toString();
    }

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
