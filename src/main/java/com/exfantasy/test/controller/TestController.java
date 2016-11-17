package com.exfantasy.test.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.exfantasy.test.enu.Type;
import com.exfantasy.test.vo.Consume;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ComboBox<Type> cmbTypes;

	@FXML
	private TextField tfdProdName;

	@FXML
	private TextField tfdAmount;

	@FXML
	private TextField tfdLotteryNo;

	@FXML
	private TableView<Consume> tvConsumeDatas;
	private ObservableList<Consume> mConsumes;

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
		
		initComponents();
	}
	
	private void setAquaFxStyle() {
		// FIXME 因為導入 AquaFx DatePicker 會壞掉, 所以先不用
	}
	
	private void initComponents() {
		fillTypeCombos();
	}

	private void fillTypeCombos() {
		Platform.runLater(() -> {
		    ObservableList<Type> items = cmbTypes.getItems();
		    items.clear();

		    ArrayList<Type> comboValues = new ArrayList<Type>();
			comboValues.addAll(Arrays.asList(Type.values()));
		    items.addAll(comboValues);
		});
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@FXML
	public void handleButtonsAction(ActionEvent event) {
		if (event.getSource().equals(btnInsert)) {
			makeInsert();
		} else if (event.getSource().equals(btnQuery)) {
			makeQuery();
		} else if (event.getSource().equals(btnClear)) {
			makeClear();
		}
	}

	private void makeInsert() {
		LocalDate consumeDate = dpConsumeDate.getValue();
		Type type = cmbTypes.getValue();
		String prodName = tfdProdName.getText();
		Integer amount = Integer.parseInt(tfdAmount.getText());
		String lotteryNo = tfdLotteryNo.getText();
		
		Consume consume = new Consume(consumeDate, type, prodName, amount, lotteryNo);
		
		// TODO insert somewhere
		
		// TEST
		Consume[] consumes = new Consume[] {consume};
		mConsumes = FXCollections.observableArrayList(consumes);
		setDatasToTable();
	}

	private void setDatasToTable() {
		Platform.runLater(() -> {
			tvConsumeDatas.setItems(mConsumes);
		});
	}

	private void makeQuery() {

	}

	private void makeClear() {
				
	}
}