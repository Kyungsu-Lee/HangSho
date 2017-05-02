package com.example.lmasi.hangsho;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by lmasi on 2016-09-18.
 */
public class HangShoScrollView extends ScrollView {

    RelativeLayout main;

    TextView like;
    TextView storeName;

    int index_like;
    int index_store;

    public HangShoScrollView(Context context) {
        super(context);

        main = new RelativeLayout(getContext());

        like = new TextView(getContext());
        like.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 51 * ScreenParameter.getScreenparam_y()));
        like.setTextColor(Color.argb(255, 108, 116, 182));
        like.setText("좋아요");
        like.setTypeface(FontResource.AppleSDGothicNeoB00);
        like.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        like.setGravity(Gravity.CENTER_VERTICAL);
        like.setPadding((int)(30 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        like.setBackgroundColor(Color.WHITE);
        like.setId(like.hashCode());
        main.addView(like);

        storeName = new TextView(getContext());
        storeName.setLayoutParams(new HangShowLayoutParam(ScreenParameter.getScreen_x(), 51 * ScreenParameter.getScreenparam_y()).addRules(RelativeLayout.BELOW, like.getId()).setMargin(0, (int)(2 * ScreenParameter.getScreenparam_y()), 0, 0));
        storeName.setTextColor(Color.argb(255, 108, 116, 182));
        storeName.setText("매장명");
        storeName.setTypeface(FontResource.AppleSDGothicNeoB00);
        storeName.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int)(24 * ScreenParameter.getScreenparam_y()));
        storeName.setGravity(Gravity.CENTER_VERTICAL);
        storeName.setPadding((int)(30 * ScreenParameter.getScreenparam_x()), 0, 0, 0);
        storeName.setBackgroundColor(Color.WHITE);
        storeName.setId(storeName.hashCode());
        main.addView(storeName);

        index_like = like.getId();
        index_store = storeName.getId();


    }

    public void addObject(StoreCard view)
    {
        if(view.isLike())
        {
            view.addRule(RelativeLayout.BELOW, index_like);
            view.setId(view.hashCode());
            index_like = view.getId();
            removeStoreNameRule(RelativeLayout.BELOW);
            addStoreNameRule(RelativeLayout.BELOW, index_like);
        }

        else
        {
            view.addRule(RelativeLayout.BELOW, index_store);
            view.setId(view.hashCode());
            index_store = view.getId();
        }

        main.addView(view);
    }


    public void setScrollView()
    {
        addView(main);
    }

    private void addStoreNameRule(int verb)
    {
        HangShowLayoutParam hangShowLayoutParam = (HangShowLayoutParam)storeName.getLayoutParams();
        hangShowLayoutParam.addRule(verb);
    }

    private void addStoreNameRule(int verb, int subject)
    {
        HangShowLayoutParam hangShowLayoutParam = (HangShowLayoutParam)storeName.getLayoutParams();
        hangShowLayoutParam.addRule(verb, subject);
    }

    private void removeStoreNameRule(int verb)
    {
        HangShowLayoutParam hangShowLayoutParam = (HangShowLayoutParam)storeName.getLayoutParams();
        hangShowLayoutParam.removeRule(verb);
    }

    public void removeAllView()
    {
        this.main.removeAllViews();
        main.addView(like);
        main.addView(storeName);
        index_like = like.getId();
        index_store = storeName.getId();
        removeView(main);
    }



}
