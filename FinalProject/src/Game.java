import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game extends Pane {

		
	// Variables 

    // Player
    private final Player player = new Player();
    // Key
    private Item playerKey = null;
    private boolean moveLeft = false, moveRight = false;

    private final AnimationTimer loop;

    // doors 
    private Door topDoor, midDoor, botDoor, exitDoor;
    private int currentFloor = 1; // middle 
    
    private Level_1 level1;
    private Level_2 level2;
    
    // chests
    private Chest midChest, topChest, botChest;
    
    // letter
    private Letter midLetter, botLetter;


    // height and width
    private static final int SCENE_W = 1000;
    private static final int SCENE_H = 700;
    private static final double DOOR_H = SCENE_H / 3.0;

    // background for levels
    private final ImageView bgView = new ImageView();
    
    // Level completed
    private boolean gameComplete = false;

    
    public Game(String levelLabel) {
        setPrefSize(SCENE_W, SCENE_H);

        // get player 
        // adding player to pane
        getChildren().add(bgView);
        getChildren().add(player.getView());
        
        // current floor
        player.setLayoutY(yForFloorCenter(currentFloor));
        
        setFocusTraversable(true);
        
        
        // LEVELS
        if (levelLabel.equalsIgnoreCase("Level 1")) {
            level1 = new Level_1(this, player, SCENE_W, SCENE_H);

            topDoor  = level1.getTopDoor();
            midDoor  = level1.getMidDoor();
            botDoor  = level1.getBotDoor();
            exitDoor = level1.getExitDoor();

            midChest = level1.getMidChest();
            topChest = level1.getTopChest();

            midLetter = level1.getMidLetter();
            botLetter = level1.getBotLetter();
            
        } else if (levelLabel.equalsIgnoreCase("Level 2")) {
            level2 = new Level_2(this, player, SCENE_W, SCENE_H);

            topDoor  = level2.getTopDoor();
            midDoor  = level2.getMidDoor();
            botDoor  = level2.getBotDoor();
            exitDoor = level2.getExitDoor();

            midChest = level2.getMidChest();
            botChest = level2.getBotChest();

            midLetter = level2.getMidLetter();
            botLetter = level2.getBotLetter();
        }
        
        
        // controls 
        setOnKeyPressed(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.D) moveRight = true;
            if (c == KeyCode.A) moveLeft  = true;
            if (c == KeyCode.W) {
                showBackFrame(250);
                if (CompleteGame()) return;
                if (canGoUp()) stepFloor(-1);
            }
            if (c == KeyCode.S) {
                showBackFrame(250);
                if (canGoDown()) stepFloor(+1);
            }
            // E to interact with items/open doors
            if (c == KeyCode.E) {
            	////////////// decide which Letter the player is on
                Letter targetLetter = null;
                if (midLetter != null
                        && midLetter.getFloor() == currentFloor
                        && midLetter.intersects(player.getBoundsInParent())) {
                    targetLetter = midLetter;
                } else if (botLetter != null
                        && botLetter.getFloor() == currentFloor
                        && botLetter.intersects(player.getBoundsInParent())) {
                    targetLetter = botLetter;
                } 
                
                if ( targetLetter != null
                    && targetLetter.getFloor() == currentFloor
                    && targetLetter.intersects(player.getBoundsInParent())) {

                    Stage owner = getScene() != null && getScene().getWindow() instanceof Stage
                        ? (Stage) getScene().getWindow() : null;
                    
                    String imgPath = targetLetter.getImagePath();

                    Overlay.showRiddle(owner, imgPath, targetLetter.getRiddleText()); 
                    return;
                }

                ////////////// decide which chest the player is on
                Chest targetChest = null;
                if (midChest != null
                        && midChest.getFloor() == currentFloor
                        && midChest.intersects(player.getBoundsInParent())) {
                    targetChest = midChest;
                } else if (topChest != null
                        && topChest.getFloor() == currentFloor
                        && topChest.intersects(player.getBoundsInParent())) {
                    targetChest = topChest;
                } else if (botChest != null
                        && botChest.getFloor() == currentFloor
                        && botChest.intersects(player.getBoundsInParent())) {
                    targetChest = botChest;
                    }
                
                
                if (targetChest != null)
                {
                	if (targetChest.getIsLocked()) {
                        Stage owner = getScene() != null && getScene().getWindow() instanceof Stage
                                ? (Stage) getScene().getWindow() : null;

                        Chest chestRef = targetChest;

                        Overlay.showLock(owner, code -> {
                            if (code.equals(chestRef.getCombo())) {
                                chestRef.Unlocked();

                                String keyId;
                                if (chestRef == midChest) {
                                    keyId = "MID_KEY";         
                                } else { 
                                    keyId = "EXIT_KEY";       
                                }
                                
                                // use playerKey again.. we don't need that key anymore
                                playerKey = new Key(keyId, chestRef.getItemName());
                                LoadingMessage("You found the: " + playerKey.getName() + "!");

                            } else {
                                LoadingMessage("Wrong code, try again.");
                            }
                        });
                        
                	} 
                	else 
                	{
                        LoadingMessage("Chest is already unlocked.");
                    }

                    return;
                }
                    
                ///////////////// Handling Doors
                Door currentDoor = null;
                
                if (exitDoor != null
                        && exitDoor.intersects(player.getBoundsInParent())) {
                    currentDoor = exitDoor;
                } else {
                    currentDoor = doorForCurrentFloor();
                }

                if (currentDoor != null 
                    && currentDoor.getIsLocked()
                    && currentDoor.intersects(player.getBoundsInParent())) 
                {
                    if (playerKey != null) {
                        if (currentDoor.useOn(playerKey)) {
                            LoadingMessage("Used " + playerKey.getName() + " to unlock the " + currentDoor.getName() + "!");
                        } else {
                            LoadingMessage("The " + playerKey.getName() + " doesn't fit the lock.");
                        }
                    } else {
                        LoadingMessage("The door is locked. Find the key!");
                    }
                    return;
                }
            }
            
        });
        
        setOnKeyReleased(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.D)  
            	moveRight = false;
            if (c == KeyCode.A)   
            	moveLeft  = false;
        });
        
        


        // Game loop
        loop = new AnimationTimer() {
            long last = 0;
            @Override public void handle(long now) {
                if (last == 0) { last = now; return; }
                double dt = (now - last) / 1_000_000_000.0; // seconds
                last = now;
                move(now, dt);
            }
        };
        loop.start();
    }

    private void move(long nowNanos, double dt) {
        // Calculate horizontal input
        int dir = (moveRight ? 1 : 0) - (moveLeft ? 1 : 0);

        // Delegate motion + animation to Player
        player.update(nowNanos, dt, dir, SCENE_W);

        // lock Y 
        player.setLayoutY(yForFloorCenter(currentFloor));
    }

    private void showBackFrame(long millis) {
        player.showBackFrame(millis);
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
	
    // Code adapted with assistance from ChatGPT (Oct 2025).
	// Prompt: "I have this code for using a door, what are the calculations I could use to be able to use it when I press up/w"
	// Student review: applied the calculations on my methods.

    private double yForFloorCenter(int floor) {
        return (DOOR_H * floor) + (DOOR_H - player.getFrameH()) / 1;
    }

    // Assigning each door to the correct floor
    private Door doorForCurrentFloor() {
        return (currentFloor == 0) ? topDoor
             : (currentFloor == 1) ? midDoor
             : botDoor;
    }

    private boolean canGoUp()    
    { 
    	var d = doorForCurrentFloor(); 
    	return d != null && !d.getIsLocked() && d.getUpTarget() != null && d.intersects(player.getBoundsInParent()); 
	}
    private boolean canGoDown()  
    { 
    	var d = doorForCurrentFloor(); 
    	return d != null && !d.getIsLocked() && d.getDownTarget() != null && d.intersects(player.getBoundsInParent()); 
	}
    
    // Changing floors
    private void stepFloor(int delta) 
    {
        Door here = doorForCurrentFloor();
        if (here == null) return;
        Door dest = (delta < 0) ? here.getUpTarget() : here.getDownTarget();
        if (dest == null) return;

        double newX = clamp(dest.spawnX(player.getFrameW()), 0, SCENE_W - player.getFrameW());
        player.setX(newX);
        currentFloor = dest.getFloor();
        player.setLayoutY(yForFloorCenter(currentFloor));
        
        requestFocus();
    }
     
    
    private boolean CompleteGame() {
        if (gameComplete || exitDoor == null) return false;

        if (exitDoor.getFloor() == currentFloor &&
            exitDoor.intersects(player.getBoundsInParent())) {

            if (!exitDoor.getIsLocked()) 
            { 
                gameComplete = true;
                LoadingMessage("PHEW! You Escaped!");
                
                Stage stage = (Stage) this.getScene().getWindow();
                
                SelectLevelScene selectLevelScene = new SelectLevelScene(stage);
                selectLevelScene.getStylesheets().add(getClass().getResource("/ThemeStyle/theme.css").toExternalForm());                
                stage.setScene(selectLevelScene);
                                
                return true;
            } 
            else 
            {
            	LoadingMessage("Exit is locked. Find the key.");
                return true; 
            }
        }
        return false;
    }
    
    // setting background
    public void setLevelBackground(String imagePath) {
        bgView.setImage(new Image(imagePath));
    }
    
    
    // Display Message in GUI
    public void LoadingMessage(String message) {
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
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.35);"); 
        overlay.setPickOnBounds(true); 
        overlay.setOpacity(1.0); 
        overlay.setPrefSize(SCENE_W, SCENE_H);

        // Add to Pane
        getChildren().add(overlay);

        
        // Code adapted with assistance from GeminiAI (Nov 2025).
        // Prompt: "how to pause a label in the middle of the screen in JavaFX?"
        // Student review: learned about PauseTransition for labels and duration
        
        // Pause 2 seconds
        PauseTransition hold = new PauseTransition(Duration.millis(2000)); 
        hold.setOnFinished(ev -> getChildren().remove(overlay));
        
        hold.play();
    }
    
    
    
}
