package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by GYH on 14-1-21.
 */
public class DynamicCommentActivity extends BaseActivity{

    private TextView textcon;
    private EditText editcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiccomment);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("setting")
                .setChecked(false)
                .setTitle("发送")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView(){
        textcon=(TextView)findViewById(R.id.text_context);
        editcon=(EditText)findViewById(R.id.edit_context);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("发送")){
            Toast.makeText(DynamicCommentActivity.this,"发送成功！",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        Intent intent = getIntent();
        Dynamic dynamic=(Dynamic)intent.getSerializableExtra("vo");
        textcon.setText(dynamic.getContent().toString());
        String Category=intent.getStringExtra("Category");
        if(Category.equals("pl")){
            titletext.setText("评论动态");
        }else{
            titletext.setText("转发动态");
        }
    }
}
