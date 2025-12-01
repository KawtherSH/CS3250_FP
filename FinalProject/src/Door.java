import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;

public class Door extends StackPane {

    //Variables 
    private final Rectangle rect;   // Hitbox
    private final ImageView imageView;
    private final int floor;        // 0 = top, 1 = middle, 2 = bottom
    private String id;
    private String name;

    private Door upTarget;          
    private Door downTarget;     
    private String requiredKeyId;
    private Boolean isLocked = false; 

    // Constructor 
    public Door(Rectangle rect, int floor, String id, String name, String imagePath) {
    	super();
        this.rect = rect;
        this.floor = floor;
        this.id = id;
        this.name = name;
        this.rect.setMouseTransparent(true);
        this.imageView = new ImageView(new Image(imagePath)); 
        this.imageView.setFitWidth(rect.getWidth());
        this.imageView.setFitHeight(rect.getHeight());
        
        getChildren().addAll(this.imageView, this.rect); 
        
        setPrefSize(rect.getWidth(), rect.getHeight());
        setAlignment(Pos.CENTER);
    }
    
    // Door
    public static Door make(double x, double y, double w, double h, int floor, String id, String name, String imagePath) {
        return make(x, y, w, h, floor, 0.3, id, name, imagePath); 
    }
    
    // Create hitbox
    public static Door make(double x, double y, double w, double h, int floor, double opacity, String id, String name, String imagePath) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(opacity);
        Door door = new Door(r, floor, id, name, imagePath);
        
        door.setLayoutX(x);
        door.setLayoutY(y);
        
        return door;
    }

    public boolean useOn(Object target) {
        if (!isLocked) {
            return false;
        }

        if (target instanceof Item keyItem) {
            if (keyItem.getId().equals(requiredKeyId)) {
                unlock(keyItem);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    // Door logic
    public void unlock(Item key) {
        if (isLocked) {
            this.isLocked = false;
            this.imageView.setVisible(false);
        }
    }
    
    public void lock() {
        this.isLocked = true;
        System.out.println(this.getName() + " locked. Key required.");
    }

    // Setters
    public Door setUpTarget(Door target)   
    	{ this.upTarget = target; return this; }
    public Door setDownTarget(Door target) 
    	{ this.downTarget = target; return this; }
    public void setIsLocked(Boolean isLocked) 
    	{ this.isLocked = isLocked; }
    public void setRequiredKeyId(String keyId)
        { this.requiredKeyId = keyId; }
    
    // Getters
    public Rectangle getRect()  
    	{ return rect; }
    public int getFloor()       
    	{ return floor; }
    public String getName()     
        { return name; }
    public Door getUpTarget()   
    	{ return upTarget; }
    public Door getDownTarget() 
    	{ return downTarget; }
    public Boolean getIsLocked() 	
    	{ return isLocked; }
    public String getRequiredKeyId()
        { return requiredKeyId; }

    
    
    // Helpers 
    public boolean intersects(Bounds playerBounds) {
        return playerBounds.intersects(this.getBoundsInParent());
    }
    
    public double spawnX(int playerWidth) {
        return getLayoutX() + getRect().getWidth()/2.0 - playerWidth/2.0;
    }

}
