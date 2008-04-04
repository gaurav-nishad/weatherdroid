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
public class NetQuery implements Queryable, Runnable {

	public static final String YAHOO_URL = "http://weather.yahooapis.com/forecastrss?u=c&p=";

	public static final String WEATHER_DETAIL_URL = "http://www.weather.com/weather/mpdwcr/dailydetails?locid=";

	public static final String WEATHER_URL_PREFIX = "http://xoap.weather.com/weather/local/";

	public static final String WEATHER_URL_SUFFIX = "?prod=xoap&unit=c&dayf=7&par=1057677963&key=fcac442aff2dd9c0";

	public static final String LOC_QUERY = "http://xoap.weather.com/search/search?where=";

	InputSource ySource = null;

	InputSource wTodaySource = null;

	InputSource wDetailSource = null;

	private String locid;

	private WeatherData data;

	private boolean over = false;

	private Thread queryThread;

	/*
	 * （非 Javadoc）
	 * 
	 * @see cn.tearcry.api.weather.Queryable#query(java.lang.String,
	 *      cn.tearcry.api.weather.WeatherData)
	 */
	public void query(String locid, WeatherData data) throws WeatherException {
		this.locid = locid;
		this.data = data;
		queryThread.start();

	}

	public NetQuery() {
		queryThread = new Thread(this);

	}

	public static void main(String[] args) {
		WeatherData data = new WeatherData();
		Queryable q = new NetQuery();

		try {
			q.query("CHXX0141", data);
		} catch (WeatherException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}
		while (!q.isOver())
			;

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

	/*
	 * （非 Javadoc）
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {

			ySource = DataSourceManager.getInputSource(YAHOO_URL + locid);
			wTodaySource = DataSourceManager.getInputSource(WEATHER_URL_PREFIX
					+ locid + WEATHER_URL_SUFFIX, DataSourceManager.ISO);
			wDetailSource = DataSourceManager.getInputSource(WEATHER_DETAIL_URL
					+ locid);
			if (ySource != null)
				new NowParser(ySource, data).parse();

			if (wDetailSource != null)
				new DetailsParser(wDetailSource, data).parse();

			if (wTodaySource != null)
				new TodayFutureParser(wTodaySource, data).parse();

			over = data.bDetailsParsed && data.bFutureParsed && data.bNowParsed
					&& data.bTodayParsed;
		} catch (WeatherException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}

	}

}
