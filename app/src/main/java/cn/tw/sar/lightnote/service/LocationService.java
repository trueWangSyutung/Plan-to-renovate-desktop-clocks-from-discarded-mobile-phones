package cn.tw.sar.lightnote.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import cn.tw.sar.lightnote.util.LogUtils;
import cn.tw.sar.lightnote.util.ObjectUtils;

/**
 * 获取定位服务
 */
public class LocationService extends Service {
    private LocationManager locationManager;
    private MyLocationListener myLocationListener;
    public static LocationCallBack mCallBack = null;

    public interface LocationCallBack {
        void Location_Return(double Location_latitude, double Location_longitude, String province, String city, String area, String featureName);
    }

    public void setCallback(LocationCallBack callback) {
        this.mCallBack = callback;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        myLocationListener = new MyLocationListener();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            // 每隔5秒获取一次GPS的定位信息，使用线程
            Thread thread = new Thread("LocationServiceThread") {
                @Override
                public void run() {
                    try {
                        while (true) {
                            // 1.获取位置提供者，GPS或者NetWork
                            // 2.获取位置
                            // 3.获取经纬度
                            // 4.停止定位服务
                            // 5.停止service
                            GPSLocation();
                            Thread.sleep(30*60*1000); // 30分钟获取一次
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();

        } catch (Exception e) {
            if (ObjectUtils.isNotEmpty(locationManager) && ObjectUtils.isNotEmpty(myLocationListener))
                locationManager.removeUpdates(myLocationListener); // 停止所有的定位服务
            stopSelf();  // 获取到经纬度以后，停止该service
        }
    }

    class MyLocationListener implements LocationListener {
        // 位置改变时获取经纬度
        @Override
        public void onLocationChanged(Location location) {
            if (ObjectUtils.isNotEmpty(location)) {
                toGeocoder(location);
            }
        }

        // 状态改变时
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // 提供者可以使用时
        @Override
        public void onProviderEnabled(String provider) {
        }

        // 提供者不可以使用时
        @Override
        public void onProviderDisabled(String provider) {
        }

    }
    /*
     **系统内容提供器介绍
     **一共为四种方式"passive","network","fused","gps"
     ** "gps"：GPS_PROVIDER(GNSS) GNSS HAL 接口和芯片打交道
     ** "network"：NETWORK_PROVIDER 依赖设备厂商的具体实现，Android 默认实现依赖 Google 提供的 GMS **  实现国内无法使用；一般厂商会实现为通过基站 / WIFI / 蓝牙进行融合定位
     ** "fused"：调用 GMS（谷歌移动服务） 进行定位，国内用不了
     ** "passive"：使用 PassiveProvider 在系统位置更新时通知给应用，分享其他 PROVIDER 的定位结果
     */
    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation(LocationManager locationManager) {
        Location bestLocation = null;
        List<String> providers = locationManager.getProviders(true);
        //由于国内用不了fused 剔除他
        if (providers.contains("fused")) providers.remove("fused");
        //这里我们按照首字母排序，使内容提供者调用顺序为gps network passive
        Collections.sort(providers);
        for (String provider : providers) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (ObjectUtils.isEmpty(location)) {
                //执行下一个
                continue;
            } else {
                bestLocation = location;
                //定位成功返回
                break;
            }
        }
        return bestLocation;
    }
    @SuppressLint("MissingPermission")
    private void GPSLocation() {
        Location location = getLastKnownLocation(locationManager);
        if (location != null) {
            //不为空,显示地理位置经纬度
            String longitude = "Longitude:" + location.getLongitude();
            String latitude = "Latitude:" + location.getLatitude();
            toGeocoder(location);
        } else {
            if (ObjectUtils.isNotEmpty(locationManager) && ObjectUtils.isNotEmpty(myLocationListener))
                locationManager.removeUpdates(myLocationListener); // 停止所有的定位服务
            stopSelf();  // 获取到经纬度以后，停止该service
        }
    }

    @SuppressLint("MissingPermission")
    private void toGeocoder(Location location) {
        String province = "";
        String city = "";
        String area = "";
        String featureName = "";
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (ObjectUtils.isNotEmpty(addresses) && 0 < addresses.size()) {
                Address address = addresses.get(0);
                String addressLine = "";
                if (ObjectUtils.isNotEmpty(address)) {
                    // 获取省份（province）
                    province = address.getAdminArea();
                    // 获取城市（City）
                    city = address.getLocality();
                    // 获取区县（area）
                    area = address.getSubLocality();
                    //  获取详细地址
                    featureName = address.getFeatureName();
                    // 获取街道地址
                    addressLine = address.getAddressLine(0);
                    // 打印详细地址信息
                    LogUtils.e("AddressInfo", "province: " + province);
                    LogUtils.e("AddressInfo", "City: " + city);
                    LogUtils.e("AddressInfo", "area: " + area);
                    LogUtils.e("AddressInfo", "FeatureName: " + featureName);
                    LogUtils.e("AddressInfo", "Address Line: " + addressLine);
                }
                mCallBack.Location_Return(location.getLatitude(), location.getLongitude(), province, city, area, addressLine);
            }
            if (ObjectUtils.isNotEmpty(locationManager) && ObjectUtils.isNotEmpty(myLocationListener))
                locationManager.removeUpdates(myLocationListener); // 停止所有的定位服务
            stopSelf();  // 获取到经纬度以后，停止该service
        } catch (Exception e) {
            if (ObjectUtils.isNotEmpty(locationManager) && ObjectUtils.isNotEmpty(myLocationListener))
                locationManager.removeUpdates(myLocationListener); // 停止所有的定位服务
            stopSelf();  // 获取到经纬度以后，停止该service
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.isNotEmpty(locationManager) && ObjectUtils.isNotEmpty(myLocationListener))
            locationManager.removeUpdates(myLocationListener); // 停止所有的定位服务
        stopSelf();
    }

}
