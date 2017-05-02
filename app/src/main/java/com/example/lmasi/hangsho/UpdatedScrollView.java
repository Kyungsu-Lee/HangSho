package com.example.lmasi.hangsho;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by lmasi on 2016-09-18.
 */
public class UpdatedScrollView extends ScrollView {

    RelativeLayout main;

    public UpdatedScrollView(Context context) {
        super(context);

        main = new RelativeLayout(getContext());
    }

    public void addObject(View view)
    {
        main.addView(view);
    }

    public void showScroll()
    {
        this.addView(main);
    }
}
