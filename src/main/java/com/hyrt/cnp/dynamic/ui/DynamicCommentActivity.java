package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.account.model.Comment;
import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.request.DynamicCommentRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamiccommentRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-21.
 */
public class DynamicCommentActivity extends BaseActivity{

    private TextView textcon;
    private EditText editcon;
    private Dynamic dynamic;
    private String Category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiccomment);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("setting")
                .setChecked(false)
                .setTitle("发送")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    private void initView(){
        textcon=(TextView)findViewById(R.id.text_context);
        editcon=(EditText)findViewById(R.id.edit_context);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("发送")){
            if(!editcon.getText().toString().equals("")){

                if(Category.equals("pl")){
                    addcomment();
                }else{
                    setzfData();
                }
            }else{
                Toast.makeText(DynamicCommentActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void initData(){
        Intent intent = getIntent();
        dynamic=(Dynamic)intent.getSerializableExtra("vo");
        textcon.setText(dynamic.getContent().toString());
        Category=intent.getStringExtra("Category");
        if(Category.equals("pl")){
            titletext.setText("评论动态");
        }else{
            titletext.setText("转发动态");
        }
    }

    private void setzfData(){
        Dynamic data=new Dynamic();
        data.set_id(dynamic.get_id());
        data.setContent(editcon.getText().toString());
        DynamiccommentRequestListener sendwordRequestListener = new DynamiccommentRequestListener(this);
        DynamicCommentRequest schoolRecipeRequest=new DynamicCommentRequest(Dynamic.Model3.class,this,data);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private void addcomment(){
        Comment comment=new Comment();
        comment.set_id(dynamic.get_id()+"");
        comment.setInfoid2(dynamic.get_id());
        if(dynamic.getTitle().equals("")){
            comment.setInfoTitle("null");
        }else{
            comment.setInfoTitle(dynamic.getTitle());
        }
        comment.setInfoUserId(dynamic.gettUserId());
        comment.setInfoNurseryId(dynamic.getNueseryId());
        comment.setInfoClassroomId(dynamic.getClassRoomId());
        comment.setSiteid("50");
        comment.setUrl("null");
        comment.setLstatus("N");
        comment.setContent(editcon.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        DynamicaddcommentRequestListener sendwordRequestListener = new DynamicaddcommentRequestListener(this);
        DynamicaddcommentRequest schoolRecipeRequest=
                new DynamicaddcommentRequest(Comment.Model3.class,this,comment);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void showSuccess(){
        Toast.makeText(DynamicCommentActivity.this,"评论成功！",Toast.LENGTH_SHORT).show();
        editcon.setText("");
    }

}
