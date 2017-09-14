package me.zdnuist.component.entity;

/**
 * Created by zd on 2017/9/12.
 */

public class NetworkInfo {

  public String area_id;

  public String region_id;

  public String city_id;

  public String isp;

  public String isp_id;

  public String ip;

  public String innerIp;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("area_id").append(":").append(area_id).append("\n")
        .append("region_id").append(":").append(region_id).append("\n")
        .append("city_id").append(":").append(city_id).append("\n")
        .append("isp").append(":").append(isp).append("\n")
        .append("isp_id").append(":").append(isp_id).append("\n")
        .append("ip").append(":").append(ip).append("\n")
        .append("innerIp").append(":").append(innerIp).append("\n");
    return sb.toString();
  }
}
