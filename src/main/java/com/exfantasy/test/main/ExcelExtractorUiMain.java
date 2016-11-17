package com.exfantasy.test.main;

import com.aquafx_project.AquaFx;
import com.exfantasy.test.cnst.UiConstant;
import com.exfantasy.test.controller.ExcelExtractorUiViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExcelExtractorUiMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// 導入 AquaFx -> http://aquafx-project.com/documentation.html
		AquaFx.style();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/ExcelExtractorUiView.fxml"));
		Parent root = (Parent) loader.load();

		ExcelExtractorUiViewController controller = (ExcelExtractorUiViewController) loader.getController();
		controller.setStage(stage);

		Scene scene = new Scene(root);
		stage.setTitle(UiConstant.EXCEL_EXTRACOTR_UI_NAME);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
