package com.exfantasy.test.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TestController implements Initializable {
	// ---------------- FX Related ----------------
	private Stage stage;

	@FXML
	private DatePicker dpConsumeDate;

	@FXML
	private ComboBox cmbTypes;

	@FXML
	private TextField tfdProdName;

	@FXML
	private TextField tfdAmount;

	@FXML
	private TextField tfdLotteryNo;

	@FXML
	private TableView tvConsumeDatas;

	@FXML
	private Button btnInsert;

	@FXML
	private Button btnQuery;

	@FXML
	private Button btnClear;

	@FXML
	private Label lblProcessResult;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setAquaFxStyle();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void setAquaFxStyle() {
	}

	@FXML
	public void handleButtonsAction(ActionEvent event) {
		if (event.getSource().equals(btnInsert)) {
			System.out.println("Insert button clicked...");
		} else if (event.getSource().equals(btnQuery)) {
			System.out.println("Query button clicked...");
		} else if (event.getSource().equals(btnClear)) {
			System.out.println("Clear button clicked...");
		}
	}
}