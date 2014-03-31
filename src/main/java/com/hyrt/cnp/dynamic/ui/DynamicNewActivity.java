package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAddphotoAdapter;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by GYH on 2014/3/28.
 */
public class DynamicNewActivity extends BaseActivity{
    private MenuItem sendbtn;

    private DynamicAddphotoAdapter dynamicAddphotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamicnew);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.senddy, menu);
        menu.findItem(R.id.senddy).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.senddy);
        return true;
    }


    private void initView(){
//        dynamicAddphotoAdapter=new DynamicAddphotoAdapter();
    }

}
