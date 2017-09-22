package me.zdnuist.proxy.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;

/**
 * Created by zd on 2017/9/18.
 */

@Entity
public class ZhiHu implements Serializable{
  @PrimaryKey(autoGenerate = false)
  @NonNull
  public String title;
  public String link;
  public String des;
  public String creator;
  public String pubDate;
  public String guid;

}
