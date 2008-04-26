package cn.tearcry.api.weather;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
 * @author Rajab Ma<majianle@gmail.com>
 * 
 */
/**
 * 
 * @author  Rajab Ma<majianle@gmail.com>
 *
 */
public class DataSourceManager {

	/**
	 * ISO－8859－1 字符编码集
	 */
	public static final String ISO = "ISO-8859-1";
	
	/**
	 * UTF－8 字符编码集
	 */
	public static final String UTF = "UTF-8";

	/**
	 *  默认缓冲区大小 8K
	 */
	private static final int BUF_SIZE = 8192;
	
	
	
	
	
	/**
	 * 通过文件创建InputSource,编码为UTF-8
	 * @param file 输入的文件对象
	 * @return 输出的InputSource
	 * @throws IOException
	 */
	public static InputSource getInputSource(File file) throws IOException {
		return getInputSource(file, UTF);

	}

	/**
	 * 通过文件创建指定的编码输出的InputSource
	 * @param file 输入的文件对象
	 * @param charset 输出的字符编码集
	 * @return 输出的InputSource
	 * @throws IOException
	 */
	public static InputSource getInputSource(File file, String charset)
			throws IOException {
		InputSource source = null;
		if (!file.exists())
			return null;

		source = new InputSource(new BufferedInputStream(new FileInputStream(
				file), BUF_SIZE));
		source.setEncoding(charset);

		return source;

	}

	/**
	 * 通过URL创建InputSource
	 * @param addr URL地址
	 * @return 输出的InputSource
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static InputSource getInputSource(String addr) throws IOException,
			MalformedURLException {
		return getInputSource(addr, UTF);
	}

	/**
	 * 通过URL创建指定的编码输出的InputSource
	 * @param addr URL地址
	 * @param charset 输出的字符编码集
	 * @return 输出的InputSource
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static InputSource getInputSource(String addr, String charset)
			throws IOException, MalformedURLException {
		URL url = new URL(addr);
		InputSource source = null;

		source = new InputSource(new BufferedInputStream(url.openStream(),
				BUF_SIZE));
		source.setEncoding(charset);

		return source;
	}
}
