package cn.tearcry.api.weather;


import java.util.ArrayList;
import java.util.HashMap;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;



/**
 * @author majianle
 *
 */
public class WeatherData {
	
	
	public WeatherData() {
		init();
	}
	
	/** 现在天气 */
	private HashMap<String,String> mNowData;	
	/** 今日详情 */
	private ArrayList<HashMap<String, String>> mTodayDetailsData;
	/** 今日详情头信息 */
	private HashMap<String, String> mTodayDetailsHead;
	/** 今晚天气 */
	private HashMap<String, String> mTonightData;	
	/** 未来天气 */
	private ArrayList<HashMap<String, String>> mFutureData;
	//今日天气
	private HashMap<String, String> mTodayData;
	
	private HashMap<String, String> mHeadData;
	
	
	

	
	/**
	 * 初始化天气数据
	 * 
	 */
	private void init() {
		mNowData = new HashMap<String, String>();
		mTodayDetailsData = new ArrayList<HashMap<String, String>>();
		mTodayDetailsHead = new HashMap<String, String>();
		mTonightData = new HashMap<String, String>();
		mFutureData = new ArrayList<HashMap<String, String>>();
		mHeadData = new HashMap<String, String>();
		mTodayData=new HashMap<String, String>();

	}


	/**
	 * 获得当前天气信息
	 * @return mNowData
	 */
	public HashMap<String, String> getCurrentData() {
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
	 * @return mTonightData
	 */
	public HashMap<String, String> getTonightData() {
		return mTonightData;
	}
	

	/**
	 * 获得今日详情
	 * @return mTodayDetailsData
	 */
	public ArrayList<HashMap<String, String>>getTodayDetails() {
		return mTodayDetailsData;
	}

	
	public static WeatherData getWeatherData(String xml,Parser parser)
	{
		WeatherData data=new WeatherData();
		parser.parse(xml,data);
		return data;
		
	}


	/**
	 * @return mTodayData
	 */
	public HashMap<String, String> getTodayData() {
		return mTodayData;
	}
	

	
}
