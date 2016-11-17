package com.exfantasy.test.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.aquafx_project.AquaFx;
import com.aquafx_project.controls.skin.styles.ButtonType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ExcelExtractorUiViewController implements Initializable {
	// ---------------- Extension Filter ----------------
	private ExtensionFilter extXlsFilter;

	// ---------------- FX Related ----------------
	private Stage stage;
	@FXML
	private TextField tfdSrcFile;
	@FXML
	private TextField tfdResultFolder;
	@FXML
	private Button btnChooseSrcFile;
	@FXML
	private Button btnCreateResultFile;
	@FXML
	private Button btnClear;
	@FXML
	private Label lblResult;
	@FXML
	private Button btnChooseResultFolder;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		extXlsFilter = new ExtensionFilter("Excel File", "*.xls", "*.xlsx");

		setAquaFxStyle();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	private void setAquaFxStyle() {
		AquaFx.createButtonStyler().setType(ButtonType.ROUND_RECT).style(btnChooseSrcFile);
	}

	@FXML
	public void handleButtonsAction(ActionEvent event) {
		if (event.getSource() == btnChooseSrcFile) {
			chooseSrcFile();
		} else if (event.getSource() == btnChooseResultFolder) {
			chooseResultFolder();
		} else if (event.getSource() == btnCreateResultFile) {
//			createResultFile();
		} else if (event.getSource() == btnClear) {
			clear();
		}
	}

	private void chooseSrcFile() {
		final FileChooser fcSrcFile = new FileChooser();
		fcSrcFile.setTitle("�п�ܭn�B�z�� Excel");
		fcSrcFile.getExtensionFilters().add(extXlsFilter);

		File selectedSrcFile = fcSrcFile.showOpenDialog(stage);

		String srcFilePath = "";
		try {
			srcFilePath = selectedSrcFile != null ? selectedSrcFile.getCanonicalPath() : "";
			tfdSrcFile.setText(srcFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void chooseResultFolder() {
		final DirectoryChooser dcResultFolder = new DirectoryChooser();
		dcResultFolder.setTitle("�п�ܵ��G���͸��|");

		File selectedResultFolder = dcResultFolder.showDialog(stage);

		String resultFolderPath = "";
		try {
			resultFolderPath = selectedResultFolder != null ? selectedResultFolder.getCanonicalPath() : "";
			tfdResultFolder.setText(resultFolderPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void switchToProcessing() {
		showInfoMsg("處理中");
		tfdSrcFile.setEditable(false);
		tfdResultFolder.setEditable(false);
		btnCreateResultFile.setDisable(true);
		btnClear.setDisable(true);
	}

	private void switchToProcessDone() {
		showInfoMsg("處理完成");
		tfdSrcFile.setEditable(true);
		tfdResultFolder.setEditable(true);
		btnCreateResultFile.setDisable(false);
		btnClear.setDisable(false);
    }

	private void showInfoMsg(String msg) {
		showMsg(false, msg);
	}

	private void showErrorMsg(String msg) {
		showMsg(true, msg);
	}

	private void showMsg(boolean isError, String msg) {
		if (!isError) {
			lblResult.setTextFill(Color.BLACK);
		} else {
			lblResult.setTextFill(Color.RED);
		}
		lblResult.setText(msg);
	}

	private void clear() {
		tfdSrcFile.setText("");
		tfdResultFolder.setText("");
		lblResult.setText("");
	}
}