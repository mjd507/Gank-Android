package common.test.utilsTest;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import common.utils.LocationUtils;
import common.utils.ToastUtils;

/**
 * 描述:
 * Created by mjd on 2017/1/6.
 */

public class LocationService extends Service {

    private boolean isSuccess;
    private String lastLatitude = "loading...";
    private String lastLongitude = "loading...";
    private String latitude = "loading...";
    private String longitude = "loading...";
    private String country = "loading...";
    private String locality = "loading...";
    private String street = "loading...";
    private OnGetLocationListener mOnGetLocationListener;

    public void setOnGetLocationListener(OnGetLocationListener onGetLocationListener) {
        mOnGetLocationListener = onGetLocationListener;
    }

    private LocationUtils.OnLocationChangeListener mOnLocationChangeListener = new LocationUtils.OnLocationChangeListener() {
        @Override
        public void getLastKnownLocation(Location location) {
            lastLatitude = String.valueOf(location.getLatitude());
            lastLongitude = String.valueOf(location.getLongitude());
            if (mOnGetLocationListener != null) {
                mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
            }
        }

        @Override
        public void onLocationChanged(final Location location) {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            if (mOnGetLocationListener != null) {
                mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
            }
            country = LocationUtils.getCountryName(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
            locality = LocationUtils.getLocality(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
            street = LocationUtils.getStreet(getApplicationContext(), Double.parseDouble(latitude), Double.parseDouble(longitude));
            if (mOnGetLocationListener != null) {
                mOnGetLocationListener.getLocation(lastLatitude, lastLongitude, latitude, longitude, country, locality, street);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                isSuccess = LocationUtils.register(getApplicationContext(), 0, 0, mOnLocationChangeListener);
                if (isSuccess) ToastUtils.showShort(getApplicationContext(), "init success");
                Looper.loop();
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocationBinder();
    }

    public class LocationBinder extends Binder {
        LocationService getService() {
            return LocationService.this;
        }
    }

    @Override
    public void onDestroy() {
        LocationUtils.unregister(this);
        // 一定要制空，否则内存泄漏
        mOnGetLocationListener = null;
        super.onDestroy();
    }

    /**
     * 获取位置监听器
     */
    public interface OnGetLocationListener {
        void getLocation(
                String lastLatitude, String lastLongitude,
                String latitude, String longitude,
                String country, String locality, String street
        );
    }
}
