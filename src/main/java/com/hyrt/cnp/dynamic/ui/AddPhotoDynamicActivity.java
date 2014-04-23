package com.hyrt.cnp.dynamic.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.ui.AlbumBrowserActivity;
import com.hyrt.cnp.base.account.ui.LightAlertDialog;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.AddPhotoAdapter;
import com.hyrt.cnp.dynamic.request.AddPhotoRequest;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.AddPhotoRequestListener;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by Zoe on 2014-04-08.
 */
public class AddPhotoDynamicActivity extends BaseActivity{

    private EditText etContent;
    private RelativeLayout layoutChangeAblum;
    private TextView tvAblumText;
    private ImageView btnAddPhoto;
    private MenuItem sendbtn;
    private ImageView photo;
    private GridView gvPhoto;

    private AddPhotoAdapter mAdapter;

    private Uri faceFile;
    private PhotoUpload photoUpload;
    private Bitmap bitmap;
    private File targetFile;

    private Album selectAlbum;

    private List<Album> datas = new ArrayList<Album>();
    private ArrayList<String> checkeds = new ArrayList<String>();

    public static final int RESULT_FOR_CHANGE_ALBUM = 101;
    public static final int RESULT_FOR_PHONE_ALBUM = 102;

    private boolean inited = false;

    private static final String TAG = "AddPhotoDynamicActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(inited){
           return;
        }
        inited = true;
        setContentView(R.layout.activity_add_ablum);
        findView();

        mAdapter = new AddPhotoAdapter(checkeds, this);
        gvPhoto.setAdapter(mAdapter);

        setListener();
        loadData();
    }

    private void loadData() {
        MyAlbumRequestListener requestListener = new MyAlbumRequestListener(this);
        requestListener.setListener(mAlbumRequestListener);
        MyAlbumRequest request = new MyAlbumRequest(Album.Model.class, this);
        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                requestListener.start());
    }

    private MyAlbumRequestListener.RequestListener mAlbumRequestListener = new MyAlbumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            datas.clear();
            datas.addAll(((Album.Model)data).getData());
            if(datas.size() > 0 && tvAblumText.getText().length() <= 0){
                tvAblumText.setText(datas.get(0).getAlbumName());
                selectAlbum = datas.get(0);
            }

        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    public void setListener(){
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face_cover.png"));
                photoUpload = new PhotoUpload(AddPhotoDynamicActivity.this, faceFile);
//                photoUpload.setRang(true);
                photoUpload.choiceItem();

                /*Intent intent = new Intent();
                intent.setClass(AddPhotoDynamicActivity.this, AlbumBrowserActivity.class);
                startActivity(intent);*/
            }
        });

        layoutChangeAblum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AddPhotoDynamicActivity.this, ChangeAlbumActivity.class);
                startActivityForResult(intent, RESULT_FOR_CHANGE_ALBUM);
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(sendbtn != null){
                    if(etContent.getText().length() > 0 && checkeds.size() > 0){
                        sendbtn.setEnabled(true);
//                        sendbtn.setTitle(Html.fromHtml("<font color='#ffffff'>上传</font>"));
                    }else{
                        sendbtn.setEnabled(false);
//                        sendbtn.setTitle(Html.fromHtml("<font color='#999999'>上传</font>"));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = null;
                targetFile = null;
                photo.setVisibility(View.GONE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.VISIBLE);
                btnAddPhoto.setVisibility(View.VISIBLE);
                if(sendbtn != null && checkeds.size() <= 0){
                    sendbtn.setEnabled(false);
//                    sendbtn.setTitle(Html.fromHtml("<font color='#999999'>上传</font>"));
                }
            }
        });

        mAdapter.setOnClickListener(new AddPhotoAdapter.OnClickListener() {
            @Override
            public void onClick(int type, int position) {
                if(type == 0){
                    Intent intent = new Intent();
                    intent.setClass(AddPhotoDynamicActivity.this, AlbumBrowserActivity.class);
                    intent.putStringArrayListExtra("checkeds", checkeds);
                    startActivityForResult(intent, AddPhotoDynamicActivity.RESULT_FOR_PHONE_ALBUM);
                }else{
                    checkeds.remove(checkeds.get(position));
                    mAdapter.notifyDataSetChanged();
                    if(sendbtn != null){
                        if(etContent.getText().length() > 0 && checkeds.size() > 0){
                            sendbtn.setEnabled(true);
//                            sendbtn.setTitle(Html.fromHtml("<font color='#ffffff'>上传</font>"));
                        }else{
                            sendbtn.setEnabled(false);
//                            sendbtn.setTitle(Html.fromHtml("<font color='#999999'>上传</font>"));
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (etContent.getText().length() > 0 && checkeds.size() > 0 && sendbtn != null) {
            sendbtn.setEnabled(true);
//            sendbtn.setTitle(Html.fromHtml("<font color='#ffffff'>上传</font>"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(resultCode == RESULT_FOR_CHANGE_ALBUM){
                Album tempSelectAlbum = (Album) data.getSerializableExtra("album");
                if(tempSelectAlbum != null){
                    selectAlbum = tempSelectAlbum;
                    tvAblumText.setText(selectAlbum.getAlbumName());
                }
            }else if (requestCode == PhotoUpload.FROM_CAMERA && data.getParcelableExtra("data") != null) {
                //保存剪切好的图片
                bitmap = data.getParcelableExtra("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "dynamic_upload_photo"+System.currentTimeMillis()+".png");
                android.util.Log.i(TAG, "getPath():"+targetFile.getPath());
                checkeds.add(targetFile.getPath());
                mAdapter.notifyDataSetChanged();
               /* BitmapDrawable bd = new BitmapDrawable(bitmap);
                photo.setBackground(bd);
                photo.setVisibility(View.VISIBLE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.GONE);
                btnAddPhoto.setVisibility(View.GONE);*/

                if(etContent.getText().length() > 0 && sendbtn != null){
                    sendbtn.setEnabled(true);
//                    sendbtn.setTitle(Html.fromHtml("<font color='#ffffff'>上传</font>"));
                }
            }else if(resultCode == RESULT_FOR_PHONE_ALBUM){
                if(data.getSerializableExtra("checkeds") != null){
                    checkeds.clear();
                    checkeds.addAll(data.getStringArrayListExtra("checkeds"));
                    mAdapter.notifyDataSetChanged();
                    if(sendbtn != null){
                        if(etContent.getText().length() > 0 && checkeds.size() > 0){
                            sendbtn.setEnabled(true);
//                            sendbtn.setTitle(Html.fromHtml("<font color='#ffffff'>上传</font>"));
                        }else{
                            sendbtn.setEnabled(false);
//                            sendbtn.setTitle(Html.fromHtml("<font color='#999999'>上传</font>"));
                        }
                    }
                }
            }
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
//        sendbtn.setTitle(Html.fromHtml("<font color='#999999'>上传</font>"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("上传") && selectAlbum != null){
            android.util.Log.i(TAG, "selectAlbum:"+selectAlbum.getAlbumName()+"-"+selectAlbum.getPaId());
            AddPhotoRequestListener requestListener = new AddPhotoRequestListener(this);
            requestListener.setListener(mAddPhotoRequestListener);
            AddPhotoRequest request =
                    new AddPhotoRequest(BaseTest.class, this, selectAlbum.getPaId()+"",
                            selectAlbum.getAlbumName(), etContent.getText().toString(), targetFile);
            spiceManager.execute(request, request.getcachekey(), 1,
                    requestListener.start());
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private AddPhotoRequestListener.RequestListener mAddPhotoRequestListener = new AddPhotoRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object o) {
            Toast.makeText(AddPhotoDynamicActivity.this, "发送成功", 0).show();
            Intent intent = new Intent();
            intent.setClass(AddPhotoDynamicActivity.this, DynamicPhotoListActivity.class);
            intent.putExtra("album", selectAlbum);
            startActivity(intent);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
//            Toast.makeText(AddPhotoDynamicActivity.this, "发送失败", 0).show();
            AlertDialog dialog = LightAlertDialog.create(AddPhotoDynamicActivity.this);
            dialog.setTitle("温馨提示");
            dialog.setMessage("发送失败!");
            dialog.setButton(BUTTON_POSITIVE, getString(android.R.string.ok),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
    };

    public void findView(){
        etContent = (EditText) findViewById(R.id.et_add_ablum_content);
        layoutChangeAblum = (RelativeLayout) findViewById(R.id.layout_change_ablum);
        tvAblumText = (TextView) findViewById(R.id.tv_ablum_text);
        btnAddPhoto = (ImageView) findViewById(R.id.btn_add_photo);
        photo = (ImageView) findViewById(R.id.iv_photo1);
        gvPhoto = (GridView) findViewById(R.id.gv_photo);
    }


}
