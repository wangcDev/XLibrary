package com.laker.xlibrary.ui.activity;

import android.os.Bundle;
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
import com.laker.xlibrary.view.dropDownMenu.entity.FilterUrl;
import com.laker.xlibs.widget.XToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class CategoryDetailActivity extends BaseActivity implements OnFilterDoneListener,DistrictSearch.OnDistrictSearchListener {
    @Bind(R.id.tv_content)
    TextView tv_content;

    @Bind(R.id.dropDownMenu)
    DropDownMenu dropDownMenu;

    @Bind(R.id.mFilterContentView)
    TextView mFilterContentView;

//    public static final String COUNTRY = "country"; // 行政区划，国家级
//
//    public static final String PROVINCE = "province"; // 行政区划，省级

    public static final String CITY = "city"; // 行政区划，市级

    public static final String DISTRICT = "district"; // 行政区划，区级

    public static final String BUSINESS = "biz_area"; // 行政区划，商圈级

    //当前选中的级别
    private String selectedLevel = CITY;

    // 当前行政区划
    private DistrictItem currentDistrictItem = null;

    // 下级行政区划集合
    private Map<String, List<DistrictItem>> subDistrictMap = new HashMap<String, List<DistrictItem>>();

//    // 省级列表
//    private List<DistrictItem> provinceList = new ArrayList<DistrictItem>();
//
//    // 市级列表
//    private List<DistrictItem> cityList = new ArrayList<DistrictItem>();

    // 区县级列表
    private List<DistrictItem> districtList = new ArrayList<DistrictItem>();

    // 是否已经初始化
    private boolean isInit = false;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_content.setText("this is fragment 2");
//        initFilterDropDownView();

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
        dropDownMenu.setMenuAdapter(new DropMenuAdapter(this, titleList,leftData, this));
    }

    @Override
    public void onFilterDone(int position, String positionTitle, String urlValue) {
        if (position != 3) {
            dropDownMenu.setPositionIndicatorText(FilterUrl.instance().position, FilterUrl.instance().positionTitle);
        }

        dropDownMenu.close();
        mFilterContentView.setText(FilterUrl.instance().toString());
    }

    @Override
    public void onDistrictSearched(DistrictResult result) {
//        List<DistrictItem> subDistrictList  = null;
//        if (result != null) {
//            if (result.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
//
//                List<DistrictItem> district = result.getDistrict();
//
//                if (!isInit) {
//                    isInit = true;
//                    currentDistrictItem = district.get(0);
//                }
//
//                // 将查询得到的区划的下级区划写入缓存
//                for (int i = 0; i < district.size(); i++) {
//                    DistrictItem districtItem = district.get(i);
//                    subDistrictMap.put(districtItem.getAdcode(),
//                            districtItem.getSubDistrict());
//                }
//                // 获取当前区划的下级区划列表
//                subDistrictList = subDistrictMap
//                        .get(currentDistrictItem.getAdcode());
//            } else {
//                XToast.error(result.getAMapException().getErrorCode()+result.getAMapException().getErrorMessage());
//            }
//        }
//        setData(subDistrictList);


        List<DistrictItem> subDistrictList  = new ArrayList<>();
        if (result != null) {
            if (result.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {

               List<DistrictItem> district = result.getDistrict();

                if (!isInit) {
                    isInit = true;
                    currentDistrictItem = district.get(0);
                }

                // 将查询得到的区划的下级区划写入缓存
                for (int i = 0; i < district.size(); i++) {

                    DistrictItem districtItem = district.get(i);
                    subDistrictMap.put(districtItem.getAdcode(),
                            districtItem.getSubDistrict());

                }
                // 获取当前区划的下级区划列表
                subDistrictList = subDistrictMap
                        .get(currentDistrictItem.getAdcode());

            } else {
                XToast.error(result.getAMapException().getErrorCode()+result.getAMapException().getErrorMessage());
            }
        }
//        initFilterDropDownView(subDistrictList);
        setData(subDistrictList);




    }

    private void setData(List<DistrictItem> subDistrictList) {
        XToast.normal("setData");
        if (selectedLevel.equals(CITY)) {


        }else if (selectedLevel.equals(DISTRICT)) {


        }else  {


        }
    }

    public void query(DistrictItem districtItem){
        if (districtItem != null) {
            currentDistrictItem = districtItem;
            // 先查缓存如果缓存存在则直接从缓存中查找，无需再执行查询请求
            List<DistrictItem> cache = subDistrictMap.get(districtItem
                    .getAdcode());
            if (null != cache) {
                setData(cache);
            } else {
                querySubDistrict(districtItem);
            }
        }
    }

    /**
     * 查询下级区划
     *
     * @param districtItem
     *            要查询的区划对象
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
     * @param districtItem
     * @return
     */
    private String getDistrictInfoStr(DistrictItem districtItem){
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
}
