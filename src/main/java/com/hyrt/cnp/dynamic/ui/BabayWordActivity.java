package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hyrt.cnp.dynamic.R;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GYH on 14-1-21.
 */
public class BabayWordActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babayword);
        initView();
    }

    private void initView(){
        ListView listView = (ListView)findViewById(R.id.dynamic_listview);
        List<Map<String,Object>> contents = new ArrayList<Map<String, Object>>();
        int [] touid=new int[]{R.drawable.babay1,R.drawable.babay2,R.drawable.babay3,R.drawable.babay4};
        String[] name=new String[]{"andy丽丽","许安安","甄炎","燕燕"};
        String[] time=new String[]{"25分钟前","29分钟前","26分钟前","24分钟前"};
        String[] contexts=new String[]{"谁是测试实施好似","","谁是测试实施好似","谁是测试实施好似"};
        int [] photos1 = new int[]{0,R.drawable.image_test4,R.drawable.image_test2,R.drawable.image_test3};
        int [] photos2= new int[]{0,R.drawable.image_test4,R.drawable.image_test2,R.drawable.image_test3};
        int [] photos3 = new int[]{0,R.drawable.image_test4,R.drawable.image_test2,R.drawable.image_test3};
        String [] strpl1=new String[]{"谁是测试实施好似","","谁是测试实施好似","谁是测试实施好似"};
        String [] strpl2=new String[]{"谁是测试实施好似","","谁是测试实施好似","谁是测试实施好似"};
        String [] strpl3=new String[]{"谁是测试实施好似","","谁是测试实施好似","谁是测试实施好似"};
        for (int i = 0; i < touid.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("touid",touid[i] );
            map.put("time", time[i]);
            map.put("contexts",contexts[i]);
            map.put("photos1",photos1[i]);
            map.put("strpl1",strpl1[i]);
            map.put("photos2",photos2[i]);
            map.put("strpl2",strpl2[i]);
            map.put("photos3",photos3[i]);
            map.put("strpl3",strpl3[i]);
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,
                contents, R.layout.layout_item_dynamic,
                new String[] { "touid", "time", "contexts","photos1", "strpl1", "photos2", "strpl2", "photos3"
                        , "strpl3"}, new int[] {
                R.id.dynamic_Avatar, R.id.dynamic_time,
                R.id.dynamic_context, R.id.dynamic_image1,
                R.id.dynamic_commit1, R.id.dynamic_image2,
                R.id.dynamic_commit2, R.id.dynamic_image3,
                R.id.dynamic_commit3 });

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent().setClass(BabayWordActivity.this,DynamicCommentActivity.class));
            }
        });
    }
}
