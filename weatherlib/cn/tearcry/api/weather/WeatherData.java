
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


import java.util.ArrayList;
import java.util.HashMap;



/**
 *  定义天气情况结构
 * @author majianle
 *
 */
public class WeatherData {
	
	
	public  WeatherData() {
		init();
	}
	/** 头信息 */
	private HashMap<String, String> mHeadData;
	/** 现在天气 */
	private HashMap<String,String> mNowData;	
	/** 今日详情 */
	private ArrayList<HashMap<String, String>> mTodayDetails;
	
	/** 今晚天气 */
	private HashMap<String, String> mTonightData;	
	/** 未来天气 */
	private ArrayList<HashMap<String, String>> mFutureData;
	//今日天气
	private HashMap<String, String> mTodayData;
	
	//private HashMap<String, String> mHeadData;
	
	protected boolean bDetailsParsed;
	protected boolean bTodayParsed;
	protected boolean bFutureParsed;
	protected boolean bNowParsed;
	
	

	
	/**
	 * 初始化天气数据
	 * 
	 */
	private void init() {
		mNowData = new HashMap<String, String>();
		mTodayDetails = new ArrayList<HashMap<String, String>>();		
		mTonightData = new HashMap<String, String>();
		mFutureData = new ArrayList<HashMap<String, String>>();
		mHeadData = new HashMap<String, String>();
		mTodayData = new HashMap<String, String>();
		bDetailsParsed = false;
		bFutureParsed = false;
		bTodayParsed = false;
	}


	/**
	 * 获得当前天气信息
	 * @return mNowData
	 */
	public HashMap<String, String> getNowData() {
		return mNowData;
	}


	/**
	 * 获得未来天气信息
	 * @return mFutureData
	 */
	public ArrayList<HashMap<String, String>> getFutureData() {
		return mFutureData;
	}


	/**
	 * 获得今晚天气信息
	 * @return mTonightData 今晚天气信息
	 */
	public HashMap<String, String> getTonightData() {
		return mTonightData;
	}
	

	/**
	 * 获得今日详情
	 * @return mTodayDetails 今天详情
	 */
	public ArrayList<HashMap<String, String>>getTodayDetails() {
		return mTodayDetails;
	}

	
	
	/**
	 * 
	 * @param xml
	 * @param parser
	 * @return
	 * @throws WeatherException 
	 */
	public static WeatherData getWeatherData(String locid,Queryable que) throws WeatherException
	{
		WeatherData data=new WeatherData();
		que.query(locid, data);
		return data;

	}


	/**
	 * 返回今天天气概况
	 * @return mTodayData
	 */
	public HashMap<String, String> getTodayData() {
		return mTodayData;
	}


	


	/**
	 * 返回详情是否已被解析
	 * @return bDetailsParsed
	 */
	public boolean hasDetailsParsed() {
		return bDetailsParsed;
	}


	/**
	 * @return bFutureParsed
	 */
	public boolean hasFutureParsed() {
		return bFutureParsed;
	}


	/**
	 * @return bTodayParsed
	 */
	public boolean hasTodayParsed() {
		return bTodayParsed;
	}
	

	public HashMap<String, String> getHeadData() {
		return mHeadData;
	}
	
}
