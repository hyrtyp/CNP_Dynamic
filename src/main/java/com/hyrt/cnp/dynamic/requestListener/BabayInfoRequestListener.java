package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class BabayInfoRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public BabayInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        BabayIndexActivity activity = (BabayIndexActivity)context.get();
        if(data!=null){
            BabyInfo.Model result= (BabyInfo.Model)data;
            activity.UpdataBabayinfo(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public BabayInfoRequestListener start() {
        showIndeterminate("加载宝宝资料中...");
        return this;
    }
}
