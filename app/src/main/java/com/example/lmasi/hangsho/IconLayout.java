package com.example.lmasi.hangsho;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Vector;


public class IconLayout extends HorizontalScrollView {

    private RelativeLayout main;
    private int count = 0;
    private int index;

    private ArrayList<LikeIcon> likeIcons = new ArrayList<>();

    public IconLayout(Context context) {
        super(context);

        main = new RelativeLayout(getContext());
        count = 0;


        setHorizontalScrollBarEnabled(false);
    }

    public int getCount() {
        return count;
    }

    public void addObject(LikeIcon view)
    {

        if(count == 0)
        {
            view.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(40 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
            view.setId(view.hashCode());
            index = view.getId();
        }

        else
        {
            Log.e("CHECK", "WELL");
            view.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(30 * ScreenParameter.getScreenparam_x(), 0, 0, 0).addRules(RelativeLayout.RIGHT_OF, index));
            view.setId(view.hashCode());
            index = view.getId();
        }

        main.addView(view);
        likeIcons.add(view);
        count = likeIcons.size();
        Log.e("checkcount", Integer.toString(count));
    }

    public void removeObject(LikeIcon view)
    {
        if(likeIcons.indexOf(view) != likeIcons.size()-1 && likeIcons.indexOf(view) != 0)
        {
            LikeIcon likeIcon = likeIcons.get(likeIcons.indexOf(view) + 1);
            likeIcon.removeRule(RelativeLayout.RIGHT_OF);
            likeIcon.addRule(RelativeLayout.RIGHT_OF, likeIcons.get(likeIcons.indexOf(view)-1).getId());
        }

        else if(likeIcons.indexOf(view) != 0)
        {
            index = likeIcons.get(likeIcons.indexOf(view)-1).getId();
        }

        else if(count == 1)
        {

        }

        else
        {
            LikeIcon likeIcon = likeIcons.get(likeIcons.indexOf(view) + 1);
            likeIcon.removeRule(RelativeLayout.RIGHT_OF);
            likeIcon.setLayoutParams(new HangShowLayoutParam(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).addRules(RelativeLayout.CENTER_VERTICAL).setMargin(40 * ScreenParameter.getScreenparam_x(), 0, 0, 0));
        }

        main.removeView(view);
        likeIcons.remove(view);
        count = likeIcons.size();
    }

    public void removeObject(String name, String type)
    {
        for(int i=0; i<likeIcons.size(); i++)
            if(likeIcons.get(i).type.equals(type) && likeIcons.get(i).shopName.getText().toString().equals(name))
                removeObject(likeIcons.get(i));
    }

    public void setScrollView()
    {
        addView(main);
    }
}
