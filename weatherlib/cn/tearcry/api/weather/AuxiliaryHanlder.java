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

import cn.tearcry.api.weather.utility.UnitConvert;

/**
 * @author rajab
 * 
 */
public class AuxiliaryHanlder extends DefaultHandler {
	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			WeatherData data = new WeatherData();
			AuxiliaryHanlder hanlder = new AuxiliaryHanlder(data);
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(new File(
					"d:\\xian_yahoo.xml")));
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
		} catch (WeatherException ex) {
			ex.printStackTrace();
		}

	}

	private HashMap<String, String> headData;

	private boolean lat = false;

	private boolean lon = false;
	private HashMap<String, String> nowData;
	
	private WeatherData wData;

	public AuxiliaryHanlder(WeatherData wData) {
		nowData = wData.getNowData();
		headData = wData.getHeadData();
		this.wData=wData;
	}

	public void characters(char[] ch, int start, int length) {
		if (lat) {
			nowData.put(WeatherKey.LATITUDE, new String(ch, start, length));
		} else if (lon) {
			nowData.put(WeatherKey.LONGITUDE, new String(ch, start, length));
		}
		
	}

	public void endElement(String uri, String localName, String qName) {
		if (qName.equalsIgnoreCase("geo:lat")) {
			lat = false;
		} else if (qName.equalsIgnoreCase("geo:long")) {
			lon = false;
		}else if(qName.equals("yweather:condition"))
			wData.mNowParsed=true;

	}

	public void startElement(String uri, String localName, String qName,
			Attributes attr) {
		// 地区信息放入头部
		if (qName.equalsIgnoreCase("yweather:location")) {
			headData.put(WeatherKey.LOCATION, attr.getValue(0));
		} else if (qName.equalsIgnoreCase("yweather:wind")) {
			// 风向
			nowData.put(WeatherKey.WIND_DIRECTION, UnitConvert
					.converWindDirection(attr.getValue(1)));
			// 风速
			nowData.put(WeatherKey.WIND_SPEED, attr.getValue(2));
		} else if (qName.equalsIgnoreCase("yweather:atmosphere")) {
			// 湿度
			nowData.put(WeatherKey.HUMIDITY, attr.getValue(0)+"%");
			// 能见度
			nowData.put(WeatherKey.VISIBILITY, attr.getValue(1));
			// 气压
			nowData.put(WeatherKey.PREESURE, attr.getValue(2));
			// 气压描述
			nowData.put(WeatherKey.PREESURE_STATE, UnitConvert
					.convertPressureState(attr.getValue(3)));
		} else if (qName.equalsIgnoreCase("yweather:astronomy")) {
			// 日出时间
			headData.put(WeatherKey.SUN_RISE, UnitConvert.convertTime("k:mm a",
					"H:mm", attr.getValue(0)));
			// 日落时间
			headData.put(WeatherKey.SUN_SET, UnitConvert.convertTime("k:mm a",
					"H:mm", attr.getValue(1)));
		} else if (qName.equalsIgnoreCase("geo:lat")) {
			// 纬度
			lat = true;
		} else if (qName.equalsIgnoreCase("geo:long")) {
			// 经度
			lon = true;
		} else if (qName.equalsIgnoreCase("yweather:condition")) {
			// 天气文字描述
			nowData.put(WeatherKey.DESCRIPTION, attr.getValue(0));
			// 图标
			nowData.put(WeatherKey.ICON, attr.getValue(1));
			nowData.put(WeatherKey.TEMPERATURE, attr.getValue(2));
			headData.put(WeatherKey.LAST_UPDATE, UnitConvert.convertTime(
					"EEE, d MMM yyyy h:mm a z", attr.getValue(3)));

		}

	}
}
