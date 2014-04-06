package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.ItInfoService;

/**
 * Created by GYH on 2014/4/6.
 */
public class ItInfoRequest extends BaseRequest {

    @Inject
    private ItInfoService itInfoService;
    private String more;
    public ItInfoRequest(Class clazz, Context context,String more) {
        super(clazz, context);
        this.more=more;
    }
    @Override
    public Base run() {
            return itInfoService.getMyitinfoData(getRestTemplate(), more);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "myitinfo"+more;
    }
}
