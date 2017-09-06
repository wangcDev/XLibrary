package com.laker.xlibrary.ui.fragment.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;

import butterknife.Bind;

/**
 * des:新闻首页首页
 * Created by xsf
 * on 2016.09.16:45
 */
public class MainFragment1 extends BaseFragment {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 1 ");
    }

    @Override
    public void initView() {

    }
}
