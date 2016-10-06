package UI;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class StartFensterController {
	
	@FXML
	private Button btn_openNewPage;

	// Event Listener on Button[#btn_openNewPage].onAction
	@FXML
	public void openNewPage(ActionEvent event) throws Exception {
		Stage stage = (Stage) btn_openNewPage.getScene().getWindow();
		stage.close();
		new OpenMainPage();
	}
}
