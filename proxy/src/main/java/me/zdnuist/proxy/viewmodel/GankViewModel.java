package me.zdnuist.proxy.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.proxy.entity.Ganks;
import me.zdnuist.proxy.util.HttpUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zd on 2017/12/13.
 */

public class GankViewModel extends AndroidViewModel {

  public static final String TYPE_ANDROID = "Android";
  public static final String TYPE_IOS = "iOS";
  public static final String TYPE_FULI = "福利";

  public static final String DEFAULT_PAGE_COUNT = "50";
  public static final String DEFAULT_PAGE_NUM = "1";

  public final static String GANK_URL = "http://gank.io/api/data/#type#/#page_count#/#page_num#";

  private MediatorLiveData<Ganks> ganksLiveData = new MediatorLiveData<>();

  public GankViewModel(Application application) {
    super(application);
  }

  public LiveData<Ganks> queryGanks(String type){
    Map<String,String> map= new HashMap<>(3);
    map.put("type",type);
    map.put("page_count",DEFAULT_PAGE_COUNT);
    map.put("page_num",DEFAULT_PAGE_NUM);

    final String url = HttpUtil.modifyUrl(GANK_URL,map);

    AppExecutors.getInstance().networkIO().execute(new Runnable() {
      @Override
      public void run() {
        try {
          Response response =  HttpUtil.okHttpClient.newCall(new Request.Builder().url(url).build()).execute();
          if(response != null && response.isSuccessful()){
            String json = response.body().string();
            final Ganks ganks = JSON.parseObject(json , Ganks.class);
            if(ganks != null){
              AppExecutors.getInstance().mainThread().execute(new Runnable() {
                @Override
                public void run() {
                  ganksLiveData.setValue(ganks);
                }
              });
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });


    return ganksLiveData;
  }


}
