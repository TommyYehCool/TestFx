package com.exfantasy.test.vo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TyphoonVacationInfoTableModel {
	private StringProperty region = new SimpleStringProperty();
	private StringProperty counties = new SimpleStringProperty();
	private StringProperty vacationInfo = new SimpleStringProperty();

	public String getRegion() {
		return region.get();
	}

	public void setRegion(String region) {
		this.region.set(region);
	}

	public String getCounties() {
		return counties.get();
	}

	public void setCounties(String counties) {
		this.counties.set(counties);
	}

	public String getVacationInfo() {
		return vacationInfo.get();
	}

	public void setVacationInfo(String vacationInfo) {
		this.vacationInfo.set(vacationInfo);
	}

}
