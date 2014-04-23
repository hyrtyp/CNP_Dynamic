package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.ForwardListAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.MyDynamicRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-22.
 */
public class ForwardListFragment extends Fragment{

    private View parentView;
    private ListView listview;
    private Dynamic dynamic;
    private List<Dynamic> datas = new ArrayList<Dynamic>();
    private ForwardListAdapter dynamicAdapter;

    public ForwardListFragment(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_list, null);
        findView();
        loadData();
        return parentView;
    }

    public void loadData(){
        MyDynamicRequestListener sendwordRequestListener = new MyDynamicRequestListener(getActivity());
        sendwordRequestListener.setListener(mForwardReuqestListener);
        BabayDynamicRequest schoolRecipeRequest = new BabayDynamicRequest(
                Dynamic.Model.class, (BaseActivity)getActivity(), "", dynamic.get_id(), "1");
        ((BaseActivity)getActivity()).spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1,
                sendwordRequestListener.start());
    }

    private MyDynamicRequestListener.RequestListener mForwardReuqestListener = new MyDynamicRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Dynamic.Model data) {
            UpDataUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            UpDataUI(null);
        }
    };

    public void UpDataUI(Dynamic.Model model){
        if(model == null && datas.size() == 0){
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)parentView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            datas.clear();
            datas.addAll(model.getData());
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getUserName","getPosttime3"};
                int[] reses=new int[]{R.id.tv_forward_name, R.id.tv_forward_time};
                dynamicAdapter = new ForwardListAdapter(
                        (BaseActivity)getActivity(),
                        datas,
                        R.layout.forward_item,
                        resKeys,reses);
                listview.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
    }

    private void findView(){
        listview = (ListView) parentView.findViewById(R.id.listview);
    }
}
