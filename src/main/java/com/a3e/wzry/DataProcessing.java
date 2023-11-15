package com.a3e.wzry;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.a3e.wzry.bean.HeroBean;
import com.a3e.wzry.bean.HeroInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataProcessing {

    public static String getHeroList() {
        String ret;
        try {
            ret = HttpRequestUtil.sendGet(Constants.apiDataUrl, null);
            // System.out.println(ret);
        } catch (Exception e) {
            e.printStackTrace();
            ret = "[]";
        }
        return ret;
    }

    public static ArrayList<HeroInfo> getHeroData(String herolist) {
        Type type = new TypeToken<ArrayList<HeroBean>>() {
        }.getType();

        ArrayList<HeroBean> hList = new Gson().fromJson(herolist, type);
        ArrayList<HeroInfo> ret = new ArrayList<>();

        for (HeroBean hBean : hList) {
            HeroInfo heroInfo = new HeroInfo(hBean.getTitle(), hBean.getCname(), hBean.getEname());
            ret.add(heroInfo);
            // System.out.println(heroInfo);
        }

        return ret;
    }

    static String getSkinNames(int heroId) throws IOException {
        String url = Constants.getPageUrl(heroId);
        Document doc = Jsoup.connect(url).get();
        String ret = doc.select(Constants.cssString).attr("data-imgname");
        return ret;
    }

    static String[] splitSkinNames(String skins) {
        Pattern pattern = Pattern.compile("[(?:&\\d+)\\|]+");
        return pattern.split(skins);
    }

    public static ArrayList<String[]> wrapSkinName_SkinUrl(HeroInfo heroInfo) {
        ArrayList<String[]> ret = new ArrayList<>();

        try {
            String skins = getSkinNames(heroInfo.getEname());
            String[] skinList = splitSkinNames(skins);
            for (int skinId = 0; skinId < skinList.length; skinId++) {
                String skinFilename = String.format(
                        "%d_%s_%s.jpg", skinId + 1,
                        heroInfo.getCname(), skinList[skinId]);
                String skinUrl = Constants.getImageUrl(heroInfo.getEname(), skinId + 1);
                String[] tmp = { skinFilename, skinUrl };
                ret.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
