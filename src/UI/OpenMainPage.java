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
	public static Projekt newProjekt;
	public static Projekt oldProjekt;
	private Datenbank myDB = new Datenbank();
	
	public OpenMainPage(String projektname, Projekt projekt, boolean vorlage, boolean newProjekt) throws Exception{
		
		if(newProjekt){
			OpenMainPage.newProjekt = projekt;
			if(vorlage){
				/* TODO Vorlage muss noch erstellt werden.
				 * Bsp: 3 Kompetenzen + 3 Phasen.
				 */
			}
			myDB.speicherProjekt(OpenMainPage.newProjekt);
		}
		else{
			OpenMainPage.oldProjekt = myDB.getProjekt(projekt);
		}
			
		stage = this;
		Parent root = FXMLLoader.load(getClass().getResource("HauptFenster.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle(projektname); // Hier soll der jeweilige Projektname einegtragen werden!
		stage.getIcons().add(new Image(Main.class.getResourceAsStream("VanillaSky.png")));
		stage.show();
	}
	
}
