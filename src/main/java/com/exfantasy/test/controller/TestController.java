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

import com.exfantasy.test.cnst.ApiCnst;
import com.exfantasy.test.cnst.ResultCode;
import com.exfantasy.test.config.Config;
import com.exfantasy.test.config.ConfigHolder;
import com.exfantasy.test.enu.DataType;
import com.exfantasy.test.enu.TableColDef;
import com.exfantasy.test.enu.Type;
import com.exfantasy.test.util.ImageUtil;
import com.exfantasy.test.vo.Consume;
import com.exfantasy.test.vo.ResponseVo;
import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

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
	
	private final Image gotImage = ImageUtil.createGotImage();
	private final Image notGotImage = ImageUtil.createNotGotImage();
    
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
		}).start();
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
		
		defineTableContextMenu();
		
		tvConsumes.setItems(mConsumes);
	}
	
	private void defineTableContextMenu() {
		// 參考: http://stackoverflow.com/questions/20802208/delete-a-row-from-a-javafx-table-using-context-menu
		MenuItem menuDel = new MenuItem("刪除");
		menuDel.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent t) {
		    	Consume selectedConsume = tvConsumes.getSelectionModel().getSelectedItem();
		    	makeDelete(selectedConsume);
		    }
		});
		tvConsumes.setContextMenu(new ContextMenu(menuDel));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void defineTableColumnCellFactory() {
		tvConsumes.setEditable(true);
		
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
			
			// 若 Table 欄位可編輯
			if (tableColDef.isEditable()) {
				// 參考: http://blog.csdn.net/slf2046/article/details/52813130
				StringConverter converter = null;
				if (dataType == DataType.String) {
					converter = new DefaultStringConverter();
				}
				else if (dataType == DataType.Integr) {
					converter = new IntegerStringConverter();
				}
				column.setCellFactory(TextFieldTableCell.forTableColumn(converter));
				column.setOnEditCommit(new EventHandler<CellEditEvent<Consume, ?>>() {
					@Override
					public void handle(CellEditEvent<Consume, ?> event) {
						processCellEdit(tableColDef, event);
					}
				});
			}
			
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
								imageView.setImage(notGotImage);
								setGraphic(imageView);
							}
						}
					}
				});
			}
			// 獎金欄位, 若為 0 不顯示
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
	
	protected void processCellEdit(TableColDef tableColDef, CellEditEvent<Consume, ?> event) {
		Consume consume = (Consume) event.getTableView().getItems().get(event.getTablePosition().getRow());
		switch (tableColDef) {
			case AMOUNT:
				int newAmount = (Integer) event.getNewValue();
				consume.setAmount(newAmount);
				break;
				
			case PROD_NAME:
				String newProdName = (String) event.getNewValue();
				consume.setProdName(newProdName);
				break;
				
			// 其他不可修改, 這邊不會跑到
			default:
				break;
		}

		new Thread(() -> {
			makeUpdate(consume);
		}).start();
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
			final String url = mConfig.getHost() + ApiCnst.INS_CONSUME;
			
			ObjectMapper reqMapper = new ObjectMapper();
			String jsonData = reqMapper.writeValueAsString(consume);
			
			String respData = HttpUtil.sendPostRequest(url, jsonData);
			
			ObjectMapper respMapper = new ObjectMapper();
			final ResponseVo responseVo = respMapper.readValue(respData, ResponseVo.class);

			if (responseVo.getResultCode() == ResultCode.SUCCESS) {
				// 加在第一筆
				mConsumes.add(0, consume);
				showMsg("新增成功");
			}
			else if (responseVo.getResultCode() == ResultCode.DUPLICATE_KEY) {
				showErrorMsg("資料重複");
			}
		} catch (HttpUtilException e) {
			String errorMsg = "新增消費資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		} catch (JsonProcessingException e) {
			String errorMsg = "轉換 json 為物件失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		} catch (IOException e) {
			String errorMsg = "解析回應資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		}
	}

	private void makeUpdate(Consume consume) {
		try {
			final String url = mConfig.getHost() + ApiCnst.UPD_CONSUME;
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(consume);
			
			HttpUtil.sendPostRequest(url, jsonData);
			
			showMsg("更新成功");
			
		} catch (HttpUtilException e) {
			String errorMsg = "更新消費資料失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		} catch (JsonProcessingException e) {
			String errorMsg = "轉換 json 為物件失敗";
			logger.error(errorMsg, e);
			showErrorMsg(errorMsg);
		}
	}

	private void makeDelete(Consume consumeToDelete) {
		try {
			final String url = mConfig.getHost() + ApiCnst.DEL_CONSUME;
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(consumeToDelete);
			
			HttpUtil.sendPostRequest(url, jsonData);
	
			mConsumes.remove(consumeToDelete);
			
			showMsg("刪除成功");
			
		} catch (HttpUtilException e) {
			String errorMsg = "刪除消費資料失敗";
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
		
		final String url = mConfig.getHost() + ApiCnst.QRY_CONSUME + uriBuilder.toString();
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