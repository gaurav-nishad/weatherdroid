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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.xml.sax.InputSource;

/**
 * 
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */
public class Output {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FutureTask<InputSource> mNowTask;

		FutureTask<InputSource> mSummaryTask;

		FutureTask<InputSource> mDetailedTask;

		if (args.length < 2) {
			System.out.println("Usage:weather location");
			System.exit(0);
		}
		String locationID = args[1];
		System.out.println("你要查询的地区是: " + locationID);

		Executor executor = Executors.newFixedThreadPool(3);
		WeatherData data = new WeatherData();

		mNowTask = new FutureTask<InputSource>(new Callable<InputSource>() {
			public InputSource call() {
				InputSource source = null;
				try {
					source = DataSourceManager.getInputSource(new File(
							"/sdcard/04_09_xian_now.xml"));
				} catch (MalformedURLException ex) {
					System.out.println(ex);
				} catch (IOException ex) {
					System.out.println(ex);
				}
				return source;
			}
		});

		mSummaryTask = new FutureTask<InputSource>(new Callable<InputSource>() {
			public InputSource call() {
				InputSource source = null;
				try {
					source = DataSourceManager.getInputSource(new File(
							"/sdcard/04_09_xian_sum.xml"));
					// source = DataSourceManager
					// .getInputSource(WeatherKey.Url.WEATHER_URL_PREFIX
					// + "CHXX0141"
					// + WeatherKey.Url.WEATHER_URL_SUFFIX);
				} catch (MalformedURLException ex) {
					System.out.println(ex);
				} catch (IOException ex) {
					System.out.println(ex);
				}
				return source;
			}
		});

		mDetailedTask = new FutureTask<InputSource>(
				new Callable<InputSource>() {
					public InputSource call() {
						InputSource source = null;
						try {
							source = DataSourceManager.getInputSource(new File(
									"/sdcard/04_09_xian_detail.txt"));
							// source = DataSourceManager
							// .getInputSource(WeatherKey.Url.WEATHER_DETAIL_URL
							// + "CHXX0141");
						} catch (MalformedURLException ex) {
							System.out.println(ex);
						} catch (IOException ex) {
							System.out.println(ex);
						}
						return source;
					}
				});
		executor.execute(mNowTask);
		executor.execute(mSummaryTask);
		executor.execute(mDetailedTask);
		try {
			NowParser np = new NowParser(mNowTask.get(30L, TimeUnit.SECONDS),
					data);
			np.parse();
			SummaryParser sp = new SummaryParser(mSummaryTask.get(30L,
					TimeUnit.SECONDS), data);
			sp.parse();
			DetailedParser dp = new DetailedParser(mDetailedTask.get(30L,
					TimeUnit.SECONDS), data);
			dp.parse();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		System.out.println(data.isParseOver());

	}
}
