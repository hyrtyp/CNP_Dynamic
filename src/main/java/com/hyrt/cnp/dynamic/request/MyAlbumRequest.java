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

    private int type = 0;//0:获取专辑列表；2:删除专辑；3:修改专辑
    private String paid, albumName, describes;

    @Inject
    private AlbumService schoolListService;
    public MyAlbumRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public MyAlbumRequest(Class clazz, Context context, String paid) {
        super(clazz, context);
        this.type = 1;
    }

    public MyAlbumRequest(Class clazz, Context context, String paid, String albumName, String describes) {
        super(clazz, context);
        this.type = 2;
    }

    @Override
    public Base run() {
        if(type == 1){
            return schoolListService.delAlbum(paid);
        }else if(type == 2){
            return schoolListService.alterAlbum(paid, albumName, describes);
        }else{
            return schoolListService.getMyAlbumData(getRestTemplate());
        }

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "myalbum11233";
    }
}
