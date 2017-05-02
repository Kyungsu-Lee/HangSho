package com.example.lmasi.hangsho;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lmasi on 2016-09-25.
 */
public class Wave extends ImageView {

    public Wave(Context context) {
        super(context);

        this.setBackground(getResources().getDrawable(R.drawable.timer_wave));
        setId(this.hashCode());
    }
}
