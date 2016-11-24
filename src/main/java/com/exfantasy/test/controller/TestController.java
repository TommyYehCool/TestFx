package com.exfantasy.test.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class TestController implements Initializable {
	private Logger logger = LoggerFactory.getLogger(TestController.class);
	
	private Config mConfig;
	
	// ---------------- FX Related ----------------
	@SuppressWarnings("unused")
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
	private DatePicker dpEndDate;

	@FXML 
	private DatePicker dpStartDate;
	
	@FXML
	private Button btnInsert;

	@FXML
	private Button btnQuery;

	@FXML
	private Button btnClear;

	@FXML
	private TableView<Consume> tvConsumes;
	private ObservableList<Consume> mConsumes;

	@FXML
	private Label lblProcessResult;
	
	private final Image gotImage = createImage();
    
    private final Image createImage() {
    	Circle circle = new Circle();
    	circle.setRadius(5);
    	circle.setFill(Color.WHITE);
        circle.setStroke(Color.GREEN);
        circle.setStrokeWidth(3);
        circle.setStrokeType(StrokeType.OUTSIDE);
        return circle.snapshot(null, null);
    }

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
		// FIXME 登入帳號看要怎麼決定
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
			
			// 是否中獎欄位, 判斷是否中獎塞入圖式
			// 參考: http://stackoverflow.com/questions/34896299/javafx-change-tablecell-column-of-selected-tablerow-in-a-tableview
			if (tableColDef == TableColDef.GOT) {
				column.setCellFactory(col -> new TableCell<Consume, Boolean>() {
					private final ImageView imageView = new ImageView();
					
					@Override
					protected void updateItem(Boolean got, boolean empty) {
						super.updateItem(got, empty);
						
						// http://stackoverflow.com/questions/13455326/javafx-tableview-text-alignment
						this.setAlignment(Pos.CENTER);
						if (got == null || empty) {
							setGraphic(null);
						}
						else {
							if (got) {
								imageView.setImage(gotImage);
								setGraphic(imageView);
							}
							else {
								setGraphic(null);
							}
						}
					}
				});
			}
			// 獎金欄位
			// http://code.makery.ch/blog/javafx-8-tableview-cell-renderer/
			else if (tableColDef == TableColDef.PRIZE) {
				column.setCellFactory(col -> new TableCell<Consume, Integer>() {
					@Override
					protected void updateItem(Integer prize, boolean empty) {
						super.updateItem(prize, empty);

						this.setAlignment(Pos.CENTER);
						if (prize == null || empty) {
							setText(null);
						}
						else {
							if (prize != 0) {
								setText(String.valueOf(prize));
							}
							else {
								setText(null);
							}
						}
					}
				});
			}
		}
	}
	
	@FXML
	public void handleButtonsAction(ActionEvent event) {
		if (event.getSource().equals(btnInsert)) {
			if (checkInsertInput()) {
				makeInsert();
			}
		} else if (event.getSource().equals(btnQuery)) {
			makeQuery();
		} else if (event.getSource().equals(btnClear)) {
			makeClear();
		}
	}

	private boolean checkInsertInput() {
		LocalDate date = dpConsumeDate.getValue();
		if (date == null) {
			showErrorMsg("請輸入消費日期");
			return false;
		}
		Type type = cmbTypes.getValue();
		if (type == null || type == Type.ALL) {
			showErrorMsg("請選擇類別");
			return false;
		}
		String prodName = tfdProdName.getText();
		if (prodName == null || prodName.isEmpty()) {
			showErrorMsg("請輸入商品名稱");
			return false;
		}
		String strAmount = tfdAmount.getText();
		if (strAmount == null || strAmount.isEmpty()) {
			showErrorMsg("請輸入金額");
			return false;
		}
		try {
			Integer.parseInt(strAmount);
		} catch (Exception e) {
			showErrorMsg("請輸入正確金額");
			return false;
		}
		String lotteryNo = tfdLotteryNo.getText();
		if (lotteryNo == null || lotteryNo.isEmpty()) {
			showErrorMsg("請輸入發票號碼");
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
		
		Consume consume = new Consume();
		consume.setConsumeDate(consumeDate);
		consume.setType(type);
		consume.setProdName(prodName);
		consume.setAmount(amount);
		consume.setLotteryNo(lotteryNo);
		try {
			final String url = mConfig.getHost() + "/consume/add_consume";
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(consume);
			
			HttpUtil.sendPostRequest(url, jsonData);
			
			mConsumes.add(consume);
			
			showMsg("新增成功");
			
		} catch (HttpUtilException e) {
			String errorMsg = "新增消費資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		} catch (JsonProcessingException e) {
			String errorMsg = "轉換 json 為物件失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		}
	}

	private void makeQuery() {
		LocalDate startDate = dpStartDate.getValue();
		LocalDate endDate = dpEndDate.getValue();
		Integer type = cmbTypes.getValue() != null ? cmbTypes.getValue().getCode() : null;
		String prodName = tfdProdName.getText();
		String lotteryNo = tfdLotteryNo.getText();
		
		URIBuilder uriBuilder = new URIBuilder();
		if (startDate != null) {
			uriBuilder.addParameter("startDate", startDate.toString());
		}
		if (endDate != null) {
			uriBuilder.addParameter("endDate", endDate.toString());
		}
		if (type != null) {
			uriBuilder.addParameter("type", String.valueOf(type));
		}
		if (prodName != null && !prodName.isEmpty()) {
			uriBuilder.addParameter("prodName", prodName);
		}
		if (lotteryNo != null && !lotteryNo.isEmpty()) {
			uriBuilder.addParameter("lotteryNo", lotteryNo);
		}
		
		final String url = mConfig.getHost() + "/consume/get_consume" + uriBuilder.toString();
		try {
			String respData = HttpUtil.sendGetRequest(url);
			
//			// http://stackoverflow.com/questions/30652314/gson-datetypeexception-when-converting-date-in-typed-in-milliseconds
//			// https://github.com/joffrey-bion/fx-gson
//			Gson gson = 
//				FxGson.coreBuilder()
//					  .registerTypeAdapter(LocalDate.class, new GsonLocalDateDeserializer())
//					  .registerTypeAdapter(Type.class, new ConsumeTypeAdapter())
//					  .create();
//
//			Consume[] consumes = gson.fromJson(respData, Consume[].class);
			
			ObjectMapper mapper = new ObjectMapper();
			final Consume[] consumes = mapper.readValue(respData, Consume[].class);

			mConsumes.clear();
			mConsumes.addAll(Arrays.asList(consumes));
			
			new Thread(() -> {
				int totalSpent = getTotalSpent(consumes);
				showMsg("查詢成功, 共 " + consumes.length + " 筆資料, 總花費: $" + totalSpent);
			}).start();
			
		} catch (HttpUtilException e) {
			String errorMsg = "查詢消費資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		} catch (IOException e) {
			String errorMsg = "查詢成功, 但轉換為物件失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		}
	}

	private int getTotalSpent(Consume[] consumes) {
		int totalSpent = 0;
		for (Consume consume : consumes) {
			totalSpent += consume.getAmount();
		}
		return totalSpent;
	}

	private void makeClear() {
		mConsumes.clear();
		dpConsumeDate.setValue(null);
		cmbTypes.setValue(null);
		tfdProdName.setText("");
		tfdAmount.setText("");
		tfdLotteryNo.setText("");
		dpStartDate.setValue(null);
		dpEndDate.setValue(null);
		
		showMsg("清除成功");
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