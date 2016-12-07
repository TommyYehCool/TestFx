package com.exfantasy.test.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.exfantasy.test.animation.FadeInLeftTransition;
import com.exfantasy.test.animation.FadeInLeftTransition1;
import com.exfantasy.test.animation.FadeInRightTransition;
import com.exfantasy.test.config.Config;
import com.exfantasy.test.config.ConfigHolder;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
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

//		if (username.equals("herudi") && password.equals("herudi")) {
//            StageChangeUtil util = new StageChangeUtil();
//            util.newStage(stage, lblClose, "/herudi/view/formMenu.fxml", "Test App", true, StageStyle.UNDECORATED, false);
//        } else{
//            StageChangeUtil.dialog(Alert.AlertType.ERROR, "Error Login, Please Chek Username And Password");
//        }
		
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
        service.setOnRunning((WorkerStateEvent wst) -> {
        	imgLoading.setVisible(true);
        });
        service.setOnSucceeded((WorkerStateEvent wst) -> {
            System.out.println("onSucceeded");
        });
    }
}