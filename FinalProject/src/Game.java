import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Game extends Pane {

	// Code adapted with assistance from ChatGPT (Oct 2025).
	// Prompt: "I have a sprite that has the movement sheet, and I have one separate frame for 'Idle' what are the 
	// 				calculations to get the movements correctly?"
	// Student review: used the calculations to set the movements and flip the character.
	// Variables 
    private final Image walkSheet  = new Image("Images/avatarT.png");   
    private final Image idleImage    = new Image("Images/Idle.png");   
    private final Image backImage    = new Image("Images/backA.png");   

    private final int FRAME_W = 100;
    private final int FRAME_H = 100;
    private final int WALK_FRAMES = 4;          
    private final double WALK_FPS = 10.0;       

    // Player
    private final ImageView player = new ImageView();
    private double x = 100;                      
    private final double speed = 200;            
    private boolean moveLeft = false, moveRight = false;
    private boolean showingBack = false;
    private long backUntilNanos = 0;

    // walk animation
    private double frameAcc = 0.0;
    private int frameIndex = 0;

    // current facing: flip
    private int facing = 1;

    private enum Mode { IDLE, WALK, BACK }
    private Mode mode = Mode.IDLE;

    private final AnimationTimer loop;

    // door 
    private int currentFloor = 1; // start on the middle 

    // height and width
    private static final int SCENE_W = 1000;
    private static final int SCENE_H = 750;
    private static final double BAND_H = SCENE_H / 3.0;

    private Rectangle topDoorRect, midDoorRect, botDoorRect;
    
    // background for levels
    private final ImageView bgView = new ImageView();

    
    public Game() {
        setPrefSize(SCENE_W, SCENE_H);

        // get player 
        player.setImage(idleImage);
        player.setFitWidth(FRAME_W);
        player.setFitHeight(FRAME_H);
        player.setViewport(null); 
        getChildren().add(bgView);
        getChildren().add(player);
        

        // current floor
        player.setLayoutY(yForFloorCenter(currentFloor));
        
        setFocusTraversable(true);
        setOnKeyPressed(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.D)  
            	moveRight = true;
            if (c == KeyCode.A)   
            	moveLeft  = true;
            if (c == KeyCode.W) {
            	// show back frame
            	showBackFrame(400);
            	// one floor up 
            	if (canGoUp()) stepFloor(-1);
            }
            if (c == KeyCode.S) {
            	// show back frame
            	showBackFrame(400);
            	// one floor down
            	if (canGoDown()) stepFloor(+1);
            }
        });
        
        setOnKeyReleased(e -> {
            KeyCode c = e.getCode();
            if (c == KeyCode.D)  
            	moveRight = false;
            if (c == KeyCode.A)   
            	moveLeft  = false;
        });
        
        // hitboxes
        // TODO: set the correct cords when you finish background
        topDoorRect = makeDoorHitbox(600, bandTopConst(0) + 20, 60, 100); // top - 0
        midDoorRect = makeDoorHitbox(720, bandTopConst(1) + 20, 60, 100); // middle - 1
        botDoorRect = makeDoorHitbox( 40, bandTopConst(2) + 20, 60, 100); // bottom  - 2
        getChildren().addAll(topDoorRect, midDoorRect, botDoorRect);

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
        
        if (showingBack && nowNanos >= backUntilNanos) {
            showingBack = false;
        }

        int dir = (moveRight ? 1 : 0) - (moveLeft ? 1 : 0);
        double vx = dir * speed;
        if (dir != 0) facing = dir;

        x += vx * dt;
        x = clamp(x, 0, SCENE_W - FRAME_W); // fixed width

        Mode newMode;
        if (showingBack) newMode = Mode.BACK;
        else if (dir != 0) newMode = Mode.WALK;
        else newMode = Mode.IDLE;

        if (newMode != mode) {
            mode = newMode;
            switch (mode) {
                case IDLE -> {
                    player.setImage(idleImage);
                    player.setViewport(null);
                }
                case WALK -> {
                    player.setImage(walkSheet);
                    // start on frame 0
                    frameIndex = 0;
                    frameAcc = 0;
                    player.setViewport(new Rectangle2D(0, 0, FRAME_W, FRAME_H));
                }
                case BACK -> {
                    player.setImage(backImage);
                    player.setViewport(null);
                }
            }
        }

        // used calculations from ChatGPT 
        if (mode == Mode.WALK) {
            frameAcc += dt;
            double frameDuration = 1.0 / WALK_FPS;
            while (frameAcc >= frameDuration) {
                frameAcc -= frameDuration;
                frameIndex = (frameIndex + 1) % WALK_FRAMES;
                double sx = frameIndex * FRAME_W;
                player.setViewport(new Rectangle2D(sx, 0, FRAME_W, FRAME_H));
            }
        }

        player.setScaleX(facing == -1 ? -1 : 1);

        player.setLayoutX(x);

        // lock Y 
        player.setLayoutY(yForFloorCenter(currentFloor));
    	}

    private void showBackFrame(long millis) {
        showingBack = true;
        backUntilNanos = System.nanoTime() + millis * 1_000_000L;
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
	
    // Code adapted with assistance from ChatGPT (Oct 2025).
	// Prompt: "I have this code for using a door, what are the calculations I could use to be able to use it when I press up/w"
	// Student review: applied the calculations on my methods.
    // (Note: Door logic removed for the simplified W/S floor switching. Comments kept as requested.)

    private double bandTopConst(int floor) { return BAND_H * floor; }
    private double yForFloorCenter(int floor) {
        return bandTopConst(floor) + (BAND_H - FRAME_H) / 2.0;
    }

    // hitboxes 
    private Rectangle makeDoorHitbox(double x, double y, double w, double h) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(0.3);          // (0.001) 
        r.setMouseTransparent(true);
        return r;
    }
    
    // center X 
    private double spawnXAt(Rectangle r) {
        return r.getX() + r.getWidth()/2.0 - FRAME_W/2.0;
    }
    
    // go up/down one floor
    private void stepFloor(int delta) {
        int target = Math.max(0, Math.min(2, currentFloor + delta)); // clamp 0..2
        if (target == currentFloor) return;

        // choose destination door rect based on direction + current floor
        Rectangle dest;
        if (delta < 0) { // going UP
            dest = (currentFloor == 2) ? midDoorRect     // bottom -> mid
                  : (currentFloor == 1) ? topDoorRect    // mid -> top
                  : null;                                 // top can't go up
        } else { // delta > 0, going DOWN
            dest = (currentFloor == 0) ? midDoorRect     // top -> mid
                  : (currentFloor == 1) ? botDoorRect    // mid -> bottom
                  : null;                                 // bottom can't go down
        }
        if (dest == null) return;

        // center X 
        x = clamp(spawnXAt(dest), 0, SCENE_W - FRAME_W);
        player.setLayoutX(x);

        currentFloor = target;
        player.setLayoutY(yForFloorCenter(currentFloor));

        requestFocus();
    }

   
    private boolean canGoUp() {
        Bounds pb = player.getBoundsInParent();
        if (currentFloor == 1) return pb.intersects(midDoorRect.getBoundsInParent());   // mid -> top
        if (currentFloor == 2) return pb.intersects(botDoorRect.getBoundsInParent());   // bot -> mid
        return false;
    }

    private boolean canGoDown() {
        Bounds pb = player.getBoundsInParent();
        if (currentFloor == 0) return pb.intersects(topDoorRect.getBoundsInParent());   // top -> mid
        if (currentFloor == 1) return pb.intersects(midDoorRect.getBoundsInParent());   // mid -> bot
        return false;
    }
    
    // setting background
    public void setLevelBackground(String imagePath) {
        bgView.setImage(new Image(imagePath));
    }
}
