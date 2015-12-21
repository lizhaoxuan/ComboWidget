package com.zhaoxuan.combowidget.embed.banner;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhaoxuan.combowidget.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 负责子banner（CustomBannerItem）管理：获取、隐藏、显示、轮播
 * 该控件所有外部接口均为protected，使用BannerManager增加业务逻辑做显示控制
 * 禁止直接使用
 * Created by lizhaoxuan on 15/12/16.
 */
public class ScrollBanner extends LinearLayout implements ScrollBannerItem.OnCloseListener {
    private static final int WHEEL_SHOW = 0;

    //banner高度
    private static int bannerHeight = 0;

    private Context context;

    private LayoutParams layoutParams;

    //banner点击事件接口
    private OnBannerClickListener bannerClickListener;

    //当前展示View
    private int showSize = 0;

    //CustomBannerItem滚播列表
    private ScrollBannerItem[] scrollBanners;

    //需要展示的banner data数据集
    private List<IBannerDto> bannerDatas = new ArrayList<>();

    //banner 数据轮播标志位
    private int showPosition = 0;

    //banner View轮播标志位
    private int viewPosition = 0;

    //是否正在轮播
    private boolean isWheelShowing = false;

    //是否收到广播
    private boolean isSendMessage = true;

    private Handler handler;

    public ScrollBanner(Context context) {
        super(context);
        init(context);
    }

    public ScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        bannerHeight = (int) context.getResources().getDimension(R.dimen.banner_width);
        this.setOrientation(VERTICAL);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, bannerHeight);
        initScrollView();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == WHEEL_SHOW) {
                    if (showSize <= 0) {
                        hideCustomBanner();
                        isSendMessage = true;
                        return;
                    } else {
                        showCustomBanner();
                    }

                    if (showSize == 1) {
                        showOnlyOne();
                    } else {
                        showNext();
                    }
                    isSendMessage = true;
                }
            }
        };

    }

    private void initScrollView() {
        scrollBanners = new ScrollBannerItem[]{new ScrollBannerItem(context, 0),
                new ScrollBannerItem(context, 1)};
        this.addView(scrollBanners[0], layoutParams);
        this.addView(scrollBanners[1], layoutParams);
        scrollBanners[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerClickListener != null && scrollBanners[0].getBannerData() != null) {
                    bannerClickListener.onClick(scrollBanners[0].getBannerData());
                }
            }
        });
        scrollBanners[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bannerClickListener != null && scrollBanners[1].getBannerData() != null) {
                    bannerClickListener.onClick(scrollBanners[1].getBannerData());
                }
            }
        });
        scrollBanners[0].setCloseListener(this);
        scrollBanners[1].setCloseListener(this);
    }

    /**
     * 开始轮播展示
     */
    public void start() {
        if (showSize <= 0 || bannerDatas == null) {
            hideCustomBanner();
            return;
        } else if (isWheelShowing) {
            //如果正在进行轮播，就不能再发出开始轮播命令
            return;
        }
        if (isSendMessage) {
            isSendMessage = false;
            showSize = bannerDatas.size();
            int time = bannerDatas.get(0).getWheelTime();
            handler.sendMessageDelayed(handler.obtainMessage(WHEEL_SHOW), time);

        }
    }

    /**
     * 轮播显示下一个
     */
    private void showNext() {
        isWheelShowing = true;
        scrollBanners[1 - viewPosition].setBannerData(bannerDatas.get((showPosition + 1) % showSize));
        scrollBanners[viewPosition].setBannerData(bannerDatas.get(showPosition));

        startAnimation(scrollBanners[1 - viewPosition], scrollBanners[viewPosition]);

        viewPosition = 1 - viewPosition;
        showPosition = (showPosition + 1) % showSize;

        //返回下一个banner的保持时间
        int time = scrollBanners[viewPosition].getWheelTime();
        handler.sendMessageDelayed(handler.obtainMessage(WHEEL_SHOW), time);
    }

    /**
     * 只显示一个BannerItem
     */
    private void showOnlyOne() {
        isWheelShowing = false;
        scrollBanners[1 - viewPosition].setBannerData(bannerDatas.get(0));
        startAnimation(scrollBanners[1 - viewPosition], scrollBanners[viewPosition]);
        viewPosition = 1 - viewPosition;
        showPosition = 0;
    }

    private void startAnimation(ScrollBannerItem v1, ScrollBannerItem v2) {
        v2.setTitleFocusable(false);
        v1.setTitleFocusable(true);
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v1,
                "y", -bannerHeight, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v1,
                "x", 0f, 0f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v2,
                "y", 0f, bannerHeight);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v2,
                "x", 0f, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4);
        animSet.setDuration(500);
        animSet.start();
    }

    private void removeAnimation(ScrollBannerItem v1, ScrollBannerItem v2) {
        int width = v1.getWidth();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(v1,
                "y", 0f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(v1,
                "x", 0f, width);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(v2,
                "y", 0f, 0);
        ObjectAnimator anim4 = ObjectAnimator.ofFloat(v2,
                "x", -width, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim1).with(anim2).with(anim3).with(anim4);
        animSet.setDuration(250);
        animSet.start();
    }

    /**
     * 设置bannerItem数据
     */
    public void setBannerItems(List<IBannerDto> bannerDtos) {
        if (bannerDtos == null) {
            showSize = 0;
            return;
        }
        this.bannerDatas = bannerDtos;
        showSize = bannerDatas.size();
    }

    /**
     * 设置bannerItem数据
     */
    public void setBannerItems(BannerDto bannerDto) {
        if (bannerDto == null) {
            showSize = 0;
            return;
        }
        this.bannerDatas.clear();
        bannerDatas.add(bannerDto);
        showSize = bannerDatas.size();
    }

    /**
     * 移除bannerItem
     *
     * @param id viewId
     */
    @Override
    public void onClose(int id) {
        bannerDatas.remove(scrollBanners[id].getBannerData());
        if (bannerDatas.size() <= 0) {
            hideCustomBanner();
        } else {
            scrollBanners[1 - id].setBannerData(bannerDatas.get(showPosition = (showPosition + 1) % showSize));
            int time = scrollBanners[1 - id].getWheelTime() + 250;
            handler.sendMessageDelayed(handler.obtainMessage(WHEEL_SHOW), time);
        }
        removeAnimation(scrollBanners[id], scrollBanners[1 - id]);

    }

    public List<IBannerDto> getBannerDatas() {
        if (bannerDatas == null) {
            return bannerDatas = new ArrayList<>();
        }
        return bannerDatas;
    }



    public int getBannerHeight(){
        return bannerHeight;
    }

    public void setBannerClickListener(OnBannerClickListener listener) {
        this.bannerClickListener = listener;
    }

    private void showCustomBanner() {
        if (this.getVisibility() == GONE) {
            this.setVisibility(VISIBLE);
        }
    }

    private void hideCustomBanner() {
        isWheelShowing = false;
        if (showSize == 0) {
            this.setVisibility(GONE);
        }
    }

    /**
     * banner点击事件
     */
    public interface OnBannerClickListener {
        void onClick(IBannerDto bannerData);
    }

}
