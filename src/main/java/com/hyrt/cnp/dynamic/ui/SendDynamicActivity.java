package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.BrowGridAdapter;
import com.hyrt.cnp.dynamic.request.DynamicCommentRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.request.SendDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamiccommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.SendDynamicRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-09.
 */
public class SendDynamicActivity extends BaseActivity{

    private EditText etContent;
    private ImageView btnAddBrow;
    private ImageView btnAddPhoto;
    private ImageView btnAddAbout;
    private GridView gvBrows;
    private MenuItem sendbtn;
    private LinearLayout layoutForwardSpan;
    private LinearLayout layoutAddPhoto;
    private ImageView photo;
    private TextView tvForwardContent;
    private RelativeLayout layoutInput;
    private ImageView ivForwardPhoto;

    private DisplayImageOptions mImageloaderoptions;

    private boolean hideKeyboard = false;

    private BrowGridAdapter mBrowGridAdapter;

    private Map<String, Integer> brows = new HashMap<String, Integer>();
    private List<Object[]> browArray = new ArrayList<Object[]>();
    private Map<String, Integer> aboutArray = new HashMap<String, Integer>();
    private Uri faceFile;
    private PhotoUpload photoUpload;
    private Bitmap bitmap;
    private File targetFile;
    private int type = 0;

    private Dynamic mDynamic;
    private Comment mComment;

    public static final int FROM_ABOUT_FRIEND = 101;

