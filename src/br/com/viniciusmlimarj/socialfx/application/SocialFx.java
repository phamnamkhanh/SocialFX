package br.com.viniciusmlimarj.socialfx.application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Application
 * @author Vinicius Mello Lima
 */
public class SocialFx extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));

		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        try {
					stop();
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}