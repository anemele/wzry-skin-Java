package com.a3e.wzry;

public class Constants {
    public static String apiDataUrl = "http://pvp.qq.com/web201605/js/herolist.json";
    public static String apiPageUrl = "https://pvp.qq.com/web201605/herodetail/%d.shtml";
    public static String apiImageUrl = "http://game.gtimg.cn/images/yxzj/img201606/skin/hero-info/%d/%d-%sskin-%d.jpg";
    public static String savePath = "./wzrySkin";
    public static String cssString = "div.pic-pf > ul";

    public static String getPageUrl(int heroId) {
        return String.format(apiPageUrl, heroId);
    }

    public static String getImageUrl(int heroId, int skinId) {
        return String.format(apiImageUrl, heroId, heroId, "big", skinId);
    }
}