    public static final int TYPE_SEND = 0;
    public static final int TYPE_FORWARD = 1;
    public static final int TYPE_COMMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", TYPE_SEND);
        findView();
        initImageLoader();
        if(type == TYPE_FORWARD){
            layoutForwardSpan.setVisibility(View.VISIBLE);
            btnAddAbout.setVisibility(View.VISIBLE);
            mDynamic = (Dynamic) intent.getSerializableExtra("dynamic");
            actionBar.setTitle("转发动态");

            if(mDynamic.getbPicAry().size()>0){
                ImageLoader.getInstance().displayImage(mDynamic.getsPicAry0(), ivForwardPhoto, mImageloaderoptions);
                ivForwardPhoto.setVisibility(View.VISIBLE);
            }else{
                ivForwardPhoto.setVisibility(View.GONE);
            }
            if(mDynamic.gettContent().length()>0){
                LinearLayout layout_forward_content_span = (LinearLayout) findViewById(R.id.layout_forward_content_span);
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layout_forward_content_span.getLayoutParams();
                if(mParams == null){
                    mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }else{
                    mParams.width = LinearLayout.LayoutParams.FILL_PARENT;
                }
                tvForwardContent.setText(mDynamic.gettContent());
                tvForwardContent.setVisibility(View.VISIBLE);
            }else{
                LinearLayout layout_forward_content_span = (LinearLayout) findViewById(R.id.layout_forward_content_span);
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layout_forward_content_span.getLayoutParams();
                if(mParams == null){
                    mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }else{
                    mParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
                tvForwardContent.setVisibility(View.GONE);
            }

        }else if(type == TYPE_COMMENT){
            btnAddAbout.setVisibility(View.VISIBLE);
            layoutInput.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.send_dynamic_content_height_big);
            mDynamic = (Dynamic) intent.getSerializableExtra("dynamic");
            mComment = (Comment) intent.getSerializableExtra("comment");
            actionBar.setTitle("发评论");
        }else{
            layoutAddPhoto.setVisibility(View.VISIBLE);
            actionBar.setTitle("发布动态");
        }
        setListener();
        loadData();
    }

    public void initImageLoader(){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        mImageloaderoptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.cnp_spinner_inner)
                .showImageOnFail(R.drawable.cnp_spinner_inner)
                .showImageForEmptyUri(R.drawable.cnp_spinner_inner)
                .cacheInMemory(true)
                .build();
    }

    public void loadData(){
        Object[] brow1 = new Object[2];
        brow1[0] = "[img]哈哈[/img]";
        brow1[1] = R.drawable.laugh;
        brows.put("[img]哈哈[/img]", R.drawable.laugh);
        browArray.add(brow1);

        Object[] brow2 = new Object[2];
        brow2[0] = "[img]鄙视[/img]";
        brow2[1] = R.drawable.bs2_thumb;
        brows.put("[img]鄙视[/img]", R.drawable.bs2_thumb);
        browArray.add(brow2);

        Object[] brow3 = new Object[2];
        brow3[0] = "[img]闭嘴[/img]";
        brow3[1] = R.drawable.bz_thumb;
        brows.put("[img]闭嘴[/img]", R.drawable.bz_thumb);
        browArray.add(brow3);

        Object[] brow4 = new Object[2];
        brow4[0] = "[img]偷笑[/img]";
        brow4[1] = R.drawable.heia_thumb;
        brows.put("[img]偷笑[/img]", R.drawable.heia_thumb);
        browArray.add(brow4);

        Object[] brow5 = new Object[2];
        brow5[0] = "[img]吃惊[/img]";
        brow5[1] = R.drawable.cj_thumb;
        brows.put("[img]吃惊[/img]", R.drawable.cj_thumb);
        browArray.add(brow5);

        Object[] brow6 = new Object[2];
        brow6[0] = "[img]可怜[/img]";
        brow6[1] = R.drawable.kl_thumb;
        brows.put("[img]可怜[/img]", R.drawable.kl_thumb);
        browArray.add(brow6);

        Object[] brow7 = new Object[2];
        brow7[0] = "[img]懒得理你[/img]";
        brow7[1] = R.drawable.ldln_thumb;
        brows.put("[img]懒得理你[/img]", R.drawable.ldln_thumb);
        browArray.add(brow7);

        Object[] brow8 = new Object[2];
        brow8[0] = "[img]太开心[/img]";
        brow8[1] = R.drawable.mb_thumb;
        brows.put("[img]太开心[/img]", R.drawable.mb_thumb);
        browArray.add(brow8);

        Object[] brow9 = new Object[2];
        brow9[0] = "[img]爱你[/img]";
        brow9[1] = R.drawable.lovea_thumb;
        brows.put("[img]爱你[/img]", R.drawable.lovea_thumb);
        browArray.add(brow9);

        Object[] brow10 = new Object[2];
        brow10[0] = "[img]亲亲[/img]";
        brow10[1] = R.drawable.qq_thumb;
        brows.put("[img]亲亲[/img]", R.drawable.qq_thumb);
        browArray.add(brow10);

        Object[] brow11 = new Object[2];
        brow11[0] = "[img]泪[/img]";
        brow11[1] = R.drawable.sada_thumb;
        brows.put("[img]泪[/img]", R.drawable.sada_thumb);
        browArray.add(brow11);

        Object[] brow12 = new Object[2];
        brow12[0] = "[img]生病[/img]";
        brow12[1] = R.drawable.sb_thumb;
        brows.put("[img]生病[/img]", R.drawable.sb_thumb);
        browArray.add(brow12);

        Object[] brow13 = new Object[2];
        brow13[0] = "[img]害羞[/img]";
        brow13[1] = R.drawable.shamea_thumb;
        brows.put("[img]害羞[/img]", R.drawable.shamea_thumb);
        browArray.add(brow13);

        Object[] brow14 = new Object[2];
        brow14[0] = "[img]呵呵[/img]";
        brow14[1] = R.drawable.smilea_thumb;
        brows.put("[img]呵呵[/img]", R.drawable.smilea_thumb);
        browArray.add(brow14);

        Object[] brow15 = new Object[2];
        brow15[0] = "[img]嘻嘻[/img]";
        brow15[1] = R.drawable.tootha_thumb;
        brows.put("[img]嘻嘻[/img]", R.drawable.tootha_thumb);
        browArray.add(brow15);

        Object[] brow16 = new Object[2];
        brow16[0] = "[img]可爱[/img]";
        brow16[1] = R.drawable.tza_thumb;
        brows.put("[img]可爱[/img]", R.drawable.tza_thumb);
        browArray.add(brow16);

        Object[] brow17 = new Object[2];
        brow17[0] = "[img]挤眼[/img]";
        brow17[1] = R.drawable.zy_thumb;
        brows.put("[img]挤眼[/img]", R.drawable.zy_thumb);
        browArray.add(brow17);

        Object[] brow18 = new Object[2];
        brow18[0] = "[img]阴险[/img]";
        brow18[1] = R.drawable.yx_thumb;
        brows.put("[img]阴险[/img]", R.drawable.yx_thumb);
        browArray.add(brow18);

        Object[] brow19 = new Object[2];
        brow19[0] = "[img]右哼哼[/img]";
        brow19[1] = R.drawable.yhh_thumb;
        brows.put("[img]右哼哼[/img]", R.drawable.yhh_thumb);
        browArray.add(brow19);

        Object[] brow20 = new Object[2];
        brow20[0] = "[img]来[/img]";
        brow20[1] = R.drawable.come_thumb;
        brows.put("[img]来[/img]", R.drawable.come_thumb);
        browArray.add(brow20);

        mBrowGridAdapter = new BrowGridAdapter(this, browArray);
        gvBrows.setAdapter(mBrowGridAdapter);
    }

    private void setListener() {
        btnAddBrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(SendDynamicActivity.this.INPUT_METHOD_SERVICE);
                if(gvBrows.getVisibility() != View.VISIBLE){
                    if(imm.isActive()){
                        hideKeyboard = true;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    gvBrows.setVisibility(View.VISIBLE);
                }else{
                    gvBrows.setVisibility(View.GONE);
                    if(hideKeyboard){
                        hideKeyboard = false;
                        imm.showSoftInput(view,InputMethodManager.RESULT_SHOWN);
                    }
                }
            }
        });

        btnAddAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SendDynamicActivity.this, AboutFriendActivity.class);
                startActivityForResult(intent, FROM_ABOUT_FRIEND);
            }
        });

        gvBrows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 20){
                    String text = etContent.getText().toString();
                    if(text.length()>0){
                        int selectionStart = etContent.getSelectionStart();
                        String tempStr = text.substring(0, selectionStart);
                        if(tempStr.substring(tempStr.length()-1, tempStr.length()).equals("]")){
                            int start = tempStr.lastIndexOf("[img]");
                            etContent.getEditableText().delete(start, selectionStart);
                        }else if(tempStr.length() >= 2 && tempStr.substring(tempStr.length()-2, tempStr.length()).equals("//")) {
                            int start = tempStr.lastIndexOf("@");
                            String delName = tempStr.substring(start+1, tempStr.lastIndexOf("//")-1);
                            aboutArray.remove(delName);
                            etContent.getEditableText().delete(start, selectionStart);
                        } else {
                            android.util.Log.i("tag", tempStr.length() - 1+":"+ selectionStart);
                            etContent.getEditableText().delete(tempStr.length() - 1, selectionStart);
                        }

                    }
                }else{
                    Object[] objs = browArray.get(i);
                    SpannableString spannable = new SpannableString(objs[0]+"");
                    Drawable drawable = getResources().getDrawable((Integer) objs[1]);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    spannable.setSpan(
                            span,
                            0,
                            objs[0].toString().length(),
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    etContent.append(spannable);
                    etContent.setSelection(etContent.getText().length());
                }
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(sendbtn != null){
                    if(charSequence.length()>0){
                        sendbtn.setEnabled(true);
                    }else{
                        sendbtn.setEnabled(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face_cover.png"));
                photoUpload = new PhotoUpload(SendDynamicActivity.this, faceFile);
//                photoUpload.setRang(true);
                photoUpload.choiceItem();
            }
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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (etContent.getText().length() > 0 && sendbtn != null) {
            sendbtn.setEnabled(true);
        }if(type == TYPE_FORWARD){
            titletext.setText("转发动态");
        }else if(type == TYPE_COMMENT){
            titletext.setText("发评论");
        }else{
            titletext.setText("发布动态");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FROM_ABOUT_FRIEND) {
            if(data != null){
                String name = data.getStringExtra("name");
                int uid = data.getIntExtra("uid", -1);
                etContent.append(Html.fromHtml("<font color='#6ecbd9'>@"+name+"</font>//"));
                aboutArray.put(name, uid);
            }
        }else if (requestCode == PhotoUpload.PHOTO_ZOOM && data != null && data.getParcelableExtra("data") != null) {
            //保存剪切好的图片
            bitmap = data.getParcelableExtra("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "dynamic_upload_photo.png");

            BitmapDrawable bd = new BitmapDrawable(bitmap);
            photo.setBackground(bd);
            photo.setVisibility(View.VISIBLE);
            TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
            textview.setVisibility(View.GONE);
            btnAddPhoto.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.senddy, menu);
        menu.findItem(R.id.senddy).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.senddy);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("发送")){
            if(type == TYPE_COMMENT){
                addComment();
            }else if(type == TYPE_FORWARD){
                addForward();
            }else{
                sendDynamic();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void addComment(){
        Comment comment=new Comment();
        if(mDynamic == null){
            comment.set_id(mComment.get_id()+"");
            comment.setInfoid2(mComment.get_id());
            if(mComment.getInfoTitle().equals("")){
                comment.setInfoTitle("null");
            }else{
                comment.setInfoTitle(mComment.getInfoTitle());
            }
            comment.setInfoUserId(mComment.getUserid()+"");
            comment.setInfoNurseryId(mComment.getNursery_id()+"");
            comment.setInfoClassroomId(mComment.getInfoClassroomId()+"");
        }else{
            comment.set_id(mDynamic.get_id()+"");
            comment.setInfoid2(mDynamic.get_id());
            if(mDynamic.getTitle().equals("")){
                comment.setInfoTitle("null");
            }else{
                comment.setInfoTitle(mDynamic.getTitle());
            }
            comment.setInfoUserId(mDynamic.gettUserId()+"");
            comment.setInfoNurseryId(mDynamic.getNueseryId()+"");
            comment.setInfoClassroomId(mDynamic.getClassRoomId()+"");
        }
        comment.setSiteid("50");
        comment.setUrl("null");
        comment.setLstatus("Y");
        comment.setContent(etContent.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        DynamicaddcommentRequestListener sendwordRequestListener = new DynamicaddcommentRequestListener(this);
        sendwordRequestListener.setListener(mAddCommentRequestListener);
        DynamicaddcommentRequest schoolRecipeRequest=
                new DynamicaddcommentRequest(Comment.Model3.class,this,comment);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private DynamicaddcommentRequestListener.requestListener mAddCommentRequestListener
            = new DynamicaddcommentRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            Toast.makeText(SendDynamicActivity.this, "发送成功", 0).show();
            setResult(1);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            Toast.makeText(SendDynamicActivity.this, "发送失败", 0).show();
        }
    };

    public void addForward(){
        Dynamic data=new Dynamic();
        data.set_id(mDynamic.get_id());
        data.setContent(etContent.getText().toString());
        DynamiccommentRequestListener sendwordRequestListener = new DynamiccommentRequestListener(this);
        sendwordRequestListener.setListener(mForwardRequestListener);
        DynamicCommentRequest schoolRecipeRequest=new DynamicCommentRequest(Dynamic.Model3.class,this,data);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private DynamiccommentRequestListener.RequestListener mForwardRequestListener = new DynamiccommentRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            Toast.makeText(SendDynamicActivity.this, "发送成功", 0).show();
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            Toast.makeText(SendDynamicActivity.this, "发送失败", 0).show();
        }
    };

    public void sendDynamic(){
        android.util.Log.i("tag", "targetFile:"+targetFile);
        SendDynamicRequest request = new SendDynamicRequest(this, etContent.getText().toString(), targetFile, null, null);
        SendDynamicRequestListener requestListener = new SendDynamicRequestListener(this);
        requestListener.setListener(mSendDynamicRequestListener);
        spiceManager.execute(request, request.createCacheKey(), 0, requestListener.start());
    }

    private SendDynamicRequestListener.requestListener mSendDynamicRequestListener = new SendDynamicRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(Object o) {
            Toast.makeText(SendDynamicActivity.this, "发送成功", 0).show();
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            Toast.makeText(SendDynamicActivity.this, "发送失败", 0).show();
        }
    };

    private void findView() {
        etContent = (EditText) findViewById(R.id.et_send_dynamic_content);
        btnAddBrow = (ImageView) findViewById(R.id.btn_add_brow);
        btnAddPhoto = (ImageView) findViewById(R.id.btn_add_photo);
        gvBrows = (GridView) findViewById(R.id.gv_brows);
        btnAddAbout = (ImageView) findViewById(R.id.btn_add_about);
        layoutForwardSpan = (LinearLayout)findViewById(R.id.layout_forward_span);
        layoutAddPhoto = (LinearLayout) findViewById(R.id.layout_add_photo);
        photo = (ImageView) findViewById(R.id.iv_photo1);
        tvForwardContent = (TextView) findViewById(R.id.tv_forward_content);
        layoutInput = (RelativeLayout) findViewById(R.id.layout_input);
        ivForwardPhoto = (ImageView) findViewById(R.id.iv_forward_photo);
    }
}
