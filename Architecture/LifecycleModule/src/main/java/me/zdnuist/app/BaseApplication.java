package me.zdnuist.app;

import android.app.Application;
import com.tencent.bugly.crashreport.CrashReport;
import me.zdnuist.lifecycle.BuildConfig;
import me.zdnuist.lifecycle.R;

/**
 * Created by zd on 2017/9/15.
 */

public class BaseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id),
        BuildConfig.DEBUG);
  }
}
