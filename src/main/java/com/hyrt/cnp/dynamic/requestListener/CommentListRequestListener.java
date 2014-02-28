package com.hyrt.cnp.dynamic.requestListener;

import android.app.Activity;

import com.hyrt.cnp.account.model.Comment;
import com.hyrt.cnp.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-2-24.
 */
public class CommentListRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public CommentListRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
//        showMessage(R.string.nodata_title,R.string.nodata_content);
        super.onRequestFailure(e);
        CommentListActivity activity = (CommentListActivity)context.get();
        activity.UpDataUI(null);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        CommentListActivity activity = (CommentListActivity)context.get();
        if(data!=null){
            Comment.Model result= (Comment.Model)data;
            activity.UpDataUI(result);
        }else{
            activity.UpDataUI(null);
//            showMessage(R.string.nodata_title,R.string.nodata_content);
        }

    }

    @Override
    public CommentListRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }
}
