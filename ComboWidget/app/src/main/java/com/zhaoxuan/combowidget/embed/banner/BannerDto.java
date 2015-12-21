package com.zhaoxuan.combowidget.embed.banner;

/**
 * Created by lizhaoxuan on 15/12/16.
 */
public class BannerDto implements IBannerDto {

    /**
     * 根据类型不同，id含义不同
     * (通知是通知id，活动是活动id)
     */
    private int id;

    /**
     * banner 内容
     */
    private String title = "";

    /**
     * 点击动作
     * (0: 什么都不做, 1: 打开url, 2: 打开本地页面, 3: 执行操作)
     */
    private int actionType;

    /**
     * 动作参数
     */
    private String params = "";

    /**
     * banner滚播展示时间
     */
    private int wheelTime;

    /**
     * banner背景色
     */
    private String bgColor = "";

    /**
     * 右边图标类型
     */
    private int enterType;

    /**
     * 右边角标配套文字
     */
    private String enterStr = "";

    private boolean isClose;

    @Override
    public int getId() {
        return id;
    }

    public BannerDto(int id, String title, int actionType, String params, int wheelTime, String bgColor, int enterType, String enterStr, boolean isClose) {
        this.id = id;
        this.title = title;
        this.actionType = actionType;
        this.params = params;
        this.wheelTime = wheelTime;
        this.bgColor = bgColor;
        this.enterType = enterType;
        this.enterStr = enterStr;
        this.isClose = isClose;
    }

    @Override
    public String getBgColor() {
        return bgColor;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getEnterStr() {
        return enterStr;
    }

    @Override
    public int getEnterType() {
        return enterType;
    }

    @Override
    public int getEnterIcon() {
        return 0;
    }

    @Override
    public boolean isClose() {
        return isClose;
    }

    @Override
    public int getActionType() {
        return actionType;
    }

    @Override
    public int getWheelTime() {
        return wheelTime;
    }

    @Override
    public String getParams() {
        return params;
    }
}
