package com.exfantasy.test.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exfantasy.test.config.Config;
import com.exfantasy.test.config.ConfigHolder;
import com.exfantasy.test.enu.DataType;
import com.exfantasy.test.enu.TableColDef;
import com.exfantasy.test.enu.Type;
import com.exfantasy.test.vo.Consume;
import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;
import com.google.gson.Gson;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TestController implements Initializable {
	private Logger logger = LoggerFactory.getLogger(TestController.class);
	
	private Config mConfig;
	
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComponents();

		setAquaFxStyle();
		
		loadConfig();
		
		new Thread(() -> {
			loginToSever();
		}).start();;
	}
	
	private void setAquaFxStyle() {
		// FIXME 因為導入 AquaFx DatePicker 會壞掉, 所以先不用
	}
	
	private void initComponents() {
		changeButtonsStateByIsFxAppThread(true);
		
		fillTypeCombos();
		
		initTable();
	}
	
	private void changeButtonsStateByIsFxAppThread(boolean disable) {
		boolean isFxAppThread = Platform.isFxApplicationThread();
		if (isFxAppThread) {
		    changeButtonsState(disable);
		}
		else {
		    Platform.runLater(() -> {
		    	changeButtonsState(disable);
		    });
		}
    }
	
	private void changeButtonsState(boolean disable) {
		btnInsert.setDisable(disable);
		btnQuery.setDisable(disable);
	}

	private void loadConfig() {
		mConfig = ConfigHolder.getInstance().getConfig();
	}
	
	private void loginToSever() {
		String url = mConfig.getHost() + "/login";

		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("username", "tommy.yeh1112@gmail.com"));
		params.add(new BasicNameValuePair("password", "1234qwer"));
		try {
			HttpUtil.sendPostRequest(url, params);
			changeButtonsState(false);
			showMsg("登入伺服器成功");
		} catch (HttpUtilException e) {
			logger.error("Try to login to url: <{}> failed", url, e);
			showErrorMsg("嘗試登入伺服器失敗, 網址: " + mConfig.getHost());
		}
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
			if (checkInput()) {
				makeInsert();
			}
		} else if (event.getSource().equals(btnQuery)) {
			makeQuery();
		} else if (event.getSource().equals(btnClear)) {
			makeClear();
		}
	}

	private boolean checkInput() {
		LocalDate date = dpConsumeDate.getValue();
		if (date == null) {
			showErrorMsg("請輸入消費日期");
			return false;
		}
		return true;
	}

	private void makeInsert() {
		LocalDate consumeDate = dpConsumeDate.getValue();
		Type type = cmbTypes.getValue();
		String prodName = tfdProdName.getText();
		Integer amount = Integer.parseInt(tfdAmount.getText());
		String lotteryNo = tfdLotteryNo.getText();
		
		Consume consume = new Consume(consumeDate, type, prodName, amount, lotteryNo);
		try {
			sendAddConsume(consume);
			mConsumes.add(consume);
		} catch (HttpUtilException e) {
			String errorMsg = "新增消費資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		}
	}

	private void sendAddConsume(Consume consume) throws HttpUtilException {
		final String url = mConfig.getHost() + "/consume/add_consume";
		// FIXME 這邊需要解決 LocalDate serialize 與 deserialize 問題
		Gson gson = new Gson();
		String jsonData = gson.toJson(consume);
		HttpUtil.sendPostRequest(url, jsonData);
	}

	private void makeQuery() {
		// TODO query from somewhere
	}

	private void makeClear() {
		mConsumes.clear();
	}
	
	private void showMsg(String msg) {
		showMsg(false, msg);
	}
	
	private void showErrorMsg(String msg) {
		showMsg(true, msg);
	}
	
	private void showMsg(boolean isError, String msg) {
		boolean isFxAppThread = Platform.isFxApplicationThread();
		if (isFxAppThread) {
			lblProcessResult.setText(msg);
			lblProcessResult.setTextFill(isError ? Color.RED : Color.BLACK);
		} else if (!isFxAppThread) {
			Platform.runLater(() -> {
				lblProcessResult.setText(msg);
				lblProcessResult.setTextFill(isError ? Color.RED : Color.BLACK);
			});
		}
	}
}