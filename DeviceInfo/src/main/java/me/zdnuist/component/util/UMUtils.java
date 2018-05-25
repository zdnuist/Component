//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.zdnuist.component.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.microedition.khronos.opengles.GL10;

public class UMUtils {
  private static final String TAG = "UMUtils";
  private static final Pattern pattern = Pattern.compile("UTDID\">([^<]+)");

  public UMUtils() {
  }






  public static String getUTDID(Context var0) {
    try {
      if(var0 == null) {
        return null;
      } else {
        try {
          Class var1 = Class.forName("com.ut.device.UTDevice");
          Method var2 = var1.getMethod("getUtdid", new Class[]{Context.class});
          return (String)var2.invoke((Object)null, new Object[]{var0.getApplicationContext()});
        } catch (Exception var3) {
          return readUTDId(var0);
        }
      }
    } catch (Exception var4) {

      return null;
    } catch (Throwable var5) {

      return null;
    }
  }

  private static String readUTDId(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      File var1 = getFile(var0);
      if(var1 != null && var1.exists()) {
        try {
          FileInputStream var2 = new FileInputStream(var1);

          String var3;
          try {
            var3 = parseId(readStreamToString(var2));
          } finally {
            safeClose(var2);
          }

          return var3;
        } catch (Exception var8) {
          return null;
        }
      } else {
        return null;
      }
    }
  }

  private static void safeClose(InputStream var0) {
    if(var0 != null) {
      try {
        var0.close();
      } catch (Exception var2) {
      }
    }

  }

  private static String parseId(String var0) {
    if(var0 == null) {
      return null;
    } else {
      Matcher var1 = pattern.matcher(var0);
      return var1.find()?var1.group(1):null;
    }
  }

  private static String readStreamToString(InputStream var0) throws IOException {
    InputStreamReader var1 = new InputStreamReader(var0);
    char[] var2 = new char[1024];
    boolean var3 = false;
    StringWriter var4 = new StringWriter();

    int var5;
    while(-1 != (var5 = var1.read(var2))) {
      var4.write(var2, 0, var5);
    }

    return var4.toString();
  }

  private static File getFile(Context var0) {
    if(var0 == null) {
      return null;
    } else if(!checkPermission(var0, "android.permission.WRITE_EXTERNAL_STORAGE")) {
      return null;
    } else {
      if(Environment.getExternalStorageState().equals("mounted")) {
        File var1 = Environment.getExternalStorageDirectory();

        try {
          return new File(var1.getCanonicalPath(), ".UTSystemConfig/Global/Alvin2.xml");
        } catch (Exception var3) {
          ;
        }
      }

      return null;
    }
  }

  public static String[] getGPU(GL10 var0) {
    try {
      String[] var1 = new String[2];
      String var2 = var0.glGetString(7936);
      String var3 = var0.glGetString(7937);
      var1[0] = var2;
      var1[1] = var3;
      return var1;
    } catch (Exception var4) {

      return new String[0];
    } catch (Throwable var5) {

      return new String[0];
    }
  }

  public static String getCPU() {
    try {
      String var0 = null;
      FileReader var1 = null;
      BufferedReader var2 = null;

      try {
        var1 = new FileReader("/proc/cpuinfo");
        if(var1 != null) {
          try {
            var2 = new BufferedReader(var1, 1024);
            var0 = var2.readLine();
            var2.close();
            var1.close();
          } catch (IOException var4) {
          }
        }
      } catch (FileNotFoundException var5) {
      }

      if(var0 != null) {
        int var3 = var0.indexOf(58) + 1;
        var0 = var0.substring(var3);
        return var0.trim();
      } else {
        return "";
      }
    } catch (Exception var6) {

      return "";
    } catch (Throwable var7) {

      return "";
    }
  }

  public static String getImsi(Context var0) {
    try {
      TelephonyManager var1 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
      String var2 = null;
      if(checkPermission(var0, "android.permission.READ_PHONE_STATE")) {
        var2 = var1.getSubscriberId();
      }

      return var2;
    } catch (Exception var3) {
      return null;
    } catch (Throwable var4) {
      return null;
    }
  }

  public static String getRegisteredOperator(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        TelephonyManager var1 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
        String var2 = null;
        if(checkPermission(var0, "android.permission.READ_PHONE_STATE")) {
          var2 = var1.getNetworkOperator();
        }

        return var2;
      } catch (Exception var3) {
        return null;
      } catch (Throwable var4) {
        return null;
      }
    }
  }

  public static String getNetworkOperatorName(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        TelephonyManager var1 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
        return !checkPermission(var0, "android.permission.READ_PHONE_STATE")?"":(var1 == null?"":var1.getNetworkOperatorName());
      } catch (Exception var2) {
        return "";
      } catch (Throwable var3) {
        return "";
      }
    }
  }


  public static String[] getNetworkAccessMode(Context var0) {
    String[] var1 = new String[]{"", ""};
    if(var0 == null) {
      return var1;
    } else {
      try {
        if(!checkPermission(var0, "android.permission.ACCESS_NETWORK_STATE")) {
          var1[0] = "";
          return var1;
        }

        ConnectivityManager var2 = (ConnectivityManager)var0.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(var2 == null) {
          var1[0] = "";
          return var1;
        }

        NetworkInfo var3 = var2.getNetworkInfo(1);
        if(var3 != null && var3.getState() == State.CONNECTED) {
          var1[0] = "Wi-Fi";
          return var1;
        }

        NetworkInfo var4 = var2.getNetworkInfo(0);
        if(var4 != null && var4.getState() == State.CONNECTED) {
          var1[0] = "2G/3G";
          var1[1] = var4.getSubtypeName();
          return var1;
        }
      } catch (Exception var5) {
      } catch (Throwable var6) {
      }

      return var1;
    }
  }

  public static boolean isSdCardWrittenable() {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static Locale getLocale(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        Locale var1 = null;

        try {
          Configuration var2 = new Configuration();
          var2.setToDefaults();
          System.getConfiguration(var0.getContentResolver(), var2);
          if(var2 != null) {
            var1 = var2.locale;
          }
        } catch (Exception var3) {
        }

        if(var1 == null) {
          var1 = Locale.getDefault();
        }

        return var1;
      } catch (Exception var4) {
        return null;
      } catch (Throwable var5) {
        return null;
      }
    }
  }

  public static String getMac(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        WifiManager var1 = (WifiManager)var0.getSystemService("wifi");
        if(checkPermission(var0, "android.permission.ACCESS_WIFI_STATE")) {
          WifiInfo var2 = var1.getConnectionInfo();
          return var2.getMacAddress();
        } else {

          return "";
        }
      } catch (Exception var3) {
        return null;
      } catch (Throwable var4) {
        return null;
      }
    }
  }



  public static String getSubOSName(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        String var1 = null;
        Properties var2 = getBuildProp();

        try {
          var1 = var2.getProperty("ro.miui.ui.version.name");
          if(TextUtils.isEmpty(var1)) {
            if(isFlyMe()) {
              var1 = "Flyme";
            } else if(!TextUtils.isEmpty(getYunOSVersion(var2))) {
              var1 = "YunOS";
            }
          } else {
            var1 = "MIUI";
          }
        } catch (Exception var4) {
          var1 = null;
        }

        return var1;
      } catch (Exception var5) {
        return null;
      } catch (Throwable var6) {
        return null;
      }
    }
  }

  public static String getSubOSVersion(Context var0) {
    if(var0 == null) {
      return null;
    } else {
      try {
        String var1 = null;
        Properties var2 = getBuildProp();

        try {
          var1 = var2.getProperty("ro.miui.ui.version.name");
          if(TextUtils.isEmpty(var1)) {
            if(isFlyMe()) {
              try {
                var1 = getFlymeVersion(var2);
              } catch (Exception var5) {
                ;
              }
            } else {
              try {
                var1 = getYunOSVersion(var2);
              } catch (Exception var4) {
                ;
              }
            }
          }
        } catch (Exception var6) {
          var1 = null;
        }

        return var1;
      } catch (Exception var7) {
        return null;
      } catch (Throwable var8) {
        return null;
      }
    }
  }

  private static String getYunOSVersion(Properties var0) {
    String var1 = var0.getProperty("ro.yunos.version");
    return !TextUtils.isEmpty(var1)?var1:null;
  }

  private static String getFlymeVersion(Properties var0) {
    try {
      String var1 = var0.getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
      if(var1.contains("flyme os")) {
        return var1.split(" ")[2];
      }
    } catch (Exception var2) {
      ;
    }

    return null;
  }

  public static Properties getBuildProp() {
    Properties var0 = new Properties();
    FileInputStream var1 = null;

    try {
      var1 = new FileInputStream(new File(Environment.getRootDirectory(), "build.prop"));
      var0.load(var1);
    } catch (IOException var11) {
      ;
    } finally {
      if(var1 != null) {
        try {
          var1.close();
        } catch (IOException var10) {
          ;
        }
      }

    }

    return var0;
  }

  private static boolean isFlyMe() {
    try {
      Method var0 = Build.class.getMethod("hasSmartBar", new Class[0]);
      return true;
    } catch (Exception var1) {
      return false;
    }
  }

  public static String getDeviceType(Context var0) {
    try {
      String var1 = "Phone";
      if(var0 == null) {
        return var1;
      } else {
        boolean var2 = (var0.getResources().getConfiguration().screenLayout & 15) >= 3;
        if(var2) {
          var1 = "Tablet";
        } else {
          var1 = "Phone";
        }

        return var1;
      }
    } catch (Exception var3) {
      return null;
    } catch (Throwable var4) {
      return null;
    }
  }

  public static String getAppVersionCode(Context var0) {
    if(var0 == null) {
      return "";
    } else {
      try {
        PackageInfo var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0);
        int var2 = var1.versionCode;
        return String.valueOf(var2);
      } catch (Exception var3) {

        return "";
      } catch (Throwable var4) {
        return "";
      }
    }
  }

  public static String getAppVersinoCode(Context var0, String var1) {
    if(var0 != null && var1 != null) {
      try {
        PackageInfo var2 = var0.getPackageManager().getPackageInfo(var1, 0);
        int var3 = var2.versionCode;
        return String.valueOf(var3);
      } catch (Exception var4) {

        return "";
      } catch (Throwable var5) {
        return "";
      }
    } else {
      return "";
    }
  }

  public static String getAppVersionName(Context var0) {
    if(var0 == null) {
      return "";
    } else {
      try {
        PackageInfo var1 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0);
        return var1.versionName;
      } catch (NameNotFoundException var2) {
        return "";
      } catch (Throwable var3) {
        return "";
      }
    }
  }

  public static String getAppVersionName(Context var0, String var1) {
    if(var0 != null && var1 != null) {
      try {
        PackageInfo var2 = var0.getPackageManager().getPackageInfo(var1, 0);
        return var2.versionName;
      } catch (NameNotFoundException var3) {
        return "";
      } catch (Throwable var4) {
        return "";
      }
    } else {
      return "";
    }
  }

  public static boolean checkPermission(Context var0, String var1) {
    boolean var2 = false;
    if(var0 == null) {
      return false;
    } else {
      if(VERSION.SDK_INT >= 23) {
        try {
          Class var3 = Class.forName("android.content.Context");
          Method var4 = var3.getMethod("checkSelfPermission", new Class[]{String.class});
          int var5 = ((Integer)var4.invoke(var0, new Object[]{var1})).intValue();
          if(var5 == 0) {
            var2 = true;
          } else {
            var2 = false;
          }
        } catch (Exception var6) {
          var2 = false;
        }
      } else {
        PackageManager var7 = var0.getPackageManager();
        if(var7.checkPermission(var1, var0.getPackageName()) == 0) {
          var2 = true;
        }
      }

      return var2;
    }
  }

  private static String byte2HexFormatted(byte[] var0) {
    StringBuilder var1 = new StringBuilder(var0.length * 2);

    for(int var2 = 0; var2 < var0.length; ++var2) {
      String var3 = Integer.toHexString(var0[var2]);
      int var4 = var3.length();
      if(var4 == 1) {
        var3 = "0" + var3;
      }

      if(var4 > 2) {
        var3 = var3.substring(var4 - 2, var4);
      }

      var1.append(var3.toUpperCase());
      if(var2 < var0.length - 1) {
        var1.append(':');
      }
    }

    return var1.toString();
  }



  public static String MD5(String var0) {
    try {
      if(var0 == null) {
        return null;
      } else {
        try {
          byte[] var1 = var0.getBytes();
          MessageDigest var2 = MessageDigest.getInstance("MD5");
          var2.reset();
          var2.update(var1);
          byte[] var3 = var2.digest();
          StringBuffer var4 = new StringBuffer();

          for(int var5 = 0; var5 < var3.length; ++var5) {
            var4.append(String.format("%02X", new Object[]{Byte.valueOf(var3[var5])}));
          }

          return var4.toString();
        } catch (Exception var6) {
          return var0.replaceAll("[^[a-z][A-Z][0-9][.][_]]", "");
        }
      }
    } catch (Exception var7) {

      return null;
    } catch (Throwable var8) {

      return null;
    }
  }


  public static String encryptBySHA1(String var0) {
    try {
      MessageDigest var1 = null;
      String var2 = null;
      byte[] var3 = var0.getBytes();

      try {
        var1 = MessageDigest.getInstance("SHA1");
        var1.update(var3);
        var2 = bytes2Hex(var1.digest());
      } catch (Exception var5) {
        return null;
      }

      return var2;
    } catch (Exception var6) {

      return null;
    } catch (Throwable var7) {

      return null;
    }
  }

  private static String bytes2Hex(byte[] var0) {
    String var1 = "";
    String var2 = null;

    for(int var3 = 0; var3 < var0.length; ++var3) {
      var2 = Integer.toHexString(var0[var3] & 255);
      if(var2.length() == 1) {
        var1 = var1 + "0";
      }

      var1 = var1 + var2;
    }

    return var1;
  }


  public static String getDeviceToken(Context var0) {
    String var1 = null;
    if(var0 != null) {
      var0 = var0.getApplicationContext();

      try {
        Class var2 = Class.forName("com.umeng.message.MessageSharedPrefs");
        if(var2 != null) {
          Method var3 = var2.getMethod("getInstance", new Class[]{Context.class});
          if(var3 != null) {
            Object var4 = var3.invoke(var2, new Object[]{var0});
            if(var4 != null) {
              Method var5 = var2.getMethod("getDeviceToken", new Class[0]);
              if(var5 != null) {
                Object var6 = var5.invoke(var4, new Object[0]);
                if(var6 != null && var6 instanceof String) {
                  var1 = (String)var6;
                }
              }
            }
          }
        }
      } catch (Throwable var7) {
        ;
      }
    }

    return var1;
  }



  public static boolean checkPath(String var0) {
    try {
      Class var1 = Class.forName(var0);
      return var1 != null;
    } catch (ClassNotFoundException var2) {
      return false;
    }
  }

  public static boolean checkAndroidManifest(Context var0, String var1) {
    try {
      ComponentName var2 = new ComponentName(var0.getApplicationContext().getPackageName(), var1);
      PackageManager var3 = var0.getApplicationContext().getPackageManager();
      var3.getActivityInfo(var2, 0);
      return true;
    } catch (NameNotFoundException var5) {
      return false;
    }
  }


  public static boolean checkResource(Context var0, String var1, String var2) {
    int var3 = var0.getApplicationContext().getResources().getIdentifier(var1, var2, var0.getApplicationContext().getPackageName());
    return var3 > 0;
  }




}
