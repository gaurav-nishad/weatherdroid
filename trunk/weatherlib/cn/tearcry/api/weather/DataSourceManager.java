package cn.tearcry.api.weather;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.InputSource;

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

/**
 * 
 * @author  Rajab Ma<majianle@gmail.com>
 *
 */
public class DataSourceManager {

	/**
	 * @param args
	 */
	
	
	
	public static final String UTF="UTF-8";
	public static final String ISO="ISO-8859-1";
	
	
	

	
	public static InputSource getInputSource(String addr,String charset) throws WeatherException {
		URL url=null;
		//HttpURLConnection connection=null;
		InputSource source=null;
		try {
			url=new URL(addr);
			//connection=(HttpURLConnection) url.openConnection();
			source=new InputSource(new BufferedInputStream(url.openStream()));
			source.setEncoding(charset);
			
		} catch (MalformedURLException ex) {
			throw new WeatherException("指定的链接错误");			
		} catch (IOException ex) {
			throw new WeatherException("连接错误");
		}
		
		return source;
	}
	
	public static InputSource getInputSource(String addr) throws WeatherException {
		return getInputSource(addr,UTF);
	}
	
	public static InputSource getInputSource(File file,String charset) throws WeatherException {
		InputSource source=null;
		if(!file.exists())
			return null;
		try {
			source=new InputSource(new BufferedInputStream(new FileInputStream(file)));
			source.setEncoding(charset);
		} catch (FileNotFoundException ex) {
			throw new WeatherException(ex.getMessage());
		}
		return source;
	
	}
	
	public static InputSource getInputSource(File file) throws WeatherException {
		return getInputSource(file,UTF);
	
	}
	
	
	

	
	public static String getString(String addr) throws WeatherException {
		URL url=null;
		HttpURLConnection connection=null;
		BufferedReader reader;
		String str = null;		
		StringBuffer result=null;
		try {
			url=new URL(addr);
			connection=(HttpURLConnection) url.openConnection();
			reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			result=new StringBuffer("");
			while((str=reader.readLine())!=null)
				result.append(str);
			
		} catch (MalformedURLException ex) {
			throw new WeatherException("指定的链接错误");			
		} catch (IOException ex) {
			throw new WeatherException("连接错误");
		}
		
		return result.toString();
	
	}

	
	
	public static void closeStream(InputStream stream) {
		if(stream!=null)
			try {
				stream.close();
			} catch (IOException ex) {
				
			}
	}
	
	
	public static String getString(File file) {
		if(!file.exists())
			return null;
		BufferedReader reader=null;
		StringBuffer result=null;
		
		try {
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			result=new StringBuffer("");
			String str=null;
			while((str=reader.readLine())!=null)
				result.append(str);
			
		} catch (FileNotFoundException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}
		return result.toString();
	}

	
	
	
	
}
