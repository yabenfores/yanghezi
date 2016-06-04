package com.raoleqing.yangmatou.ben;

import android.app.Activity;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by Administrator on 2016/2/3.
 */
public class BaiduMapHelper {

    private static String encode = "bd09ll";

    public static LocationClient buildLocationClient(Activity activity, BDLocationListener bdLocationListener) {
        LocationClient mLocClient = new LocationClient(activity);
        mLocClient.registerLocationListener(bdLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);// 打开gps
        option.setCoorType(encode); // 设置坐标类型
        option.setIsNeedAddress(true);  //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        //option.setScanSpan(2000);    //设置更新定位时间
        //option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        //option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        //option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        return mLocClient;
    }

    public static void geoCodeResult(String city, String address, final OnGetGeoCoderResultListener geoCoderResultListener) {
        final GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                if (geoCoderResultListener != null)
                    geoCoderResultListener.onGetGeoCodeResult(geoCodeResult);
                mSearch.destroy();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                mSearch.destroy();
            }
        });
//        if (TextUtils.isEmpty(address))
//            mSearch.geocode(new GeoCodeOption().city(city));
//        else
            mSearch.geocode(new GeoCodeOption().city(city).address(address));
    }

    public static void reverseGeoCode(double latitude, double longitude, final OnReverseGeoCodeResult reverseGeoCodeResult) {
        final GeoCoder mSearch = GeoCoder.newInstance();
        LatLng ll = new LatLng(latitude, longitude);
        mSearch.setOnGetGeoCodeResultListener(new com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                mSearch.destroy();
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (reverseGeoCodeResult != null)
                    reverseGeoCodeResult.onReverseGeoCodeResult(result);
                mSearch.destroy();
            }
        });
        // 反Geo搜索
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
    }

    public interface OnGetGeoCoderResultListener {
        void onGetGeoCodeResult(GeoCodeResult geoCodeResult);
    }

    public interface OnReverseGeoCodeResult {
        void onReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult);
    }
}
