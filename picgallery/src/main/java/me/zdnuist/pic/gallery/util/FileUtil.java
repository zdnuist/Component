package me.zdnuist.pic.gallery.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zd on 2017/9/14.
 */

public class FileUtil {

  public final static String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath()+ "/PicGallery/";

  public static void copyFile(Context context,File originFile, String targetName){

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
      Toast.makeText(context, "图片成功保存在目录" + SDCARD_DIR , Toast.LENGTH_SHORT).show();
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
