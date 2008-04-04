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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.tearcry.api.weather.utility.UnitConvert;

/**
 * 解析当前实时天气情况
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */
public class NowParser implements Parser{
	
	private InputSource source;
	private WeatherData wData;

	public static void main(String[] args) {

		WeatherData data = new WeatherData();
		File file = new File("D:\\xi'an_yahoo.xml");
		try {
			new NowParser(DataSourceManager.getInputSource(file), data).parse();
		} catch (WeatherException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}
		HashMap<String, String> map = data.getNowData();
		HashMap<String, String> head = data.getHeadData();
		Iterator<String> ithead=head.keySet().iterator();
		while(ithead.hasNext()) {
			String k = ithead.next();
			System.out.println(k+ ":" + head.get(k));
		}
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + map.get(key));
		}

	}
	
	/**
	 * 创建一个实时天气解析器
	 * @param stream  解析的流
	 * @param wData   返回的天气数据对象
	 */
	public NowParser(InputSource source, WeatherData wData) {
		this.source=source;
		this.wData=wData;
	}
	
	/**
	 * 解析当前天气情况返回给一个WeatherData
	 * @throws WeatherException
	 */
	public  void parse()
			throws WeatherException {
		if (source == null)
			throw new WeatherException("数据源为空");
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		HashMap<String, String> nowData = wData.getNowData();
		HashMap<String, String> head = wData.getHeadData();
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(source);
			/*
			 * 这里，我们默认将查询时的单位制式置为公制，因此不需要转换, 待显示时根据用户需求再使用UnitConvert进行转换
			 */
			Element root = doc.getDocumentElement();
			// 更新时间
			String lsup=root.getElementsByTagName("lastBuildDate").item(0).getFirstChild().getNodeValue();
			head.put(WeatherKey.LAST_UPDATE, UnitConvert.convertTime("EEE, d MMM yyyy h:mm a z", lsup));
			Element location = (Element) root.getElementsByTagName(
					"yweather:location").item(0);
			
			head.put(WeatherKey.LOCATION_NAME, location.getAttribute("city"));
			head.put(WeatherKey.LATITUDE, root.getElementsByTagName("geo:lat")
					.item(0).getFirstChild().getNodeValue());
			head.put(WeatherKey.LONGITUDE,root.getElementsByTagName("geo:long")
					.item(0).getFirstChild().getNodeValue());

			Element unit = (Element) root
					.getElementsByTagName("yweather:units").item(0);
			nowData.put(WeatherKey.Unit.UNIT_TEMP, unit
					.getAttribute("temperature"));
			nowData.put(WeatherKey.Unit.UNIT_SPEED, unit.getAttribute("speed"));
			nowData.put(WeatherKey.Unit.UNIT_PREESURE, unit
					.getAttribute("pressure"));
			nowData.put(WeatherKey.Unit.UNIT_DISTANCE, unit
					.getAttribute("distance"));
			unit = null;

			Element wind = (Element) root.getElementsByTagName("yweather:wind")
					.item(0);
			nowData.put(WeatherKey.WIND_DIRECTION, UnitConvert
					.converWindDirection(wind.getAttribute("direction")));
			nowData.put(WeatherKey.WIND_SPEED, wind.getAttribute("speed"));
			wind = null;

			Element atmosphere = (Element) root.getElementsByTagName(
					"yweather:atmosphere").item(0);
			nowData.put(WeatherKey.HUMIDITY, atmosphere
					.getAttribute("humidity"));
			nowData.put(WeatherKey.VISIBILITY, atmosphere
					.getAttribute("visibility"));
			nowData.put(WeatherKey.PREESURE, atmosphere
					.getAttribute("pressure"));
			atmosphere = null;

			Element condition = (Element) root.getElementsByTagName(
					"yweather:condition").item(0);
			nowData.put(WeatherKey.FEELS_LIKE, condition.getAttribute("temp"));
			nowData.put(WeatherKey.TEXT_DESC, condition.getAttribute("text"));
			nowData.put(WeatherKey.ICON, condition.getAttribute("code"));
			condition = null;
			root = null;
			doc = null;
			db = null;
			dbf = null;
			System.gc();
			// 今天天气概况已经解析完毕
			wData.bNowParsed=true;;

		} catch (SAXException ex) {

			ex.printStackTrace();
		} catch (IOException ex) {

			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {

			ex.printStackTrace();
		}

	}

}
