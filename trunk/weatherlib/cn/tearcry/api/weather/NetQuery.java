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

import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.InputSource;

/**
 * 
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */
public class NetQuery implements Queryable {
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

	InputSource ySource = null;

	InputSource wTodaySource = null;

	InputSource wDetailSource = null;


	private boolean over = false;

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.tearcry.api.weather.Queryable#query(java.lang.String,
	 *      cn.tearcry.api.weather.WeatherData)
	 */
	public void query( String locid, WeatherData data)
			throws WeatherException {

		ySource = DataSourceManager.getInputSource(YAHOO_URL + locid);
		new AuxiliaryParser(ySource, data).parse();

		wTodaySource = DataSourceManager.getInputSource(WEATHER_URL_PREFIX
				+ locid + WEATHER_URL_SUFFIX, DataSourceManager.ISO);
		new TodayFutureParser(wTodaySource,data).parse();
		
		InputSource wDetailSource = DataSourceManager
				.getInputSource(WEATHER_DETAIL_URL + locid);
		new DetailedParser(wDetailSource, data).parse();

		data.setParseOver(data.mDetailsParsed && data.mFutureParsed && data.mNowParsed
				&& data.mTodayParsed);

	}

	public static void main(String[] args) {
		WeatherData data = new WeatherData();
		Queryable q = new NetQuery();

		try {
			q.query("CHXX0141", data);
			
		} catch (WeatherException ex) {
			ex.printStackTrace();
		}

		HashMap<String, String> map = data.getTonightData();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + map.get(key));
		}

	}

	public boolean isOver() {
		return over;
	}

}
