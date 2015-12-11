package com.zhaoxuan.combowidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhaoxuan.combowidget.flotage.NoDataTip;
import com.zhaoxuan.combowidget.flotage.TopToast;

/**
 * 漂浮式控件  演示Activity
 */
public class FlotageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button3, button4;

    private NoDataTip noDataTip ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flotage);

        noDataTip = new NoDataTip(this,null,"什么都没有");

        button1 = (Button) findViewById(R.id.button1);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.button1:
                TopToast.makeText(this,"漂浮式控件").showPopupWindow(button1);
                break;
            case R.id.button3:  noDataTip.show(button1);
                break;
            case R.id.button4:  noDataTip.hide();
                break;
        }

    }
}
