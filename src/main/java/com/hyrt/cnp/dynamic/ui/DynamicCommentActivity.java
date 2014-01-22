package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.hyrt.cnp.dynamic.R;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by GYH on 14-1-21.
 */
public class DynamicCommentActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiccomment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("setting")
                .setChecked(false)
                .setTitle("发送")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }
}
