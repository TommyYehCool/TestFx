package com.exfantasy.test.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exfantasy.test.controller.MainController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageChangeUtil {
	private Logger logger = LoggerFactory.getLogger(StageChangeUtil.class);

	public static void dialog(Alert.AlertType alertType, String msg) {
		Alert alert = new Alert(alertType, msg);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Info");
		alert.showAndWait();
	}

	public void changeStage(Label labelToGetCurrentStage, String fxmlToLoad, String title, boolean resize, StageStyle style, boolean maximized) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(fxmlToLoad));
			
			Parent root = loader.load();
			
			Scene scene = new Scene(root);

			Stage st = new Stage();
			st.initStyle(style);
			st.setResizable(resize);
			st.setMaximized(maximized);
			st.setTitle(title);
			st.setScene(scene);
			if (fxmlToLoad.equals("/view/fxml/main.fxml")) {
				MainController controller = loader.getController();
				controller.setStage(st);
				controller.init();
			}
			st.show();

			Stage stage = (Stage) labelToGetCurrentStage.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			logger.error("Change stage got exception, msg: <{}>", e.getMessage(), e);
		}
	}
}
