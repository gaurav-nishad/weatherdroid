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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author rajab
 * 
 */
public class NowHanlder extends DefaultHandler {
	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			WeatherData data = new WeatherData();
			NowHanlder hanlder = new NowHanlder(data);
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(new File(
					"D:/GraduationDesign/data/05_10_xian_now.xml")));
			HashMap<String, String> map = data.getNowData();
			HashMap<String, String> head = data.getHeadData();
			Iterator<String> ithead = head.keySet().iterator();
			while (ithead.hasNext()) {
				String k = ithead.next();
				System.out.println(k + ":" + head.get(k));
			}
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				System.out.println(key + ":" + map.get(key));
			}

		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	private HashMap<String, String> nowData;

	private WeatherData wData;

	public NowHanlder(WeatherData wData) {
		nowData = wData.getNowData();
		this.wData = wData;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attr) {

		if (qName.equalsIgnoreCase("yweather:wind")) {
			// 风向
			nowData.put(WeatherKey.Weather.WIND_DIRECTION, UnitUtil
					.converWindDirection(attr.getValue(1)));
			// 风速
			nowData.put(WeatherKey.Weather.WIND_SPEED, attr.getValue(2));
		} else if (qName.equalsIgnoreCase("yweather:atmosphere")) {
			// 湿度
			nowData.put(WeatherKey.Weather.HUMIDITY, attr.getValue(0) + "%");
			// 能见度

			try {
				float vis = Math.round(Float.parseFloat(attr.getValue(1))/100)	;
				nowData.put(WeatherKey.Weather.VISIBILITY, vis  + "");
			} catch (NumberFormatException ex) {
				nowData.put(WeatherKey.Weather.VISIBILITY, attr.getValue(1));
			}

			// 气压
			nowData.put(WeatherKey.Weather.PRESSURE, attr.getValue(2));
			// 气压描述
			// nowData.put(WeatherKey.Weather.PREESURE_STATE, UnitUtil
			// .convertPressureState(attr.getValue(3)));
		} else if (qName.equalsIgnoreCase("yweather:condition")) {
			// 天气文字描述
			nowData.put(WeatherKey.Weather.DESCRIPTION, attr.getValue(0));
			// 图标
			nowData.put(WeatherKey.Weather.ICON, attr.getValue(1));
			nowData.put(WeatherKey.Weather.TEMPERATURE, attr.getValue(2));
			nowData.put(WeatherKey.Weather.LAST_UPDATE, UnitUtil.convertTime(
					"EEE, dd MMM yyyy h:mm a", attr.getValue(3)));
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (qName.equals("yweather:condition"))
			wData.mNowParsed = true;

	}

}
