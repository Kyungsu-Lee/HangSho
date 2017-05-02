package com.example.lmasi.hangsho;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by lmasi on 2016-10-15.
 */
public class Timer_Shop_List extends RelativeLayout {

    ArrayList<TimeLinePlace> timeLinePlaces;
    ImageView leftbar;

    public Timer_Shop_List(Context context, ArrayList<TimeLinePlace> places) {
        super(context);

        timeLinePlaces = places;
        setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, 962 * ScreenParameter.getScreenparam_y()).setMargin(38 * ScreenParameter.getScreenparam_x(), 250 * ScreenParameter.getScreenparam_y(), 0, 0));

        leftbar = new ImageView(getContext());
        leftbar.setBackground(getResources().getDrawable(R.drawable.timer_bar_left));

        for(int i=0; i<places.size(); i++)
            timeLinePlaces.get(i).setY(0f);

        if(timeLinePlaces.size() == 1)
        {
            this.addView(timeLinePlaces.get(0).setRange(1, 1));
        }
        else if(timeLinePlaces.size() == 2)
        {
            leftbar.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 352 * ScreenParameter.getScreenparam_y()).setMargin(40 * ScreenParameter.getScreenparam_x(), 65 * ScreenParameter.getScreenparam_y(), 0, 0));
            this.addView(leftbar);
            this.addView(timeLinePlaces.get(0).setRange(330, 650));
            this.addView(timeLinePlaces.get(1).setLocation(320).setRange(330, 660));
        }
        else if(timeLinePlaces.size() == 3)
        {
            leftbar.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 660 * ScreenParameter.getScreenparam_y()).setMargin(40 * ScreenParameter.getScreenparam_x(), 65 * ScreenParameter.getScreenparam_y(), 0, 0));
            this.addView(leftbar);
            this.addView(timeLinePlaces.get(0).setRange(330, 650));
            this.addView(timeLinePlaces.get(1).setLocation(320).setRange(330, 970));
            this.addView(timeLinePlaces.get(2).setLocation(640).setRange(330, 970));
        }
        else if(timeLinePlaces.size() == 4)
        {
            leftbar.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 660 * ScreenParameter.getScreenparam_y()).setMargin(40 * ScreenParameter.getScreenparam_x(), 65 * ScreenParameter.getScreenparam_y(), 0, 0));
            this.addView(leftbar);
            this.addView(timeLinePlaces.get(0).setRange(330, 650));
            this.addView(timeLinePlaces.get(1).setLocation(214).setRange(330, 970));
            this.addView(timeLinePlaces.get(2).setLocation(428).setRange(330, 970));
            this.addView(timeLinePlaces.get(3).setLocation(640).setRange(330, 970));
        }
        else if(timeLinePlaces.size() == 5)
        {
            leftbar.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 660 * ScreenParameter.getScreenparam_y()).setMargin(40 * ScreenParameter.getScreenparam_x(), 65 * ScreenParameter.getScreenparam_y(), 0, 0));
            this.addView(leftbar);
            this.addView(timeLinePlaces.get(0).setRange(330, 650));
            this.addView(timeLinePlaces.get(1).setLocation(160).setRange(330, 970));
            this.addView(timeLinePlaces.get(2).setLocation(320).setRange(330, 970));
            this.addView(timeLinePlaces.get(3).setLocation(480).setRange(330, 970));
            this.addView(timeLinePlaces.get(4).setLocation(640).setRange(330, 970));
        }
        else if(timeLinePlaces.size() >= 6)
        {
            double margin = 910;
            leftbar.setLayoutParams(new HangShowLayoutParam(13 * ScreenParameter.getScreenparam_x(), 930 * ScreenParameter.getScreenparam_y()).setMargin(40 * ScreenParameter.getScreenparam_x(), 65 * ScreenParameter.getScreenparam_y(), 0, 0));
            this.addView(leftbar);
            for(int i=0; i<timeLinePlaces.size(); i++)
                this.addView(timeLinePlaces.get(i).setLocation((margin * i)/(timeLinePlaces.size()-1)).setRange(200, 1100));
        }

        if(timeLinePlaces.size()>5)
            setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).setMargin(38 * ScreenParameter.getScreenparam_x(), 100 * ScreenParameter.getScreenparam_y(), 0, 0));


    }
}
