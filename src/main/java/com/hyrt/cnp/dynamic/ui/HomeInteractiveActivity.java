package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.dynamic.R;
import com.hyrt.cnp.dynamic.fragment.AboutmeFragment;
import com.hyrt.cnp.dynamic.fragment.AlldynamicFragment;
import com.hyrt.cnp.dynamic.fragment.MyAblumsFragment;
import com.hyrt.cnp.dynamic.fragment.MyIndexFragment;
import com.jingdong.common.frame.BaseActivity;

/**
 * Created by GYH on 14-3-12.
 */
public class HomeInteractiveActivity extends BaseActivity{

    //fragment管理
    public FragmentTransaction fragmentTransaction;
    public FragmentManager fragmentManager;

    public AlldynamicFragment alldaynamicfragment=null;//全部动态
    public AboutmeFragment aboutmeFragment=null;//与我相关
    public MyIndexFragment myIndexFragment=null;//我的主页
    public MyAblumsFragment myAblumsFragment=null;//动感相册

    private Menu mymenu;//菜单

    private LinearLayout mContainer;//内容布局

    //布局
    private LinearLayout ly_alldynamic;
    private LinearLayout ly_aboutme;
    private LinearLayout ly_myindex;
    private LinearLayout ly_ablums;

    private TextView tv_num_alldynamic;
    private TextView tv_num_aboutme;
    private TextView tv_num_myindex;
    private TextView tv_num_ablums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeinteractive);
        initView();
        initDataFragment(0);
    }

    /**
     *初始化布局
     * */
    private void initView(){
        mContainer=(LinearLayout)findViewById(R.id.homeinter);
        ly_ablums=(LinearLayout)findViewById(R.id.layout_bottom_ablums);
        ly_myindex=(LinearLayout)findViewById(R.id.layout_bottom_myindex);
        ly_aboutme=(LinearLayout)findViewById(R.id.layout_bottom_aboutme);
        ly_alldynamic=(LinearLayout)findViewById(R.id.layout_bottom_alldynamic);
        tv_num_alldynamic=(TextView)findViewById(R.id.layout_bottom_alldynamic_num);
        tv_num_aboutme=(TextView)findViewById(R.id.layout_bottom_aboutme_num);
        tv_num_myindex=(TextView)findViewById(R.id.layout_bottom_myindex_num);
        tv_num_ablums=(TextView)findViewById(R.id.layout_bottom_ablums_num);
        ly_ablums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymenu.clear();
                mymenu.add("abc")
                        .setIcon(R.drawable.actionbar_right)
                        .setShowAsAction(
                                MenuItem.SHOW_AS_ACTION_ALWAYS);
                titletext.setText("动感相册");
                showFragment(3);
            }
        });
        ly_myindex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymenu.clear();
                mymenu.add("abc")
                        .setIcon(R.drawable.actionbar_right)
                        .setShowAsAction(
                                MenuItem.SHOW_AS_ACTION_ALWAYS);
                titletext.setText("我的主页");
                fragmentTransaction = fragmentManager.beginTransaction();//时候transaction来管理集合
                showFragment(2);
            }
        });

        ly_alldynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymenu.clear();
                mymenu.add("发布动态")
                        .setIcon(R.drawable.editbtn)
                        .setShowAsAction(
                                MenuItem.SHOW_AS_ACTION_ALWAYS);
                titletext.setText("全部动态");
                showFragment(0);
            }
        });

        ly_aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymenu.clear();
                mymenu.add("abc")
                        .setIcon(R.drawable.actionbar_right)
                        .setShowAsAction(
                                MenuItem.SHOW_AS_ACTION_ALWAYS);
                titletext.setText("与我相关");
                showFragment(1);
//                initDataFragment(1);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("发布动态")
                .setIcon(R.drawable.editbtn)
                .setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_ALWAYS);
        this.mymenu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("发布动态")){
            Intent intent = new Intent();
//            intent.setClass(HomeInteractiveActivity.this, DynamicCommentActivity.class);
            intent.putExtra("Category", "pl");
//            intent.putExtra("vo", dynamic);
//            startActivityForResult(intent, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化界面布局
     * */

    private void initDataFragment(int id){
        fragmentManager = getSupportFragmentManager();//实例化fragment管理
        fragmentTransaction = fragmentManager.beginTransaction();//时候transaction来管理集合
        alldaynamicfragment=new AlldynamicFragment();
        fragmentTransaction.add(R.id.homeinter,alldaynamicfragment);//向布局中添加fragment
        fragmentTransaction.commit();//提交到ui线程中去

//        Fragment fragment = (Fragment) mFragmentPagerAdapter.instantiateItem(mContainer, id);
//        mFragmentPagerAdapter.setPrimaryItem(mContainer, 0, fragment);
//        mFragmentPagerAdapter.finishUpdate(mContainer);
    }

    /**
     * fragment切换
     * */
    private void showFragment(int id){
        fragmentTransaction = fragmentManager.beginTransaction();//时候transaction来管理集合
        switch (id){
            case 0:
                if(alldaynamicfragment==null){
                    alldaynamicfragment=new AlldynamicFragment();
                }
                fragmentTransaction.replace(R.id.homeinter,alldaynamicfragment);//向布局中添加fragment
                break;
            case 1:
                if(aboutmeFragment==null){
                    aboutmeFragment=new AboutmeFragment();
                }
                fragmentTransaction.replace(R.id.homeinter,aboutmeFragment);//向布局中添加fragment
                break;
            case 2:
                if(myIndexFragment==null){
                    myIndexFragment=new MyIndexFragment();
                }
                fragmentTransaction.replace(R.id.homeinter,myIndexFragment);//向布局中添加fragment
                break;
            case 3:
                if(myAblumsFragment==null){
                    myAblumsFragment=new MyAblumsFragment();
                }
                fragmentTransaction.replace(R.id.homeinter,myAblumsFragment);//向布局中添加fragment
                break;
        }
        fragmentTransaction.commit();//提交到ui线程中去
    }

    private FragmentPagerAdapter mFragmentPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int i) {
            switch (i){
                default:
                    alldaynamicfragment=new AlldynamicFragment().instantiation(0);
                    return alldaynamicfragment;
                case 1:
                    aboutmeFragment=new AboutmeFragment().instantiation(1);
                    return aboutmeFragment;
                case 2:
                    return null;
                case 3:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

}
