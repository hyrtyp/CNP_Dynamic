package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class MyDynamicRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public MyDynamicRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        BabayIndexActivity activity = (BabayIndexActivity)context.get();
        activity.updateUI(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            Dynamic.Model result= (Dynamic.Model)data;
            activity.alldaynamicfragment.upUiData(result);
        }else{
            HomeInteractiveActivity activity = (HomeInteractiveActivity)context.get();
            activity.alldaynamicfragment.upUiData(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public MyDynamicRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
