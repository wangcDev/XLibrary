package com.laker.xlibrary.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.laker.xlibrary.R;
import com.laker.xlibrary.app.AppConstant;
import com.laker.xlibrary.base.BaseActivity;
import com.laker.xlibrary.model.TabEntity;
import com.laker.xlibrary.ui.fragment.MainFragment1;
import com.laker.xlibrary.ui.fragment.MainFragment2;
import com.laker.xlibrary.ui.fragment.MainFragment3;
import com.laker.xlibrary.ui.fragment.MainFragment4;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity2 extends BaseActivity {
    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private String[] mTitles = {"首页", "美女", "视频", "关注"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.ic_home_normal, R.mipmap.ic_girl_normal, R.mipmap.ic_video_normal, R.mipmap.ic_care_normal};
    private int[] mIconSelectIds = {
            R.mipmap.ic_home_selected, R.mipmap.ic_girl_selected, R.mipmap.ic_video_selected, R.mipmap.ic_care_selected};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private MainFragment1 fragment1;
    private MainFragment2 fragment2;
    private MainFragment3 fragment3;
    private MainFragment4 fragment4;
    private static int tabLayoutHeight;

    /**
     * 入口
     *
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity2.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        setStatusBarColor(R.color.blue);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        tabLayout.measure(0, 0);
        tabLayoutHeight = tabLayout.getMeasuredHeight();
    }

    @Override
    public void initView() {
        //初始化菜单
        initTab();
    }

    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
//        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                SwitchTo(position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//            }
//        });
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            fragment1 = (MainFragment1) getSupportFragmentManager().findFragmentByTag("fragment1");
            fragment2 = (MainFragment2) getSupportFragmentManager().findFragmentByTag("fragment2");
            fragment3 = (MainFragment3) getSupportFragmentManager().findFragmentByTag("fragment3");
            fragment4 = (MainFragment4) getSupportFragmentManager().findFragmentByTag("fragment4");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            fragment1 = new MainFragment1();
            fragment2 = new MainFragment2();
            fragment3 = new MainFragment3();
            fragment4 = new MainFragment4();

//            transaction.add(R.id.fl_body, fragment1, "fragment1");
//            transaction.add(R.id.fl_body, fragment2, "fragment2");
//            transaction.add(R.id.fl_body, fragment3, "fragment3");
//            transaction.add(R.id.fl_body, fragment4, "fragment4");
        }
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mFragments.add(fragment4);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        transaction.commit();
//        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(currentTabPosition);
    }


    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(fragment2);
                transaction.hide(fragment3);
                transaction.hide(fragment4);
                transaction.show(fragment1);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(fragment1);
                transaction.hide(fragment3);
                transaction.hide(fragment4);
                transaction.show(fragment2);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(fragment1);
                transaction.hide(fragment2);
                transaction.hide(fragment4);
                transaction.show(fragment3);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(fragment1);
                transaction.hide(fragment2);
                transaction.hide(fragment3);
                transaction.show(fragment4);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    /**
     * 菜单显示隐藏动画
     *
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide) {
        final ViewGroup.LayoutParams layoutParams = tabLayout.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator alpha;
        if (!showOrHide) {
            valueAnimator = ValueAnimator.ofInt(tabLayoutHeight, 0);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 1, 0);
        } else {
            valueAnimator = ValueAnimator.ofInt(0, tabLayoutHeight);
            alpha = ObjectAnimator.ofFloat(tabLayout, "alpha", 0, 1);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                tabLayout.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.playTogether(valueAnimator, alpha);
        animatorSet.start();
    }

    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (tabLayout != null) {
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

private class MyPagerAdapter extends FragmentPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
}
