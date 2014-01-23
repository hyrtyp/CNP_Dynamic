package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hyrt.cnp.account.model.ClassRoomBabay;
import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.account.model.UserDetail;
import com.hyrt.cnp.account.utils.FaceUtils;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.BabayDynamicRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-20.
 */
public class BabayIndexActivity extends BaseActivity{

    @Inject
    @Named("classroomAlbumActivity")
    private Class schoolPhotoActivity;
    @Inject
    @Named("userInfoActivity")
    private Class userInfoActivity;

    private ImageView faceview;
    private TextView nameview;
    private ClassRoomBabay classRoomBabay;
    private  ListView listView;

    private DynamicAdapter dynamicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babayindex);
        actionBar.hide();
        initView();
        initData();
        loadData();
    }

    private void initView(){
        faceview=(ImageView)findViewById(R.id.face_iv);
        nameview =(TextView)findViewById(R.id.name_tv);
        listView = (ListView)findViewById(R.id.dynamic_listview);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                startActivity(new Intent().setClass(BabayIndexActivity.this,DynamicCommentActivity.class));
//            }
//        });

        TextView all_daynamic=(TextView)findViewById(R.id.all_daynamic);
        TextView child_word=(TextView)findViewById(R.id.child_word);
        TextView daynamic_photos=(TextView)findViewById(R.id.daynamic_photos);
        TextView babay_information=(TextView)findViewById(R.id.babay_information);
        child_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("vo",classRoomBabay);
                intent.setClass(BabayIndexActivity.this,BabayWordActivity.class);
                startActivity(intent);
            }
        });

        daynamic_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(BabayIndexActivity.this,schoolPhotoActivity);
                intent.putExtra("Category","BabayIndexActivity");
                intent.putExtra("vo",classRoomBabay);
                startActivity(intent);
            }
        });

        babay_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetail.UserDetailModel userDetailModel=new UserDetail.UserDetailModel();
                UserDetail userDetail =new UserDetail();
                userDetail.setRenname("gyh");
                userDetail.setBirthday("2013-1-1");
                userDetail.setNurseryName("abc版");
                userDetail.setSex("man");
                userDetail.setNationality("chian");
                userDetail.setBloodType("A");
                Intent intent = new Intent();
                userDetailModel.setData(userDetail);
                intent.setClass(BabayIndexActivity.this,userInfoActivity);
                intent.putExtra("vo", userDetailModel);
                startActivity(intent);
            }
        });
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
        BabayDynamicRequestListener sendwordRequestListener = new BabayDynamicRequestListener(this);
        BabayDynamicRequest schoolRecipeRequest=new BabayDynamicRequest(Dynamic.Model.class,this,classRoomBabay.getUser_id()+"");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private void initData(){
        Intent intent=getIntent();
        classRoomBabay = (ClassRoomBabay)intent.getSerializableExtra("vo");
        showDetailImage(FaceUtils.getAvatar(classRoomBabay.getUser_id(),FaceUtils.FACE_BIG),faceview,false);
        nameview.setText(classRoomBabay.getRenname().toString());
    }
}
