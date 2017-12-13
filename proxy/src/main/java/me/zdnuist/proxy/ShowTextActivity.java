package me.zdnuist.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import java.io.IOException;
import me.zdnuist.lifecycle.util.AppExecutors;
import me.zdnuist.proxy.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by zd on 2017/12/6.
 */

public class ShowTextActivity extends FragmentActivity {

  private TextView showView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_text);
    initView();
    initDatas();
  }

  private void initView(){
    showView = findViewById(R.id.id_tv);
  }

  private void initDatas(){
    final String url = getIntent().getStringExtra("_url");
    AppExecutors.getInstance().networkIO().execute(new Runnable() {
      @Override
      public void run() {
        try {
          Document doc = Jsoup.connect(url).get();
          String text = doc.text();
          final String result = HttpUtil.delHTMLTag(text);
          AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {
              showView.setText(result);
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }
}
