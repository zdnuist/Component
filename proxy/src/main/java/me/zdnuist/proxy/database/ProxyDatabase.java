package me.zdnuist.proxy.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import me.zdnuist.proxy.dao.ZhiHuDao;
import me.zdnuist.proxy.entity.ZhiHu;

/**
 * Created by zd on 2017/9/22.
 */

@Database(entities = {ZhiHu.class} ,version = 1)
public abstract class ProxyDatabase extends RoomDatabase {

  public static final String DATABASE_NAME = "proxy-db";

  public abstract ZhiHuDao getZhiHuDao();

}
