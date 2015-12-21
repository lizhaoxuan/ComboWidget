package com.zhaoxuan.combowidget.embed;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhaoxuan.combowidget.R;

/**
 * Created by lizhaoxuan on 15/12/14.
 */
public class LoadingView extends RelativeLayout {
    private TextView loading_msg;

    public LoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_loading, this, true);
        loading_msg = (TextView) findViewById(R.id.loading_msg);

        if (attrs == null) {
            return;
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
            CharSequence keyStr = a.getText(R.styleable.LoadingView_android_text);
            if (keyStr != null) {
                loading_msg.setText(keyStr);
            }

            a.recycle();
        }
    }

    public LoadingView setMsg(String msg) {
        loading_msg.setText(msg);
        return this;
    }
}
