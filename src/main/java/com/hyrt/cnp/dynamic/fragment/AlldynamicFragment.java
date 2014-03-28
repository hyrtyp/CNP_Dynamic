package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.MyDynamicRequestListener;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.hyrt.cnp.base.view.XListView;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-3-12.
 */
public class AlldynamicFragment extends Fragment {

    private XListView xListView;
    private String more = "1";
    private HomeInteractiveActivity activity;

    private String STATE;
    final private String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";

    private DynamicAdapter dynamicAdapter;
    private ArrayList<Dynamic> dynamics = new ArrayList<Dynamic>();

    private View rootview;

    private boolean isFirst = true;

    public static AlldynamicFragment instantiation(int position) {
        AlldynamicFragment fragment = new AlldynamicFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        STATE = HASDATA;
        rootview = inflater.inflate(R.layout.fragment_alldynamic, container, false);
        dynamicAdapter = null;
        dynamics.clear();
        isFirst = false;
        initView(rootview);
        initData();
        loadData();
        return rootview;
    }


    /**
     * 初始化布局
     */
    private void initView(View view) {
        xListView = (XListView) view.findViewById(R.id.dynamic_listview);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
//                    Toast.makeText(BabayIndexActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
//                    Toast.makeText(BabayIndexActivity.this,"正在刷新,请稍后!",Toast.LENGTH_SHORT).show();
                    loadData();
                }
                xListView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(BabayIndexActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                } else {
                    loadData();
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                xListView.stopLoadMore();
            }
        });
    }

    private void loadData() {
        activity = (HomeInteractiveActivity) getActivity();
        MyDynamicRequestListener sendwordRequestListener = new MyDynamicRequestListener(activity);
        BabayDynamicRequest schoolRecipeRequest = new BabayDynamicRequest(
                Dynamic.Model.class, activity, "", more);
        activity.spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void upUiData(Dynamic.Model model) {
        if (model == null && dynamics.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) rootview.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) rootview.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(activity, "已经全部加载", Toast.LENGTH_SHORT).show();
        } else {
            more = model.getMore();
            if (STATE.equals(REFRESH)) {//如果正在刷新就清空
                dynamics.clear();
            }
            dynamics.addAll(model.getData());
            if (dynamicAdapter == null) {
                String[] resKeys = new String[]{"getUserphoto", "getUserName",
                        "getPosttime3", "getContent2",
                        "getsPicAry0", "getsPicAry1",
                        "getsPicAry2", "getPosttime2", "getTransmit2", "getReview2", "gettContent"};
                int[] reses = new int[]{R.id.dynamic_Avatar, R.id.dynamic_name,
                        R.id.dynamic_time, R.id.dynamic_context,
                        R.id.dynamic_image1, R.id.dynamic_image2,
                        R.id.dynamic_image3, R.id.dynamic_time2, R.id.dynamic_zf_num, R.id.dynamic_pl_num, R.id.dynamic_dcontext};
                dynamicAdapter = new DynamicAdapter(activity, dynamics, R.layout.layout_item_dynamic, resKeys, reses);
                xListView.setAdapter(dynamicAdapter);
            } else {
                dynamicAdapter.notifyDataSetChanged();
            }
        }
        STATE = "";//清空状态
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
