/* 
 * Copyright (C) 2008 Ma JianLe
 * majianle@gmail.com
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
	public static final String LOCATION_ID="loc_id";
	/** 地名 */
	public static final String LOCATION="dnam";
	/** 标准制 */
	public static final String STANDARD="standard";
	/** 公制 */
	public static final String METRIC="metric";
	/** 风速 */
	public static final String WIND_SPEED="wind_s";
	/** 风向 */
	public static final String WIND_DIRECTION="wind_d";
	/** 当前时间 */
	public static final String TIME="tm";
	/** 日出时间 */
	public static final String SUN_RISE="sunr";
	/** 日落时间 */
	public static final String SUN_SET="surs";
	/** 经度:<br> <i>东经为正，西经为负</i> */	 
	public static final String LONGITUDE="lon";
	/** 纬度:<br> <i>北纬为正，南纬为负</i> */
	public static final String LATITUDE="lat";
	/** 最后更新时间 */	
	public static final String LAST_UPDATE="lsup";
	/** 平均温度 */
	public static final String TEMPERATURE="tmp";
	/** 最高温度 */
	public static final String HIGH_TEMP="hi";
	/** 最低温度 */
	public static final String LOW_TEMP="low";
	/** 体感温度 */
	public static final String FEELS_LIKE="flik";
	/** 最大阵风时速 */
	public static final String GUST_SPEED="gust";
	/** 时区:<br> <i>向东为正，向西方为负 </i> */
	public static final String TIME_ZONE="zone";
	/** 气压 具体数字 */
	public static final String PREESURE="bar_r";
	/** 气压描述 上升或下降 */
	public static final String PREESURE_DESCRIPTION="bar_d";
	/** 天气情况图标 */
	public static final String ICON="icon";
	/** 天气状况 */
	public static final String DESCRIPTION="desc";
	/** 能见度 */
	public static final String VISIBILITY="vis";
	/** 相对湿度 */
	public static final String HUMIDITY="hmid";
	/** 露点 */
	public static final String DEW_POINT="dewp";
	/** 不可用 */
	public static final String NA="N/A";
	
	public static class Unit {
		
		/** 华氏 */
		public static final String F="°F";
		/** 摄氏度 */
		public static final String C="℃";		
		/** 上午 */
		public static final String AM="PM";
		/** 下午 */
		public static final String PM="PM";
		/** 24小时制 */
		public static final String MODE_24="24";
		/** 12小时制 */
		public static final String MODE_12="12";
			
		
	}
	
	 
}
