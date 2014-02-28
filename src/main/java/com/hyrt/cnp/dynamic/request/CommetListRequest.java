package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.CommentService;

/**
 * Created by GYH on 14-2-24.
 */
public class CommetListRequest extends BaseRequest{

    @Inject
    private CommentService schoolListService;
    private String infoid;
    private String siteid;

    public CommetListRequest(Class clazz, Context context, String infoid, String siteid) {
        super(clazz, context);
        this.infoid=infoid;
        this.siteid= siteid;
    }
    @Override
    public Base run() {
        return schoolListService.getCommenthomeData(getRestTemplate(),infoid,siteid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "commentList"+siteid+infoid;
    }
}
