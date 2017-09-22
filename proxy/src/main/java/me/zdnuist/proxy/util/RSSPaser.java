package me.zdnuist.proxy.util;

import android.util.Log;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import me.zdnuist.proxy.entity.ZhiHu;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Created by zd on 2017/9/18.
 */

public class RSSPaser {
  private static final String TAG = "RSSPaser";

  static OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(new StethoInterceptor()).hostnameVerifier(new HostnameVerifier() {
    @Override
    public boolean verify(String hostname, SSLSession session) {
      return true;
    }
  }).build();


  public static InputStream getRssConentFromUrl(String url){
    Log.w(TAG , "getRssConentFromUrl");
    Request.Builder builder = new Builder().url(url).cacheControl(new CacheControl.Builder().build());
    try {
      Response response = okHttpClient.newCall(builder.build())
          .execute();
      if(response.isSuccessful()){
        return response.body().byteStream();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static List<ZhiHu> parseZhihuRss(InputStream is){
    List<ZhiHu> zhList = null;
    try {
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      XmlPullParser parser = factory.newPullParser();
      parser.setInput(is , "UTF-8");
      int eventType = parser.getEventType();
      zhList = new ArrayList<>();
      boolean isBegin = false;
      ZhiHu zhiHu = null;
      while(eventType != XmlPullParser.END_DOCUMENT){
          switch (eventType){
            case XmlPullParser.START_DOCUMENT:
              break;
            case XmlPullParser.END_DOCUMENT:
              break;
            case XmlPullParser.START_TAG:
              String tagName = parser.getName();
              if(tagName != null && tagName.equals("item")){
                isBegin = true;
                zhiHu = new ZhiHu();
              }

              if(isBegin){
                if(tagName != null && tagName.equals("title")){
                  try {
                    String title = parser.nextText();
                    zhiHu.title = title;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

                if(tagName != null && tagName.equals("link")){
                  try {
                    String link = parser.nextText();
                    zhiHu.link = link;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }

                }

                if(tagName != null && tagName.equals("description")){
                  try {
                    String desc = parser.nextText();
                    zhiHu.des = desc;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }

                }

                if(tagName != null && tagName.equals("creator")){
                  try {
                    String creator = parser.nextText();
                    zhiHu.creator = creator;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

                if(tagName != null && tagName.equals("pubDate")){
                  try {
                    String pubDate = parser.nextText();
                    zhiHu.pubDate = pubDate;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

                if(tagName != null && tagName.equals("guid")){
                  try {
                    String guid = parser.nextText();
                    zhiHu.guid = guid;
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                }

              }
              break;
            case XmlPullParser.END_TAG:

              if(parser.getName().equals("item")){
                zhList.add(zhiHu);
              }
              break;
          }

          eventType = parser.next();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return zhList;
  }

}
