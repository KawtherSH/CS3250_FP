import java.io.InputStream;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SelectLevelScene extends Scene
{
	
	private Canvas foreground;
	private Image controlStripeSheet;
	
	private StackPane rootLayer;


	
	public SelectLevelScene(Stage stage) 
	{
		super(new BorderPane(), 1000, 750);
		
        BorderPane root = (BorderPane)getRoot();
        
        rootLayer = new StackPane(root);
        setRoot(rootLayer);
        
        root.setPadding(new Insets(0));
	 	root.getStyleClass().add("hallway");
	 	

        // Canvas - rectangle with text
        Canvas canvas = new Canvas(480, 360);
        GraphicsContext g = canvas.getGraphicsContext2D();
        
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        double pad = 50;     
        double x = pad, y = pad;
        double rw = w - 2 * pad, rh = h - 2 * pad;

        g.setFill(Color.rgb(0, 0, 0, 0.25)); // shadow
        g.fillRoundRect(x + 6, y + 6, rw, rh, 12, 12);

        g.setFill(Color.web("#E6E6E6"));
        g.fillRoundRect(x, y, rw, rh, 12, 12);

        g.setStroke(Color.web("#333333"));
        g.setLineWidth(3);
        g.strokeRoundRect(x, y, rw, rh, 12, 12);
        
        // Text 
        double cx = x + 20;                    
        double ty = y + 33;                           
		g.setFill(Color.BLACK);
		g.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 25.0));
        g.fillText("Controls:", cx, ty);

        
        // Level buttons 
        GridPane grid = new GridPane();
        // Note: Googled how to make gaps between buttons
        grid.setHgap(24);
        grid.setVgap(16);
        grid.setPadding(new Insets(45, 45, 45, 45));

        int levelNum = 1;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                Button levelButton = makeLevelButton("Level " + levelNum++);
                levelButton.setPrefWidth(220);
                levelButton.setMinHeight(48);
                grid.add(levelButton, col, row);
            }
        }
        VBox leftBox = new VBox(grid);
        leftBox.setAlignment(Pos.TOP_LEFT);

        foreground = new Canvas(480, 360);
        foreground.setMouseTransparent(true);
        
        // Back button
        Button backButton = Back();
        HBox bottomLeft = new HBox(backButton);
        bottomLeft.setAlignment(Pos.BOTTOM_LEFT);
        bottomLeft.setPadding(new Insets(12, 12, 12, 12));
        root.setBottom(bottomLeft);

        
        StackPane characterPane = new StackPane(canvas, foreground);

        // layout: buttons left, controls right
        HBox center = new HBox(30, grid, characterPane);
        center.setAlignment(Pos.BOTTOM_RIGHT);
        root.setCenter(center);
        
        
        // TODO: Make it bigger?
        controlStripeSheet = loadImage("Images/Control.PNG");
        startAnimation();
    }
	        	 
	
	
	 // Helper Methods ==================================================================================================
	private Button makeLevelButton(String label) {
	        Button b = new Button(label);
	        b.setOnAction(e -> {
	            LoadingMessage("Entering the room...");

	            PauseTransition delay = new PauseTransition(javafx.util.Duration.millis(900));
	            delay.setOnFinished(ev -> {

	                Stage stage = (Stage) getWindow();
	                LevelScene levelScene = new LevelScene(stage);
	                levelScene.getGame().setLevelBackground("Images/" + label + ".png");
	                stage.setScene(levelScene);
	                levelScene.getGame().requestFocus();
	            });
	            delay.play();
	        });

	        return b;  
	        }

	
	// Code adapted with assistance from GeminiAI (Google) (Oct 2025).
    // Prompt: "How do I get the scene for another Scene in JavaFX?"
    // Student review: I created a helper method to return to mainMenu 	
	// Back Button
	private Button Back() {
		Button back = new Button("Back");
	    back.setOnAction(e -> {
	        // get the stage from the event source
	        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

	        stage.getScene().setRoot(new MainMenu(stage));

	    });
	    return back;
    }
	
	// Character moving
	private void startAnimation()
	{

		GraphicsContext fgc = foreground.getGraphicsContext2D();
		
		new AnimationTimer() 
		{
			
			private int row = 0;
			private int col = 0;
			
			long lastupdate = System.nanoTime();
			private final long DELAY = 1000_000_000; // 30 Milliseconds

			@Override
			public void handle(long now) 
			{
				if (now - lastupdate >= DELAY)
				{
				fgc.clearRect(200, 200, 100, 100);
				fgc.drawImage(controlStripeSheet, col * 100, row * 100, 100, 100, 200, 200, 100, 100);
				
				col++;
				if (col == 2)
				{
					col = 0;
					row++;
				}
				if (row == 2)
				{
					row = 0;
					col = 0;
				}
				
				lastupdate = now;
				}
			}
			
		}.start();
	}
	
	// Code adapted with assistance from GeminiAI (Google) (Oct 2025).
    // Prompt: "How to make a pop-up message in JavaFX?"
    // Student review: I created a helper method to show a loading screen 
	private void LoadingMessage(String message) {
	    Label card = new Label(message);
	    card.setPadding(new Insets(16, 28, 16, 28));
	    card.setStyle(
	        "-fx-background-color: white;" +
	        "-fx-background-radius: 12;" +
	        "-fx-border-color: #333;" +
	        "-fx-border-width: 2;" +
	        "-fx-border-radius: 12;" +
	        "-fx-font-weight: bold;"
	    );
	    card.setMaxWidth(Region.USE_PREF_SIZE);

	    StackPane overlay = new StackPane(card);
	    overlay.setAlignment(Pos.CENTER);
	    overlay.setStyle("-fx-background-color: rgba(0,0,0,0.35);"); // dim backdrop
	    overlay.setPickOnBounds(true); 
	    overlay.setOpacity(0);

	    rootLayer.getChildren().add(overlay);

	    FadeTransition fadeIn = new FadeTransition(Duration.millis(150), overlay);
	    fadeIn.setToValue(1.0);

	    PauseTransition hold = new PauseTransition(Duration.millis(900));

	    FadeTransition fadeOut = new FadeTransition(Duration.millis(200), overlay);
	    fadeOut.setToValue(0);

	    SequentialTransition seq = new SequentialTransition(fadeIn, hold, fadeOut);
	    seq.setOnFinished(ev -> rootLayer.getChildren().remove(overlay));
	    seq.play();
	}
	
	
	
	// To load an Image
	private Image loadImage(String filePath) 
	{
		Image image = null ;
		try {
			InputStream inputstream = getClass().getResourceAsStream(filePath);
			image = new Image(inputstream);
			} catch (Exception e)
			{
				System.err.println("Error loading file" + filePath);
			}
		return image;
	}
	 
}

