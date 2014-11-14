package com.sand.ws.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;

public class TestClient {

	public static void main(String[] args) {

		String userName = "carrefour"; // 商户ID，待告知
		String password = "ff3fe2e028a2f1b5"; // 用户密码,待告知
		String qryType = "0"; // 查询类型固定为根据退货单号（单据编号）查询
		String returnGoodsNum = "CR201410220929501810173";
		String endpoint = "https://220.248.12.124:36666/CarrefourRefund/Refund?wsdl";
		String res = "";
		SSLUtilities.trustAllHostnames();
		RefundService refundService = new RefundService();
		System.out.println("endpoint:" + endpoint);
		System.out.println("userName:" + userName);
		System.out.println("password:" + password);
		System.out.println("qryType:" + qryType);
		System.out.println("returnGoodsNum:" + returnGoodsNum);
		RefundServiceDelegate port = refundService.getRefund();
		HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
		TLSClientParameters tlsCP = new TLSClientParameters();
		// other TLS/SSL configuration like setting up TrustManagers
		tlsCP.setDisableCNCheck(true);
		httpConduit.setTlsClientParameters(tlsCP);
		res = port.queryGoodsReturnSsl(userName, password, qryType, returnGoodsNum);
		System.out.println("res:" + res);

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
