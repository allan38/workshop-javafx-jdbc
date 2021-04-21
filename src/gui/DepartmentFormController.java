package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exeptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;

	private DepartmentService service;
	
	private List<DataChangeListener> listeners = new ArrayList<>();

	@FXML
	private TextField txtid;
	@FXML
	private TextField txtname;
	@FXML
	private Label labelErroName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;	

	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Was Null");
		}
		if(service == null) {
			throw new IllegalStateException("Was Null");
		}
		try {
			entity = getFormData();
			service.seveOrUpdate(entity);
			notifyDataChangeListener();
			Alerts.showAlert("Sucesso", null, "Departamento salvo com sucesso", AlertType.INFORMATION);
			Utils.currentStage(event).close();
		}
		catch(ValidationException e){
			setErrorMenssage(e.getErrors());
		}
		catch(DbException e){
			Alerts.showAlert("Error Saving Object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListener() {
		// TODO Auto-generated method stub
		for(DataChangeListener dcl : listeners) {
			dcl.onDataChange();
		}
	}

	private Department getFormData() {
		
		ValidationException vException = new ValidationException("Error of Validation.");
		Department dep = new Department();
		dep.setId(Utils.tryParseToInt(txtid.getText()));
		if(txtname.getText() == null || txtname.getText().trim().equals("")) {
			vException.addError("name", "PREENCHIMENTO OBRIGATÓRIO!.");
		}
		dep.setName(txtname.getText());
		if(vException.getErrors().size() > 0) {
			throw vException;
		}
		return dep;
	}

	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
		//Alerts.showAlert("Canceled", null, "Departamento cancelado com sucesso", AlertType.ERROR);
	}

	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setService(DepartmentService service) {
		this.service = service;
	}
	
	public void subscribeDataChangeListerner(DataChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	public void initializeConstraints() {
		Constraints.setTextFieldInteger(txtid);
		Constraints.setTextFieldMaxLength(txtname, 40);
	}

	public void updateFormData() {
		txtid.setText(String.valueOf(entity.getId()));
		txtname.setText(entity.getName());
	}
	
	public void setErrorMenssage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErroName.setText(errors.get("name"));
		}
	}
	
}
