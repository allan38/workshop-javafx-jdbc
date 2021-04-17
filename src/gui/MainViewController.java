package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class MainViewController implements Initializable {

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	public void onItemSellerAction () {
		System.out.println("Seller");
	}
	
	public void onItemDepartmentAction () {
		System.out.println("Department");
	}
	
	public void onItemAboutAction () {
		loadView("/gui/About.fxml");
	}
	
	public void loadView (String absolutName) {
		
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName)); 
		VBox vbox = loader.load();
		Scene mainScene = Main.getMainScene();
		VBox vboxmain = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
		
		Node menuMain = vboxmain.getChildren().get(0);
		vboxmain.getChildren().clear();
		vboxmain.getChildren().add(menuMain);
		vboxmain.getChildren().addAll(vbox.getChildren());
		
		}catch(IOException e) {
			Alerts.showAlert(e.getMessage(), null, "Não foi possível carregar a tela.", AlertType.ERROR);
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
	}

}
