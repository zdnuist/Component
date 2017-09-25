package me.zdnuist.proxy.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;
import java.io.InputStream;
import java.util.List;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.proxy.ProxyApp;
import me.zdnuist.proxy.database.ProxyDatabase;
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

  private LiveData<PagedList<ZhiHu>> mObservablePageList;

  private final MediatorLiveData<PagedList<ZhiHu>> pageListResult = new MediatorLiveData<>();

  private final MediatorLiveData<List<ZhiHu>> result = new MediatorLiveData<>();

  public ZhiHuViewModel(Application application) {
    super(application);
    AppExecutors.getInstance().diskIO().execute(new Runnable() {
      @Override
      public void run() {
        ProxyApp.getInstance().getDatabase().getZhiHuDao().deleteAll();
      }
    });


    mObservablePageList = ProxyApp.getInstance().getDatabase().getZhiHuDao().getZhiHus().create(
                /* initial load position */ 0,
        new PagedList.Config.Builder()
            .setPageSize(10)
            .setPrefetchDistance(10)
            .build());

//    pageListResult.addSource(mObservablePageList, new Observer<PagedList<ZhiHu>>() {
//      @Override
//      public void onChanged(@Nullable PagedList<ZhiHu> zhiHus) {
//        pageListResult.removeSource(mObservablePageList);
//        AppExecutors.getInstance().networkIO().execute(new Runnable() {
//          @Override
//          public void run() {
//            InputStream is = RSSPaser.getRssConentFromUrl("https://www.zhihu.com/rss");
//            if(is != null){
//              final List<ZhiHu> results = RSSPaser.parseZhihuRss(is);
//              AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {
//                  ProxyApp.getInstance().getDatabase().getZhiHuDao().insertZhiHu(results.subList(0,5));
//
//                  AppExecutors.getInstance().mainThread().execute(new Runnable(){
//                    @Override
//                    public void run() {
//                      pageListResult.addSource(mObservablePageList, new Observer<PagedList<ZhiHu>>() {
//                        @Override
//                        public void onChanged(@Nullable PagedList<ZhiHu> zhiHus) {
//                          pageListResult.setValue(zhiHus);
//                        }
//                      });
//                    }
//                  });
//                }
//              });
//            }
//          }
//        });
//      }
//    });

    fetchDatas();

    result.addSource(ABSENT, new Observer() {
      @Override
      public void onChanged(@Nullable Object o) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
          @Override
          public void run() {
            InputStream is = RSSPaser.getRssConentFromUrl("https://www.zhihu.com/rss");
            if(is != null){
              final List<ZhiHu> results = RSSPaser.parseZhihuRss(is);
              AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                  ProxyApp.getInstance().getDatabase().getZhiHuDao().insertZhiHu(results);
                }
              });
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

  public LiveData<PagedList<ZhiHu>> getObservablePageList() {
    return mObservablePageList;
  }

  int i = 0;
  public void fetchDatas(){
    AppExecutors.getInstance().networkIO().execute(new Runnable() {
      @Override
      public void run() {
        InputStream is = RSSPaser.getRssConentFromUrl("https://www.zhihu.com/rss");
        if(is != null){
          final List<ZhiHu> results = RSSPaser.parseZhihuRss(is);
          AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
              ProxyApp.getInstance().getDatabase().getZhiHuDao().insertZhiHu(results.subList(i*5,(i+1)*5));
              i++;
            }
          });
        }
      }
    });
  }
}
