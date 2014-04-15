package com.hyrt.cnp.dynamic.adapter;

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
public class MyForwardListAdapter extends MySimpleAdapter{

    private List<ItInfo> mData;

    public MyForwardListAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.mData = data;
    }

    @Override
    public View getView(int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_forward_face);
        iv.setImageDrawable(null);
        ImageLoader.getInstance().displayImage(mData.get(0).getUserphoto(), iv, AppContext.getInstance().mImageloaderoptions);
        return view;
    }
}
