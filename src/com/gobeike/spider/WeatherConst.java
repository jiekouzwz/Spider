package com.gobeike.spider;

import java.util.HashMap;
import java.util.Map;


public interface WeatherConst
{
	Map<String, String> weatherMap = new HashMap<String, String>()
	{
		/*
        * serialVersionUID 
        */
        private static final long serialVersionUID = 1L;

		{
			put("晴", "0.gif");
			put("多云", "1.gif");
			put("晴转多云", "1.gif");
			put("多云转晴", "1.gif");
			put("阴转多云", "1.gif");
			put("多云转阴", "2.gif");
			put("阴", "2.gif");
			put("晴转阴", "2.gif");
			put("小雨转阴", "2.gif");
			put("阴转小雨", "2.gif");
			put("晴转小雨", "2.gif");
			put("阵雨", "3.gif");
			put("多云转阵雨", "3.gif");
			put("雷阵雨", "4.gif");
			put("雷阵雨伴有冰雹", "5.gif");
			put("雨夹雪", "6.gif");
			put("小雨", "7.gif");
			put("多云转小雨", "7.gif");
			put("中雨", "8.gif");
			put("多云转中雨", "8.gif");
			put("大雨", "9.gif");
			put("小雨转大雨", "9.gif");
			put("多云转大雨", "9.gif");
			put("暴雨", "10.gif");
			put("大暴雨", "11.gif");
			put("特大暴雨", "12.gif");
			put("阵雪", "13.gif");
			put("小雪", "14.gif");
			put("雨夹雪转小雪", "14.gif");
			put("中雪", "15.gif");
			put("大雪", "16.gif");
			put("暴雪", "17.gif");
			put("雾", "18.gif");
			put("冻雨", "19.gif");
			put("沙尘暴", "20.gif");
			put("小雨-中雨", "21.gif");
			put("小雨转中雨", "21.gif");
			put("中雨-大雨", "22.gif");
			put("中雨转大雨", "22.gif");
			put("大雨-暴雨", "23.gif");
			put("大雨转暴雨", "23.gif");
			put("暴雨-大暴雨", "24.gif");
			put("暴雨转大暴雨", "24.gif");
			put("大暴雨-特大暴雨", "25.gif");
			put("大暴雨转特大暴雨", "25.gif");
			put("小雪-中雪", "26.gif");
			put("小雪转中雪", "26.gif");
			put("中雪-大雪", "27.gif");
			put("中雪转大雪", "27.gif");
			put("大雪-暴雪", "28.gif");
			put("大雪转暴雪", "28.gif");
			put("浮尘", "29.gif");
			put("扬沙", "30.gif");
			put("强沙尘暴", "31.gif");
			put("霾", "28.gif");
			
		}
	};

}
