package com.laker.xlibrary.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kerchin.widget.GridItemClickListener;
import com.kerchin.widget.GridItemLongClickListener;
import com.kerchin.widget.GridViewPager;
import com.kerchin.widget.Model;
import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseFragment;
import com.laker.xlibrary.callback.DialogCallback;
import com.laker.xlibrary.loader.GlideImageLoaderForBanner;
import com.laker.xlibrary.model.Weather;
import com.laker.xlibrary.utils.Urls;
import com.laker.xlibs.XFrame;
import com.laker.xlibs.adapter.XRecyclerViewAdapter;
import com.laker.xlibs.adapter.XViewHolder;
import com.laker.xlibs.adapter.decoration.DividerDecoration;
import com.laker.xlibs.utils.log.XLog;
import com.laker.xlibs.utils.permission.XPermission;
import com.laker.xlibs.widget.ActionSheetDialog;
import com.laker.xlibs.widget.XToast;
import com.laker.xlibs.widget.loadingview.XLoadingView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainFragment1 extends BaseFragment implements OnBannerListener, SwipeRefreshLayout.OnRefreshListener {
    XRecyclerViewAdapter<String> xRecyclerViewAdapter;
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.xLoadingView)
    XLoadingView xLoadingView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.mGridViewPager)
    GridViewPager mGridViewPager;

    ActionSheetDialog mActionSheetDialog;
    ActionSheetDialog mActionSheetDialogCustom;
    List<ActionSheetDialog.SheetItem> dataListSheetItem = new ArrayList<>();

    String[] paths = {
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg",
    };
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    List adList = new ArrayList();
    List<String> adList2 = new ArrayList();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 1 ");
        dataListSheetItem.add(new ActionSheetDialog.SheetItem("相机", 0, R.mipmap.ic_launcher));
        dataListSheetItem.add(new ActionSheetDialog.SheetItem("相册", 1, R.mipmap.ic_launcher));
        dataListSheetItem.add(new ActionSheetDialog.SheetItem("相册2", 2, R.mipmap.ic_launcher));
        dataListSheetItem.add(new ActionSheetDialog.SheetItem("相册3", 3, R.mipmap.ic_launcher));
        mActionSheetDialog = new ActionSheetDialog(getActivity());
        mActionSheetDialogCustom = new ActionSheetDialog(getActivity(), true);
        mActionSheetDialog.builder().addSheetItems(dataListSheetItem).OnSheetItemClickListener(
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        XToast.normal(dataListSheetItem.get(which).name);
                    }
                });
        mActionSheetDialogCustom.builder().addSheetItems(dataListSheetItem).OnSheetItemClickListener(
                new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        XToast.normal(dataListSheetItem.get(which).name);

                    }
                }).OnSheetItemRightClickListener(new ActionSheetDialog.OnSheetItemRightClickListener() {
            @Override
            public void onClick(int which) {
                XToast.normal("right " + dataListSheetItem.get(which).name);
            }
        });
        for (int i = 0; i < paths.length; i++) {
            adList.add(paths[i]);
            adList2.add(paths[i]);
            adList2.add(paths[i]);
            adList2.add(paths[i]);
        }
        banner.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, XFrame.screenHeight / 4));
        banner.setImages(adList)
                .setImageLoader(new GlideImageLoaderForBanner())
                .setOnBannerListener(this)
                .isLoop(true)
                .isAutoPlay(false)
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        xRecyclerViewAdapter = new XRecyclerViewAdapter<String>(recyclerView, adList2, R.layout.test) {
            @Override
            protected void bindData(XViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_title, data);
            }
        };
        //添加分割线
        recyclerView.addItemDecoration(new DividerDecoration(Color.parseColor("#C4C4C4"), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(xRecyclerViewAdapter);
        xLoadingView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XToast.success("重新加载");
                xLoadingView.showLoading();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.main_color);
        mGridViewPager
                .setNumColumns(5)
                .setNumRows(2)
                .setGridItemClickListener(new GridItemClickListener() {
                    @Override
                    public void click(int pos, int position, String str) {
                        XToast.normal("click" + pos + "/" + str);
                    }
                })
                .setGridItemLongClickListener(new GridItemLongClickListener() {
                    @Override
                    public void click(int pos, int position, String str) {
                        XToast.normal("longClick" + pos + "/" + str);
                    }
                })
                .init(initGridPagerData());


    }

    @Override
    public void initView() {

    }

    /**
     * 初始化数据源
     */
    private List<Model> initGridPagerData() {
        List<Model> mData = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getActivity().getPackageName());
            mData.add(new Model(titles[i], imageId));
        }
        return mData;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden) {
//            XToast.info("hidden");
//            banner.stopAutoPlay();
//        } else {
//            XToast.info("show");
//            banner.startAutoPlay();
//        }
    }

    @OnClick({R.id.action_network_error, R.id.action_loading, R.id.action_error,
            R.id.action_content, R.id.action_empty, R.id.btn_call, R.id.btn_camera,
            R.id.btn_all, R.id.btn_request, R.id.btn_action_sheet, R.id.btn_custom_sheet})
    public void click(TextView button) {
        switch (button.getId()) {
            case R.id.action_network_error:
                xLoadingView.showNoNetwork();
                break;
            case R.id.action_loading:
                xLoadingView.showLoading();

                break;
            case R.id.action_error:
                xLoadingView.showEmpty();
                break;
            case R.id.action_content:
                xLoadingView.showContent();
                break;
            case R.id.action_empty:
                xLoadingView.showEmpty();
                break;
            case R.id.btn_call:
                doCallPhone();
                break;
            case R.id.btn_camera:
                doCamera();
                break;
            case R.id.btn_all:
                sendPermission();
                break;
            case R.id.btn_action_sheet:
                mActionSheetDialog.show();
                break;
            case R.id.btn_custom_sheet:
                mActionSheetDialogCustom.show();
                break;
            case R.id.btn_request:
                OkGo.<Weather>get(Urls.TEST_URL).execute(new DialogCallback<Weather>(getActivity()) {
                    @Override
                    public void onSuccess(Response<Weather> response) {
                        XToast.normal("onSuccess response.code=" + response.code());
                        XLog.i(" response = " + response.body().toString());
                    }
                });
                break;
            default:
                break;
        }
    }

    @Override
    public void OnBannerClick(int position) {
        XToast.info("click ad " + position);
    }

    @Override
    public void onRefresh() {
        XToast.normal("onRefresh");
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 拨打电话
     */
    private void doCallPhone() {
        XPermission.requestPermissions(getActivity(), 100, new String[]{Manifest.permission.CALL_PHONE}, new XPermission.OnPermissionListener() {
            //权限申请成功时调用
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:18782938042"));
                startActivity(intent);
            }

            //权限被用户禁止时调用
            @Override
            public void onPermissionDenied() {
                //给出友好提示，并且提示启动当前应用设置页面打开权限
                XPermission.showTipsDialog(getActivity());
            }
        });
    }

    /**
     * 照相
     */
    private void doCamera() {
        XPermission.requestPermissions(getActivity(), 101, new String[]{Manifest.permission
                .CAMERA}, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }

            @Override
            public void onPermissionDenied() {
                XPermission.showTipsDialog(getActivity());
            }
        });
    }

    /**
     * 多个权限
     */
    private void sendPermission() {
        XPermission.requestPermissions(getActivity(), 102, new String[]{
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.WRITE_CONTACTS
        }, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                XToast.normal("申请成功！");
            }

            @Override
            public void onPermissionDenied() {
                XPermission.showTipsDialog(getActivity());
            }
        });
    }
}
