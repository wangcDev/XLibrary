package com.laker.xlibrary.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;
import com.laker.xlibrary.view.dropDownMenu.DropMenuAdapter;
import com.laker.xlibrary.view.dropDownMenu.entity.FilterUrl;

import butterknife.Bind;


public class MainFragment2 extends BaseFragment  {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.mFilterContentView)
    TextView mFilterContentView;

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
