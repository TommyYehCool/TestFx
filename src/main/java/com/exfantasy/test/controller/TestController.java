package com.exfantasy.test.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.exfantasy.test.enu.DataType;
import com.exfantasy.test.enu.TableColDef;
import com.exfantasy.test.enu.Type;
import com.exfantasy.test.exception.HttpUtilException;
import com.exfantasy.test.util.HttpUtil;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Consume> tvConsumes;
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void setAquaFxStyle() {
		// FIXME 因為導入 AquaFx DatePicker 會壞掉, 所以先不用
	}
	
	private void initComponents() {
		fillTypeCombos();
		
		initTable();
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
	
	private void initTable() {
		mConsumes = FXCollections.observableArrayList();
		
		defineTableColumnCellFactory();
		
		tvConsumes.setItems(mConsumes);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void defineTableColumnCellFactory() {
		ObservableList<TableColumn<Consume, ?>> columns = tvConsumes.getColumns();
		
		for (int tableColIndex = 0; tableColIndex < columns.size(); tableColIndex++) {
			TableColDef tableColDef = TableColDef.convertByTableIndex(tableColIndex);
			
			PropertyValueFactory propValFactory = null;
			
			DataType dataType = tableColDef.getDateType();
			String mappingVoField = tableColDef.getMappingVoField();
			
			switch (dataType) {
				case LocalDate:
					propValFactory = new PropertyValueFactory<Consume, LocalDate>(mappingVoField);
					break;

				case Type:
					propValFactory = new PropertyValueFactory<Consume, Type>(mappingVoField);
					break;

				case String:
					propValFactory = new PropertyValueFactory<Consume, String>(mappingVoField);
					break;

				case Integr:
					propValFactory = new PropertyValueFactory<Consume, Integer>(mappingVoField);
					break;
				
				case Boolean:
					propValFactory = new PropertyValueFactory<Consume, Boolean>(mappingVoField);
					break;
			}
			
			// 設定 PropertyValueFactory 到 TableColumn
			TableColumn column = columns.get(tableColIndex);
			column.setCellValueFactory(propValFactory);
		}
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
		
		mConsumes.add(consume);
	}

	private void makeQuery() {
		String url = "http://localhost:8080/night-web/user/login";
		String jsonData = "{\"username\": \"bensonQQQQ\", \"password\": \"abc123\", \"validCode\": \"LKWR\"}";
		try {
			HttpUtil.sendPostRequest(url, jsonData);
		} catch (HttpUtilException e) {
			e.printStackTrace();
		}
	}

	private void makeClear() {
		mConsumes.clear();
	}
}