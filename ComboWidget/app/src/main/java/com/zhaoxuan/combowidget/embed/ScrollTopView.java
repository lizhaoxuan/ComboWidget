package com.zhaoxuan.combowidget.embed;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zhaoxuan.combowidget.R;

/**
 * Created by lizhaoxuan on 15/12/15.
 */
public class ScrollTopView extends LinearLayout {

    private CustomBanner[] topToastArray;
    private CustomBanner topToast1;
    private CustomBanner topToast2;
    private CustomBanner topToast3;
    private CustomBanner topToast4;

    private Context context;

    private int showPosition = 0;
    int length = 0;
    int height = 0;

    public ScrollTopView(Context context) {
        super(context);
        this.context = context;
        topToast1 = new CustomBanner(context).setBackground(Color.parseColor("#CD6090"));
        topToast1.enterText.setVisibility(GONE);
        topToast2 = new CustomBanner(context).setBackground(Color.parseColor("#F5F5DC"));
        topToast2.removeText.setVisibility(GONE);
        topToast3 = new CustomBanner(context).setBackground(Color.parseColor("#FF00FF"));
        topToast3.enterText.setVisibility(GONE);
        topToast3.removeText.setVisibility(GONE);
        topToast4 = new CustomBanner(context).setBackground(Color.parseColor("#9ACD32"));
        topToastArray = new CustomBanner[]{topToast1, topToast2, topToast3, topToast4};
        init();
    }

    private void init() {
        this.setOrientation(VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setLayoutParams(params);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        this.addView(topToastArray[0], params1);
        this.addView(topToastArray[1], params1);
        this.addView(topToastArray[2], params1);
        this.addView(topToastArray[3], params1);
        length = topToastArray.length;
        Log.d("TAG","first:"+topToast1.getHeight());
    }


    public void showNext() {
        if (height <= 0 ){
            height = topToast1.getHeight();
        }
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(topToastArray[(showPosition + 1)%length],
                "y", -height, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(topToastArray[showPosition],
                "y", 0f, height);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2);
        animSet.setDuration(500);
        animSet.start();
        showPosition = (showPosition+1)%length;
    }


}

