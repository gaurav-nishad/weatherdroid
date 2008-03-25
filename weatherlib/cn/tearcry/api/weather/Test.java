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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


/**
 * @author Rajab
 *
 */
public class Test {
	
	private static final SimpleDateFormat OUT_FORMATTER=new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.US);
	
	public  String getHead(String str) {
		return str.substring((str.indexOf("[")+1),str.indexOf("]"));
	}
	
	public Test() {
		mTodayData=new ArrayList<HashMap<String,String>>();
		mTodayDataHead=new HashMap<String, String>();
	}
	
	private ArrayList<HashMap<String, String>> mTodayData;
	private HashMap<String, String> mTodayDataHead;
	
	public void getWeatherCondition(String hour_string) {

		// 不包含天气信息则返回
		if (!hour_string.contains("mpdHMHrObj")
				&& !hour_string.contains("hour["))
			return;
		HashMap<String, String> hourData = new HashMap<String, String>();

		String time = hour_string.substring(hour_string.indexOf("Date(") + 4,
				hour_string.lastIndexOf(")"));
		SimpleDateFormat formatter = new SimpleDateFormat(
				"(yyyy,mm,dd,HH,MM,ss)");
		GregorianCalendar now = new GregorianCalendar();
		Date date = null;
		try {
			date = formatter.parse(time);

		} catch (ParseException ex) {
			if (date == null)
				date = now.getTime();
		}

		hourData.put(WeatherKey.TIME, OUT_FORMATTER.format(date));

		String all_str = hour_string.substring(hour_string.indexOf("),") + 2,
				hour_string.lastIndexOf(")"));
		String[] condition = all_str.split(",");
		// 风力信息
		String[] wind = condition[7].split(" ");

		hourData.put(WeatherKey.ICON, condition[0]);
		hourData.put(WeatherKey.TEXT_DESC, condition[1]);
		String unit_temp = mTodayDataHead.get(WeatherKey.Unit.UNIT_TEMP);

		// 如果不是公制，则转换
		if (!UnitConvert.fromMetricSystem(unit_temp)) {
			hourData.put(WeatherKey.HIGH_TEMP, UnitConvert.convertTemp(
					condition[2], unit_temp).split(" ")[0]);
			hourData.put(WeatherKey.LOW_TEMP, UnitConvert.convertTemp(
					condition[3], unit_temp).split(" ")[0]);
		} else {
			hourData.put(WeatherKey.HIGH_TEMP, condition[2]);
			hourData.put(WeatherKey.LOW_TEMP, condition[3]);
		}

		hourData.put(WeatherKey.PRECIP_CHANCE, condition[3]);
		hourData.put(WeatherKey.WIND_DIRECTION, wind[1]);
		// 如果不是公制，则转换
		if (!UnitConvert.fromMetricSystem(wind[3]))
			hourData.put(WeatherKey.WIND_SPEED, UnitConvert.convertWind(
					wind[2], wind[3]).split(" ")[0]);
		else
			hourData.put(WeatherKey.WIND_SPEED, wind[2]);

		mTodayData.add(hourData);
	}
	
	
	

	
	public void parse(String[] data) {
		int line = 0;

		while (!getHead(data[line]).equals("head"))
			line++;
		// 取得单位制式

		String[] units = data[line].substring(data[line].indexOf("(") + 1,
				data[line].indexOf(")")).split(", ");
		mTodayDataHead.put(WeatherKey.Unit.UNIT_TEMP, units[0]);
		mTodayDataHead.put(WeatherKey.Unit.UNIT_SPEED,units[2]);
		/*for (String s : units) 
			System.out.println(s);
		*/
		line+=4;
		while(line<10)
			getWeatherCondition(data[line++]);
		int count=1;
		for(HashMap<String, String> hour:mTodayData) {
			Iterator<String> iter=hour.keySet().iterator();
			System.out.println("===="+(count++)+"====");
			while(iter.hasNext()) {
				String key=iter.next();
				System.out.println(key+":"+hour.get(key));				
			}
		}

	}
	
	
	public static void main(String[] args) {
		
		/*try {
				URL url=new URL("http://www.weather.com/weather/mpdwcr/dailydetails?locid=CHXX0141");
				//URL url=new URL("http://www.nwpu.edu.cn");
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
				
				String str;
				StringBuffer sb=new StringBuffer("");
				BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while((str=reader.readLine())!=null) {
					sb.append(str+"\n");
				
				}				
				reader.close();
				
				
				System.out.println(sb.toString());
				/*String[] data=sb.toString().split(";");
				Test t=new Test();
				t.parse(data);
			} catch (MalformedURLException ex) {
				// TODO 自动生成 catch 块
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO 自动生成 catch 块
				ex.printStackTrace();
			}
			*/
			
			
			File file=new File("D:\\today.txt");
			BufferedReader reader=null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String str;
				StringBuffer sb=new StringBuffer("");
				while((str=reader.readLine())!=null) {
					sb.append(str.replaceAll("'", "")+"\n");
				
				}
				String[] data=sb.toString().split(";");
				Test t=new Test();
				t.parse(data);
				//System.out.println(sb);
				
			} catch (FileNotFoundException ex) {
				// TODO 自动生成 catch 块
				ex.printStackTrace();
			} catch (IOException ex) {
				// TODO 自动生成 catch 块
				ex.printStackTrace();
			}finally {
				
				try {
					reader.close();
				} catch (IOException ex) {
					
				}
			}
			
			
		}

	
	
}
