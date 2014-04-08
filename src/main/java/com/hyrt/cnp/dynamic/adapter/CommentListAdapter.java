package com.hyrt.cnp.dynamic.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;

import java.util.List;

/**
 * Created by Zoe on 2014-04-08.
 */
public class CommentListAdapter extends MySimpleAdapter{

    private BaseActivity mActivity;
    private List<ItInfo> mDatas;

    public CommentListAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.mActivity = paramMyActivity;
        this.mDatas = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        final int posi = position;
        TextView btn_comment = (TextView) view.findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, CommentListActivity.class);
                intent.putExtra("vo", mDatas.get(posi));
                intent.putExtra("Category","hf");
                mActivity.startActivityForResult(intent,0);
            }
        });
        return view;
    }
}
