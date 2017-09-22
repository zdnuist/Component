package me.zdnuist.proxy;

import android.arch.persistence.room.Room;
import com.facebook.stetho.Stetho;
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
  }

  public ProxyDatabase getDatabase() {
    return db;
  }

  public static ProxyApp getInstance(){
    return instance;
  }
}
