package com.sand.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.sand.ws.client package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _ApplyGoodsReturnSsl_QNAME = new QName(
			"http://www.sand.com.cn/", "applyGoodsReturnSsl");
	private final static QName _ApplyGoodsReturnSslResponse_QNAME = new QName(
			"http://www.sand.com.cn/", "applyGoodsReturnSslResponse");
	private final static QName _QueryGoodsReturnSslResponse_QNAME = new QName(
			"http://www.sand.com.cn/", "queryGoodsReturnSslResponse");
	private final static QName _QueryGoodsReturnSsl_QNAME = new QName(
			"http://www.sand.com.cn/", "queryGoodsReturnSsl");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.sand.ws.client
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link QueryGoodsReturnSsl }
	 * 
	 */
	public QueryGoodsReturnSsl createQueryGoodsReturnSsl() {
		return new QueryGoodsReturnSsl();
	}

	/**
	 * Create an instance of {@link QueryGoodsReturnSslResponse }
	 * 
	 */
	public QueryGoodsReturnSslResponse createQueryGoodsReturnSslResponse() {
		return new QueryGoodsReturnSslResponse();
	}

	/**
	 * Create an instance of {@link ApplyGoodsReturnSsl }
	 * 
	 */
	public ApplyGoodsReturnSsl createApplyGoodsReturnSsl() {
		return new ApplyGoodsReturnSsl();
	}

	/**
	 * Create an instance of {@link ApplyGoodsReturnSslResponse }
	 * 
	 */
	public ApplyGoodsReturnSslResponse createApplyGoodsReturnSslResponse() {
		return new ApplyGoodsReturnSslResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ApplyGoodsReturnSsl }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.sand.com.cn/", name = "applyGoodsReturnSsl")
	public JAXBElement<ApplyGoodsReturnSsl> createApplyGoodsReturnSsl(
			ApplyGoodsReturnSsl value) {
		return new JAXBElement<ApplyGoodsReturnSsl>(_ApplyGoodsReturnSsl_QNAME,
				ApplyGoodsReturnSsl.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link ApplyGoodsReturnSslResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.sand.com.cn/", name = "applyGoodsReturnSslResponse")
	public JAXBElement<ApplyGoodsReturnSslResponse> createApplyGoodsReturnSslResponse(
			ApplyGoodsReturnSslResponse value) {
		return new JAXBElement<ApplyGoodsReturnSslResponse>(
				_ApplyGoodsReturnSslResponse_QNAME,
				ApplyGoodsReturnSslResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link QueryGoodsReturnSslResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.sand.com.cn/", name = "queryGoodsReturnSslResponse")
	public JAXBElement<QueryGoodsReturnSslResponse> createQueryGoodsReturnSslResponse(
			QueryGoodsReturnSslResponse value) {
		return new JAXBElement<QueryGoodsReturnSslResponse>(
				_QueryGoodsReturnSslResponse_QNAME,
				QueryGoodsReturnSslResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link QueryGoodsReturnSsl }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.sand.com.cn/", name = "queryGoodsReturnSsl")
	public JAXBElement<QueryGoodsReturnSsl> createQueryGoodsReturnSsl(
			QueryGoodsReturnSsl value) {
		return new JAXBElement<QueryGoodsReturnSsl>(_QueryGoodsReturnSsl_QNAME,
				QueryGoodsReturnSsl.class, null, value);
	}

}
