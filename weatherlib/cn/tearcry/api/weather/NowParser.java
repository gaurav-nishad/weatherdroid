package cn.tearcry.api.weather;
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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


/**
 * 
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */
public class NowParser {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		WeatherData data = new WeatherData();
		parse(new File("D:\\yahoo.xml"), data);
		HashMap<String, String> map = data.getCurrentData();
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + map.get(key));
		}
	}

	public static void parse(File file, WeatherData wData) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		HashMap<String, String> nowData = wData.getCurrentData();

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			/*
			 * 这里，我们默认将查询时的单位制式置为公制，因此不需要转换, 待显示时根据用户需求再使用UnitConvert进行转换
			 */
			Element root = doc.getDocumentElement();
			Element unit = (Element) root
					.getElementsByTagName("yweather:units").item(0);
			nowData.put(WeatherKey.Unit.UNIT_TEMP, unit
					.getAttribute("temperature"));
			nowData.put(WeatherKey.Unit.UNIT_SPEED, unit.getAttribute("speed"));
			nowData.put(WeatherKey.Unit.UNIT_PREESURE, unit
					.getAttribute("pressure"));
			nowData.put(WeatherKey.Unit.UNIT_DISTANCE, unit
					.getAttribute("distance"));
			Element wind = (Element) root.getElementsByTagName("yweather:wind")
					.item(0);
			nowData.put(WeatherKey.WIND_DIRECTION, UnitConvert
					.converWindDirection(wind.getAttribute("direction")));
			nowData.put(WeatherKey.WIND_SPEED, wind.getAttribute("speed"));

			Element atmosphere = (Element) root.getElementsByTagName(
					"yweather:atmosphere").item(0);
			nowData.put(WeatherKey.HUMIDITY, atmosphere
					.getAttribute("humidity"));
			nowData.put(WeatherKey.VISIBILITY, atmosphere
					.getAttribute("visibility"));
			nowData.put(WeatherKey.PREESURE, atmosphere
					.getAttribute("pressure"));
			Element condition = (Element) root.getElementsByTagName(
					"yweather:condition").item(0);
			nowData.put(WeatherKey.FEELS_LIKE, condition.getAttribute("temp"));
			nowData.put(WeatherKey.TEXT_DESC, condition.getAttribute("text"));
			nowData.put(WeatherKey.ICON, condition.getAttribute("code"));
		} catch (SAXException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}

	}

}
