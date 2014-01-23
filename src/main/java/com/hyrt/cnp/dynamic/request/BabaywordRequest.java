package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.account.model.Base;
import com.hyrt.cnp.account.request.BaseRequest;
import com.hyrt.cnp.account.service.DynamicService;

/**
 * Created by GYH on 14-1-23.
 */
public class BabaywordRequest extends BaseRequest {

    @Inject
    private DynamicService schoolListService;
    private String uid;
    public BabaywordRequest(Class clazz, Context context, String uid) {
        super(clazz, context);
        this.uid=uid;
    }
    @Override
    public Base run() {
            return schoolListService.getBabaywordData(getRestTemplate(),uid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Babyword"+uid;
    }
}
