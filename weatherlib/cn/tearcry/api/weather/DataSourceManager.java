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
public class DataSourceManager {

	public static final String ISO = "ISO-8859-1";

	private static final int BUF_SIZE = 8192;

	/**
	 * @param args
	 */

	public static final String UTF = "UTF-8";

	public static void closeStream(InputStream stream) {
		if (stream != null)
			try {
				stream.close();
			} catch (IOException ex) {

			}
	}

	public static InputSource getInputSource(File file) throws IOException {
		return getInputSource(file, UTF);

	}

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

	public static InputSource getInputSource(String addr) throws IOException,
			MalformedURLException {
		return getInputSource(addr, UTF);
	}

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
