package com.zhaoxuan.combowidget.embed.banner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoxuan.combowidget.R;


/**
 * Banner子项
 * Created by lizhaoxuan on 15/12/16.
 */
public class ScrollBannerItem extends LinearLayout {

    //该banner标示id
    private int id;

    private ImageView closeView;
    private TextView titleView;
    private TextView enterView;

    private Context context;
    private IBannerDto bannerData;
    private OnCloseListener closeListener;

    public ScrollBannerItem(Context context, int id) {
        super(context);
        this.context = context;
        init();
        this.id = id;
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_banner_item, this, true);
        closeView = (ImageView) findViewById(R.id.closeImg);
        titleView = (TextView) findViewById(R.id.tipsText);
        enterView = (TextView) findViewById(R.id.enterText);

        closeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeListener != null) {
                    closeListener.onClose(id);
                }
            }
        });
    }

    public ScrollBannerItem setBannerData(IBannerDto bannerData) {
        if (bannerData == null) {
            enterView.setVisibility(GONE);
            titleView.setText("");
            this.setBackgroundResource(0);
            return this;
        }
        this.bannerData = bannerData;
        updateView();
        return this;
    }

    private void updateView() {
        titleView.setText(bannerData.getTitle());
        if (!bannerData.getBgColor().equals("")) {
            this.setBackgroundColor(Color.parseColor(bannerData.getBgColor()));
        }

        if (bannerData.isClose()) {
            closeView.setVisibility(VISIBLE);
        } else {
            closeView.setVisibility(GONE);
        }

        int enterType = bannerData.getEnterType();

        if (enterType == IBannerDto.ENTER_TYPE_NULL) {
            enterView.setVisibility(GONE);
        } else {
            if (bannerData.getEnterIcon() != 0) {
                enterView.setCompoundDrawables(null, null, context.getResources().getDrawable(bannerData.getEnterIcon()), null);
            }
            if (enterType == IBannerDto.ENTER_TYPE_ALL) {
                //打开URL
                enterView.setVisibility(VISIBLE);
                enterView.setText(bannerData.getEnterStr());
            } else if (enterType == BannerDto.ENTER_TPYE_ONLY_ICON) {
                //打开Activity
                enterView.setVisibility(VISIBLE);
                enterView.setText("");
            }
        }

        titleView.setFocusableInTouchMode(true);
        titleView.setFocusable(true);
        titleView.requestFocus();
    }


    public ScrollBannerItem setText(String str) {
        titleView.setText(str);
        return this;
    }

    public void setCloseListener(OnCloseListener closeListener) {
        this.closeListener = closeListener;
    }

    @Override
    public int getId() {
        return id;
    }

    public IBannerDto getBannerData() {
        return bannerData;
    }

    public int getWheelTime() {
        return bannerData.getWheelTime() * 1000;
    }

    public boolean isShowing() {
        return this.getVisibility() == VISIBLE;
    }

    public void showView() {
        if (!isShowing()) {
            this.setVisibility(VISIBLE);
        }
        titleView.setFocusableInTouchMode(true);
        titleView.setFocusable(true);
    }

    public void hideView() {
        if (isShowing()) {
            this.setVisibility(GONE);
        }
    }

    public ScrollBannerItem setTitleFocusable(boolean focusable) {
        titleView.setFocusable(focusable);
        return this;
    }

    public interface OnCloseListener {
        void onClose(int id);
    }

}
