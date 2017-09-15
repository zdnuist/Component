package me.zdnuist.pic.gallery.util;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zd on 2017/9/14.
 */

public class FileUtil {

  public final static String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath()+ "/PicGallery/";

  public static void copyFile(File originFile, String targetName){

    FileOutputStream fileOutputStream = null;
    FileInputStream fileInputStream = null;
    try {
      File file = new File(SDCARD_DIR,targetName);
      if(!file.getParentFile().exists()){
        file.getParentFile().mkdirs();
        file.createNewFile();
      }

       fileOutputStream = new FileOutputStream(file);
       fileInputStream = new FileInputStream(originFile);

      byte[] buffer = new byte[2*1024];

      int len = 0;
      while((len = fileInputStream.read(buffer)) != -1){
        fileOutputStream.write(buffer,0,len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try{
        if(fileInputStream != null){
          fileInputStream.close();
        }

        if(fileOutputStream != null){
          fileOutputStream.close();
        }
      }catch (Exception e){

      }
    }

  }

}
