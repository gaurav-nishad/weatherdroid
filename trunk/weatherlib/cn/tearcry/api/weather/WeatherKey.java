
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
 * 定义天气预报内容的相关字段　*/
public class WeatherKey {
	/** 地区编号 */
	public static final String LOCATION_ID="loc_id";
	/** 地名 */
	public static final String LOCATION_NAME="loc_name";
	/** 标准�?*/
	public static final String STANDARD="standard";
	/** 公制 */
	public static final String METRIC="metric";
	/** 风�? */
	public static final String WIND_SPEED="wind_speed";
	/** 风向 */
	public static final String WIND_DIRECTION="wind_direction";
	/** 时间 */
	public static final String TIME="time";
	/** 日出时间 */
	public static final String SUN_RISE="sunrise";
	/** 日落时间 */
	public static final String SUN_SET="surset";
	/** 经度:<br> <i>东经为正，西经为负 </i> */	 
	public static final String LONGITUDE="longitude";
	/** 纬度:<br> <i>北纬为正，南纬为� </i> */
	public static final String LATITUDE="latitude";
	/** �?��更新时间 */	
	public static final String LAST_UPDATE="lsup";
	/** 平均温度 */
	public static final String TEMPERATURE="tmp";
	/** �?��温度 */
	public static final String HIGH_TEMP="hi";
	/** �?��温度 */
	public static final String LOW_TEMP="low";
	/** 体感温度 */
	public static final String FEELS_LIKE="flik";
	/** �?��阵风时�? */
	public static final String GUST_SPEED="gust";
	/** 时区:<br> <i>向东为正，向西方为负 </i> */
	public static final String TIME_ZONE="zone";
	/** 气压 具体数字 */
	public static final String PREESURE="preesure";
	/** 气压描述 上升或下降　*/
	public static final String PREESURE_DESC="bard";
	/** 天气情况图标 */
	public static final String ICON="icon";
	/** 天气状况 */
	public static final String TEXT_DESC="desc";
	/** 能见�?*/
	public static final String VISIBILITY="vis";
	/** 相对湿度 */
	public static final String HUMIDITY="hmid";
	/** 露点 */
	public static final String DEW_POINT="dewp";
	/** 紫外线强度描述（文字) */
	public static final String UV="uv";
	/** 降水概率*/
	public static final String PRECIP_CHANCE="ppcp"; 
	
	/** 不可�用 */
	public static final String NA="NA";
	
	
	public static class Unit {
		public static final String F="F";
		public static final String C="C";
		public static final String UNIT_DISTANCE="ud";
		public static final String UNIT_SPEED="us";
		public static final String UNIT_PREESURE="ur";
		public static final String UNIT_TEMP="ut";
		public static final String UNIT_PRECIPITATION="up";
		
		public static final String KM="km";
		public static final String MPH="mph";
		public static final String Fahrenheit="F";
		public static final String Celsius="C";
		public static final String MILES="mi";
		public static final String KPH="km/h";
		public static final String HPA="hPa";
		public static final String INCHES="inches";
			
		
	}
	
	 
}
