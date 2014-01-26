package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.hyrt.cnp.account.model.ClassRoomBabay;
import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabaywordRequest;
import com.hyrt.cnp.dynamic.requestListener.BabaywordRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-21.
 * 童言稚语
 */
public class BabayWordActivity extends BaseActivity{

    private DynamicAdapter dynamicAdapter;
    private ListView listView;
    private ClassRoomBabay classRoomBabay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babayword);
        initView();
        initData();
        loadData();
    }

    private void initView(){
        listView = (ListView)findViewById(R.id.dynamic_listview);
    }

    private void initData(){
        Intent intent=getIntent();
        classRoomBabay = (ClassRoomBabay)intent.getSerializableExtra("vo");
    }

    public void updateUI(Dynamic.Model model){
        String[] resKeys=new String[]{"getUserphoto","getUserName",
                "getPosttime","getContent",
                "getsPicAry0","getsPicAry1",
                "getsPicAry2","getPosttime"};
        int[] reses=new int[]{R.id.dynamic_Avatar,R.id.dynamic_name,
                R.id.dynamic_time,R.id.dynamic_context,
                R.id.dynamic_image1,R.id.dynamic_image2,
                R.id.dynamic_image3,R.id.dynamic_time2};
        dynamicAdapter = new DynamicAdapter(this,model.getData(),R.layout.layout_item_dynamic,resKeys,reses);
        listView.setAdapter(dynamicAdapter);
    }


    private void loadData(){
        BabaywordRequestListener sendwordRequestListener = new BabaywordRequestListener(this);
        BabaywordRequest schoolRecipeRequest=new BabaywordRequest(Dynamic.Model.class,this,classRoomBabay.getUser_id()+"");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
