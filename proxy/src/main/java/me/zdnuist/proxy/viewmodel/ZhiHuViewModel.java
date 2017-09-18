package me.zdnuist.proxy.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.proxy.entity.ZhiHu;
import me.zdnuist.proxy.util.RSSPaser;

/**
 * Created by zd on 2017/9/18.
 */

public class ZhiHuViewModel extends AndroidViewModel {

  private final static String TAG = "ZhiHuViewModel";

  private static final MutableLiveData ABSENT = new MutableLiveData();

  {
    //noinspection unchecked
    ABSENT.setValue(null);
  }

  private LiveData<List<ZhiHu>> mObservableList;


  private final MediatorLiveData<List<ZhiHu>> result = new MediatorLiveData<>();

  public ZhiHuViewModel(Application application) {
    super(application);

    result.addSource(ABSENT, new Observer() {
      @Override
      public void onChanged(@Nullable Object o) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
          @Override
          public void run() {
            InputStream is = RSSPaser.getRssConentFromUrl("https://www.zhihu.com/rss");
            if(is != null){
              final List<ZhiHu> results = RSSPaser.parseZhihuRss(is);
              if(results != null){
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                  @Override
                  public void run() {
                    result.setValue(results);
                  }
                });

              }
            }
          }
        });
      }
    });

    mObservableList = result;
  }

  public LiveData<List<ZhiHu>> getObservableList(){
    return mObservableList;
  }
}
