package me.zdnuist.proxy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import cn.jpush.android.api.JPushInterface;
import me.zdnuist.proxy.util.HttpUtil;

/**
 * Created by zd on 2017/12/5.
 */

public class SplashActivity  extends AppCompatActivity{

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_splash);
    JPushInterface.init(getApplicationContext());
    HttpUtil.isShow(this,"https://apk.kosungames.com/app-diyicaipiao-release.apk");
//    HttpUtil.jumpMain(this,0);
  }
}
