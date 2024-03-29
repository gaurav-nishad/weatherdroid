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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

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
/**
 * @author 马建乐
 *
 */
public class SummaryHanlder extends DefaultHandler {
	private ArrayList<HashMap<String, String>> futureData;

	private HashMap<String, String> todayData;

	private HashMap<String, String> tonightData;

	private HashMap<String, String> headData;

	private HashMap<String, String> current;

	// private HashMap<String, String> nowData;

	private boolean today = false;

	private boolean day = false;

	// private boolean now = false;

	private String last_update;

	private LinkedList<String> stack;

	private WeatherData wData;

	/**
	 * @param wData
	 */
	public SummaryHanlder(WeatherData wData) {
		futureData = wData.getFutureData();
		todayData = wData.getTodayData();
		tonightData = wData.getTonightData();
		headData = wData.getHeadData();
		// nowData = wData.getNowData();
		stack = new LinkedList<String>();
		this.wData = wData;
	}

	/* （非 Javadoc）
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attr) {
		if (qName != null)
			stack.addFirst(qName);

		if (qName.equalsIgnoreCase("loc")) {
			// System.out.println(attr.getValue(0));
			headData.put(WeatherKey.Weather.LOCATION_ID, attr.getValue(0));
		} else if (qName.equalsIgnoreCase("day")) {
			if (attr.getValue(0).equals("0")) {
				today = true;
			} else {
				today = false;
				current = new HashMap<String, String>();

				current.put(WeatherKey.Weather.TIME, UnitUtil.convertTime(
						"MMM dd,E", "MM/dd EEE", attr.getValue(2) + ","
								+ attr.getValue(1)));
				if (last_update != null)
					current.put(WeatherKey.Weather.LAST_UPDATE, UnitUtil
							.convertTime("MM/dd/yy h:mm a", last_update));

			}
		} else if (qName.equalsIgnoreCase("part")) {
			if (attr.getValue(0).equalsIgnoreCase("d")) {
				day = true;
			} else {
				day = false;
			}
		}
	}

	public void characters(char[] ch, int start, int length) {
		if (stack.size() == 0)
			return;
		else if (stack.getFirst().equalsIgnoreCase("dnam")) {
			headData.put(WeatherKey.Weather.LOCATION, new String(ch, start, length));
		} else if (stack.getFirst().equalsIgnoreCase("tm")) { // 当前时间
			headData.put(WeatherKey.Weather.TIME, UnitUtil.convertTime("h:mm a",
					"H:mm", new String(ch, start, length)));
		} else if (stack.getFirst().equalsIgnoreCase("lat")) {// 经纬度
			headData.put(WeatherKey.Weather.LATITUDE, new String(ch, start, length));
		} else if (stack.getFirst().equalsIgnoreCase("lon")) {
			headData.put(WeatherKey.Weather.LONGITUDE, new String(ch, start, length));
		} else if (stack.getFirst().equalsIgnoreCase("sunr") // 日出日落时间
				&& stack.contains("loc")) {
			todayData.put(WeatherKey.Weather.SUN_RISE, UnitUtil.convertTime(
					"h:mm a", "HH:mm", new String(ch, start, length)));
		} else if (stack.getFirst().equalsIgnoreCase("suns")
				&& stack.contains("loc")) {
			todayData.put(WeatherKey.Weather.SUN_SET, UnitUtil.convertTime("h:mm a",
					"HH:mm", new String(ch, start, length)));
		} else if (stack.getFirst().equalsIgnoreCase("zone")) { // 时区
			headData.put(WeatherKey.Weather.TIMEZONE, new String(ch, start, length));
		} else if (stack.getFirst().equalsIgnoreCase("lsup")) { // 最后更新时间
			String time = new String(ch, start, length);

			todayData.put(WeatherKey.Weather.LAST_UPDATE, UnitUtil.convertTime(
					"MM/dd/yy h:mm a", time));
			tonightData.put(WeatherKey.Weather.LAST_UPDATE, UnitUtil.convertTime(
					"MM/dd/yy h:mm a", time));
			last_update = time;

		} else if (stack.getFirst().equalsIgnoreCase("obst")) { // 观察点
			headData.put(WeatherKey.Weather.OBSERVATION_STATION, new String(ch, start,
					length));
		}
		// 最高温度为未来天气数据，今天的最高温度即为白天温度，最低温度为夜间温度
		else if (stack.getFirst().equalsIgnoreCase("hi")) {
			if (today)
				todayData.put(WeatherKey.Weather.TEMPERATURE, new String(ch, start,
						length));
			else
				current
						.put(WeatherKey.Weather.HIGH_TEMP,
								new String(ch, start, length));

		} else if (stack.getFirst().equalsIgnoreCase("low")) {
			if (today)
				tonightData.put(WeatherKey.Weather.TEMPERATURE, new String(ch, start,
						length));
			else
				current.put(WeatherKey.Weather.LOW_TEMP, new String(ch, start, length));

		}
		/**
		 * 如果是今天即day=0 那么根据day真假分别判断白天和晚上的数据
		 * 另外，未来的天气数据只需要获取其白天部分，即part="n",这部分的数据是day n!="0" && part n="d" *
		 */
		else if (stack.getFirst().equalsIgnoreCase("icon")) { // 图标
			String icon = new String(ch, start, length);

			if (today) { // 今天的数据，分别判断白天还是晚上
				if (day) // 白天
					todayData.put(WeatherKey.Weather.ICON,
							new String(ch, start, length));
				else
					// 夜间
					tonightData.put(WeatherKey.Weather.ICON, icon);

			} else if (day) // 未来的数据，只需白天的
				current.put(WeatherKey.Weather.ICON, icon);

		}

