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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author rajab
 * 
 */
public class FindLocation extends DefaultHandler {
	public static ArrayList<HashMap<String, String>> find(String locid)
			throws WeatherException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		ArrayList<HashMap<String, String>> location = null;

		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			LocationHandler hanlder = new LocationHandler();
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(WeatherKey.Url.LOC_QUERY
					+ locid));
			location = hanlder.getLocList();
		} catch (Exception ex) {
			throw new WeatherException(ex.getMessage());
		}

		return location;
	}

	public static ArrayList<HashMap<String, String>> find(File file)
			throws WeatherException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		ArrayList<HashMap<String, String>> location = null;
		
		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			LocationHandler hanlder = new LocationHandler();
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(file));
			location = hanlder.getLocList();
		} catch (Exception ex) {
			throw new WeatherException(ex.getMessage());
		}

		return location;
	}

	public static void main(String[] args) {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp;
		ArrayList<HashMap<String, String>> location = null;

		try {
			sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			LocationHandler hanlder = new LocationHandler();
			xr.setContentHandler(hanlder);
			xr.parse(DataSourceManager.getInputSource(new File(
					"D:/citylist.xml")));
			location = hanlder.getLocList();
			for (HashMap<String, String> map : location) {
				String key = map.keySet().iterator().next();
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

}
