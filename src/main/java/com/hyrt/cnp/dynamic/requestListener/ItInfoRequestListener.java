package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.MyItListActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 2014/4/6.
 */
public class ItInfoRequestListener extends BaseRequestListener {

    /**
     * @param context
     */
    public ItInfoRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        MyItListActivity activity = (MyItListActivity)context.get();
        activity.updateUI(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){
            MyItListActivity activity = (MyItListActivity)context.get();
            ItInfo.Model result= (ItInfo.Model)data;
            activity.updateUI(result);
        }else{
            MyItListActivity activity = (MyItListActivity)context.get();
            activity.updateUI(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public ItInfoRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
