package me.zdnuist.pic.gallery.entity;

import java.io.Serializable;

/**
 * Created by zd on 2017/9/14.
 */

public class ImgObj implements Serializable{

  public String fromPageTitle;

  public String downloadUrl;
  public String imageUrl;
  public int  imageWidth;
  public int imageHeight;

  public String thumbnailUrl;
  public int thumbnailWidth;
  public int thumbnailHeight;

}
