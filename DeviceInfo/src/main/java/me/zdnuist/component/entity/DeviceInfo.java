package me.zdnuist.component.entity;

/**
 * Created by zd on 2017/9/12.
 */

public class DeviceInfo {

  /**设备相关信息**/
  public String name;

  public String brand;

  public String model;

  public int sdkInt;

  public String archCpu;

  public String imei;

  public String imsi;

  public String deviceId;

  public String phoneNumber;

  public int heightPixels;

  public int widthPixels;

  public float density; //屏幕密度

  public int densityDpi;// 屏幕密度Dpi

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("name").append(":").append(name).append("\n")
        .append("brand").append(":").append(brand).append("\n")
        .append("model").append(":").append(model).append("\n")
        .append("sdkInt").append(":").append(sdkInt).append("\n")
        .append("archCpu").append(":").append(archCpu).append("\n")
        .append("imei").append(":").append(imei).append("\n")
        .append("imsi").append(":").append(imsi).append("\n")
        .append("deviceId").append(":").append(deviceId).append("\n")
        .append("phoneNumber").append(":").append(phoneNumber).append("\n")
        .append("heightPixels").append(":").append(heightPixels).append("\n")
        .append("widthPixels").append(":").append(widthPixels).append("\n")
        .append("屏幕密度(像素比例)").append(":").append(density).append("\n")
        .append("屏幕密度Dpi").append(":").append(densityDpi).append("\n")

    ;

    return sb.toString();
  }
}
