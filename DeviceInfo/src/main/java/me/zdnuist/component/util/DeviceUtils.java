package me.zdnuist.component.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.lang.reflect.Method;
import me.zdnuist.component.entity.DeviceInfo;

/**
 * Created by zd on 2017/9/12.
 */

public class DeviceUtils {

  public static void getDeiviceInfo(Context context,DeviceInfo info){
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    info.deviceId = tm.getDeviceId();
    info.imei = tm.getSubscriberId();
    info.imsi = getSystemProperty(context, "gsm.sim.imsi");
    info.phoneNumber = tm.getLine1Number();

    info.brand = Build.BRAND;
    info.model = Build.MODEL;
    info.archCpu = Build.CPU_ABI2;
    info.name = Build.DEVICE;
    info.sdkInt = VERSION.SDK_INT;


    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
      Point size = new Point();
      display.getSize(size);
      info.widthPixels = size.x;
      info.heightPixels = size.y;
    } else {
      info.widthPixels = display.getWidth();
      info.heightPixels = display.getHeight();
    }

    DisplayMetrics metric = new DisplayMetrics();
    windowManager.getDefaultDisplay().getMetrics(metric);
    info.density = metric.density;
    info.densityDpi = metric.densityDpi;

  }


  public static String getSystemProperty(Context context, String key) throws IllegalArgumentException {

    String ret= "";

    try{
      ClassLoader cl = context.getClassLoader();
      @SuppressWarnings("rawtypes")
      Class SystemProperties = cl.loadClass("android.os.SystemProperties");

      //Parameters Types
      @SuppressWarnings("unchecked")
      Method get = SystemProperties.getMethod("get", String.class);

      //Parameters
      ret= (String) get.invoke(SystemProperties, key);

    }catch( Exception e ){
    }
    return ret;
  }

}
