
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
        // System.out.println("Welcome to my final project");

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Scene scene = new Scene (new MainMenu(), 800, 500);
		primaryStage.setTitle("Escape Room Game");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
