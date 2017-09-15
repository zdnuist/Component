package me.zdnuist.proxy.util;

import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import me.zdnuist.lifecycle.util.AppExecutors;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by zd on 2017/9/14.
 */

public class NetworkProcessor {

  public static void getUrl(){
    AppExecutors.getInstance().networkIO().execute(new Runnable() {
      @Override
      public void run() {
        Request.Builder builder = new Builder().url("http://www.baidu.com/");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .proxy(new Proxy(Type.HTTP,new InetSocketAddress("42.177.209.38",	808)))
            ;
        try {
          Response response = clientBuilder.build().newCall(builder.build()).execute();
          if(response != null){

            Log.w("zd" , "code:" + response.code() + ",result:" + response.body().string());
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

}
