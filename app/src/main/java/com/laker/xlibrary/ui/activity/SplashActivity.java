package com.laker.xlibrary.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.widget.RatingBar;
import android.widget.TextView;

import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseActivity;
import com.laker.xlibrary.callback.DialogCallback;
import com.laker.xlibrary.model.StoreBean;
import com.laker.xlibrary.model.Weather;
import com.laker.xlibrary.utils.Urls;
import com.laker.xlibs.adapter.XRecyclerViewAdapter;
import com.laker.xlibs.adapter.XViewHolder;
import com.laker.xlibs.adapter.decoration.DividerDecoration;
import com.laker.xlibs.base.XActivity;
import com.laker.xlibs.common.XActivityStack;
import com.laker.xlibs.util.DoubleExitUtil;
import com.laker.xlibs.utils.XAppUtils;
import com.laker.xlibs.utils.log.XLog;
import com.laker.xlibs.widget.FixedLinearLayoutManager;
import com.laker.xlibs.widget.XToast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SplashActivity extends BaseActivity {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.rv_list)
    RecyclerView rv_list;
    XRecyclerViewAdapter<StoreBean> xRecyclerViewAdapter;
    List<StoreBean> dataList = new ArrayList<>();
    private DoubleExitUtil mDoubleClickExit;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mDoubleClickExit = new DoubleExitUtil(this);
        tv_content.setText("this is fragment 2");
        getData();
        xRecyclerViewAdapter = new XRecyclerViewAdapter<StoreBean>(rv_list, dataList, R.layout.fragment_home_rv_item) {
            @Override
            protected void bindData(XViewHolder xViewHolder, StoreBean s, int i) {
                xViewHolder.setText(R.id.tv_user_name, s.getStoreName());
                xViewHolder.setText(R.id.tv_product_des, s.getStoreLabel());
                ((RatingBar) xViewHolder.getView(R.id.rb_store_rating)).setRating(s.getStoreRate());
            }
        };
        rv_list.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"), 1));
        rv_list.setLayoutManager(new FixedLinearLayoutManager(this));
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
        OkGo.<Weather>get(Urls.TEST_URL).execute(new DialogCallback<Weather>(this) {
            @Override
            public void onSuccess(Response<Weather> response) {
                XToast.normal("onSuccess response.code=" + response.code());
                XLog.i(" response = " + response.body().toString());

                StoreBean storeBean;
                for (int i = 0; i < 10; i++) {
                    storeBean = new StoreBean();
                    storeBean.setStoreName("name" + (dataList.size()));
                    storeBean.setStoreLabel("label" + (dataList.size()));
                    storeBean.setStoreRate(dataList.size() % 6);
                    dataList.add(storeBean);
                }

                if (xRecyclerViewAdapter != null) {
                    xRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 是否退出应用
            boolean exit = mDoubleClickExit.onKeyDown(keyCode, event);
            if (exit) {
                XActivityStack.getInstance().appExit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
