package com.exfantasy.test.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exfantasy.test.animation.FadeInLeftTransition;
import com.exfantasy.test.animation.FadeInLeftTransition1;
import com.exfantasy.test.animation.FadeInRightTransition;
import com.exfantasy.test.cnst.ApiCnst;
import com.exfantasy.test.cnst.UiCnst;
import com.exfantasy.test.config.Config;
import com.exfantasy.test.config.ConfigHolder;
import com.exfantasy.test.util.StageChangeUtil;
import com.exfantasy.utils.http.HttpUtil;
import com.exfantasy.utils.http.HttpUtilException;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	private Config mConfig;

	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Text lblWelcome;
	@FXML
	private Text lblUserLogin;
	@FXML
	private Text lblUsername;
	@FXML
	private Text lblPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Text lblRudyCom;
	@FXML
	private Label lblClose;
	@FXML
	private ImageView imgLoading;

	private Stage stage;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		imgLoading.setVisible(false);

		loadConfig();

		Platform.runLater(() -> {
			new FadeInRightTransition(lblUserLogin).play();
			new FadeInLeftTransition(lblWelcome).play();
			new FadeInLeftTransition1(lblPassword).play();
			new FadeInLeftTransition1(lblUsername).play();
			new FadeInLeftTransition1(txtUsername).play();
			new FadeInLeftTransition1(txtPassword).play();
			new FadeInRightTransition(btnLogin).play();
			lblClose.setOnMouseClicked((MouseEvent event) -> {
				Platform.exit();
				System.exit(0);
			});
		});
	}

	private void loadConfig() {
		mConfig = ConfigHolder.getInstance().getConfig();
	}

	@FXML
	private void doLogin(ActionEvent event) {
		String username = txtUsername.getText();
		String password = txtPassword.getText();

		Service<Boolean> service = new Service<Boolean>() {
			@Override
			protected Task<Boolean> createTask() {
				return new Task<Boolean>() {
					@Override
					protected Boolean call() throws Exception {
						String url = mConfig.getHost() + ApiCnst.LOGIN;
						List<NameValuePair> params = new ArrayList<>();
						params.add(new BasicNameValuePair("username", username));
						params.add(new BasicNameValuePair("password", password));
						HttpUtil.sendPostRequest(url, params);
						return true;
					}
				};
			}
		};
		service.start();
		service.setOnRunning((WorkerStateEvent wst) -> {
			imgLoading.setVisible(true);
		});
		service.setOnSucceeded((WorkerStateEvent wst) -> {
			StageChangeUtil util = new StageChangeUtil();
			util.changeStage(stage, lblClose, "/view/fxml/main.fxml", UiCnst.UI_TITLE, true, StageStyle.DECORATED, false);
		});
		service.setOnFailed((WorkerStateEvent wst) -> {
			String errorMsg = "";

			imgLoading.setVisible(false);
			Throwable exception = service.getException();
			if (exception instanceof HttpUtilException) {
				HttpUtilException ex = (HttpUtilException) exception;
				int errorCode = ex.getErrorCode();
				if (errorCode == HttpUtilException.COMMUNICATE_ERROR) {
					errorMsg = "連線至 server 失敗, host: " + mConfig.getHost();
					logger.error(errorMsg, ex);
				} else if (errorCode == HttpUtilException.LOGIN_FAILED) {
					errorMsg = "請確認帳號密碼是否正確";
					logger.warn(errorMsg, ex);
				}
			}
			StageChangeUtil.dialog(Alert.AlertType.ERROR, "登入失敗\n" + errorMsg);
		});
	}
}