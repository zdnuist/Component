package me.zdnuist.component.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import java.util.List;
import me.zdnuist.component.entity.DeviceInfo;
import me.zdnuist.component.entity.NetworkInfo;
import me.zdnuist.component.util.DeviceUtils;
import me.zdnuist.component.util.NetworkUtils;
import me.zdnuist.lifecycle.util.AppExecutors;

/**
 * Created by zd on 2017/9/12.
 */

public class DeviceInfoViewModel extends AndroidViewModel {

  private static final MutableLiveData ABSENT = new MutableLiveData();
  {
    //noinspection unchecked
    ABSENT.setValue(null);
  }

  private  LiveData<DeviceInfo> mObservableDeviceInfo;

  private  LiveData<NetworkInfo> mObservableNetworkInfo;

  private final MediatorLiveData<DeviceInfo> result = new MediatorLiveData<>();
  private final MediatorLiveData<NetworkInfo> result2 = new MediatorLiveData<>();

  public DeviceInfoViewModel(final Application application) {
    super(application);

    result.addSource(ABSENT, new Observer() {
      @Override
      public void onChanged(@Nullable Object o) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
          @Override
          public void run() {
            final  DeviceInfo deviceInfo = new DeviceInfo();
            DeviceUtils.getDeiviceInfo(application.getApplicationContext(),deviceInfo);
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
              @Override
              public void run() {
                result.setValue(deviceInfo);
              }
            });

          }
        });
      }
    });

    mObservableDeviceInfo = result;

    result2.addSource(ABSENT, new Observer() {
      @Override
      public void onChanged(@Nullable Object o) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
          @Override
          public void run() {
            final NetworkInfo networkInfo = NetworkUtils.getNetworkInfo(application.getApplicationContext());
            if(networkInfo != null){
              AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                  result2.setValue(networkInfo);
                }
              });
            }
          }
        });
      }
    });

    mObservableNetworkInfo = result2;

  }

  public LiveData<DeviceInfo> getDeviceInfo() {
    return mObservableDeviceInfo;
  }

  public LiveData<NetworkInfo> getNetworkInfo(){
    return mObservableNetworkInfo;
  }
}
