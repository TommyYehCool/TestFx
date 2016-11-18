package com.exfantasy.test.config;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Config {

	private String host;

	public String getHost() {
		return host;
	}

	@XmlElement
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Config [host=").append(host).append("]");
		return builder.toString();
	}

}