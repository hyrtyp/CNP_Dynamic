package com.hyrt.cnp.dynamic.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.dynamic.R;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by Zoe on 2014-04-08.
 */
public class AddAblumActivity extends BaseActivity{

    private EditText etContent;
    private RelativeLayout layoutChangeAblum;
    private TextView tvAblumText;
    private ImageView btnAddPhoto;
    private MenuItem sendbtn;

    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ablum);
        findView();
        setListener();
    }

    public void setListener(){
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("/sdcard/cnpFile/image.JPG");
                PhotoUpload pu = new PhotoUpload(AddAblumActivity.this, uri);
                pu.choiceItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            uploadPhoto(requestCode, data);
        }
    }

    public void uploadPhoto(int requestCode, Intent data){
        Uri photoUri = null;
        if(requestCode == SELECT_PIC_BY_PICK_PHOTO){
            if(data == null || data.getData() == null){
                Toast.makeText(this , getString(R.string.select_ablum_fail), Toast.LENGTH_SHORT).show();
                return;
            }
            photoUri = data.getData();
            Toast.makeText(this, photoUri.toString(), 0).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.upload, menu);
        menu.findItem(R.id.upload).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.upload);
        return true;
    }

    public void findView(){
        etContent = (EditText) findViewById(R.id.et_add_ablum_content);
        layoutChangeAblum = (RelativeLayout) findViewById(R.id.layout_change_ablum);
        tvAblumText = (TextView) findViewById(R.id.tv_ablum_text);
        btnAddPhoto = (ImageView) findViewById(R.id.btn_add_photo);
    }


}
