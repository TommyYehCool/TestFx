package com.exfantasy.test.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.exfantasy.test.animation.FadeInLeftTransition;
import com.exfantasy.test.animation.FadeInRightTransition;
import com.exfantasy.test.animation.FadeInTransition;
import com.exfantasy.test.cnst.UiCnst;
import com.exfantasy.test.util.StageChangeUtil;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class SplashController implements Initializable {
    @FXML
    private Text lblWelcome;
    @FXML
    private Text lblRudy;
    @FXML
    private VBox vboxBottom;
    @FXML
    private Label lblClose;
    @FXML
    private ImageView imgLoading;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        longStart();
        lblClose.setOnMouseClicked((MouseEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
    }   
    
    private void longStart() {
        Service<Boolean> service = new Service<Boolean>() {
            @Override
            protected Task<Boolean> createTask() {
                return new Task<Boolean>() {           
                    @Override
                    protected Boolean call() throws Exception {
                    	Thread.sleep(2000);
                    	return true;
                    }
                };
            }
        };
        service.start();
        service.setOnRunning((WorkerStateEvent event) -> {
            new FadeInLeftTransition(lblWelcome).play();
            new FadeInRightTransition(lblRudy).play();
            new FadeInTransition(vboxBottom).play();
        });
        service.setOnSucceeded((WorkerStateEvent event) -> {
            StageChangeUtil util = new StageChangeUtil();
            util.changeStage(lblClose, "/view/fxml/login.fxml", UiCnst.UI_TITLE, true, StageStyle.UNDECORATED, false);
        });
    } 
}
