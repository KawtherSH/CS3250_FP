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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class MainMenu extends BorderPane {
	
	// variable to start the level and settings 
	private final Game game = new Game();
	private final Settings settings = new Settings();
	
	public MainMenu() {
		
		setPadding(new Insets(20));
		
        // Top: Displays the game title
        Label title = new Label("Escape Room!");
        title.setStyle("-fx-font-size: 37px; -fx-font-weight: bold;");
        VBox top = new VBox(title);
        top.setAlignment(Pos.TOP_CENTER);
        setTop(top);

        // Left: Menu buttons for the player to choose what to do
        Button startButton = new Button("Start level");
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
        
        // Right: Allows the player to choose the game difficulty 
        Label difficultyLabel = new Label("Difficulty");
        RadioButton easy   = new RadioButton("Easy");
        RadioButton normal = new RadioButton("Normal");
        RadioButton hard   = new RadioButton("Hard");
        
        // Grouping and default 
        ToggleGroup difficultyGroup = new ToggleGroup();
        easy.setToggleGroup(difficultyGroup);
        normal.setToggleGroup(difficultyGroup);
        hard.setToggleGroup(difficultyGroup);
        normal.setSelected(true);
        
        VBox difficultyPane = new VBox(8, difficultyLabel, easy, normal, hard);
        difficultyPane.setPadding(new Insets(0, 40, 0, 0)); 
        difficultyPane.setMaxWidth(180);
        setRight(difficultyPane );

        
        
        // Bottom: Button on the right corner "Displays Info about the game"
        Button infoButton = new Button("info");
        HBox bottom = new HBox(infoButton);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setPadding(new Insets(0, 20, 10, 0));
        setBottom(bottom);
        
        
        
        // Events: connection to my project’s classes
        // Start the level so the player could play the game/continue their progress
        startButton.setOnAction(event -> game.startLevel());
        
        // Displays info about the game and creator 
        infoButton.setOnAction(event -> game.Info());
        
        // should redirect player to settings menu
        settingsButton.setOnAction(event -> settings.settingsMenu());
        
        // Exits the game
        quitButton.setOnAction(event -> Platform.exit());
        
    }

}
