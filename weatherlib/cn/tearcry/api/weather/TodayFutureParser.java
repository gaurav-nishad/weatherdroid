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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 * 解析今天白天与夜间天气概况
 * 
 * @author  Rajab Ma<majianle@gmail.com>
 *
 */
public class TodayFutureParser {

	/**
	 * @param args
	 */
	
	private static void parseAllData(Element day,HashMap<String, String> daymap,HashMap<String, String> nightmap) {
		
		daymap.put(WeatherKey.LOW_TEMP, day.getElementsByTagName("hi")
				.item(0).getFirstChild().getNodeValue());
		// 最高温度
		daymap.put(WeatherKey.HIGH_TEMP, day.getElementsByTagName("low")
				.item(0).getFirstChild().getNodeValue());
		// 日出时间
		daymap.put(WeatherKey.SUN_RISE, day.getElementsByTagName("sunr")
				.item(0).getFirstChild().getNodeValue());
		// 日落时间
		daymap.put(WeatherKey.SUN_SET, day.getElementsByTagName("suns")
				.item(0).getFirstChild().getNodeValue());

// ----白天部分开始
		Element part_d = (Element) day.getElementsByTagName("part").item(0);
		//图标
		daymap.put(WeatherKey.ICON, part_d.getElementsByTagName("icon")
				.item(0).getFirstChild().getNodeValue());
		//文字描述
		daymap.put(WeatherKey.TEXT_DESC, part_d.getElementsByTagName("t")
				.item(0).getFirstChild().getNodeValue());
		
		// 降水概率
		daymap.put(WeatherKey.PRECIP_CHANCE, part_d
				.getElementsByTagName("ppcp").item(0).getFirstChild()
				.getNodeValue());
		daymap.put(WeatherKey.HUMIDITY, part_d.getElementsByTagName(
				"hmid").item(0).getFirstChild().getNodeValue());
		Element wind = (Element) part_d.getElementsByTagName("wind")
				.item(0);
		// 风速
		daymap.put(WeatherKey.WIND_SPEED, wind.getElementsByTagName("s")
				.item(0).getFirstChild().getNodeValue());
		daymap.put(WeatherKey.WIND_DIRECTION, wind.getElementsByTagName(
				"t").item(0).getFirstChild().getNodeValue());
		wind=null;
		part_d=null;
// ----白天部分结束

		
//----夜间部分开始
		if(nightmap==null)
			return;
		Element part_n=(Element)day.getElementsByTagName("part").item(1);
		nightmap.put(WeatherKey.ICON, part_n.getElementsByTagName("icon")
				.item(0).getFirstChild().getNodeValue());
		//	文字描述
		nightmap.put(WeatherKey.TEXT_DESC, part_n.getElementsByTagName("t")
				.item(0).getFirstChild().getNodeValue());
		// 降水概率
		nightmap.put(WeatherKey.PRECIP_CHANCE, part_n
				.getElementsByTagName("ppcp").item(0).getFirstChild()
				.getNodeValue());
		nightmap.put(WeatherKey.HUMIDITY, part_n.getElementsByTagName(
				"hmid").item(0).getFirstChild().getNodeValue());
		wind = (Element) part_n.getElementsByTagName("wind")
				.item(0);
		// 风速
		nightmap.put(WeatherKey.WIND_SPEED, wind.getElementsByTagName("s")
				.item(0).getFirstChild().getNodeValue());
		nightmap.put(WeatherKey.WIND_DIRECTION, wind.getElementsByTagName(
				"t").item(0).getFirstChild().getNodeValue());		
		
//----夜间部分结束
		
		wind=null;
		part_n=null;
		
	}

	public static void parse(File file, WeatherData wData) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		HashMap<String, String> todayData = wData.getTodayData();
		HashMap<String, String> tonightData = wData.getTonightData();

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
			Element root = doc.getDocumentElement();
			// 第0天为今天天气
			Element day = (Element) root.getElementsByTagName("day").item(0);
			//解析今天天信息，包括白天和夜间
			parseAllData(day, todayData, tonightData);
			day=null;
			
			ArrayList<HashMap<String, String>> futureList=wData.getFutureData();
			NodeList future=root.getElementsByTagName("day");
			System.out.println(future.getLength());
			for(int i=1;i<future.getLength();i++) {
				day=(Element)future.item(i);
				HashMap<String, String>map=new HashMap<String, String>();
				parseAllData(day, map, null);
				futureList.add(map);
			}
			future=null;
			day=null;
			root=null;
			
			System.gc();
		} catch (ParserConfigurationException ex) {
		
			ex.printStackTrace();
		} catch (SAXException ex) {
		
			ex.printStackTrace();
		} catch (IOException ex) {
		
			ex.printStackTrace();
		}

	}
	public static void main(String[] args) {
		WeatherData data=new WeatherData();
		parse(new File("D:\\CHXX0259.xml"),data);
		ArrayList<HashMap<String, String>> future=data.getFutureData();
		System.out.println(future.size());
		HashMap<String, String> map=future.get(0);
		Iterator<String> iter=map.keySet().iterator();
		while(iter.hasNext()) {
			String key=iter.next();
			System.out.println(key+":"+map.get(key));
		}

	}

}