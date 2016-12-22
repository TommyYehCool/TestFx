package com.exfantasy.test.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.exfantasy.test.vo.TyphoonVacationInfoTableModel;
import com.exfantasy.utils.tools.typhoon_vacation.CountiesInfo;
import com.exfantasy.utils.tools.typhoon_vacation.TyphoonVacationInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TyphoonVacationInfosDialogController implements Initializable {

	@FXML
	private TableView<TyphoonVacationInfoTableModel> tvTyphoonVacationInfos;
	private ObservableList<TyphoonVacationInfoTableModel> tableDatas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableDatas = FXCollections.observableArrayList();

		defineTyphoonVacationInfosTableColumnCellFactory();

		tvTyphoonVacationInfos.setItems(tableDatas);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void defineTyphoonVacationInfosTableColumnCellFactory() {
		ObservableList<TableColumn<TyphoonVacationInfoTableModel,?>> columns = tvTyphoonVacationInfos.getColumns();
		for (int tableColIndex = 0; tableColIndex < columns.size(); tableColIndex++) {
			PropertyValueFactory propValFactory = null;
			switch (tableColIndex) {
				case 0:
					propValFactory = new PropertyValueFactory<TyphoonVacationInfoTableModel, String>("region");
					break;
				case 1:
					propValFactory = new PropertyValueFactory<TyphoonVacationInfoTableModel, String>("counties");
					break;
				case 2:
					propValFactory = new PropertyValueFactory<TyphoonVacationInfoTableModel, String>("vacationInfo");
					break;
			}
			TableColumn column = columns.get(tableColIndex);
			column.setCellValueFactory(propValFactory);
		}
	}

	public void setTyphoonVacationInfos(TyphoonVacationInfo[] typhoonVacationInfos) {
		createTableDatas(typhoonVacationInfos);
	}

	private void createTableDatas(TyphoonVacationInfo[] typhoonVacationInfos) {
		for (TyphoonVacationInfo typhoonVacationInfo : typhoonVacationInfos) {
			List<CountiesInfo> countiesInfos = typhoonVacationInfo.getCountiesInfos();
			
			for (CountiesInfo countiesInfo : countiesInfos) {
				TyphoonVacationInfoTableModel tableData = new TyphoonVacationInfoTableModel();
				tableData.setRegion(typhoonVacationInfo.getRegion());
				tableData.setCounties(countiesInfo.getCounties());
				tableData.setVacationInfo(countiesInfo.getVacationInfo());
				tableDatas.add(tableData);
			}
		}
	}
}
