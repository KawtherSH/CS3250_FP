import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

	public class Player {
		
		// Code adapted with assistance from ChatGPT (Oct 2025).
		// Prompt: "I have a sprite that has the movement sheet, and I have one separate frame for 'Idle' what are the 
		// 				calculations to get the movements correctly?"
		// Student review: used the calculations to set the movements and flip the character.

	    // Assets (loaded here so Game stays cleaner)
	    private final Image walkSheet  = new Image("Images/avatarT.png");
	    private final Image idleImage  = new Image("Images/Idle.png");
	    private final Image backImage  = new Image("Images/backA.png");

	    // sprite frame sizes
	    private final int FRAME_W = 100;
	    private final int FRAME_H = 100;

	    // Walk animation config
	    private final int WALK_FRAMES = 4;
	    private final double WALK_FPS = 10.0;

	    // Rendering node
	    private final ImageView view = new ImageView();

	    // Motion + animation state
	    private double x = 100;           
	    private final double speed = 200; 
	    private int facing = 1;           

	    private enum Mode { IDLE, WALK, BACK }
	    private Mode mode = Mode.IDLE;

	    private double frameAcc = 0.0;
	    private int frameIndex = 0;

	    private boolean showingBack = false;
	    private long backUntilNanos = 0;

	    public Player() {
	        // Initial visual
	        view.setImage(idleImage);
	        view.setFitWidth(FRAME_W);
	        view.setFitHeight(FRAME_H);
	        view.setViewport(null);
	        view.setLayoutX(x);
	    }

	    // API used "Game"

	    public ImageView getView() { return view; }

	    public Bounds getBoundsInParent() { return view.getBoundsInParent(); }

	    public int getFrameW() { return FRAME_W; }
	    public int getFrameH() { return FRAME_H; }

	    public double getX() { return x; }

	    public void setX(double newX) {
	        this.x = newX;
	        view.setLayoutX(x);
	    }

	    public void setLayoutY(double y) { view.setLayoutY(y); }

	    public void showBackFrame(long millis) {
	        showingBack = true;
	        backUntilNanos = System.nanoTime() + millis * 1_000_000L;
	        setMode(Mode.BACK);
	    }

	    
	    public void update(long nowNanos, double dt, int dir, double sceneW) {
	        // back-frame timeout
	        if (showingBack && nowNanos >= backUntilNanos) {
	            showingBack = false;
	        }

	        // facing and velocity
	        double vx = dir * speed;
	        if (dir != 0) facing = dir;

	        //  x and clamp
	        x += vx * dt;
	        x = clamp(x, 0, sceneW - FRAME_W);
	        view.setLayoutX(x);

	        // choose mode
	        Mode newMode;
	        if (showingBack) newMode = Mode.BACK;
	        else if (dir != 0) newMode = Mode.WALK;
	        else newMode = Mode.IDLE;

	        if (newMode != mode) {
	            setMode(newMode);
	        }

	        // walk
	        if (mode == Mode.WALK) {
	            frameAcc += dt;
	            double frameDuration = 1.0 / WALK_FPS;
	            while (frameAcc >= frameDuration) {
	                frameAcc -= frameDuration;
	                frameIndex = (frameIndex + 1) % WALK_FRAMES;
	                double sx = frameIndex * FRAME_W;
	                view.setViewport(new Rectangle2D(sx, 0, FRAME_W, FRAME_H));
	            }
	        }

	        // flip 
	        view.setScaleX(facing == -1 ? -1 : 1);
	    }


	    private void setMode(Mode m) {
	        mode = m;
	        switch (mode) {
	            case IDLE -> {
	                view.setImage(idleImage);
	                view.setViewport(null);
	            }
	            case WALK -> {
	                view.setImage(walkSheet);
	                frameIndex = 0;
	                frameAcc = 0;
	                view.setViewport(new Rectangle2D(0, 0, FRAME_W, FRAME_H));
	            }
	            case BACK -> {
	                view.setImage(backImage);
	                view.setViewport(null);
	            }
	        }
	    }

	    private static double clamp(double v, double lo, double hi) {
	        return Math.max(lo, Math.min(hi, v));
	    }
	}


