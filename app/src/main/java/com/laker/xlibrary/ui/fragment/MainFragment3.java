package com.laker.xlibrary.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aitsuki.swipe.SwipeItemLayout;
import com.aitsuki.swipe.SwipeMenuRecyclerView;
import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;
import com.laker.xlibrary.model.StoreBean;
import com.laker.xlibs.adapter.XRecyclerViewAdapter;
import com.laker.xlibs.adapter.XViewHolder;
import com.laker.xlibs.adapter.decoration.DividerDecoration;
import com.laker.xlibs.widget.FixedLinearLayoutManager;
import com.laker.xlibs.widget.XToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainFragment3 extends BaseFragment {

    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.rv_list)
    SwipeMenuRecyclerView rv_list;
    XRecyclerViewAdapter<StoreBean> xRecyclerViewAdapter;
    List<StoreBean> dataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_3;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
        getData();
        xRecyclerViewAdapter = new XRecyclerViewAdapter<StoreBean>(rv_list, dataList, R.layout.item_right_menu) {
            @Override
            protected void bindData(XViewHolder xViewHolder, StoreBean s,final int i) {
                xViewHolder.setText(R.id.tv_content, s.getStoreName());
                SwipeItemLayout swipeItemLayout = xViewHolder.getView(R.id.swipe_layout);
                swipeItemLayout.setSwipeEnable(true);
                xViewHolder.getView(R.id.right_menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        XToast.normal("click pos = "+i);
                    }
                });


            }
        };
        rv_list.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"), 1));
        rv_list.setLayoutManager(new FixedLinearLayoutManager(getActivity()));
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setAdapter(xRecyclerViewAdapter);
//        xRecyclerViewAdapter.isLoadMore(true);
//        xRecyclerViewAdapter.setOnLoadMoreListener(new XRecyclerViewAdapter.OnLoadMoreListener() {
//            @Override
//            public void onRetry() {
//
//            }
//
//            @Override
//            public void onLoadMore() {
//                getData();
//            }
//        });
//        ItemTouchListener mItemTouchListener = new ItemTouchListener() {
//            @Override
//            public void onItemClick(String str) {
////                ToastUtil.show(str);
//            }
//
//            @Override
//            public void onLeftMenuClick(String str) {
////                ToastUtil.show(str);
//            }
//
//            @Override
//            public void onRightMenuClick(String str) {
////                ToastUtil.show(str);
//            }
//        };

    }

    @Override
    public void initView() {

    }

    private void getData() {
        rv_list.scrollToPosition(dataList.size());
        StoreBean storeBean;
        for (int i = 0; i < 10; i++) {
            storeBean = new StoreBean();
            storeBean.setStoreName("name" + (dataList.size()));
            storeBean.setStoreLabel("label" + (dataList.size()));
            storeBean.setStoreRate(dataList.size() % 6);
            dataList.add(storeBean);
        }
        if (xRecyclerViewAdapter != null){
            xRecyclerViewAdapter.notifyDataSetChanged();
        }
    }
}
