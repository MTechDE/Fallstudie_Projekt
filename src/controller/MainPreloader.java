package controller;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainPreloader extends Preloader {
	
	 Stage primaryStage;
	
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass().getResource("../view/SplashView.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Vanilla Sky");
		primaryStage.setScene(scene);
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
