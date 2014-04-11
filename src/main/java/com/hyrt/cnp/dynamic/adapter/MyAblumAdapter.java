package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.dynamic.R;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;

import java.util.List;

/**
 * Created by Zoe on 2014-04-11.
 */
public class MyAblumAdapter extends MySimpleAdapter{

    private List<Album> datas;

    public MyAblumAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys, int[] reses) {
        super(paramMyActivity, data, resourceId, resKeys, reses);
        this.datas = data;
    }

    @Override
    public View getView(final int position, View paramView, ViewGroup paramViewGroup) {
        View view = super.getView(position, paramView, paramViewGroup);
        TextView btn_change_album = (TextView) view.findViewById(R.id.btn_change_album);
        TextView btn_del_album = (TextView) view.findViewById(R.id.btn_del_album);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btn_change_album){
                    android.util.Log.i("tag", datas.get(position).getAlbumName()+"--修改");
                }else if(view.getId() == R.id.btn_del_album){
                    android.util.Log.i("tag", datas.get(position).getAlbumName()+"--删除");
                }
            }
        };
        btn_change_album.setOnClickListener(mOnClickListener);
        btn_del_album.setOnClickListener(mOnClickListener);
        return view;
    }
}
