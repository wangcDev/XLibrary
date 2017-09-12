package com.laker.xlibrary.view.dropDownMenu;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.services.district.DistrictItem;
import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.laker.xlibrary.R;
import com.laker.xlibrary.view.dropDownMenu.entity.FilterDistrict;
import com.laker.xlibrary.view.dropDownMenu.entity.FilterType;
import com.laker.xlibrary.view.dropDownMenu.view.betterDoubleGrid.BetterDoubleGridView;

import java.util.ArrayList;
import java.util.List;

public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;
    private List<FilterDistrict> leftData;

    public DropMenuAdapter(Context context, String[] titles, List<FilterDistrict> leftData, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
        this.leftData = leftData;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createSingleListView(0);
                break;
            case 1:
                view = createDoubleListViewWithDistrict2(1,leftData);
                break;
            case 2:
                view = createSingleGridView(2);
                break;
            case 3:
                view = createBetterDoubleGrid(3);
                break;
        }

        return view;
    }

    private View createSingleListView(final int titlePosition) {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(int pos, String item) {
                        int[] ints = {pos};
                        onFilterDone(titlePosition, item, ints);
                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            list.add("" + i);
        }
        singleListView.setList(list, -1);

        return singleListView;
    }


    private View createDoubleListViewWithDistrict2(final int titlePosition, List<FilterDistrict> left) {
        DoubleListView<FilterDistrict, DistrictItem> comTypeDoubleListView = new DoubleListView<FilterDistrict, DistrictItem>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterDistrict>(null, mContext) {
                    @Override
                    public String provideText(FilterDistrict districtItem) {
                        return districtItem.desc.getName();
                    }
                })
                .rightAdapter(new SimpleTextAdapter<DistrictItem>(null, mContext) {
                    @Override
                    public String provideText(DistrictItem districtItem) {
                        return districtItem.getName();
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterDistrict, DistrictItem>() {
                    @Override
                    public List<DistrictItem> provideRightList(FilterDistrict district, int position) {
                        int[] poss = {position, -1};
                        if (district.child.size() == 0) {
                            onFilterDone(titlePosition, district.desc.getName(), poss);
                        }
                        return district.child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterDistrict, DistrictItem>() {
                    @Override
                    public void onRightItemClick(FilterDistrict item, DistrictItem childItem,int leftPosition,int rightPosition) {
                        int[] poss = {leftPosition, rightPosition};
                        onFilterDone(titlePosition, childItem.getName(), poss);
                    }
                });
        comTypeDoubleListView.setLeftList(left, 0);
        if (left.get(0).child.size() >= 1)
            comTypeDoubleListView.setRightList(left.get(0).child, 0);
        return comTypeDoubleListView;
    }


    private View createDoubleListViewWithString(final int titlePosition) {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            int[] ints = {position};
                            onFilterDone(titlePosition, item.desc, ints);
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String childItem, int leftPosition, int rightPosition) {
                        int[] ints = {leftPosition,rightPosition};
                        onFilterDone(titlePosition, childItem, ints);
                    }
                });


        List<FilterType> list = new ArrayList<>();

        //第一项
        FilterType filterType = new FilterType();
        filterType.desc = "10";
        list.add(filterType);

        //第二项
        filterType = new FilterType();
        filterType.desc = "11";
        List<String> childList = new ArrayList<>();
        for (int i = 0; i < 13; ++i) {
            childList.add("11" + i);
        }
        filterType.child = childList;
        list.add(filterType);

        //第三项
        filterType = new FilterType();
        filterType.desc = "12";
        childList = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            childList.add("12" + i);
        }
        filterType.child = childList;
        list.add(filterType);

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 1);
        comTypeDoubleListView.setRightList(list.get(1).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


    private View createSingleGridView(final int titlePosition) {
        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
                        checkedTextView.setGravity(Gravity.CENTER);
                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(int pos, String item) {
                        int[] ints = {pos};
                        onFilterDone(titlePosition, item, ints);

                    }
                });

        List<String> list = new ArrayList<>();
        for (int i = 20; i < 39; ++i) {
            list.add(String.valueOf(i));
        }
        singleGridView.setList(list, -1);


        return singleGridView;
    }


    private View createBetterDoubleGrid(int titlePosition) {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setTitlePosition(titlePosition)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private void onFilterDone(int titlePosition, String positionTitle, int[] dataPositions) {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(titlePosition, positionTitle, dataPositions);
        }
    }

//    private void onFilterDone(int position, String positionTitle, String urlValue) {
//        if (onFilterDoneListener != null) {
//            onFilterDoneListener.onFilterDone(position, positionTitle, urlValue);
//        }
//    }

}
