package com.hyrt.cnp.dynamic.adapter;

import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

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
        ImageView dynamic_Avatar = (ImageView) view.findViewById(R.id.dynamic_Avatar);
        TextView tcontext=(TextView)view.findViewById(R.id.dynamic_dcontext);

        Dynamic mDynamic = list.get(position);
        imageView1.setImageDrawable(null);
        imageView2.setImageDrawable(null);
        imageView3.setImageDrawable(null);
        dynamic_Avatar.setImageDrawable(null);

        if(mDynamic.getsPicAry0()!= null){
            ImageLoader.getInstance().displayImage(
                    mDynamic.getsPicAry0(),
                    imageView1,
                    AppContext.getInstance().mImageloaderoptions);
        }
        if(mDynamic.getsPicAry1()!= null){
            ImageLoader.getInstance().displayImage(
                    mDynamic.getsPicAry2(),
                    imageView2,
                    AppContext.getInstance().mImageloaderoptions);
        }
        if(mDynamic.getsPicAry2()!= null){
            ImageLoader.getInstance().displayImage(
                    mDynamic.getsPicAry2(),
                    imageView3,
                    AppContext.getInstance().mImageloaderoptions);
        }

        ImageLoader.getInstance().displayImage(
                mDynamic.getUserphoto(),
                dynamic_Avatar,
                AppContext.getInstance().mImageloaderoptions);

        textView.setText(StringUtils.getSpannableString(mDynamic.getContent2(), activity));
        tcontext.setText(StringUtils.getSpannableString(mDynamic.gettContent(), activity));

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

        if(list.get(position).gettContent()==null || list.get(position).gettContent().length()<=0){
            tcontext.setVisibility(View.GONE);
        }else{
            tcontext.setVisibility(View.VISIBLE);
        }

        LinearLayout dynamic_zf=(LinearLayout)view.findViewById(R.id.dynamic_zf);
        dynamic_zf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, SendDynamicActivity.class);
                intent.putExtra("dynamic", list.get(posi));
                intent.putExtra("type", SendDynamicActivity.TYPE_FORWARD);
                activity.startActivityForResult(intent,0);
               /* intent.setClass(activity, DynamicCommentActivity.class);
                intent.putExtra("vo", list.get(posi));
                intent.putExtra("Category","zf");
                activity.startActivityForResult(intent,0);*/
            }
        });
        LinearLayout dynamic_pl=(LinearLayout)view.findViewById(R.id.dynamic_pl);
        dynamic_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                intent.setClass(activity, SendDynamicActivity.class);
                intent.putExtra("dynamic", list.get(posi));
                intent.putExtra("type", SendDynamicActivity.TYPE_COMMENT);
                activity.startActivityForResult(intent,0);*/
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
