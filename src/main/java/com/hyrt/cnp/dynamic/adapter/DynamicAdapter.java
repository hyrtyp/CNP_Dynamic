package com.hyrt.cnp.dynamic.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-23.
 */
public class DynamicAdapter extends MySimpleAdapter {

    private BaseActivity activity;
    private ArrayList<Dynamic> list;
    public DynamicAdapter(BaseActivity context, List data, int layoutId, String[] resKeys, int[] reses) {
        super(context, data, layoutId, resKeys, reses);
        this.list=(ArrayList<Dynamic>)data;
        this.activity=context;
    }


    @Override
    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {
        View view= super.getView(position, paramView, paramViewGroup);
        TextView textView =(TextView)view.findViewById(R.id.dynamic_context);
        ImageView imageView1=(ImageView)view.findViewById(R.id.dynamic_image1);
        ImageView imageView2=(ImageView)view.findViewById(R.id.dynamic_image2);
        ImageView imageView3=(ImageView)view.findViewById(R.id.dynamic_image3);
        TextView tcontext=(TextView)view.findViewById(R.id.dynamic_dcontext);
        final int posi=position;
        if(list.get(position).getContent().equals("")){
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getsPicAry0()==null){
            imageView1.setVisibility(View.GONE);
        }else{
            imageView1.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getsPicAry1()==null){
            imageView2.setVisibility(View.GONE);
        }else{
            imageView2.setVisibility(View.VISIBLE);
        }
        if(list.get(position).getsPicAry2()==null){
            imageView3.setVisibility(View.GONE);
        }else{
            imageView3.setVisibility(View.VISIBLE);
        }

        if(list.get(position).gettContent()==null){
            tcontext.setVisibility(View.GONE);
        }else{
            tcontext.setVisibility(View.VISIBLE);
        }

        LinearLayout dynamic_zf=(LinearLayout)view.findViewById(R.id.dynamic_zf);
        dynamic_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, DynamicCommentActivity.class);
                intent.putExtra("vo", list.get(posi));
                intent.putExtra("Category","zf");
                activity.startActivityForResult(intent,0);
            }
        });
        LinearLayout dynamic_pl=(LinearLayout)view.findViewById(R.id.dynamic_pl);
        dynamic_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, CommentListActivity.class);
                intent.putExtra("vo", list.get(posi));
                intent.putExtra("Category","pl");
                activity.startActivityForResult(intent,0);
            }
        });
        return view;
    }
}
