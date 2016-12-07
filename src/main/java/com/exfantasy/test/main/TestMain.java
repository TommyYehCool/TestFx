package com.exfantasy.test.main;

import java.util.List;

import com.exfantasy.test.cnst.UiCnst;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// 導入 AquaFx -> http://aquafx-project.com/documentation.html
		// FIXME 導入這個會讓 DatePicker 選日期的時候變透明, 再看怎麼辦
//		AquaFx.style();
		
		// 顯示出可使用的 Font Family
		List<String> families = javafx.scene.text.Font.getFamilies();
		for (String family : families) {
			System.out.println(family);
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/main.fxml"));
		Parent root = (Parent) loader.load();
		Scene scene = new Scene(root);
		stage.setTitle(UiCnst.UI_TITLE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
