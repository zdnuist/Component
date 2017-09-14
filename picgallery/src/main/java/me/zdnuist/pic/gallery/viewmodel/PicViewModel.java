package me.zdnuist.pic.gallery.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.pic.gallery.entity.ImgObj;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by zd on 2017/9/14.
 */

public class PicViewModel extends AndroidViewModel {

  private final static String TAG = "PicViewModel";

  private static final MutableLiveData ABSENT = new MutableLiveData();

  {
    //noinspection unchecked
    ABSENT.setValue(null);
  }

  private LiveData<List<ImgObj>> mObservableList;


  private final MediatorLiveData<List<ImgObj>> result = new MediatorLiveData<>();

  public PicViewModel(Application application) {
    super(application);

    result.addSource(ABSENT, new Observer() {
      @Override
      public void onChanged(@Nullable Object o) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
          @Override
          public void run() {
              final List<ImgObj> imgs = requstUrls("美女","性感");
            AppExecutors.getInstance().mainThread().execute(new Runnable() {
              @Override
              public void run() {
                if(imgs != null){
                  startIndex += imgs.size();
                }
                result.setValue(imgs);
              }
            });
          }
        });
      }
    });

    mObservableList = result;
  }

  public void refresh() {
    AppExecutors.getInstance().networkIO().execute(new Runnable() {
      @Override
      public void run() {
        final List<ImgObj> imgs = requstUrls("美女", "性感");
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
          @Override
          public void run() {
            if (imgs != null) {
              startIndex += imgs.size();
            }
            result.setValue(imgs);
          }
        });
      }
    });
  }

  private int startIndex = new Random().nextInt(100);
  private int limit = 10;

  /**
   * URL：http://image.baidu.com/data/imgs?col=&tag=&sort=&pn=&rn=&p=channel&from=1
   * 参数：col=大类&tag=分类&sort=0&pn=开始条数&rn=显示数量&p=channel&from=1
   */
  private List<ImgObj> requstUrls(String col, String tag) {
    String url = "http://image.baidu.com/data/imgs?col=" + col + "&tag=" + tag + "&sort=1&pn="
        + startIndex + "&rn=" + limit + "&p=channel&from=1";
    Log.d(TAG , "url:" + url);
    Request.Builder builder = new Builder()
        .url(url)
        .cacheControl(new CacheControl.Builder().build());
    Request request = builder.build();
    try {
      Response response = new OkHttpClient.Builder().build().newCall(request).execute();
      if(response.isSuccessful()){
        String json = response.body().string();
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONArray imags = jsonObject.getJSONArray("imgs");
        imags.remove(imags.size()-1);
        String js = JSONObject.toJSONString(imags);
        Log.d(TAG , "js:" + js);
        return JSONObject.parseArray(js, ImgObj.class);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public LiveData<List<ImgObj>> getObservableList(){
    return mObservableList;
  }

}
