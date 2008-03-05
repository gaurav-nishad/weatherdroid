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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author rajab
 *
 */
public class WeatherData {
	//温度内部类
	public class Temperature {
		//温度数值
		private int mTemp;
		//单位类型
		private String mType;
		
		/**
		 * 温度构造函数
		 * @param temp 温度
		 * @param type 类型
		 */
		public Temperature (int temp,String type) {
			mTemp=temp;
			mType=type;
		}		
		
		/**
		 * 转换温度
		 * @param type 要转换的类型
		 * @return 转换后的温度
		 */
		public String transform(String type) {
			int ret=0;
			//如果类型相同，直接返回
			if(mType.equals(type))
				ret=mTemp;
			else
				//转换为华氏
				if(type.equals(WeatherKey.Unit.F))
					ret=(int)(32+1.8*mTemp+0.5);
				else
					ret=(int)((mTemp-32)/1.8+0.5);
					
			return ret+type;
		}
		
		/* 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return mTemp+mType;
		}
		
	}
	
	public static  class Time {
		private GregorianCalendar mTime;
		private Locale mLocale;
		
		public Time(String time,Locale locale) {
			Date date=null;
			mLocale=locale;
			
			SimpleDateFormat formatter=new SimpleDateFormat("hh:mm a",Locale.US);
			try {
				date=formatter.parse(time);				
				
			} catch (ParseException ex) {
				
				ex.printStackTrace();
			}
			
			mTime=new GregorianCalendar();
			GregorianCalendar today=new GregorianCalendar();;
			if(date==null)
				return;
			mTime.setTime(date);
			mTime.set(Calendar.YEAR, today.get(Calendar.YEAR));
			mTime.set(Calendar.MONTH, today.get(Calendar.MONTH));
			mTime.set(Calendar.DAY_OF_MONTH, today.get(Calendar.DAY_OF_MONTH));		
		}
		
		public String transform() {
			SimpleDateFormat output=new SimpleDateFormat("HH:mm",mLocale);
			return output.format(mTime.getTime());
		}
		
		public String toString() {
			SimpleDateFormat output=new SimpleDateFormat("hh:mm,a",mLocale);
			return output.format(mTime.getTime());
		}
		
		public GregorianCalendar getDate() {
			return mTime;
		}
	}
	
	private WeatherData() {	
	}
	
	/** 天气数据 */
	protected HashMap<String,String> mData;
	
	
	private void init() {
		
	}
	public static WeatherData queryWeather(String xml,Parser parser) {
		WeatherData wd=new WeatherData();
		parser.parse(xml, wd);
		return wd;		
	}
	
	public static void main(String[] args) {
		WeatherData.Time time=new WeatherData.Time("08:42 PM",Locale.US);
		System.out.println(time);
	}
		
	

}
