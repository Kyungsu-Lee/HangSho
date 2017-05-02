package com.example.lmasi.hangsho;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Shark extends ImageView {

    private boolean right;
    private int speed;
    private double normalLocation;
    private Handler moveHandler;

    public Shark(Context context) {
        super(context);

        this.setBackground(getResources().getDrawable(R.drawable.timer_smallshark));
        this.speed = 50;

        moveHandler = new Handler()
        {
            boolean mov = true;

            @Override
            public void handleMessage(Message msg) {


                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();

                if(mov)
                {
                    params.topMargin +=1;
                    setLayoutParams(params);
                }

                else
                {
                    params.topMargin -=1;
                    setLayoutParams(params);
                }

                if(params.topMargin > (normalLocation + 5) * ScreenParameter.getScreenparam_y() || params.topMargin < (normalLocation - 5) * ScreenParameter.getScreenparam_y())
                    mov = !mov;


                sendEmptyMessageDelayed(0, speed);
            }
        };

        Handler handler = new Handler()
        {

            @Override
            public void handleMessage(Message msg) {

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)getLayoutParams();

                if(right)
                {
                    params.leftMargin += 1;
                    setLayoutParams(params);
                }
                else
                {
                    params.leftMargin -= 1;
                    setLayoutParams(params);
                }

                if(params.leftMargin > ScreenParameter.getScreen_x() - 92 * ScreenParameter.getScreenparam_x())
                {
                    setRight(false);
                }
                else if(params.leftMargin < 92 * ScreenParameter.getScreenparam_x())
                {
                    setRight(true);
                }

                sendEmptyMessageDelayed(0, 1);
            }
        };

        handler.sendEmptyMessage(0);

        Handler turn = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                Random rand = new Random();

                setRight(!Isright());

                sendEmptyMessageDelayed(0, rand.nextInt(15000) + 5000);
            }
        };

        turn.sendEmptyMessage(0);
    }

    public double getNormalLocation() {
        return normalLocation;
    }

    public void setNormalLocation(double normalLocation) {
        this.normalLocation = normalLocation;
    }

    public void setRight(boolean right) {
        this.right = right;

        if (right) {
            this.setScaleX(-1);
        } else {
            this.setScaleX(1);
        }
    }

    public boolean Isright() {
        return this.right;
    }

    public void startAnimation(int time)
    {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                moveHandler.sendEmptyMessage(0);
            }
        }, time);
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

}
