package me.zdnuist.proxy.dao;

import android.arch.paging.LivePagedListProvider;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;
import me.zdnuist.proxy.entity.ZhiHu;

/**
 * Created by zd on 2017/9/22.
 */

@Dao
public interface ZhiHuDao {

  @Query("select * from ZhiHu")
  public abstract LivePagedListProvider<Integer,ZhiHu> getZhiHus();


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public void insertZhiHu(List<ZhiHu> zhiHuList);

}
