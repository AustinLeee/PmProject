package com.emailim.cofig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {
	@XmlAttribute
	protected String fromEmail;
	@XmlAttribute
	protected String toEmail;
	@XmlAttribute
	protected String name;
	@XmlAttribute
	protected String password;
	@XmlAttribute
	protected String sendHost;
	@XmlAttribute
	protected String receiveHost;
	@XmlAttribute
	protected String latestSentDate;

	public String getFromEmail() {
		return this.fromEmail;
	}

	public void setFromEmail(String value) {
		this.fromEmail = value;
	}

	public String getToEmail() {
		return this.toEmail;
	}

	public void setToEmail(String value) {
		this.toEmail = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public String getSendHost() {
		return this.sendHost;
	}

	public void setSendHost(String value) {
		this.sendHost = value;
	}

	public String getReceiveHost() {
		return this.receiveHost;
	}

	public void setReceiveHost(String value) {
		this.receiveHost = value;
	}

	public String getLatestSentDate() {
		return this.latestSentDate;
	}

	public void setLatestSentDate(String value) {
		this.latestSentDate = value;
	}
}