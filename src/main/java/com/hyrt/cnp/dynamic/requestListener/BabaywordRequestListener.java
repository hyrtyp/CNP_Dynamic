package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Dynamic;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.BabayWordActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class BabaywordRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public BabaywordRequestListener(Activity context) {
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
        if(data!=null){
            BabayWordActivity activity = (BabayWordActivity)context.get();
            Dynamic.Model result= (Dynamic.Model)data;
            activity.updateUI(result);
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public BabaywordRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
