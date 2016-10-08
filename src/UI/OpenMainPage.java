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
	public static Projekt tmpProjekt;
	
	public OpenMainPage(Projekt projekt, boolean newProjekt, boolean vorlage) throws Exception{
		
		if(newProjekt){
			if(vorlage){
				// TODO Eine Vorlage muss noch  deffiniert werden
			} else {
				tmpProjekt = projekt;
				// Es wir ein JavaFX Bug abgefangen, der das Speichern des Projektes möglicherweise verhindert
				try{
					myDB.speicherProjekt(projekt);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} else {
			tmpProjekt = myDB.getProjekt(projekt);
		}
		
		stage = this;
		Parent root = FXMLLoader.load(getClass().getResource("HauptFenster.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(projekt.getName());
		stage.getIcons().add(new Image(OpenMainPage.class.getResourceAsStream("VanillaSky.png")));
		stage.show();
			
	}
	
}
