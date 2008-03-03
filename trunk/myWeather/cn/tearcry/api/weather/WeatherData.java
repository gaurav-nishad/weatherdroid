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

import java.text.SimpleDateFormat;

/**
 * @author rajab
 *
 */
public class WeatherData {
	public static class Temperature {		
		private int mTemp;
		private String mType;
		
		/**
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
		private String transform(String type) {
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
	
	public static class Time {
		
		public Time (String time) {
			SimpleDateFormat formatter=new SimpleDateFormat("hh:mm a");
		
		}
		@Override
		public String toString() {
			return mHour+
		}
	}
	
	public WeatherData() {
		WeatherData.Temperature tem=new WeatherData.Temperature(37,WeatherKey.Unit.C);
		
		System.out.println(tem.transform(WeatherKey.Unit.F));
	}
	
	public static WeatherData queryWeather(String xml,Parser parser) {
		WeatherData wd=new WeatherData();
		parser.parse(xml, wd);
		return wd;		
	}
	
	public static void main(String[] args) {
		new WeatherData();
	}
	

}
