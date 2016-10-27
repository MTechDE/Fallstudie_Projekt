package ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Startet den Preloader beim Start der fertigen (*.exe) Anwendung.
 * Anschließend wird dieser nach 2,5 Sekunden geschlossen, sobald die
 * Initialisierungs Methode des Startfenstern abgeschlossen wurde.
 * @author Daniel Sogl
 *
 */
public class Splash extends Preloader {

	Stage stage;

	public void start(Stage stage) throws Exception {
		this.stage = stage;
		Parent root = FXMLLoader.load(getClass().getResource("SplashView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}
	
	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
		if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
			long startTime = System.currentTimeMillis();
			while (false || (System.currentTimeMillis() - startTime) < 2500);
			stage.hide();
		}
	}
}
