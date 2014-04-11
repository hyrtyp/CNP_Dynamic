package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.ui.DynamicCommentActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-1-16.
 */
public class DynamicaddcommentRequestListener extends BaseRequestListener{

    private requestListener mListener;

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
        if(mListener != null) {
            mListener.onRequestFailure(null);
        }
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data!=null){

            Comment.Model3 result= (Comment.Model3)data;
            if(result.getCode().equals("200")){
                if(mListener != null){
                    mListener.onRequestSuccess(data);
                }else{
                    DynamicCommentActivity activity = (DynamicCommentActivity)context.get();
                    activity.showSuccess();
                }
            }else{
                if(mListener != null) {
                    mListener.onRequestFailure(null);
                }else{
                    showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
                }

            }
        }else{
            if(mListener != null) {
                mListener.onRequestFailure(null);
            }else{
                showMessage(R.string.nodata_title,R.string.nodata_addcommentfial);
            }
        }

    }

    @Override
    public DynamicaddcommentRequestListener start() {
        showIndeterminate("发送中...");
        return this;
    }

    public void setListener(requestListener listener){
        this.mListener = listener;
    }

    public static interface requestListener{
        public void onRequestSuccess(Object data);
        public void onRequestFailure(SpiceException e);
    }
}
