package com.exfantasy.test.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageChangeUtil {

	public static void dialog(Alert.AlertType alertType, String msg) {
		Alert alert = new Alert(alertType, msg);
		alert.initStyle(StageStyle.UTILITY);
		alert.setTitle("Info");
		alert.showAndWait();
	}

	public void newStage(Stage stage, Label labelToGetCurrentStage, String fxmlToLoad, String title, boolean resize, StageStyle style, boolean maximized) {
		try {
			Stage st = new Stage();
			stage = (Stage) labelToGetCurrentStage.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource(fxmlToLoad));
			Scene scene = new Scene(root);
			st.initStyle(style);
			st.setResizable(resize);
			st.setMaximized(maximized);
			st.setTitle(title);
			st.setScene(scene);
			st.show();
			stage.close();
		} catch (Exception e) {
		}
	}
}
