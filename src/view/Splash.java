package view;

import configuration.Configuration;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Startet den Preloader beim Start der fertigen (*.exe) Anwendung.
 * 
 * @author Daniel Sogl
 *
 */
public class Splash extends Preloader {

	Stage stage;

	public void start(Stage stage) throws Exception {
		try {
			this.stage = stage;
			Parent root = FXMLLoader.load(getClass().getResource("SplashView.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.show();
		} catch (Exception e) {
			if (Configuration.DEBUG)
				System.out.println(e.getMessage());
		}
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification evt) {
		// Ist die Start View geladen, wird das Fenster nach 2 Sekunden geschlossen
		if (evt.getType() == StateChangeNotification.Type.BEFORE_INIT) {
			long startTime = System.currentTimeMillis();
			while (false || (System.currentTimeMillis() - startTime) < 2000);
			stage.hide();
		}
	}
}
