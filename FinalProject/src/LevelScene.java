import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// If your Game class is in another package, fix the import accordingly.
public class LevelScene extends Scene {

    private final Game game;

            public LevelScene(Stage stage) {
            super(new BorderPane(), 1000, 700);           
            BorderPane root = (BorderPane) getRoot();
            this.game = new Game();
            root.setCenter(game);
        }

        public Game getGame() { return game; }
}

