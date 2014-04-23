package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.CommentListAdapter;
import com.hyrt.cnp.dynamic.request.CommetListRequest;
import com.hyrt.cnp.dynamic.requestListener.CommentListRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;

/**
 * Created by Zoe on 2014-04-22.
 */
public class CommentListFragment extends Fragment{

    private View parentView;
    private ListView listview;
    private Dynamic dynamic;
    private ArrayList<Comment> comments =new ArrayList<Comment>();
    private CommentListAdapter dynamicAdapter;

    public CommentListFragment(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_list, null);
        findView();
        loadData();
        return parentView;
    }

    public void loadData(){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(getActivity());
        sendwordRequestListener.setListener(mCommentListener);
        CommetListRequest schoolRecipeRequest=new CommetListRequest(
                Comment.Model.class,getActivity(),dynamic.get_id(),"50");
        ((BaseActivity)getActivity()).spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private CommentListRequestListener.RequestListener mCommentListener = new CommentListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Comment.Model data) {
            UpDataUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            UpDataUI(null);
        }
    };

    public void UpDataUI(Comment.Model model){
        if(model==null&&comments.size()==0){
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)parentView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            comments.clear();
            comments.addAll(model.getData());
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getphotoImage","getUsername","getCreatdate2"};
                int[] reses=new int[]{R.id.comment_photo,R.id.comment_name,R.id.comment_time};
                dynamicAdapter = new CommentListAdapter(
                        (BaseActivity)getActivity(),
                        comments,
                        R.layout.layout_commentitem,
                        resKeys,reses, dynamic, comments);
                listview.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void findView(){
        listview = (ListView) parentView.findViewById(R.id.listview);
    }
}
