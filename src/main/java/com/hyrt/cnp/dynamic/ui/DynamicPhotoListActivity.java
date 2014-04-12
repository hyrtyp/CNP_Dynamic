package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.request.DynamicPhotoListRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicPhotoListRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by GYH on 14-1-17.
 */
public class DynamicPhotoListActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    private DynamicPhoto.Model model;
    private String  Category;
    private TextView bottom_num;

    private DynamicPhoto selectPhoto;

    private Album mAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist);
        Intent intent = getIntent();
        mAlbum = (Album)intent.getSerializableExtra("album");
        if(mAlbum != null){
            titletext.setText(mAlbum.getAlbumName());
        }
        initView();
        loadData();
    }

    /**
     * 更新ui界面
     * */
    public void updateUI(DynamicPhoto.Model model){
        if(model==null){
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            String[] resKeys=new String[]{"getImagethpath","getTitle"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image1,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+model.getData().size()+" 张");
        }
    }
    private void initView(){
        bottom_num=(TextView)findViewById(R.id.bottom_num);
        gridView =(GridView)findViewById(R.id.cnp_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectPhoto = model.getData().get(i);
                showPop(gridView, selectPhoto.getImagepics());
            }
        });
    }

    @Override
    public void showPop(View view, String bigImgPath) {
        View popView = this.getLayoutInflater().inflate(
                R.layout.layout_dynamic_photo_popupwindow, null);
        LinearLayout layout_photo_forward = (LinearLayout) popView.findViewById(R.id.layout_photo_forward);
        LinearLayout layout_photo_comment = (LinearLayout) popView.findViewById(R.id.layout_photo_comment);
        TextView tv_photo_detail = (TextView) popView.findViewById(R.id.tv_photo_detail);
        PhotoView pop_img = (PhotoView) popView.findViewById(R.id.pop_img);

        popView.setOnClickListener(mPopOnClickListener);
        layout_photo_forward.setOnClickListener(mPopOnClickListener);
        layout_photo_comment.setOnClickListener(mPopOnClickListener);
        tv_photo_detail.setOnClickListener(mPopOnClickListener);
        pop_img.setOnClickListener(mPopOnClickListener);

        popWin = new PopupWindow(popView, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        // 需要设置一下此参数，点击外边可消失
        popWin.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        popWin.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popWin.setFocusable(true);
        popWin.setTouchable(true);
        popWin.showAtLocation(view, Gravity.CENTER, 0, 0);
        showDetailImage1(bigImgPath, pop_img, false, true);
    }

    private View.OnClickListener mPopOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.layout_photo_forward){
                Intent intent = new Intent();
                intent.setClass(DynamicPhotoListActivity.this, DynamicPhotoInfoActivity.class);
                intent.putExtra("dynamicPhoto", selectPhoto);
                intent.putExtra("album", mAlbum);
                startActivity(intent);
            }else if(view.getId() == R.id.layout_photo_comment){
                Intent intent = new Intent();
                intent.setClass(DynamicPhotoListActivity.this, DynamicPhotoInfoActivity.class);
                intent.putExtra("dynamicPhoto", selectPhoto);
                intent.putExtra("album", mAlbum);
                startActivity(intent);
            }else if(view.getId() == R.id.tv_photo_detail){
                Intent intent = new Intent();
                intent.setClass(DynamicPhotoListActivity.this, DynamicPhotoInfoActivity.class);
                intent.putExtra("dynamicPhoto", selectPhoto);
                intent.putExtra("album", mAlbum);
                startActivity(intent);
            }else if(view.getId() == R.id.pop_img){

            }
            if(popWin != null){
                popWin.dismiss();
            }
            selectPhoto = null;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("新建相册")
                .setIcon(R.drawable.ic_actionbar_upload)
                .setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.mymenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("新建相册")){
            
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData(){
        DynamicPhotoListRequestListener sendwordRequestListener = new DynamicPhotoListRequestListener(this);
        sendwordRequestListener.setListener(mPhotoListRequestListener);
        DynamicPhotoListRequest schoolRecipeRequest=new DynamicPhotoListRequest(DynamicPhoto.Model.class,this,mAlbum.getPaId());
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private DynamicPhotoListRequestListener.RequestListener mPhotoListRequestListener =
            new DynamicPhotoListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(DynamicPhoto.Model data) {
            updateUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model=null;
        classRoomAdapter=null;
    }
}