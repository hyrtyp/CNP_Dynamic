package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.PhotoService;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhotoListRequest extends BaseRequest{
    @Inject
    private PhotoService schoolListService;

    private int paid;

    public DynamicPhotoListRequest(Class clazz, Context context,int paid) {
        super(clazz, context);
        this.paid=paid;
    }
    @Override
    public Base run() {
        return schoolListService.getDynamicAlbumphotolistData(getRestTemplate(),paid);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "dynamicPhotoList"+paid;
    }
}
