package com.zhaoxuan.combowidget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zhaoxuan.combowidget.embed.EmbedUiManager;
import com.zhaoxuan.combowidget.embed.LoadingView;
import com.zhaoxuan.combowidget.embed.NoDataTips;
import com.zhaoxuan.combowidget.embed.ScrollTopView;
import com.zhaoxuan.combowidget.embed.TopToast;

/**
 * 嵌入式控件 父Activity
 * Created by lizhaoxuan on 15/12/11.
 */
public class BaseComboActivity extends AppCompatActivity {

    protected EmbedUiManager embedUiManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //初始化Package
        initPackageHelper(layoutResID);

        toolbar = embedUiManager.getToolBar() ;

        setContentView(embedUiManager.getRootView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;

    }

    /**
     * 子类可以通过重写该方法，改变BaseActivity创建模式
     * @param layoutResID
     */
    protected void initPackageHelper(int layoutResID){
        embedUiManager = new EmbedUiManager.Builder(this,layoutResID)
                .setToolbar(R.layout.widget_toolbar,R.id.id_tool_bar)
                //.setTopWidget(new TopToast(this))
                .setTopWidget(new ScrollTopView(this))
                .addCoverWidgets(new View[]{new NoDataTips(this), new LoadingView(this)})
                .build();
    }

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0, 0);
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }


    /**
     * 因CoverWidget 与 BottomWidget可能为多个，只有通过下标才能获取
     * 避免子类下标引用错误
     * 建议在父类中提供View的显示和隐藏方法
     */

    protected void showNoDataTips(){
        embedUiManager.getViewForCoverWidget(0).show();
    }

    protected void hideNoDataTips(){
        embedUiManager.getViewForCoverWidget(0).hide();
    }

    protected void showLoading(){
        embedUiManager.getViewForCoverWidget(1).show();
    }

    protected void hideLoading(){
        embedUiManager.getViewForCoverWidget(1).hide();
    }

}
