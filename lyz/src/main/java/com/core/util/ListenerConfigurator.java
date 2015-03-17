package com.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;
import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import javax.servlet.ServletContextEvent;

import java.io.File;
import java.util.Properties;

/**
 * proxool初始化
 * @author 李喻哲
 * @date 2015/03/17
 */
public class ListenerConfigurator implements
		javax.servlet.ServletContextListener {

	private static final Log LOG = LogFactory
			.getLog(ListenerConfigurator.class);

	private static final String XML_FILE_PROPERTY = "proxoolConfigLocation";

	private boolean autoShutdown = true;

	public void contextInitialized(ServletContextEvent servletConfig) {

		String appDir = servletConfig.getServletContext().getRealPath("/");

		Properties properties = new Properties();
		String value = servletConfig.getServletContext().getInitParameter(
				XML_FILE_PROPERTY);
		LOG.debug("proxoolConfigLocation:"+value);
		
		try {
			File file = new File(value);
			if (file.isAbsolute()) {
				JAXPConfigurator.configure(value, false);
			} else {
				LOG.debug(appDir + File.separator + value);
				JAXPConfigurator.configure(appDir + File.separator + value,
						false);
			}
		} catch (ProxoolException e) {
			LOG.error("Problem configuring " + value, e);
		}
		if (properties.size() > 0) {
			try {
				PropertyConfigurator.configure(properties);
			} catch (ProxoolException e) {
				LOG.error("Problem configuring using init properties", e);
			}
		}
	}

	public void contextDestroyed(ServletContextEvent s) {
		if (autoShutdown) {
			ProxoolFacade.shutdown(0);
		}
	}
}