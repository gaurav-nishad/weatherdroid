package cn.tearcry.api.weather.utility;
import java.io.File;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
public class Translate {
	
    private static final String space=" ";
    private static final String quote="\'";
    private static final String point=".";
    private static final String dash="_";
   
    public static String getText(String str,String lang) {
	
       
    	if(!lang.equals(Locale.SIMPLIFIED_CHINESE.toString()))
    		return str;
    	String currentDir=Translate.class.getResource("../resources/"+lang).getPath();
    	String letter=str.substring(0,1).toUpperCase();    	
    	File file=new File(currentDir+File.separator+letter+".xml");
    	
    	System.out.println(file.getAbsolutePath());
    
    	if(!file.exists())
    		return str;
    	DocumentBuilderFactory dbf=null;
    	DocumentBuilder db=null;
    	Document doc=null;
    	try {
    		dbf=DocumentBuilderFactory.newInstance();
    		db=dbf.newDocumentBuilder();
    		doc=db.parse(file);
                Element root=doc.getDocumentElement();
                String transCityName=str.toLowerCase();
                transCityName=transCityName.replaceFirst(space,point);
                transCityName=transCityName.replaceAll(quote,dash);
                NodeList nodelist=root.getElementsByTagName(transCityName);
                if(nodelist.getLength()==0)
                    return str;
                else
                    return nodelist.item(0).getFirstChild().getNodeValue();
    	}catch(Exception ex) {
           
    	}
    	System.out.println(currentDir);
        return str;
    }
	
	public static void main(String[] args) {
		System.out.println(Translate.getText("seoul", Locale.SIMPLIFIED_CHINESE
				.toString()));
	}
		

}
