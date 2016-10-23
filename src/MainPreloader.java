
import controller.SplashViewController;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPreloader extends Preloader {
	
	 Stage primaryStage;
	
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("view/SplashView.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());
		primaryStage.setTitle("Vanilla Sky");
		primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("img/VanillaSky.png")));
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	} 
	
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
        	try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		SplashViewController.loadProjekt();
        	primaryStage.hide();
        }
    } 
}
