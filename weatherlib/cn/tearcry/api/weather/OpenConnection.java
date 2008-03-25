package cn.tearcry.api.weather;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
public class OpenConnection extends Thread {

	/**
	 * @param args
	 */
	
	
	private static final  String YAHOO_URL="http://weather.yahooapis.com/forecastrss?u=c&p=";
	private static final String WEATHER_DETAIL_URL="http://www.weather.com/weather/mpdwcr/dailydetails?locid=";
	private static final String WEATHER_URL_PREFIX = "http://xoap.weather.com/weather/local/";
	private static final String WEATHER_URL_SUFFIX="?prod=xoap&unit=c&dayf=7&par=1057677963&key=fcac442aff2dd9c0";
	private static final String LOC_QUERY="http://xoap.weather.com/search/search?where=";
		
	public static void main(String[] args) {
		OpenConnection open=new OpenConnection(OpenConnection.LOC_QUERY+"atlanta");
		int i=0;
		open.start();
		//OpenConnection another=new OpenConnection(OpenConnection.YAHOO_URL+"CHXX0141");
		//another.start();
			class mThread extends Thread {
				public void run() {
					int i=0;
					while(i!=10)
						System.out.println(i++);
				}
			}
		//new mThread().start();
		
		//while(open.hasDone())
			System.out.println(open.getResult());
		//System.out.println(another.getResult());
		
	}
	
	public void run() {
		try {
			
				HttpURLConnection connection = getConnection(url);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String str = null;
			
				while ((str = reader.readLine()) != null) {
					result.append(str);					
					
				}
				done = true;
				
				
				
			
		} catch (WeatherException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO 自动生成 catch 块
			ex.printStackTrace();
		}

	}
	
	public HttpURLConnection getConnection(String addr) throws WeatherException {
		URL url=null;
		HttpURLConnection connection=null;
		try {
			url=new URL(addr);
			connection=(HttpURLConnection) url.openConnection();
		} catch (MalformedURLException ex) {
			throw new WeatherException("链接错误");			
		} catch (IOException ex) {
			throw new WeatherException("网络连接失败");
		}
		
		return connection;
		
		
	}
	
	private StringBuffer result;
	private boolean done=false;
	private String url;
	
	public String getResult() {
		if(done)
			return result.toString();
		else
			return null;
	}
	
	public boolean hasDone() {
		return done;
	}
	
	public OpenConnection(String url) {
		
			this.url=url;
			result=new StringBuffer("");
		
		StringBuffer sb=new StringBuffer("");
	}
	
}
