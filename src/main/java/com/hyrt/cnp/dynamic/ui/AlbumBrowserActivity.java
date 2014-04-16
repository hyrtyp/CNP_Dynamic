package com.hyrt.cnp.dynamic.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.PhoneAlbumAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-16.
 */
public class AlbumBrowserActivity extends BaseActivity{

    private static final String TAG = "AlbumBrowserActivity";
    private GridView mGridView;
    private TextView actionBarTitleCancel;
    private TextView actionBarTitleText;
    private TextView actionBarTitleSubmit;

    private PhoneAlbumAdapter mAdapter;

    private List<String> imgagePaths = new ArrayList<String>();
    private List<String> checkeds = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_browser);

        Intent intent = getIntent();
        List<String> tempCheckeds = intent.getStringArrayListExtra("checkeds");
        if(tempCheckeds != null){
            checkeds.addAll(tempCheckeds);
        }

        findView();
        setListener();
        mAdapter = new PhoneAlbumAdapter(imgagePaths, checkeds, this);
        mGridView.setAdapter(mAdapter);
        loadData();
    }

    public void loadData(){
        ContentResolver mContentResolver = getContentResolver();
        String[] projection = { MediaStore.Images.Thumbnails._ID, MediaStore.Images.Thumbnails.IMAGE_ID,
                MediaStore.Images.Thumbnails.DATA};
        Cursor mAlbumCursor = mContentResolver.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, null, null, null);
        imgagePaths.clear();
        if(mAlbumCursor.moveToFirst()){
            int _id;
            int image_id;
            String image_path;
            int _idColumn = mAlbumCursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
            int image_idColumn = mAlbumCursor.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            int dataColumn = mAlbumCursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);

            do {
                _id = mAlbumCursor.getInt(_idColumn);
                image_id = mAlbumCursor.getInt(image_idColumn);
                image_path = mAlbumCursor.getString(dataColumn);
                android.util.Log.i(TAG, "_id:" + _id + " image_id:" + image_id + " image_path:" + image_path);
                imgagePaths.add(image_path);
            } while (mAlbumCursor.moveToNext());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initTitleview() {
        actionBar.hide();
    }

    public void setListener(){
     actionBarTitleSubmit.setOnClickListener(mOnClickListener);
     actionBarTitleCancel.setOnClickListener(mOnClickListener);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.action_bar_title_cancel){
                finish();
            }else if(view.getId() == R.id.action_bar_title_submit){
                Intent data = new Intent();
                data.putStringArrayListExtra("checkeds", mAdapter.checkeds);
                setResult(AddPhotoDynamicActivity.RESULT_FOR_PHONE_ALBUM, data);
                finish();
            }
        }
    };

    public void findView(){
        mGridView = (GridView) findViewById(R.id.gv_phone_album);
        actionBarTitleCancel = (TextView) findViewById(R.id.action_bar_title_cancel);
        actionBarTitleText = (TextView) findViewById(R.id.action_bar_title_text);
        actionBarTitleSubmit = (TextView) findViewById(R.id.action_bar_title_submit);
    }
}
