package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenMainPage extends Stage{

	Stage stage;
	
	public OpenMainPage() throws Exception{
		stage = this;
		Parent root = FXMLLoader.load(getClass().getResource("HauptFenster.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Projekt Name"); // Hier soll der jeweilige Projektname einegtragen werden!
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("VanillaSky.png")));
		stage.show();
	}
	
}
