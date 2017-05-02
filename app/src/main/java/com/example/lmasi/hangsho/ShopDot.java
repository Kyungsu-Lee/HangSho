package com.example.lmasi.hangsho;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by lmasi on 2016-09-29.
 */
public class ShopDot extends ImageView {

    Animation anim;

    public ShopDot(Context context, int color) {
        super(context);

        this.setBackground(getResources().getDrawable(color == 0 ? R.drawable.add_boydot : R.drawable.add_girldot));

        anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);
        this.setAnimation(anim);
        anim.start();

    }
}
