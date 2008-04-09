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
public class LocationHanlder extends DefaultHandler {
	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			LocationHanlder hanlder = new LocationHanlder();
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(new File(
					"d:\\citylist.xml")));
			ArrayList<HashMap<String, String>> future = hanlder.getLocList();
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
		} catch (WeatherException ex) {
			ex.printStackTrace();
		}

	}

	private HashMap<String, String> current;

	private String id;

	private boolean loc = false;

	private ArrayList<HashMap<String, String>> loclist;

	public LocationHanlder() {
		loclist = new ArrayList<HashMap<String, String>>();

	}

	public void characters(char[] ch, int start, int length) {
		if (loc) {
			current = new HashMap<String, String>();
			current.put(id, new String(ch, start, length));
		}
	}

	public void endElement(String uri, String localName, String qName) {
		if (qName.equalsIgnoreCase("loc")) {
			loc = false;
			loclist.add(current);
		}
	}

	public ArrayList<HashMap<String, String>> getLocList() {
		return loclist;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attr) {
		if (qName.equalsIgnoreCase("loc")) {
			id = attr.getValue(0);
			loc = true;
		}
	}
}
