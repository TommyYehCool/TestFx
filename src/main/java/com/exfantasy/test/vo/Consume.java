package com.exfantasy.test.vo;

import java.time.LocalDate;

import com.exfantasy.test.enu.Type;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * http://stackoverflow.com/questions/32794500/serialize-javafx-model-with-gson
 * 
 * @author tommy.feng
 *
 */
public class Consume {
	private ObjectProperty<LocalDate> consumeDate = new SimpleObjectProperty<>();
	private ObjectProperty<Type> type = new SimpleObjectProperty<>();
	private StringProperty prodName = new SimpleStringProperty();
	private IntegerProperty amount = new SimpleIntegerProperty();
	private StringProperty lotteryNo = new SimpleStringProperty();
	
	private BooleanProperty got = new SimpleBooleanProperty();
	private IntegerProperty prize = new SimpleIntegerProperty();

	public LocalDate getConsumeDate() {
		return consumeDate.get();
	}

	public void setConsumeDate(LocalDate consumeDate) {
		this.consumeDate.set(consumeDate);
	}

	public Type getType() {
		return type.get();
	}

	public void setType(Type type) {
		this.type.set(type);
	}

	public String getProdName() {
		return prodName.get();
	}

	public void setProdName(String prodName) {
		this.prodName.set(prodName);
	}

	public Integer getAmount() {
		return amount.get();
	}

	public void setAmount(Integer amount) {
		this.amount.set(amount);
	}

	public String getLotteryNo() {
		return lotteryNo.get();
	}

	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo.set(lotteryNo);
	}
	
	public Boolean isGot() {
		return got.get();
	}

	public void setGot(Boolean got) {
		this.got.set(got);
	}

	public Integer getPrize() {
		return prize.get();
	}

	public void setPrize(Integer prize) {
		this.prize.set(prize);
	}
}
