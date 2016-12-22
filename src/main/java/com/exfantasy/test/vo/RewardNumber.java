package com.exfantasy.test.vo;

import com.exfantasy.test.json.deserializer.RewardNumberDeserializer;
import com.exfantasy.utils.tools.receipt_lottery.RewardType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = RewardNumberDeserializer.class)
public class RewardNumber {
	private String section;
	
	private RewardType rewardType;
	
	private String no;

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public RewardType getRewardType() {
		return rewardType;
	}

	public void setRewardType(int rewardType) {
		this.rewardType = RewardType.convertByCode(rewardType);
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
}
