package com.laker.xlibrary.ui.fragment.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;

import butterknife.Bind;


public class MainFragment2 extends BaseFragment {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
    }

    @Override
    public void initView() {

    }
}
