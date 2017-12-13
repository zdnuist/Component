package me.zdnuist.proxy;

import android.arch.persistence.room.Room;
import cn.jpush.android.api.JPushInterface;
import com.facebook.stetho.Stetho;
import me.yokeyword.fragmentation.Fragmentation;
import me.zdnuist.app.BaseApplication;
import me.zdnuist.proxy.database.ProxyDatabase;

/**
 * Created by zd on 2017/9/18.
 */

public class ProxyApp extends BaseApplication {

  private static ProxyApp instance;

  private ProxyDatabase db;

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);

    instance = this;

    db = Room.databaseBuilder(this.getApplicationContext(),
        ProxyDatabase.class, ProxyDatabase.DATABASE_NAME)
        .build();

    Fragmentation.builder()
        // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
        .stackViewMode(Fragmentation.BUBBLE)
        .debug(BuildConfig.DEBUG)
             .install();

    JPushInterface.setDebugMode(true);
    JPushInterface.init(this);
  }

  public ProxyDatabase getDatabase() {
    return db;
  }

  public static ProxyApp getInstance(){
    return instance;
  }
}
