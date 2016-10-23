package controller;

import java.util.List;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;

public class SplashViewController {
	@FXML
	private ImageView imgLoading;
	@FXML
	private Text lblWelcome;
	@FXML
	private Text lblRudy;
	@FXML
	private VBox vboxBottom;
	@FXML
	private Label lblClose;

	public static List<Projekt> projekte;

	public static void loadProjekt() {
		Datenbank myDB = new Datenbank();
		// Überprüfe ob die DB online ist
		if (!myDB.testConnection()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Keine Verbindung zur Datenbank möglich");
			alert.showAndWait();
		} else {
			SplashViewController.projekte = myDB.getProjekte();
		}
	}

}
