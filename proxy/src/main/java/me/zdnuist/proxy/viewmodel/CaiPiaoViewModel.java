package me.zdnuist.proxy.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.proxy.entity.ZhiHu;

/**
 * Created by zd on 2017/12/6.
 */

public class CaiPiaoViewModel extends AndroidViewModel {

  private MediatorLiveData<List<ZhiHu>> dataLiveData = new MediatorLiveData<>();

  public CaiPiaoViewModel(Application application) {
    super(application);
  }


  public LiveData<List<ZhiHu>> getDatas() {
    AppExecutors.getInstance().diskIO().execute(new Runnable() {
      @Override
      public void run() {
        final List<ZhiHu> datas = getDataFromFile(getApplication().getApplicationContext(),
            "file/caipiao_2017-12-06");
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
          @Override
          public void run() {
            dataLiveData.setValue(datas);
          }
        });

      }
    });

    return dataLiveData;
  }


  public List<ZhiHu> getDataFromFile(Context context, String filename) {
    try {
      AssetManager assetManager = context.getAssets();
      InputStream in = assetManager.open(filename);
      return getDataFromInputStream(in);
    } catch (IOException e) {
      return null;
    }
  }

  public static List<ZhiHu> getDataFromInputStream(InputStream inputStream) throws IOException {
    InputStreamReader is = new InputStreamReader(inputStream);
    BufferedReader br = new BufferedReader(is);
    String read = br.readLine();
    List<ZhiHu> list = new ArrayList<>();
    while (read != null) {

      ZhiHu item = new ZhiHu();
      String[] arr = read.split("\\|");
      item.link = arr[0];
      item.title = arr[1];
      list.add(item);
      read = br.readLine();
    }
    return list;
  }


}
