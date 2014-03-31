package com.hyrt.cnp.dynamic.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by GYH on 2014/3/30.
 */
public class DynamicAddphotoAdapter extends BaseAdapter{


    private List<String> list;

    public DynamicAddphotoAdapter(List<String> list){

        this.list=list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
