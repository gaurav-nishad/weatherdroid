package cn.tearcry.api.weather;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import sun.java2d.loops.GeneralRenderer;

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

/**
 * 进行单位制式的转换
 * 
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */

public class UnitConvert {

	/** 所有风向名称 */
	private static final String[] WIND_DIRECTIONS = { "N", "NNE", "NE", "ENE",
			"E", "ESE", "ES", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW",
			"NNW" };
	
	private static final SimpleDateFormat OUT_FORMATTER=new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.US);
	
	public static final String convertTime(String format,String date)
	{
		SimpleDateFormat formatter=new SimpleDateFormat(format,Locale.US);
		GregorianCalendar now=new GregorianCalendar();
		Date time=null;
		
		String ret=null;
		try {			
			time=formatter.parse(date);
			ret=OUT_FORMATTER.format(time);
		}catch (ParseException ex) {
			ex.printStackTrace();
			ret=OUT_FORMATTER.format(now.getTime());
		}
		return ret;
	}

	/**
	 * @param value
	 *            要转换的值
	 * @param unit
	 *            待转换的单位
	 * @param ratio
	 *            换算比率
	 * @param metricUnit
	 *            相应的公制单位
	 * @param standardUnit
	 *            相应的标准制单位
	 * @return 转换后的(包括单位）
	 */
	private static String convert(int value, String unit, float ratio,
			String metricUnit, String standardUnit) {
		int ret = 0;
		String ut = null;

		// 公制->标准制
		if (fromMetricSystem(unit)) {
			ret = Math.round(value / ratio);
			ut = standardUnit;
		} else {
			// 标准制->公制
			ret = Math.round(value * ratio);
			ut = metricUnit;
		}

		return ret + " " + ut;

	}

	/**
	 * 温度转换
	 * 
	 * @param value
	 *            待转换的温度值
	 * @param unit
	 *            待转换的温度单位
	 * @return 转换后的(包括单位）
	 */
	public static String convertTemp(String value, String unit) {
		int ret = 0;
		int val = 0;

		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return value + " " + unit;
		}

		String ut = null;

		// 摄氏->华氏
		if (fromMetricSystem(unit)) {
			ret = Math.round(32 + 1.8f * val);
			ut = "F";
		} else {
			// 华氏->摄氏
			ret = Math.round((val - 32) / 1.8f);
			ut = "℃";
		}
		return ret + " " + ut;
	}

	/**
	 * 转换（风）速度
	 * 
	 * @param value
	 *            待转换的速度值
	 * @param unit
	 *            待转换的速度单位
	 * @return 转换后的速度(包括单位） *
	 */
	public static String convertWind(String value, String unit) {
		int val = 0;
		try {
			val = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return value + " " + unit;
		}
		return convert(val, unit, 1.609334f, WeatherKey.Unit.KPH,
				WeatherKey.Unit.MPH);
	}

	/**
	 * 检查是否来自公制单位系统
	 * 
	 * @param unit
	 *            待检查的单位
	 * @return 是否为公制单位系统
	 */
	public static boolean fromMetricSystem(String unit) {
		if (unit.equalsIgnoreCase(WeatherKey.Unit.KM)
				|| unit.equalsIgnoreCase(WeatherKey.Unit.Celsius)
				|| unit.equalsIgnoreCase(WeatherKey.Unit.KPH))
			return true;
		else
			return false;
	}

	/**
	 * 将角度转转换为对应的风向
	 * @param degrees 要转换的角度
	 * @return	对应的风向
	 */
	public static String converWindDirection(String degrees) {
		int deg=0;
		try {
			deg=Integer.parseInt(degrees);
		}catch(NumberFormatException ex) {
			return WeatherKey.NA;
		}
		float stepSize = 360 / WIND_DIRECTIONS.length;
		int index = Math.round(deg / stepSize);
		return WIND_DIRECTIONS[index % 16];

	}

	public static void main(String[] args) {
		System.out.println(convertWind("1", WeatherKey.Unit.MPH).split(" ")[0]);
	}
}
