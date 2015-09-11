package com.mckuai.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.mckuai.adapter.FragmentAdapter;
import com.mckuai.imc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResourceFragment extends BaseFragment implements ViewPager.OnPageChangeListener, CompoundButton.OnCheckedChangeListener {
    ViewPager viewPager;
    View view;
    RadioButton btn_res_map;
    RadioButton btn_res_skin;

    FragmentAdapter adapter;

    boolean isViewChanged = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == view) {
            view = inflater.inflate(R.layout.fragment_resource, container, false);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && null != view) {
            showData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && null != view) {
            showData();
        }
    }

    private void initView() {

        viewPager = (ViewPager) view.findViewById(R.id.vp_resource);
        btn_res_skin = (RadioButton) view.findViewById(R.id.rb_resource_skin);
        btn_res_map = (RadioButton) view.findViewById(R.id.rb_resource_map);
        viewPager.setOnPageChangeListener(this);
        btn_res_map.setOnCheckedChangeListener(this);
        btn_res_skin.setOnCheckedChangeListener(this);
            /*PagerTabStrip tabStrip = (PagerTabStrip) view.findViewById(R.id.tabstrip);
            tabStrip.setTabIndicatorColor(this.getResources().getColor(R.color.background_green));
            tabStrip.setTextSpacing(200);
            tabStrip.setDrawFullUnderline(false);*/
    }


    private void showData() {
        if (null == viewPager) {
            initView();
        }
        if (null == adapter) {
            adapter = new FragmentAdapter(getChildFragmentManager(), 1);
        }
        viewPager.setAdapter(adapter);
        switch (viewPager.getCurrentItem()) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:
                isViewChanged = true;
                btn_res_map.setChecked(true);
                isViewChanged = false;
                break;

            case 1:
                isViewChanged = true;
                btn_res_skin.setChecked(true);
                isViewChanged = false;
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_resource_skin:
                if (isChecked) {
                    viewPager.setCurrentItem(1);
                    btn_res_map.setChecked(false);
                }
                break;
            case R.id.rb_resource_map:
                if (isChecked) {
                    viewPager.setCurrentItem(0);
                    btn_res_skin.setChecked(false);
                }
                break;
        }
    }

    @Override
    public void onRightButtonClicked(String searchContent) {
        if (null != adapter && null != searchContent){
            adapter.currentFragment.onRightButtonClicked(searchContent);
        }
    }

    @Override
    public boolean onBackKeyPressed() {
        if (null != viewPager){
            return adapter.currentFragment.onBackKeyPressed();
        }
        return super.onBackKeyPressed();
    }
}
