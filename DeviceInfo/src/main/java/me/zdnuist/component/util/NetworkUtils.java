package me.zdnuist.component.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import me.zdnuist.component.entity.LocalNetInfo;
import me.zdnuist.component.entity.NetworkInfo;

/**
 * Created by zd on 2017/9/12.
 */

public class NetworkUtils {

  public static NetworkInfo getNetworkInfo(Context context) {
    try {
      String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
      URL url = new URL(address);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setUseCaches(false);
      connection.setRequestProperty("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
      if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String tmpString = "";
        StringBuilder retJSON = new StringBuilder();
        while ((tmpString = reader.readLine()) != null) {
          retJSON.append(tmpString);
        }

        String result = retJSON.toString();

        JSONObject jsonObject = JSONObject.parseObject(result);
        int code = (int) jsonObject.get("code");
        if(code == 0){
          String data = jsonObject.getString("data");
          NetworkInfo info =  JSON.parseObject(data , NetworkInfo.class);
          try {
            info.innerIp = getIPAddress(context);
          }catch (Exception e){
            e.printStackTrace();
          }
          return info;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String getIPAddress(Context context) {
    android.net.NetworkInfo info = ((ConnectivityManager) context
        .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    if (info != null && info.isConnected()) {
      if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
        try {
          //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
          for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
              InetAddress inetAddress = enumIpAddr.nextElement();
              if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                return inetAddress.getHostAddress();
              }
            }
          }
        } catch (SocketException e) {
          e.printStackTrace();
        }

      } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
        return ipAddress;
      }
    } else {
      //当前无网络连接,请在设置中打开网络
    }
    return null;
  }

  /**
   * 将得到的int类型的IP转换为String类型
   *
   * @param ip
   * @return
   */
  public static String intIP2StringIP(int ip) {
    return (ip & 0xFF) + "." +
        ((ip >> 8) & 0xFF) + "." +
        ((ip >> 16) & 0xFF) + "." +
        (ip >> 24 & 0xFF);
  }

  public static LocalNetInfo getLocalNetInfo(Context context){
    LocalNetInfo netInfo = new LocalNetInfo();
    netInfo.mac = UMUtils.getMac(context);
    netInfo.operatorName = UMUtils.getNetworkOperatorName(context);
    netInfo.dhcp = getWifiDhcpInfo(context);
    netInfo.wifiInfo = getConnectWifiInfo(context);
    netInfo.proxy = getWifiProxyInfo();
    return netInfo;
  }

  /**
   * 获取dhcp信息
   * @param context
   * @return
   */
  public static String getWifiDhcpInfo(Context context){
    WifiManager wifiManager=(WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if (wifiManager.isWifiEnabled()){
      DhcpInfo dhcpInfo=wifiManager.getDhcpInfo();
      return dhcpInfo.toString();
    }
    return "";
  }

  /**
   * 获取wifi连接信息
   * @param context
   * @return
   */
  public static String getConnectWifiInfo(Context context){
    WifiManager wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    if (wifiManager != null && wifiManager.isWifiEnabled()) {
      WifiInfo wifiInfo = wifiManager.getConnectionInfo();
      return wifiInfo.toString();
    }
    return "";
  }

  /**
   * 获取wifi代理
   * @return
   */
  public static String getWifiProxyInfo(){
    String host = !TextUtils.isEmpty(android.net.Proxy.getDefaultHost()) ? android.net.Proxy.getDefaultHost():"";
    return host+":"+android.net.Proxy.getDefaultPort();
  }

}
