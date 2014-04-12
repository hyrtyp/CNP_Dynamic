package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.view.Mylistview;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.request.CommetListRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.requestListener.CommentListRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhotoInfoActivity extends BaseActivity{

    private ImageView imgphoto;
    private TextView albumname;
    private TextView photoname;
    private EditText editcommit;
    private TextView btnset;
    private Mylistview listView;
    private DynamicPhoto photo;
    private Album mAlbum;
    private ClassRoomAdapter classRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotoinfo);
        initView();
        initData();
        LoadData();
    }

    private void initView(){
        imgphoto=(ImageView)findViewById(R.id.img_photo);
        albumname=(TextView)findViewById(R.id.text_albumname);
        photoname=(TextView)findViewById(R.id.text_photoname);
        editcommit=(EditText)findViewById(R.id.edit_commit);
        btnset=(TextView)findViewById(R.id.btn_set);
        listView = (Mylistview)findViewById(R.id.commit_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editcommit.getText().toString().equals("")){
                    addcomment();
                }else{
                    Toast.makeText(DynamicPhotoInfoActivity.this, "评论不能为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initData(){
        Intent intent = getIntent();
        photo=(DynamicPhoto)intent.getSerializableExtra("dynamicPhoto");
        mAlbum = (Album) intent.getSerializableExtra("album");
        photoname.setText(Html.fromHtml("照片名称：<font color='#6ecbd9'>"+photo.getIntroduce()+"</font>"));
            titletext.setText("班级相册");
            albumname.setText(Html.fromHtml("专辑名称：<font color='#6ecbd9'>"+mAlbum.getAlbumName()+"</font>"));
        showDetailImage(photo.getImagepics(),imgphoto,false);
    }

    private void LoadData(){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(this);
        sendwordRequestListener.setListener(mCommentRequestListener);
        CommetListRequest schoolRecipeRequest=
                new CommetListRequest(Comment.Model.class,this,photo.getPhotoID()+"","51");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1000,
                sendwordRequestListener.start());
    }

    private CommentListRequestListener.RequestListener mCommentRequestListener = new CommentListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Comment.Model data) {
            updateUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };


    public void updateUI(Comment.Model model){
        if(model == null){
            return;
        }
        String[] resKeys=new String[]{"getphotoImage","getUsername","getCreatdate2","getContent"};
        int[] reses=new int[]{R.id.comment_photo,R.id.text_name,R.id.text_time,R.id.text_con};
        classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_comment,resKeys,reses);
        listView.setAdapter(classRoomAdapter);
    }

    public void ShowSuccess(){
        Toast.makeText(DynamicPhotoInfoActivity.this,"添加评论成功",Toast.LENGTH_SHORT).show();
        editcommit.setText("");
        LoadData();//刷新
        //隐藏键盘
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                DynamicPhotoInfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void addcomment(){
        Comment comment=new Comment();
        comment.set_id(photo.getUserID()+"");
        comment.setInfoID(photo.getPhotoID()+"");
        comment.setInfoid2(photo.getPhotoID()+"");
        comment.setInfoTitle(photo.getTitle());
        comment.setInfoUserId(photo.getUserID()+"");
        comment.setInfoNurseryId(photo.getNurseryID()+"");
        comment.setInfoClassroomId(photo.getClassroomID()+"");
        comment.setSiteid("51");
        comment.setUrl(photo.getImagepics());
        comment.setLstatus("Y");
        comment.setContent(editcommit.getText().toString());
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

            ShowSuccess();
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

}
