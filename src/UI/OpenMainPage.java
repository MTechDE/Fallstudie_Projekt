package UI;

import Datenbank.Datenbank;
import Projekt.Projekt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class OpenMainPage extends Stage{

	private Stage stage;
	private Datenbank myDB = new Datenbank();
	public static Projekt staticProjekt;
	
	public OpenMainPage(Projekt projekt, boolean newProjekt, boolean vorlage) throws Exception{
		stage = this;
		Parent root = FXMLLoader.load(getClass().getResource("HauptFenster.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(projekt.getName());
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("VanillaSky.png")));
		stage.show();
		
		if(newProjekt){
			if(vorlage){
				
			}
			myDB.speicherProjekt(projekt);
			staticProjekt = projekt;
		}
		else
			staticProjekt = myDB.getProjekt(projekt);
			
	}
	
}
