package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.CommentListAdapter;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.request.CommetListRequest;
import com.hyrt.cnp.dynamic.requestListener.CommentListRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-2-24.
 */
public class CommentListActivity extends BaseActivity{

    private  Dynamic dynamic;
    private ListView xListView;
    private ArrayList<Comment> comments =new ArrayList<Comment>();
    private CommentListAdapter dynamicAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("评论")
                .setIcon(R.drawable.editbtn)
                .setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("评论")){
            Intent intent = new Intent();
            intent.setClass(this, SendDynamicActivity.class);
            intent.putExtra("dynamic", dynamic);
            intent.putExtra("type", SendDynamicActivity.TYPE_COMMENT);
            startActivityForResult(intent,0);
            /*Intent intent = new Intent();
            intent.setClass(CommentListActivity.this, DynamicCommentActivity.class);
            intent.putExtra("Category", "pl");
            intent.putExtra("vo", dynamic);
            startActivityForResult(intent, 0);*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(){
        dynamic = (Dynamic)getIntent().getSerializableExtra("vo");
        xListView=(ListView)findViewById(R.id.commentlist_listview);
    }

    private void initData(){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(this);
        CommetListRequest schoolRecipeRequest=new CommetListRequest(
                Comment.Model.class,this,dynamic.get_id(),"50");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    /**
     * 跟新ui
     * */
    public void UpDataUI(Comment.Model model){
        if(model==null&&comments.size()==0){
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            comments.clear();
            comments.addAll(model.getData());
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getphotoImage","getUsername",
                        "getContent","getCreatdate2"};
                int[] reses=new int[]{R.id.comment_photo,R.id.comment_name,
                        R.id.comment_context,R.id.comment_time};
                dynamicAdapter = new CommentListAdapter(this, comments,R.layout.layout_commentitem,resKeys,reses, dynamic, comments);
                xListView.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
    }
}
