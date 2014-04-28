package com.hyrt.cnp.dynamic.adapter;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.dynamic.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.List;

/**
 * Created by Zoe on 2014-04-15.
 */
public class MyCommnentListAdapter extends MySimpleAdapter{

    private List<ItInfo> mData;

    public MyCommnentListAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.mData = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_comment_face);
        iv.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(mData.get(position).getUserphoto(), iv, AppContext.getInstance().mNoCacheOnDiscImageloadoptions);
        return view;
    }
}
