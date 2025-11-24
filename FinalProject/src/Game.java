import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
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
    private boolean moveLeft = false, moveRight = false;

    private final AnimationTimer loop;

    // doors 
    private Door topDoor, midDoor, botDoor, exitDoor;
    private int currentFloor = 1; // middle 
    
    // chests
    private Chest chest;
    
    // letter
    private Letter letter;


    // height and width
    private static final int SCENE_W = 1000;
    private static final int SCENE_H = 700;
    private static final double DOOR_H = SCENE_H / 3.0;

    // background for levels
    private final ImageView bgView = new ImageView();
    
    // Level completed
    private boolean gameComplete = false;

    
    public Game() {
        setPrefSize(SCENE_W, SCENE_H);

        // get player 
        // adding player to pane
        getChildren().add(bgView);
        getChildren().add(player.getView());
        
        // current floor
        player.setLayoutY(yForFloorCenter(currentFloor));
        
        setFocusTraversable(true);
        
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
            // E to interact with items
            if (c == KeyCode.E) {

                if (letter != null
                    && letter.getFloor() == currentFloor
                    && letter.intersects(player.getBoundsInParent())) {

                    Stage owner = getScene() != null && getScene().getWindow() instanceof Stage
                        ? (Stage) getScene().getWindow() : null;

                    Overlay.showRiddle(owner, "Images/Letter.png", letter.getRiddleText()); 
                    return;
                }

                if (chest != null
                    && chest.getFloor() == currentFloor
                    && chest.intersects(player.getBoundsInParent())) {

                    if (chest.getIsLocked()) {
                        Stage owner = getScene() != null && getScene().getWindow() instanceof Stage
                            ? (Stage) getScene().getWindow() : null;

                        Overlay.showLock(owner, code -> {
                            if (code.equals(chest.getCombo())) {
                                chest.onUnlocked();
                                LoadingMessage("You got: " + chest.getItemName());
                            } else {                                
                                LoadingMessage("Wrong Code, Noop..");
                            }
                        });
                    } else {
                    	LoadingMessage("Chest is already unlocked.");
                    }
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
        
        
        
        
        // TODO: Make a level # class for each level instead of everything being in Game.java...?
        
        
        
        // TODO: set the correct cords when you finish background
        // Hitbox (x, y, w, h, floor, opacity)
        topDoor = Door.make(100, yForFloorCenter(0) + 20, 60, 100, 0, 0.3, "D001", "Top floor Door"); // y = yForFloorCenter(#) = the cords for the floors
        midDoor = Door.make(830, yForFloorCenter(1) + 20, 60, 100, 1, 0.3, "D002", "Middle floor Door");
        botDoor = Door.make(100, yForFloorCenter(2) + 20, 60, 100, 2, 0.3, "D003", "Bottom floor Door");
        
        // TODO: add an exit door
        exitDoor = Door.make(925, yForFloorCenter(2) +10, 60, 100, 2, 0.3, "D004", "Exit Door");
        exitDoor.setIsLocked(false);

        
        getChildren().addAll(topDoor.getRect(), midDoor.getRect(), botDoor.getRect(), exitDoor.getRect());

        // pair doors with the correct (other) door and movement
        midDoor.setUpTarget(topDoor).setDownTarget(botDoor);
        topDoor.setDownTarget(midDoor);
        botDoor.setUpTarget(midDoor);
        
        // chests 
        chest = Chest.make(400, yForFloorCenter(1) + 40, 80, 60, 1, 0.25, "C01", "First floor chest");
        chest.setCombo("1234");        
        chest.setItemName("Middle Floor Key"); // player gets 
        chest.addTo(this);
        
        // letter
        letter = Letter.make(550, yForFloorCenter(1) + 10, 80, 60, 1, 0.25, "L01", "First floor letter");
        	letter.setImagePath("Images/Letter.png");
        	letter.setRiddleText(
        	    "ADD: Riddle"       	    
        	);
    	letter.addTo(this);

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
    	return d != null && d.getUpTarget() != null && d.intersects(player.getBoundsInParent()); 
	}
    private boolean canGoDown()  
    { 
    	var d = doorForCurrentFloor(); 
    	return d != null && d.getDownTarget() != null && d.intersects(player.getBoundsInParent()); 
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
     
    
    // TODO: Game is completed
    private boolean CompleteGame() {
        if (gameComplete || exitDoor == null) return false;

        if (exitDoor.getFloor() == currentFloor &&
            exitDoor.intersects(player.getBoundsInParent())) {

            if (!exitDoor.getIsLocked()) 
            { 
                gameComplete = true;
                LoadingMessage("PHEW! You Escaped!");
                
                // TODO: return to Select level Scene
                
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
