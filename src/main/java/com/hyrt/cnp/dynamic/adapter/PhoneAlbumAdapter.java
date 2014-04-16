package com.hyrt.cnp.dynamic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.hyrt.cnp.dynamic.R;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-16.
 */
public class PhoneAlbumAdapter extends BaseAdapter{

    private List<String> datas;
    private Context mContext;
    public ArrayList<String> checkeds = new ArrayList<String>();

    public PhoneAlbumAdapter(List<String> datas, List<String> checkeds, Context context) {
        this.checkeds.clear();
        this.checkeds.addAll(checkeds);
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final String data = datas.get(i);
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.phone_album_item, null);
        }
        ImageView iv = (ImageView) view.findViewById(R.id.iv_phone_album_img);
        final CheckBox cb = (CheckBox) view.findViewById(R.id.cb_phone_album_select);
        if(checkeds.contains(data)){
            cb.setChecked(true);
        }else{
            cb.setChecked(false);
        }
        ImageLoader.getInstance().displayImage("file:///"+data, iv, AppContext.getInstance().mImageloaderoptions);

        View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkeds.contains(data)){
                    checkeds.remove(data);
                    cb.setChecked(false);
                }else{
                    if(checkeds.size() < 9){
                        checkeds.add(data);
                        cb.setChecked(true);
                    }else{
                        cb.setChecked(false);
                        Toast.makeText(mContext, "最多上传9张", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };

        iv.setOnClickListener(mOnClickListener);
        cb.setOnClickListener(mOnClickListener);

        return view;
    }
}
