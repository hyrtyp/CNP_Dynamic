package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.view.BounceListView;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-3-13.
 */
public class MyAblumsFragment extends Fragment{

    private View rootView;

    private BounceListView listview;
    private ListViewAdapter listViewAdapter;
    private String Category;
    private Album.Model model;
    private HomeInteractiveActivity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_myablums,container,false);
        initView();
        loadData();
        return rootView;
    }

    private void initView(){
        listview=(BounceListView)rootView.findViewById(R.id.cnp_listview_album);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.setClass(ClassroomAlbumActivity.this,ClassroomphotolistActivity.class);
//                intent.putExtra("vo",model.getData().get(i));
//                intent.putExtra("Category",Category);
//                startActivity(intent);
            }
        });
    }

    private void loadData(){
        activity = (HomeInteractiveActivity) getActivity();
        MyAlbumRequestListener sendwordRequestListener = new MyAlbumRequestListener(activity);
        MyAlbumRequest schoolRecipeRequest=new MyAlbumRequest(Album.Model.class,activity);
        activity.spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }


    /**
     * 更新ui界面
     * */

    public void updateUI(Album.Model model){
        if (model==null){
            LinearLayout linearLayout =(LinearLayout)rootView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)rootView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            String[] resKeys=new String[]{"getImagepath","getAlbumName","getAlbumDesc","getPosttime2"};
            int[] reses=new int[]{R.id.item_album_image,R.id.item_album_title,R.id.item_album_con,R.id.item_album_time};
            listViewAdapter = new ListViewAdapter(activity,model.getData(),R.layout.layout_item_myalbum,resKeys,reses);
            listview.setAdapter(listViewAdapter);
        }

    }
}
