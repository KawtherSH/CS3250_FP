/*
 * This is the structure for the MainMenu scene. It contains nodes
 * 		and a connection to the project classes. 
 * It is shown to the player before starting any level. while the player could 
 * 		change settings and choose difficulty. 
 * 
 * 	Author: Kawther A.
 * 	Date: September 27th, 2025
 */

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenu extends BorderPane {
	
	// variable to start the level and settings 
	private final Settings settings = new Settings();
	public MainMenu(Stage stage) {
		
		setPadding(new Insets(20));
		
		// Background
		getStyleClass().add("main-menu");

		
        // Top: Displays the game title
        Label title = new Label("Escape Room!");
        title.setStyle("-fx-font-size: 37px; -fx-font-weight: bold;");
        VBox top = new VBox(title);
        top.setAlignment(Pos.TOP_CENTER);
        setTop(top);

        // Left: Menu buttons for the player to choose what to do
        Button startButton = new Button("Start");
        Button settingsButton = new Button("Settings");
        Button quitButton = new Button("Quit game");

        // Code adapted with assistance from ChatGPT (Sep 2025).
        // Prompt: "How do I make buttons on the mid-left of a BorderPane scene?"
        // Student review: Learned about Region to create a spacer.
        VBox menu = new VBox(12, startButton, settingsButton, quitButton);
        menu.setPadding(new Insets(0, 0, 0, 40));
        menu.setMaxWidth(220);

        Region topSpacer = new Region();
        Region bottomSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

        VBox leftColumn = new VBox(topSpacer, menu, bottomSpacer);
        setLeft(leftColumn);   
                
        
        // Bottom: Button on the right corner "Displays Info about the game"
        Button infoButton = new Button("info");
        HBox bottom = new HBox(infoButton);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setPadding(new Insets(0, 20, 10, 0));
        setBottom(bottom);
        
        
        // Displays info about the game and creator 
        infoButton.setOnAction(event -> System.out.println("This game is made by: Kawther A"));
        
        // should redirect player to settings menu
        settingsButton.setOnAction(event -> settings.settingsMenu());
        
        // Exits the game
        quitButton.setOnAction(event -> Platform.exit());
        
	    // Code adapted with assistance from ChatGPT (Oct 2025).
	    // Prompt: "I have a class that extends scene, how do I move to the next scene when I click a button?"
	    // Student review: Used a photo for the buttons
        // Move to the next Scene 
        startButton.setOnAction(e -> {

            // Create your Scene subclass
            SelectLevelScene next = new SelectLevelScene();

            // keep window size and styles
            next.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(next);
        });
        
    }

}
