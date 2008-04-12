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
	public static final String SUN_SET = "Surset";

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
	public static final String LAST_UPDATE = "Last_Update_Time";

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

	public static final String LOGTAG = "Weatherdroid";

	public static final String UNIT_FORM = "Unit_Form";

	public static class Unit {

		public static final String UNIT_DISTANCE = "ud";

		public static final String UNIT_SPEED = "us";

		public static final String UNIT_PREESURE = "ur";

		public static final String UNIT_TEMP = "ut";

		public static final String UNIT_PRECIPITATION = "up";

		public static final String KM = "km";

		public static final String MPH = "mph";

		public static final String Fahrenheit = "°F";

		public static final String Celsius = "°C";

		public static final String MILES = "miles";

		public static final String KPH = "km/h";

		public static final String HPA = "hPa";

		public static final String INCHES = "inches";

		public static final String STANDARD = "Standard";

		public static final String METRIC = "Metric";

	}

}
