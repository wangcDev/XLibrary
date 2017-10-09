package com.laker.xlibrary.ui.fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;

import butterknife.Bind;

public class MainFragment4 extends BaseFragment {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_4;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 4 ");
    }

    @Override
    public void initView() {

    }
}
