package com.dbdoc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.dbdoc.Constants;

/***
 * ��ȡproperties�ļ�������
 * @author moonights
 *
 * @date 2011-11-23
 */
public class PropertiesProvider {
	
	static PropertiesUtils props;
	private PropertiesProvider(){}
	
	private static void loadProperties() {
		try {
			props = new PropertiesUtils(PropertiesUtils.loadAllPropertiesFromClassLoader(Constants.PROPERTIES_FILE_NAME));
			for(Iterator it = props.entrySet().iterator();it.hasNext();) {
				Map.Entry entry = (Map.Entry)it.next();
				System.out.println("[Property] "+entry.getKey()+"="+entry.getValue());
			}
		}catch(IOException e) {
		}
	}
	
	public static Properties getProperties() {
		return getUtils().getProperties();
	}
	
	private static PropertiesUtils getUtils() {
		if(props == null)
			loadProperties();
		return props;
	}
	
	public static String getRequiredProperty(String key) {
		return getUtils().getRequiredProperty(key);
	}
	
	public static String getNullIfBlank(String key) {
		return getUtils().getNullIfBlank(key);
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getUtils().getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		return getUtils().getProperty(key);
	}
	public static void setProperty(String key,String value) {
		getUtils().setProperty(key, value);
	}
	
	public static void setProperties(Properties v) {
		props = new PropertiesUtils(v);
	}
	
	public static void main(String[] args) {
        System.out.println("jdbc_url = " + PropertiesProvider.getRequiredProperty("jdbc_url"));
    }
}
