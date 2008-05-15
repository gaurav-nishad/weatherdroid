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
 * 定义天气情况结构
 * 
 * @author majianle
 * 
 */
public class WeatherData {

	public WeatherData() {
		init();
	}

	/** 头信息 */
	private HashMap<String, String> mHeadData;

	/** 现在天气 */
	private HashMap<String, String> mNowData;

	/** 今日详情 */
	private ArrayList<HashMap<String, String>> mDetailedData;

	/** 今晚天气 */
	private HashMap<String, String> mTonightData;

	/** 未来天气 */
	private ArrayList<HashMap<String, String>> mFutureData;

	/** 今日天气 */
	private HashMap<String, String> mTodayData;

	protected boolean mDetailsParsed;

	protected boolean mTodayParsed;

	protected boolean mFutureParsed;

	protected boolean mNowParsed;

	/**
	 * 初始化天气数据
	 * 
	 */
	private void init() {
		mNowData = new HashMap<String, String>();
		mDetailedData = new ArrayList<HashMap<String, String>>();
		mTonightData = new HashMap<String, String>();
		mFutureData = new ArrayList<HashMap<String, String>>();
		mHeadData = new HashMap<String, String>();
		mTodayData = new HashMap<String, String>();
		mDetailsParsed = false;
		mFutureParsed = false;
		mTodayParsed = false;

	}

	/**
	 * 获得当前天气信息
	 * 
	 * @return mNowData
	 */
	public HashMap<String, String> getNowData() {
		return mNowData;
	}

	/**
	 * 获得未来天气信息
	 * 
	 * @return mFutureData
	 */
	public ArrayList<HashMap<String, String>> getFutureData() {
		return mFutureData;
	}

	/**
	 * 获得今晚天气信息
	 * 
	 * @return mTonightData 今晚天气信息
	 */
	public HashMap<String, String> getTonightData() {
		return mTonightData;
	}

	/**
	 * 获得今日详情
	 * 
	 * @return mDetailedData 今天详情
	 */
	public ArrayList<HashMap<String, String>> getDetailedData() {
		return mDetailedData;
	}

	/**
	 * 返回今天天气概况
	 * 
	 * @return mTodayData
	 */
	public HashMap<String, String> getTodayData() {
		return mTodayData;
	}

	public HashMap<String, String> getHeadData() {
		return mHeadData;
	}

	/**
	 * @return parseOver
	 */
	public boolean isParseOver() {
		return mDetailsParsed && mFutureParsed && mNowParsed && mNowParsed;
	}

	/**
	 * @return detailsParsed
	 */
	public boolean isDetailsParsed() {
		return mDetailsParsed;
	}

	/**
	 * @return futureParsed
	 */
	public boolean isFutureParsed() {
		return mFutureParsed;
	}

	/**
	 * @return nowParsed
	 */
	public boolean isNowParsed() {
		return mNowParsed;
	}

	/**
	 * @return todayParsed
	 */
	public boolean isTodayParsed() {
		return mTodayParsed;
	}

}
