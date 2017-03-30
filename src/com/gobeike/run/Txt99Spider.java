package com.gobeike.run;

import com.gobeike.util.EnumEncode;
import com.gobeike.util.GobeikeHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;

/**
 * Created by xuff on 17-3-28.
 */
public class Txt99Spider {

    public List<Map<String, String>> getInfo() {
        String url = "http://www.txt99.com/";
        GobeikeHttpClient client = new GobeikeHttpClient();
        client.setEncoding(EnumEncode.gb2312);
        Runtime runtime = Runtime.getRuntime();
        try {
            String result = client.sendRequestByGet(url);
            Document doc = Jsoup.parse(result);
            Element element = doc.select("#7d").select(".t").select(".clearfix").select("li").get(0);

            String weather = element.select(".wea").attr("title");
            System.out.println(weather);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> jsoupGet() {
        String url = "http://www.txt99.com/";
        try {

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getAllElements();
            System.out.print("Jsoup爬出的版权信息：\n");

            /**
             * 根据html中id为copyright的标签
             */

            Element e = doc.getElementById("copyright");
            System.out.println(e.text());
            System.out.print("Jsoup爬出的小说名称列表：\n");

            Elements list = doc.getElementsByClass("toplist");
            for (Element element : list) {
                System.out.println(element.text());
            }
//           for (Element element:elements){
//               System.out.println(element.data());
//           }
//            System.out.println(doc.title());
//            System.out.println(doc.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
