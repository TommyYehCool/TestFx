package com.exfantasy.test.config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.exfantasy.test.cnst.ConfigCnst;

public class ConfigHolder {
	private static ConfigHolder configHolder = new ConfigHolder();
	
	private static Config config;
	
	static {
		try {
			File file = new File(ConfigCnst.CONFIG_PATH);
			JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			config = (Config) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private ConfigHolder() {}
	
	public static ConfigHolder getInstance() {
		return configHolder;
	}
	
	public Config getConfig() {
		return config;
	}
}
