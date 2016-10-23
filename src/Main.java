import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * @author Daniel Sogl
 *
 */

public class Main extends Application {

	@Override
	public void init() {

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("view/StartFenster.fxml"));
			Scene scene = new Scene(root);
			try{
				scene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());
			} catch (Exception e ){
				System.out.println(e.getMessage());
			}
			primaryStage.setTitle("Vanilla Sky");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("img/VanillaSky.png")));
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
