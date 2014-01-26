package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Comment;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class DynamicaddcommentRequestListener extends BaseRequestListener{
    /**
     * @param context
     */
    public DynamicaddcommentRequestListener(Activity context) {
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
            Comment.Model3 result= (Comment.Model3)data;
            if(result.getCode().equals("200")){
                activity.showSuccess();
            }else{
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }
        }else{
            showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
        }

    }

    @Override
    public DynamicaddcommentRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
