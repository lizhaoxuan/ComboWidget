package com.zhaoxuan.combowidget.embed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoxuan.combowidget.R;

/**
 * Created by lizhaoxuan on 15/12/15.
 */
public class CustomBanner extends LinearLayout {

    public TextView tipsText;
    public TextView removeText;
    public TextView enterText;

    public CustomBanner(Context context) {
        super(context);
        init(context, null);
    }
    private void init(final Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_custom_banner, this, true);
        removeText = (TextView) findViewById(R.id.removeText);
        tipsText = (TextView) findViewById(R.id.tipsText);
        enterText = (TextView) findViewById(R.id.enterText);

        removeText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"removeText",Toast.LENGTH_SHORT).show();
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"layout",Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void setTipsText(String tipsText) {
        this.tipsText.setText(tipsText);
    }

    public void setRemoveText(String removeText) {
        this.removeText.setText(removeText);
    }

    public void setEnterText(String enterText) {
        this.enterText.setText(enterText);
    }

    public CustomBanner setBackground(int color){
        this.setBackgroundColor(color);
        return this;
    }
}
