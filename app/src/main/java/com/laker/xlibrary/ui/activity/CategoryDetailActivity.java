package com.laker.xlibrary.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.baiiu.filter.DropDownMenu;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.laker.xlibrary.R;
import com.laker.xlibrary.base.BaseActivity;
import com.laker.xlibrary.view.dropDownMenu.DropMenuAdapter;
import com.laker.xlibrary.view.dropDownMenu.entity.FilterDistrict;
import com.laker.xlibs.utils.log.XLog;
import com.laker.xlibs.widget.XToast;
import com.xsf.zxing.ScanMainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class CategoryDetailActivity extends BaseActivity implements OnFilterDoneListener, DistrictSearch.OnDistrictSearchListener {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    @Bind(R.id.mFilterContentView)
    TextView mFilterContentView;

    private boolean getAllDistrict = false;
    private int subDistrictSize = 0;
    private List<FilterDistrict> filterDistricts = new ArrayList<>();


    // 当前行政区划
    private DistrictItem currentDistrictItem = null;

    // 下级行政区划集合
    private Map<String, List<DistrictItem>> subDistrictMap = new HashMap<String, List<DistrictItem>>();

    private boolean getSubCity = false; //是否获取到市下面的区县
    private int subCityIndex = 0;
    private String cityCode = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
        // 设置行政区划查询监听
        DistrictSearch districtSearch = new DistrictSearch(this);
        districtSearch.setOnDistrictSearchListener(this);
        // 查询中国的区划
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords("成都");
        districtSearch.setQuery(query);
        // 异步查询行政区
        districtSearch.searchDistrictAsyn();

    }

    @Override
    public void initView() {

    }

    private void initFilterDropDownView(List<FilterDistrict> leftData) {
        String[] titleList = new String[]{"第一个", "第二个", "第三个", "第四个"};
        int[] resIds = {R.mipmap.ic_category_0,R.mipmap.ic_category_1,R.mipmap.ic_category_2,R.mipmap.ic_category_3};
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList, leftData, this),resIds);
    }

    @Override
    public void onFilterDone(int position, String positionTitle, int[] dataPositions) {
        dropDownMenu.close();
        mFilterContentView.setText(positionTitle);
        dropDownMenu.setPositionIndicatorText(position, positionTitle);

        if (position == 1) {
            XLog.e("onFilterDone " + getDistrictInfoStr(filterDistricts.get(dataPositions[0]).child.get(dataPositions[1])));
        }
    }

    @Override
    public void onDistrictSearched(DistrictResult result) {
        if (result != null) {
            if (result.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {

                List<DistrictItem> district = result.getDistrict();

                currentDistrictItem = district.get(0);

                // 将查询得到的区划的下级区划写入缓存
                for (int i = 0; i < district.size(); i++) {

                    DistrictItem districtItem = district.get(i);
                    // 存当前区划的下级区划列表
                    subDistrictMap.put(districtItem.getAdcode(),
                            districtItem.getSubDistrict());
                    if (getSubCity) {
                        subCityIndex++;
                        getAllDistrict = subCityIndex == subDistrictSize;
                    }

                }

                if (!getSubCity) {
                    //获取到市下面的区县，此时subDistrictList 包含所有区县
                    getSubCity = true;
                    querySubDistrictList(subDistrictMap
                            .get(currentDistrictItem.getAdcode()));
                    subDistrictSize = subDistrictMap
                            .get(currentDistrictItem.getAdcode()).size();
                    cityCode = currentDistrictItem.getAdcode();
                }

                if (getAllDistrict) {

                    List<DistrictItem> districtItems = subDistrictMap.get(cityCode);
                    FilterDistrict filterDistrict;
                    for (int i = 0; i < districtItems.size(); i++) {
                        filterDistrict = new FilterDistrict();
                        filterDistrict.desc = districtItems.get(i);
                        filterDistrict.child = subDistrictMap.get(districtItems.get(i).getAdcode());
                        filterDistricts.add(filterDistrict);
                    }
                    initFilterDropDownView(filterDistricts);
                }

            } else {
                XToast.error(result.getAMapException().getErrorCode() + result.getAMapException().getErrorMessage());
            }
        }


    }

    private void querySubDistrictList(List<DistrictItem> subDistrictList) {
        for (int i = 0; i < subDistrictList.size(); i++) {
            querySubDistrict(subDistrictList.get(i));
        }
    }

    /**
     * 查询下级区划
     *
     * @param districtItem 要查询的区划对象
     */
    private void querySubDistrict(DistrictItem districtItem) {
        DistrictSearch districtSearch = new DistrictSearch(this);
        districtSearch.setOnDistrictSearchListener(this);
        // 异步查询行政区
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(districtItem.getName());
        districtSearch.setQuery(query);
        districtSearch.searchDistrictAsyn();
    }

    /**
     * 获取行政区划的信息字符串
     *
     * @param districtItem
     * @return
     */
    private String getDistrictInfoStr(DistrictItem districtItem) {
        StringBuffer sb = new StringBuffer();
        String name = districtItem.getName();
        String adcode = districtItem.getAdcode();
        String level = districtItem.getLevel();
        String citycode = districtItem.getCitycode();
        LatLonPoint center = districtItem.getCenter();
        sb.append("区划名称:" + name + "\n");
        sb.append("区域编码:" + adcode + "\n");
        sb.append("城市编码:" + citycode + "\n");
        sb.append("区划级别:" + level + "\n");
        if (null != center) {
            sb.append("经纬度:(" + center.getLongitude() + ", "
                    + center.getLatitude() + ")\n");
        }
        return sb.toString();
    }

    @OnClick(R.id.tv_content)
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_content:
                startActivity(ScanMainActivity.class);
                break;
        }
    }
}
