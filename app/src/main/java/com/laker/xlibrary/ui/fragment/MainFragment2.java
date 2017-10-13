package com.laker.xlibrary.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;
import com.laker.xlibrary.model.StoreBean;
import com.laker.xlibrary.view.dropDownMenu.DropMenuAdapter;
import com.laker.xlibrary.view.dropDownMenu.entity.FilterUrl;
import com.laker.xlibs.adapter.XRecyclerViewAdapter;
import com.laker.xlibs.adapter.XViewHolder;
import com.laker.xlibs.adapter.decoration.DividerDecoration;
import com.laker.xlibs.widget.FixedLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainFragment2 extends BaseFragment  {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.rv_list)
    RecyclerView rv_list;
    XRecyclerViewAdapter<StoreBean> xRecyclerViewAdapter;
    List<StoreBean> dataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
        getData();
        xRecyclerViewAdapter = new XRecyclerViewAdapter<StoreBean>(rv_list, dataList, R.layout.fragment_home_rv_item) {
            @Override
            protected void bindData(XViewHolder xViewHolder, StoreBean s, int i) {
                xViewHolder.setText(R.id.tv_user_name, s.getStoreName());
                xViewHolder.setText(R.id.tv_product_des, s.getStoreLabel());
                ((RatingBar)xViewHolder.getView(R.id.rb_store_rating)).setRating(s.getStoreRate());
            }
        };
        rv_list.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"), 1));
        rv_list.setLayoutManager(new FixedLinearLayoutManager(getActivity()));
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setAdapter(xRecyclerViewAdapter);
        xRecyclerViewAdapter.isLoadMore(true);
        xRecyclerViewAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onRetry() {

            }

            @Override
            public void onLoadMore() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                getData();
            }
        });
    }

    @Override
    public void initView() {

    }

    private void getData() {
//        rv_list.
        rv_list.scrollToPosition(dataList.size());
        StoreBean storeBean;
        for (int i = 0; i < 10; i++) {
            storeBean = new StoreBean();
            storeBean.setStoreName("name" + (dataList.size()));
            storeBean.setStoreLabel("label" + (dataList.size()));
            storeBean.setStoreRate(dataList.size() % 6);
            dataList.add(storeBean);
        }
//        if (dataList.size() > 50){
//            tv_getMore.setText("没有更多数据加载了！");
//            tv_getMore.setClickable(false);
//        }
        if (xRecyclerViewAdapter != null){
//            if (rv_list.getScrollState() == RecyclerView.SCROLL_STATE_IDLE ||
//                    !rv_list.isComputingLayout()) {
//                xRecyclerViewAdapter.notifyDataSetChanged();
//            }
            xRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
