package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.hyrt.cnp.account.model.BabyInfo;
import com.hyrt.cnp.account.model.ClassRoomBabay;
import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.account.model.UserDetail;
import com.hyrt.cnp.account.utils.FaceUtils;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.request.BabayInfoRequest;
import com.hyrt.cnp.dynamic.requestListener.BabayDynamicRequestListener;
import com.hyrt.cnp.dynamic.requestListener.BabayInfoRequestListener;
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
    private ImageView imageviewback;
    private TextView nameview;
    private TextView introview;
    private ClassRoomBabay classRoomBabay;
    private  ListView listView;

    private BabyInfo babyInfo;

    private DynamicAdapter dynamicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babayindex);
        actionBar.hide();
        initView();
        loadData();
    }

    private void initView(){
        imageviewback=(ImageView)findViewById(R.id.imageviewback);
        faceview=(ImageView)findViewById(R.id.face_iv);
        nameview =(TextView)findViewById(R.id.name_tv);
        introview=(TextView)findViewById(R.id.intro);
        listView = (ListView)findViewById(R.id.dynamic_listview);
        TextView all_daynamic=(TextView)findViewById(R.id.all_daynamic);
        TextView child_word=(TextView)findViewById(R.id.child_word);
        TextView daynamic_photos=(TextView)findViewById(R.id.daynamic_photos);
        TextView babay_information=(TextView)findViewById(R.id.babay_information);
        imageviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                if(babyInfo!=null){
                    showBabayInfo();
                }else{
                    loadBabayinfoData();
                }

            }
        });
        Intent intent=getIntent();
        classRoomBabay = (ClassRoomBabay)intent.getSerializableExtra("vo");
        showDetailImage(FaceUtils.getAvatar(classRoomBabay.getUser_id(),FaceUtils.FACE_BIG),faceview,false);
        nameview.setText(classRoomBabay.getRenname().toString());
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


    private void loadBabayinfoData(){
        BabayInfoRequestListener sendwordRequestListener = new BabayInfoRequestListener(this);
        BabayInfoRequest schoolRecipeRequest=new BabayInfoRequest(BabyInfo.Model.class,this,classRoomBabay.getUser_id()+"");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void UpdataBabayinfo(BabyInfo.Model babyInfomodel){
        babyInfo=babyInfomodel.getData();
        showDetailImage(FaceUtils.getAvatar(babyInfo.getUser_id(),FaceUtils.FACE_BIG),faceview,false);
        nameview.setText(babyInfo.getRenname().toString());
        showBabayInfo();
    }

    public void loadData(){
        BabayDynamicRequestListener sendwordRequestListener = new BabayDynamicRequestListener(this);
        BabayDynamicRequest schoolRecipeRequest=new BabayDynamicRequest(Dynamic.Model.class,this,classRoomBabay.getUser_id()+"");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void showBabayInfo(){
        UserDetail.UserDetailModel userDetailModel=new UserDetail.UserDetailModel();
        UserDetail userDetail =new UserDetail();
        userDetail.setRenname(babyInfo.getRenname());
        userDetail.setBirthday(babyInfo.getBirthday());
        userDetail.setNurseryName(babyInfo.getNurseryName());
        userDetail.setSex(babyInfo.getSex());
        userDetail.setNationality("chian");
        userDetail.setBloodType("A");
        userDetailModel.setData(userDetail);
        Intent intent = new Intent();
        intent.setClass(BabayIndexActivity.this,userInfoActivity);
        intent.putExtra("vo", userDetailModel);
        startActivity(intent);
    }
}
