package com.hyrt.cnp.dynamic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.view.BounceListView;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.MyAblumAdapter;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.hyrt.cnp.dynamic.ui.AddAlbumActivity;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoListActivity;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by GYH on 14-3-13.
 */
public class MyAblumsFragment extends Fragment{

    private View rootView;

    private BounceListView listview;
    private MyAblumAdapter listViewAdapter;
    private String Category;
    private Album.Model model;
    private HomeInteractiveActivity activity;

    public static final int RESULT_FOR_ADD_ALBUM = 103;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("tag", "onCreateView");
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

//                Toast.makeText(getActivity(), "aaa", 0).show();
//                if(view.getId() == R.id.btn_change_album){
//                    Toast.makeText(getActivity(), "bbb", 0).show();
//                }
            }
        });


    }

    public void loadData(){
        Log.i("tag", "loadData");
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
        Log.i("tag", "updateUI");
        if (model==null){
            LinearLayout linearLayout =(LinearLayout)rootView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)rootView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            String[] resKeys=new String[]{"getAlbumName","getAlbumDesc","getPosttime2"};
            int[] reses=new int[]{R.id.item_album_title,R.id.tv_photo_describe,R.id.tv_photo_time};
            listViewAdapter = new MyAblumAdapter(activity,model.getData(),R.layout.dynamic_album_item,resKeys,reses);
            listViewAdapter.setListener(mAblumAdapter);
            if(listview != null){
                listview.setAdapter(listViewAdapter);
            }
        }

    }

    private MyAblumAdapter.OnItemClickListener mAblumAdapter
            = new MyAblumAdapter.OnItemClickListener() {
        @Override
        public void onClick(int type, int position) {
            Album data = model.getData().get(position);
            if(type == 0){
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddAlbumActivity.class);
                intent.putExtra("album", data);
                getActivity().startActivityForResult(intent, RESULT_FOR_ADD_ALBUM);
            }else if(type == 1){
                MyAlbumRequestListener requestListener = new MyAlbumRequestListener(getActivity());
                requestListener.setListener(mAlbumRequestListener);
                android.util.Log.i("tag", "paid:"+data.getPaId());
                MyAlbumRequest request = new MyAlbumRequest(BaseTest.class, getActivity(), data.getPaId()+"");
                ((BaseActivity)getActivity()).spiceManager.execute(
                        request, request.getcachekey(), 1,
                        requestListener.start());
            }else{
                Intent intent = new Intent();
                intent.setClass(getActivity(), DynamicPhotoListActivity.class);
                intent.putExtra("album", data);
                startActivityForResult(intent, RESULT_FOR_ADD_ALBUM);
            }

        }
    };

    MyAlbumRequestListener.RequestListener mAlbumRequestListener = new MyAlbumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            BaseTest bt = (BaseTest) data;
            android.util.Log.i("tag", "bt:"+bt.getCode()+":"+bt.getMsg());
            loadData();
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };
}
