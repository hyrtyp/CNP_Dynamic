package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AlbumService;

/**
 * Created by GYH on 14-1-16.
 */
public class MyAlbumRequest extends BaseRequest{

    @Inject
    private AlbumService schoolListService;
    public MyAlbumRequest(Class clazz, Context context) {
        super(clazz, context);
    }
    @Override
    public Base run() {
            return schoolListService.getMyAlbumData(getRestTemplate());
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "myalbum";
    }
}
