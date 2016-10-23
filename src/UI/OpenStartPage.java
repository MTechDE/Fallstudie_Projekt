package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenStartPage extends Stage{
	
	private Stage stage;
	
	public OpenStartPage(){
		try{
			stage = this;
			Parent root = FXMLLoader.load(getClass().getResource("../fxml/StartFenster.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Vanilla Sky");
			stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("../img/VanillaSky.png")));
			stage.setResizable(false);
			stage.show();
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Fehler aufgetreten!");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Bitte straten Sie die Anwendung neu.");
			alert.showAndWait();
		}
	}

}
