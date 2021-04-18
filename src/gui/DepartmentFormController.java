package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

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
	
	public void onBtSaveAction() {
		Alerts.showAlert("Sucesso", null, "Departamento salvo com sucesso", AlertType.INFORMATION);
	}
	
	public void onBtCancelAction() {
		Alerts.showAlert("Canceled", null, "Departamento cancelado com sucesso", AlertType.ERROR);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

	public void initializeConstraints() {
		Constraints.setTextFieldInteger(txtid);
		Constraints.setTextFieldMaxLength(txtname, 40);
	}
}
