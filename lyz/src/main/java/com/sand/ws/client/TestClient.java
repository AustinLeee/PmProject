package com.sand.ws.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

public class TestClient {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(new Double(0.0).equals(0.0));
	}

	protected static HostnameVerifier hv = new HostnameVerifier() {
		public boolean verify(String urlHostName, SSLSession session) {
			System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
			if (urlHostName.equals(session.getPeerHost()))
				return true;
			else
				return false;
		}

	};

	protected static boolean cardnoEquals(String cardno1, String cardno2) {
		boolean equals = false;

		// logger.info("cardno 1: start 6" + cardno1.substring(0, 6) +
		// "  end 4: " + cardno1.substring(cardno1.length() - 4, 4));
		// if (StringUtils.isNotBlank(cardno1) &&
		// StringUtils.isNotBlank(cardno2)) {
		if ((cardno1.substring(0, 6)).equals(cardno2.substring(0, 6)) && (cardno1.substring(cardno1.length() - 4)).equals(cardno2.substring(cardno2.length() - 4)))
			equals = true;
		// }
		return equals;
	}
}
