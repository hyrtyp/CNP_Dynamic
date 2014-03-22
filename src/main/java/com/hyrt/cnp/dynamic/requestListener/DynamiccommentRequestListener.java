package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-23.
 */
public class DynamiccommentRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public DynamiccommentRequestListener(Activity context) {
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
            DynamicCommentActivity activity = (DynamicCommentActivity)context.get();
            Dynamic.Model3 result= (Dynamic.Model3)data;
            if(result.getCode().equals("200")){
                activity.showSuccess();
            }else{
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }

        }else{
            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public DynamiccommentRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
