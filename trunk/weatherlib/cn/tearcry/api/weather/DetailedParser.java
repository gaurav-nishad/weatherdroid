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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.InputSource;

/**
 * @author Rajab
 * 
 */
public class DetailedParser implements Parser {

	private String year = null;

	private StringBuilder lsup_str;

	private String lsup;

	private InputSource source;

	private String[] units;

	private WeatherData wData;

	public DetailedParser(InputSource source, WeatherData wData) {
		this.source = source;
		this.wData = wData;
	}

	public void parse() throws WeatherException {
		if (source == null) {
			throw new WeatherException(
					"Today details parse failed. Cause:InputSource is null");
		}
		String str = null;
		StringBuilder sb = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(source
				.getByteStream()), 8192);

		try {
			sb = new StringBuilder("");
			while ((str = reader.readLine()) != null)
				sb.append(str);
		} catch (IOException ex) {
			throw new WeatherException(
					"Today details parse failed.Cause:IOException,read string failed");
		}

		// 删去所有的单引号，便于解析
		if (sb == null)
			return;
		String xml = sb.toString();
		xml = xml.replaceAll("'", "");
		ArrayList<HashMap<String, String>> mTodayData = wData
				.getDetailedData();

		// 拆分成行
		String[] data = xml.split(";");
		xml = null;
		int line = 0;
		// 找到第1行
		while (!getHead(data[line]).equals("head"))
			line++;

		// 取得单位制式
		units = data[line].substring(data[line].indexOf("(") + 1,
				data[line].indexOf(")")).split(", ");

		line += 3; // 解析最后更新时间，年份延迟在parseHourCondition解析
		// EEE,MMM,d,h:m,a
		String[] arr = data[line].split(" ");
		lsup_str = new StringBuilder();
		lsup_str.append(arr[3]); // 拆分星期 英文缩写 Fri,Sun
		lsup_str.append(arr[4]); // 拆分月份 英文缩写 Mar,Apr
		lsup_str.append(",");
		lsup_str.append(arr[5]); // 拆分日期
		lsup_str.append(arr[6]);// 拆分小时 12小时制式
		lsup_str.append(",");
		lsup_str.append(arr[7]); // 拆分分钟

		// 分别解析各个时段的天气
		line++;
		while (line < data.length)
			parseHourCondition(data[line++], units, mTodayData);
		// 今日详情解析完毕
		wData.mDetailsParsed = true;
		System.gc();

	}

	private static String getHead(String str) {
		return str.substring((str.indexOf("[") + 1), str.indexOf("]"));
	}

	private void parseHourCondition(String str, String[] units,
			ArrayList<HashMap<String, String>> todayData) {

		// 不包含天气信息则返回
		if (!str.contains("mpdHMHrObj") && !str.contains("hour["))
			return;

		HashMap<String, String> hourData = new HashMap<String, String>();

		String time = str.substring(str.indexOf("Date(") + 4, str
				.lastIndexOf(")"));

		hourData.put(WeatherKey.Weather.TIME, UnitUtil.convertTime(
				"(yyyy,MM,dd,HH,mm,ss)", "HH:mm", time));
		if (year == null) {
			year = UnitUtil.convertTime("(yyyy,MM,dd,HH,mm,ss)", ",yyyy", time);
			lsup_str.append(year);

			lsup = UnitUtil.convertTime("EEE,MMM,d,h:m,a,yyyy", lsup_str
					.toString());
		}
		hourData.put(WeatherKey.Weather.LAST_UPDATE, lsup);

		String all_str = str.substring(str.indexOf("),") + 2, str
				.lastIndexOf(")"));
		String[] condition = all_str.split(",");
		String[] wind = condition[7].split(" ");

		hourData.put(WeatherKey.Weather.ICON, condition[0]);
		hourData.put(WeatherKey.Weather.DESCRIPTION, condition[1]);

		// 如果不是公制，则转换
		if (!UnitUtil.fromMetricSystem(units[0])) {
			hourData.put(WeatherKey.Weather.TEMPERATURE, UnitUtil.convertTemp(
					condition[2], units[0]).split(" ")[0]);
			// hourData.put(WeatherKey.HIGH_TEMP, UnitUtil.convertTemp(
			// condition[2], units[0]).split(" ")[0]);
			// hourData.put(WeatherKey.LOW_TEMP, UnitUtil.convertTemp(
			// condition[3], units[0]).split(" ")[0]);
		} else {
			hourData.put(WeatherKey.Weather.TEMPERATURE, condition[2]);
			// hourData.put(WeatherKey.HIGH_TEMP, condition[2]);
			// hourData.put(WeatherKey.LOW_TEMP, condition[3]);
		}

		hourData.put(WeatherKey.Weather.PRECIP_CHANCE, condition[4] + "%");
		hourData.put(WeatherKey.Weather.WIND_DIRECTION, wind[1]);
		// 如果不是公制，则转换
		if (!UnitUtil.fromMetricSystem(wind[3]))
			hourData.put(WeatherKey.Weather.WIND_SPEED, UnitUtil.convertWind(wind[2],
					wind[3]).split(" ")[0]);
		else
			hourData.put(WeatherKey.Weather.WIND_SPEED, wind[2]);

		todayData.add(hourData);
	}

	public static void main(String[] args) {

		File file = new File("D:\\TodayCHXX0141.txt");
		WeatherData wd = new WeatherData();
		InputSource source = null;
		try {
			// source =
			// DataSourceManager.getInputSource("http://www.weather.com/weather/mpdwcr/dailydetails?locid=CHXX0141");
			source = DataSourceManager.getInputSource(file);
			new DetailedParser(source, wd).parse();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (WeatherException ex) {
			ex.printStackTrace();
		}

		if (wd.mDetailsParsed) {
			ArrayList<HashMap<String, String>> hourdata = wd.getDetailedData();
			for (int i = 0; i < hourdata.size(); i++) {
				HashMap<String, String> hour = hourdata.get(i);
				Iterator<String> iter = hour.keySet().iterator();
				System.out.println("===" + (i + 1) + "===");
				while (iter.hasNext()) {
					String key = iter.next();
					System.out.println(key + ":" + hour.get(key));
				}
			}
		}

	}
}
