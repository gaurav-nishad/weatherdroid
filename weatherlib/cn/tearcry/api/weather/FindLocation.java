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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 查找城市编号
 * @author  Rajab Ma<majianle@gmail.com>
 *
 */
public class FindLocation {
	
	
	
	/**
	 * @param xml 
	 * @return
	 * @throws WeatherException 查询失败抛出
	 */
	public static HashMap<String, String> getLocList(InputSource source)
			throws WeatherException {
		if(source==null)
			throw new WeatherException("数据源为空");
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		HashMap<String, String> loclist = null;

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(source);
			Element root = (Element) doc.getDocumentElement();
			NodeList loc = root.getElementsByTagName("loc");
			loclist = new HashMap<String, String>();
			
			for (int i = 0; i < loc.getLength(); i++) {
				Element elem = (Element) loc.item(i);
				loclist.put(elem.getAttribute("id"), elem.getFirstChild()
						.getNodeValue());
			}
			
			root=null;
			doc=null;
			db=null;
			

		} catch (ParserConfigurationException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		} catch (SAXException ex) {
			throw new WeatherException("查询失败");
		} catch (IOException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}

		return loclist;
	}
	
	
	public static void main(String[] args) {
		try {

			HashMap<String, String> loc = FindLocation.getLocList(DataSourceManager
					.getInputSource(new File("D:\\citylist.xml")));
			Iterator<String> iter = loc.keySet().iterator();
			while (iter.hasNext()) {
				String id = iter.next();
				System.out.println(id + ":" + loc.get(id));
			}

		} catch (WeatherException ex) {

		}
	}
}
