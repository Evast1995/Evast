package com.evast.evastcore.util.other;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.evast.evastcore.R;


/**
 * Created by hjiang on 15-12-29.
 */
public class AnimUtil {

    /**
     * 设置view 越来越高的动画
     * @param view
     */
    public static void setHeighterAndHeigher(View view){
        ScaleAnimation sAnima = new ScaleAnimation(1, 1, 0, 10);
        sAnima.setDuration(2000);
        view.startAnimation(sAnima);
    }

    /**
     * 设置从底端滑出的动画
     * @param view
     * @param context
     */
    public static void setSlipFromBottomToIn(View view,Context context){
        Animation in = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        view.startAnimation(in);
    }

    /**
     * 设置从上往下滑走的动画
     * @param view
     * @param context
     */
    public static void setSlipFromUpToOut(View view,Context context)
    {
        Animation out = AnimationUtils.loadAnimation(context, R.anim.slide_out_up);
        view.startAnimation(out);
    }
}
