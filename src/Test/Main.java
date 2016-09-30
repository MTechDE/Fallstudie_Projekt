package Test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Projekt.Projekt;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Main extends Application {
	
	@FXML
	private TableView<Projekt> projektTabelle;
	
	@FXML
	private TableColumn<Projekt, String> projektNameColumn;
	
	@FXML
	private TableColumn<Projekt, String> projektErstellerColumn;
	
	@FXML
	private TableColumn<Projekt, String> projektStartColumn;
	
	@FXML
	private TableColumn<Projekt, String> projektEndeColumn;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("TestUI.fxml"));

		Scene scene = new Scene(root);

		primaryStage.setTitle("FXML Welcome");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

	public static void main(String[] args) {
		launch(args);
	}
}