		else if (stack.getFirst().equalsIgnoreCase("s")
				&& stack.contains("wind")) { // 速度，判断栈中是否有wind来判断是风速，因为s还有其他的速度
			String speed = new String(ch, start, length);
			if (today) {
				if (day)
					todayData.put(WeatherKey.Weather.WIND_SPEED, speed);
				else
					tonightData.put(WeatherKey.Weather.WIND_SPEED, speed);

			} else if (day)
				current.put(WeatherKey.Weather.WIND_SPEED, speed);

		} else if (stack.getFirst().equalsIgnoreCase("t")) { // 文字描述，判断栈中是否有wind来判断是风向，因为t还有其他的文字描述
			String t = new String(ch, start, length);
			if (!stack.contains("wind"))// 不包含wind 说明是天气描述
			{
				if (today) {
					if (day)
						todayData.put(WeatherKey.Weather.DESCRIPTION, t);
					else
						tonightData.put(WeatherKey.Weather.DESCRIPTION, t);
				} else if (day) {
					current.put(WeatherKey.Weather.DESCRIPTION, t);
				}

			} else {
				if (today) {
					if (day)
						todayData.put(WeatherKey.Weather.WIND_DIRECTION, t);
					else
						tonightData.put(WeatherKey.Weather.WIND_DIRECTION, t);
				} else if (day)
					current.put(WeatherKey.Weather.WIND_DIRECTION, t);
			}

		} else if (stack.getFirst().equalsIgnoreCase("hmid")) { // 湿度

			if (today) {
				if (day)
					todayData.put(WeatherKey.Weather.HUMIDITY, new String(ch, start,
							length)
							+ "%");
				else
					tonightData.put(WeatherKey.Weather.HUMIDITY, new String(ch, start,
							length)
							+ "%");
			}
		} /*
			 * else if (stack.getFirst().equalsIgnoreCase("i") &&
			 * stack.contains("uv")) { // 紫外线强度 nowData.put(WeatherKey.Weather.UV, new
			 * String(ch, start, length)); }
			 */
		else if (stack.getFirst().equalsIgnoreCase("ppcp")) { // 降水概率
			if (today) {
				if (day)

					todayData.put(WeatherKey.Weather.PRECIP_CHANCE, new String(ch,
							start, length)
							+ "%");
				else
					tonightData.put(WeatherKey.Weather.PRECIP_CHANCE, new String(ch,
							start, length)
							+ "%");
			} else if (day)
				current.put(WeatherKey.Weather.PRECIP_CHANCE, new String(ch, start,
						length)
						+ "%");
		}

	}
	

	/* （非 Javadoc）
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName) {
		if (qName != null && stack.size() != 0)
			stack.removeFirst();
		if (qName.equalsIgnoreCase("day") && !today)
			futureData.add(current);
		else if (qName.equalsIgnoreCase("weather")) {
			wData.mTodayParsed = true;
			wData.mFutureParsed = true;
		}
	}

	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			WeatherData data = new WeatherData();
			DefaultHandler hanlder = new SummaryHanlder(data);
			xr.setContentHandler(hanlder);

			// xr
			// .parse(DataSourceManager
			// .getInputSource("http://xoap.weather.com/weather/local/chxx0141?prod=xoap&unit=m&dayf=7&par=1057677963&key=fcac442aff2dd9c0"));
			xr.parse(DataSourceManager.getInputSource(new File(
					"d:\\04_09_xian_sum.xml")));
			System.out.println("now/today/future: " + data.mNowParsed + "/"
					+ data.mTodayParsed + "/" + data.mFutureParsed);

			HashMap<String, String> head = data.getNowData();
			Iterator<String> ithead = head.keySet().iterator();
			while (ithead.hasNext()) {
				String k = ithead.next();
				System.out.println(k + ":" + head.get(k));
			}
			ArrayList<HashMap<String, String>> future = data.getFutureData();
			for (int i = 0; i < future.size(); i++) {
				HashMap<String, String> map = future.get(i);
				Iterator<String> iter = map.keySet().iterator();
				System.out.println("===" + (i + 1) + "===");
				while (iter.hasNext()) {
					String key = iter.next();
					System.out.println(key + ":" + map.get(key));
				}
			}

		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
