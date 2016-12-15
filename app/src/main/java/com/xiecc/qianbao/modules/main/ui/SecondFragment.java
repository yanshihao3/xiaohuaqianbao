package com.xiecc.qianbao.modules.main.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiecc.qianbao.R;
import com.xiecc.qianbao.modules.main.adapter.HomePagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    public SecondFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        HomePagerAdapter mHomePagerAdapter = new HomePagerAdapter(getActivity().getSupportFragmentManager());
        mHomePagerAdapter.addTab(new shangyeFragment(), "商业贷款");
        mHomePagerAdapter.addTab(new gongjijinFragment(), "公积金贷款");
        mHomePagerAdapter.addTab(new HunHeFragment(), "混合金贷款");
        viewPager.setAdapter(mHomePagerAdapter);
        tabLayout.setupWithViewPager(viewPager, false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
