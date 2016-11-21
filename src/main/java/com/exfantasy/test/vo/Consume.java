package com.exfantasy.test.vo;

import java.time.LocalDate;

import com.exfantasy.test.enu.Type;

public class Consume {
	private LocalDate consumeDate;
	private Type type;
	private String prodName;
	private Integer amount;
	private String lotteryNo;
	
	private Boolean isGot;
	private Integer prize;

	public Consume(LocalDate consumeDate, Type type, String prodName, Integer amount, String lotteryNo) {
		this.consumeDate = consumeDate;
		this.type = type;
		this.prodName = prodName;
		this.amount = amount;
		this.lotteryNo = lotteryNo;
	}

	public LocalDate getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(LocalDate consumeDate) {
		this.consumeDate = consumeDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getLotteryNo() {
		return lotteryNo;
	}

	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}
	
	public Boolean getIsGot() {
		return isGot;
	}

	public void setIsGot(Boolean isGot) {
		this.isGot = isGot;
	}

	public Integer getPrize() {
		return prize;
	}

	public void setPrize(Integer prize) {
		this.prize = prize;
	}
}
