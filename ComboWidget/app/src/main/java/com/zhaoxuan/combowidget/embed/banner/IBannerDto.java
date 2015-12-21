package com.zhaoxuan.combowidget.embed.banner;

/**
 * Created by lizhaoxuan on 15/12/21.
 */
public interface IBannerDto {

    int ENTER_TYPE_NULL = 0;
    int ENTER_TPYE_ONLY_ICON = 1;
    int ENTER_TYPE_ALL = 2;

    int getId();

    String getBgColor();

    String getTitle();

    int getEnterType();

    String getEnterStr();

    int getEnterIcon();

    int getActionType();

    boolean isClose();

    int getWheelTime();

    String getParams();




}
