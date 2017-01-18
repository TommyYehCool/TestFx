package com.exfantasy.test.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ConsumeRecordMain extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
    	// http://stackoverflow.com/questions/19602727/how-to-reference-javafx-fxml-files-in-resource-folder
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/view/fxml/splash.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}