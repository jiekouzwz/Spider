package com.gobeike.spider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.gobeike.util.GobeikeHttpClient;
import com.gobeike.vo.Images;

/**
 * Hello world!
 */
public class WeatherSpider {

    public static void main(String[] args) throws Exception {
        WeatherSpider spider = new WeatherSpider();

        String code = "101190101";
        String name = "南京";
        spider.getWeather(code, name);

    }

    public List<Images> getWeather(String code, String name) throws Exception {
        try {
            String url = "http://www.weather.com.cn/weather/" + code + ".shtml";

            GobeikeHttpClient client = new GobeikeHttpClient();

            String result = client.sendRequestByGet(url);

            Document doc = Jsoup.parse(result);

            Element element = doc.select("#7d").select(".t").select(".clearfix").select("li").get(0);

            String weather = element.select(".wea").attr("title");

            Element tem = element.select(".tem").get(0);

            String highT = tem.select("span").text();
            String lowT = tem.select("i").text();

            Map<String, String> map = new HashMap<String, String>();
            map.put("weather", weather);
            map.put("temp1", lowT);
            map.put("temp2", highT);
            map.put("cityid", code);
            map.put("city", name);
            map.put("img1", WeatherConst.weatherMap.get(weather));
            map.put("img2", WeatherConst.weatherMap.get(weather));

            System.out.println(map);

        } catch (Exception e) {
            System.out.println(name + " error " + code);
        }

        return null;
    }

}
