package com.a3e.wzry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.a3e.wzry.bean.HeroInfo;

public class Api {
    public static void run() throws IOException {
        String heroList = DataProcessing.getHeroList();
        ArrayList<HeroInfo> heroInfos = DataProcessing.getHeroData(heroList);
        File savePath = Utils.mkdir(Constants.savePath);
		int countAll = 0, countAdd = 0;
        for (HeroInfo heroInfo : heroInfos) {
            File heroPath = Utils.mkdir(Utils.join(savePath, heroInfo.toString()));
            ArrayList<String[]> usList = DataProcessing.wrapSkinName_SkinUrl(heroInfo);

            for (String[] us : usList) {
                File skinPath = Utils.join(heroPath, us[0]);
                if (skinPath.exists()) {
                    System.out.printf("\rgetting %d", ++countAll);
                } else {
                    HttpRequestUtil.downloadFile(us[1], null, skinPath.toString());
                    System.out.println("\r[INFO] save " + skinPath);
					countAdd++;
                }
            }
        }
		System.out.printf("\r[INFO] add:%d all:%d\n", countAdd, countAll);
    }
}
