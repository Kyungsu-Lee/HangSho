package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;


public class HistroryCard extends RelativeLayout {

    private RelativeLayout relativeLayout;
    private TouchImageView evaluated_image;
    private TextView date_place;
    private TextView date_info;
    private ArrayList<HistoryRoot> roots;
    private ArrayList<TouchImageView> lines;
    private HorizontalScrollView scrollView;


    public HistroryCard(Context context, boolean evaluated, String place, String info, Vector<Pair<String, Boolean>> roots) {
        super(context);

        // evaluated -> up : true // down : false
        // roots -> man : true // female : false

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(309 * ScreenParameter.getScreenparam_y()));
//        this.setLayoutParams(params);
        this.setBackgroundColor(Color.argb(102, 187, 221, 220));
        this.setHorizontalScrollBarEnabled(false);

        this.relativeLayout = new RelativeLayout(context);
        this.evaluated_image = new TouchImageView(context);
        this.date_place = new TextView(context);
        this.date_info = new TextView(context);
        this.roots = new ArrayList<HistoryRoot>();
        this.lines = new ArrayList<TouchImageView>();

        this.setPadding(0, 0, (int)(40 * ScreenParameter.getScreenparam_x()), 0);


        evaluated_image.setSize(92 * ScreenParameter.getScreenparam_x(), 92 * ScreenParameter.getScreenparam_y());
        evaluated_image.setLocation(67 * ScreenParameter.getScreenparam_x(), 64 * ScreenParameter.getScreenparam_y());
        evaluated_image.setBackground(evaluated ? R.drawable.home_manicon2 : R.drawable.home_manicon1);
        this.addView(evaluated_image);
        evaluated_image.setId(evaluated_image.hashCode());

        date_place.setText(place);
        RelativeLayout.LayoutParams params_place = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_place.setMargins((int)(170 * ScreenParameter.getScreenparam_x()),(int)( 74 * ScreenParameter.getScreenparam_y()), 0, 0);
        date_place.setLayoutParams(params_place);
        date_place.setTypeface(FontResource.AppleSDGothicNeoB00);
        date_place.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(30 * ScreenParameter.getScreenparam_y()));
        date_place.setTextColor(Color.argb(255, 117, 125, 133));
        this.addView(date_place);

        date_info.setText(info);
        RelativeLayout.LayoutParams params_info = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params_info.setMargins((int)(173 * ScreenParameter.getScreenparam_x()),(int)( 120 * ScreenParameter.getScreenparam_y()), 0, 0);
        date_info.setLayoutParams(params_info);
        date_info.setTypeface(FontResource.AppleSDGothicNeoB00);
        date_info.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        date_info.setTextColor(Color.argb(76, 34, 30, 31));
        this.addView(date_info);


        for(int i=0; (roots != null) && (i<roots.size()); i++)
        {
            this.roots.add(new HistoryRoot(context, roots.get(i).second, roots.get(i).first));
            this.roots.get(i).setId(this.roots.get(i).hashCode());
        }


        RelativeLayout.LayoutParams _param0 = this.roots.get(0).getparams();
        _param0.addRule(RelativeLayout.ALIGN_LEFT, evaluated_image.getId());
        relativeLayout.addView(this.roots.get(0).setLocation(0 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y()));

        for(int i=1; i<this.roots.size(); i++)
        {
            HistoryRoot historyRoot = this.roots.get(i);
            RelativeLayout.LayoutParams _param = historyRoot.getparams();
            _param.addRule(RelativeLayout.RIGHT_OF, this.roots.get(i-1).getId());
            historyRoot.SetParams(_param);
            historyRoot.setLocation(50 * ScreenParameter.getScreenparam_x(), 0 * ScreenParameter.getScreenparam_y());
            relativeLayout.addView(historyRoot);
        }

        scrollView = new HorizontalScrollView(context);
        scrollView.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).setMargin(0, 193 * ScreenParameter.getScreenparam_y(), 0, 0).addRules(RelativeLayout.ALIGN_LEFT, evaluated_image.getId()));
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);


        scrollView.addView(relativeLayout);
        this.addView(scrollView);
    }


}
