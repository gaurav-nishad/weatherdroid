/* 
 * Copyright (C) 2008 Rajab Ma <majianle@gmail.com>
 * http://www.tearcry.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */

package cn.tearcry.api.weather;

/*
 * 定义天气预报内容的相关字段
 */
public class WeatherKey {
	/** 地区编号 */
	public static final String LOCATION_ID = "Location ID";

	/** 今天白天 */
	public static final String TODAY = "Today";

	/** 今天夜间 */
	public static final String TONIGHT = "Tonight";

	/** 今日详情 */
	public static final String TODAY_DETAILED = "TodayDetailed";

	/** 未来 */
	public static final String FUTURE = "Future";

	/** 现在 */
	public static final String NOW = "Now";

	/** 地名 */
	public static final String LOCATION = "Location";

	/** 风力 */
	public static final String WIND = "Wind";

	/** 风速 */
	public static final String WIND_SPEED = "Wind Speed";

	/** 风向 */
	public static final String WIND_DIRECTION = "Wind Direction";

	/** 时间 */
	public static final String TIME = "Time";

	/** 日出时间 */
	public static final String SUN_RISE = "Sunrise";

	/** 日落时间 */
	public static final String SUN_SET = "Sunset";

	/**
	 * 经度:<br>
	 * <i>东经为正，西经为负 </i>
	 */
	public static final String LONGITUDE = "Longitude";

	/**
	 * 纬度:<br>
	 * <i>北纬为正，南纬为负 </i>
	 */
	public static final String LATITUDE = "Latitude";

	/** 最后更新时间 */
	public static final String LAST_UPDATE = "Last Update Time";

	/** 平均温度 */
	public static final String TEMPERATURE = "Temperature";

	/** 最高温度 */
	public static final String HIGH_TEMP = "High Temperature";

	/** 最低温度 */
	public static final String LOW_TEMP = "Low Temperature";

	/** 体感温度 */
	public static final String FEELS_LIKE = "Feels Like";

	/**
	 * 时区:<br>
	 * <i>向东为正，向西方为负 </i>
	 */
	public static final String TIMEZONE = "Time Zone";

	/** 气压 具体数字 */
	public static final String PREESURE = "Preesure";

	/** 气压描述 上升或下降 */
	public static final String PREESURE_STATE = "Preesure State";

	/** 天气情况图标 */
	public static final String ICON = "Icon";

	/** 天气状况 */
	public static final String DESCRIPTION = "Description";

	/** 能见度 */
	public static final String VISIBILITY = "Visibility";

	/** 相对湿度 */
	public static final String HUMIDITY = "Humidity";

	/** 紫外线强度描述（文字) */
	public static final String UV = "UV";

	/** 降水概率 */
	public static final String PRECIP_CHANCE = "Precipition Chance";

	/** 观察站 */
	public static final String OBSERVATION_STATION = "Observation Station";

	/** 不可用 */
	public static final String NA = "N/A";
	
	/** 日志标签 */
	public static final String LOGTAG = "Weatherdroid";
	
	/** 语言 */
	public static final String LANGUAGE = "Language";

	/** 单位 */
	public static class Unit {
		
		/** 距离单位 */
		public static final String UNIT_DISTANCE = "ud";
		/** 速度单位 */
		public static final String UNIT_SPEED = "us";
		/** 气压单位 */
		public static final String UNIT_PREESURE = "up";
		/** 温度单位 */
		public static final String UNIT_TEMP = "ut";
	
		/** 公里 */
		public static final String KM = "km";
		/** 英里/小时 */
		public static final String MPH = "mph";
		/** 华氏温度 */
		public static final String Fahrenheit = "°F";
		/** 摄氏温度 */
		public static final String Celsius = "°C";
		/** 英里 */
		public static final String MILES = "miles";
		/** 公里/小时 */
		public static final String KPH = "km/h";
		/** 百帕 */
		public static final String HPA = "hPa";
		/** 英寸 */
		public static final String INCHES = "inches";
		/** 标准制 */
		public static final String STANDARD = "Standard";
		/** 公制 */
		public static final String METRIC = "Metric";
		/** 时间格式 */
		public static final String Time_FORMAT = "Time Format";
		/** 24小时制 */
		public static final String TIME_24 = "24";
		/** 12小时制 */
		public static final String TIME_12 = "12";

	}
	
	public static class Url {
		/** Yahoo天气RSS请求URL */
		public static final String YAHOO_URL = "http://weather.yahooapis.com/forecastrss?u=c&p=";

		/** Weather.com 今日详情URL */
		public static final String WEATHER_DETAIL_URL = "http://www.weather.com/weather/mpdwcr/dailydetails?locid=";

		/** Weather.com 天气URL 前 */
		public static final String WEATHER_URL_PREFIX = "http://xoap.weather.com/weather/local/";

		/** Weather.com 天气URL 后 */
		public static final String WEATHER_URL_SUFFIX = "?cc=*prod=xoap&unit=m&dayf=7&par=1057677963&key=fcac442aff2dd9c0";

		/** 查询地区编号 */
		public static final String LOC_QUERY = "http://xoap.weather.com/search/search?where=";
	}

}
