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


public class MainFragment2 extends BaseFragment implements OnFilterDoneListener {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    @Bind(R.id.mFilterContentView)
    TextView mFilterContentView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
        initFilterDropDownView();
    }

    @Override
    public void initView() {

    }

    private void initFilterDropDownView() {
        String[] titleList = new String[]{"第一个", "第二个", "第三个", "第四个"};
//        dropDownMenu.setMenuAdapter(new DropMenuAdapter(getActivity(), titleList, this));
    }

    @Override
    public void onFilterDone(int position, String positionTitle, int[] dataPositions) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        dropDownMenu.close();
        mFilterContentView.setText(FilterUrl.instance().toString());
    }
}
