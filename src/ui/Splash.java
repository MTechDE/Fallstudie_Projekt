package ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
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
	public void handleProgressNotification(ProgressNotification pn) {
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
		if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
			// Zeige den Preloader nach erfolgreichem Start der Anwendung für
			// weitere 2,5 Sekunden
			long startTime = System.currentTimeMillis();
			while (false || (System.currentTimeMillis() - startTime) < 2500)
				;
			stage.hide();
		}
	}
}
