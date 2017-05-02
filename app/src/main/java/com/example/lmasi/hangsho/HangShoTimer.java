package com.example.lmasi.hangsho;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.TextView;



public abstract class HangShoTimer extends TextView {

    private int leftTime;
    private Handler timer;
    private boolean condition = true;

    public HangShoTimer(Context context) {
        super(context);

        this.setBackground(getResources().getDrawable(R.drawable.timer_backgrad));
        this.setGravity(Gravity.CENTER);

        leftTime = 0;

        timer = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {

                if(condition) {

                    if (leftTime != 0) {
                        leftTime--;
                        int hour = leftTime / 60;
                        int min = leftTime % 60;

                        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
                        nf.setMinimumIntegerDigits(2);
                        setText(nf.format(hour) + " : " + nf.format(min));

                        action(leftTime);
                    }

                    else if(leftTime == 0)
                    {
                        timeOver();
                        leftTime--;
                    }
                }
                sendEmptyMessageDelayed(0, 1000);
            }
        };
    }

    public void setTime(int time)
    {
        this.leftTime = time;
    }

    public void stopTimer(){this.condition = false;}

    public void resumeTimer(){this.condition = true;}

    public void startTimer()
    {
        timer.sendEmptyMessage(0);
    }

    public abstract void action(int time);

    public abstract void timeOver();
}
