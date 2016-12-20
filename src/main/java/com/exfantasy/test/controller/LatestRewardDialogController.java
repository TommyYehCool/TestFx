package com.exfantasy.test.controller;

import com.exfantasy.test.vo.RewardNumber;
import com.exfantasy.utils.tools.RewardType;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LatestRewardDialogController {

	@FXML 
	private Label lblFirstSection;
	@FXML 
	private Label lblFirstSectionFirstRewardNo;
	@FXML 
	private Label lblFirstSectionSecondRewardNo;
	@FXML 
	private Label lblFirstSectionThirdRewardNo;
	@FXML 
	private Label lblFirstSectionSpecialSixRewardNo;
	@FXML 
	private Label lblSecondSection;
	@FXML 
	private Label lblSecondSectionFirstRewardNo;
	@FXML 
	private Label lblSecondSectionSecondRewardNo;
	@FXML 
	private Label lblSecondSectionThirdRewardNo;
	@FXML 
	private Label lblSecondSectionSpecialSixRewardNo;
	
	public void setRewardNumbers(RewardNumber[] rewardNumbers) {
		String firstSection = getFirstSection(rewardNumbers);
		
		for (RewardNumber rewardNumber : rewardNumbers) {
			String section = rewardNumber.getSection();
			RewardType rewardType = rewardNumber.getRewardType();
			String no = rewardNumber.getNo();
			String sectionToDisplay = convertSectionToDisplay(section);

			if (section.equals(firstSection)) {
				lblFirstSection.setText(sectionToDisplay);
				switch (rewardType) {
					case FIRST_REWARD:
						lblFirstSectionFirstRewardNo.setText(no);
						break;
					case SEONCD_REWARD:
						lblFirstSectionSecondRewardNo.setText(no);
						break;
					case THIRD_REWARD:
						String currentThridRewardStr = lblFirstSectionThirdRewardNo.getText();
						currentThridRewardStr += no + "、";
						lblFirstSectionThirdRewardNo.setText(currentThridRewardStr);
						break;
					case SPECIAL_SIX:
						String currentSpecialSixRewardStr = lblFirstSectionSpecialSixRewardNo.getText();
						currentSpecialSixRewardStr += no + "、";
						lblFirstSectionSpecialSixRewardNo.setText(currentSpecialSixRewardStr);
						break;
				}
			}
			else {
				lblSecondSection.setText(sectionToDisplay);
				switch (rewardType) {
					case FIRST_REWARD:
						lblSecondSectionFirstRewardNo.setText(no);
						break;
					case SEONCD_REWARD:
						lblSecondSectionSecondRewardNo.setText(no);
						break;
					case THIRD_REWARD:
						String currentThridRewardStr = lblSecondSectionThirdRewardNo.getText();
						currentThridRewardStr += no + "、";
						lblSecondSectionThirdRewardNo.setText(currentThridRewardStr);
						break;
					case SPECIAL_SIX:
						String currentSpecialSixRewardStr = lblSecondSectionSpecialSixRewardNo.getText();
						currentSpecialSixRewardStr += no + "、";
						lblSecondSectionSpecialSixRewardNo.setText(currentSpecialSixRewardStr);
						break;
				}
			}
		}
		
		String text = null;
		text = lblFirstSectionThirdRewardNo.getText();
		lblFirstSectionThirdRewardNo.setText(text.substring(0, text.length() - 1));
		text = lblFirstSectionSpecialSixRewardNo.getText();
		lblFirstSectionSpecialSixRewardNo.setText(text.substring(0, text.length() - 1));
		text = lblSecondSectionThirdRewardNo.getText();
		lblSecondSectionThirdRewardNo.setText(text.substring(0, text.length() - 1));
		text = lblSecondSectionSpecialSixRewardNo.getText();
		lblSecondSectionSpecialSixRewardNo.setText(text.substring(0, text.length() - 1));
	}
	
	private String convertSectionToDisplay(String section) {
		String[] splitByUnderLine = section.split("_");
		String year = String.valueOf(Integer.parseInt(splitByUnderLine[0]) - 1911);
		String months = splitByUnderLine[1];
		return new StringBuilder(year).append("年").append(months).append("月").toString();
	}
	
	private String getFirstSection(RewardNumber[] rewardNumbers) {
		int tempSmallestYear = 0;
		int tempSmallestFirstMonth = 0;
		String smallestSection = null;
		for (RewardNumber rewardNumber : rewardNumbers) {
			String section = rewardNumber.getSection();
		
			String[] splitByUnderLine = section.split("_");
			int year = Integer.parseInt(splitByUnderLine[0]);
			
			String[] splitByMinus = splitByUnderLine[1].split("-");
			int firstMonth = Integer.parseInt(splitByMinus[0]);
			
			if (tempSmallestYear == 0 || year < tempSmallestYear) {
				tempSmallestYear = year;
				tempSmallestFirstMonth = firstMonth;
				smallestSection = section;
				continue;
			}
			if (firstMonth < tempSmallestFirstMonth) {
				tempSmallestFirstMonth = firstMonth;
				smallestSection = section;
			}
		}
		return smallestSection;
	}
}
