package gui;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable, DataChangeListener{

	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	@FXML
	private TableColumn<Department, String> tableColumnName;
	@FXML
	private TableColumn<Department, Department> tableColumnEDIT;
	@FXML
	private Button btNew;

	private ObservableList<Department> obsList;


	//Bot?o New da tela da lista de departamentos
	@FXML
	public void onBtNewAction(ActionEvent event) {

		Stage parentStage = Utils.currentStage(event);
		Department obj = new Department();
		createDialogForm(obj,"/gui/DepartmentForm.fxml", parentStage);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initializeNode();
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}

	//cria a instancia do department service na metodo onItemDepartmentAction na MainViewController
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	private void initializeNode() {
		// TODO Auto-generated method stub
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));

	}

	public void updateTableView() {
		if(service == null) {
			throw new IllegalStateException("Servi?o null");
		}
		List<Department> list = service.findAll();
		System.out.println(list.get(0));
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
		System.out.println(obsList.get(0));
		initEditButtons();
	}

	private void createDialogForm(Department entity, String absolutName, Stage parentStage) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName)); 
			Pane pane = loader.load();

			DepartmentFormController depFormController = loader.getController();
			depFormController.setDepartment(entity);
			depFormController.setService(new DepartmentService());
			depFormController.subscribeDataChangeListerner(this);
			depFormController.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Enter Department Data");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();


		}catch(IOException e) {
			System.out.println(e.getMessage());
			Alerts.showAlert("IOException", null, e.getMessage(), AlertType.ERROR);
		}

	}

	@Override
	public void onDataChange() {
		// TODO Auto-generated method stub
		updateTableView();
	}

	private void initEditButtons() { 
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue())); 
		tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() { 
			private final Button button = new Button("edit"); 
			@Override
			protected void updateItem(Department obj, boolean empty) { 
				super.updateItem(obj, empty); 
				if (obj == null) { 
					setGraphic(null); 
					return; 
				} 
				setGraphic(button); 
				button.setOnAction( 
						event -> createDialogForm( 
								obj, "/gui/DepartmentForm.fxml",Utils.currentStage(event))); 
			} 
		}); 
	}

}
