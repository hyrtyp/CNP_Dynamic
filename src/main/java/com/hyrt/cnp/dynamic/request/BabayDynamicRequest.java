package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.DynamicService;

/**
 * Created by GYH on 14-1-23.
 */
public class BabayDynamicRequest extends BaseRequest {

    @Inject
    private DynamicService schoolListService;
    private String uid;
    private String more;
    private boolean isAll;
    public BabayDynamicRequest(Class clazz, Context context,String uid,String more) {
        super(clazz, context);
        this.uid=uid;
        this.more=more;
        isAll = false;
    }

    public BabayDynamicRequest(Class clazz, Context context,String uid,String more, boolean isAll) {
        super(clazz, context);
        this.uid=uid;
        this.more=more;
        this.isAll = isAll;
    }

    @Override
    public Base run() {
        if(more.equals("1")){
            return schoolListService.getBabayDynamicData(getRestTemplate(),uid, isAll);
        }else{
            return schoolListService.getBabayDynamicMoreData(getRestTemplate(), uid, more, isAll);
        }
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Babydynamic"+uid+more;
    }
}
