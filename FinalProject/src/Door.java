import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class Door {

    //Variables 
    private final Rectangle rect;   // Hitbox
    private final int floor;        // 0 = top, 1 = middle, 2 = bottom
    private String id;
    private String name;

    private Door upTarget;          
    private Door downTarget;     
    private String requiredKeyId = "master_key";
    private Boolean isLocked = true; 

    // Constructor 
    public Door(Rectangle rect, int floor, String id, String name) {
        this.rect = rect;
        this.floor = floor;
        this.id = id;
        this.name = name;
        this.rect.setMouseTransparent(true);
    }
    
    // Door
    public static Door make(double x, double y, double w, double h, int floor, String id, String name) {
        return make(x, y, w, h, floor, 0.3, id, name); 
    }
    
    // Create hitbox
    public static Door make(double x, double y, double w, double h, int floor, double opacity, String id, String name) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(opacity);
        return new Door(r, floor, id, name);
    }

    public boolean useOn(Object target) {
        if (!isLocked) {
            System.out.println(this.getName() + " is already unlocked. Go through!");
            return false;
        }

        if (target instanceof Item keyItem) {
            if (keyItem.getId().equals(requiredKeyId)) {
                unlock(keyItem);
                return true;
            } else {
                System.out.println("The " + keyItem.getName() + " doesn't fit the lock of " + this.getName() + ".");
                return false;
            }
        } else {
            System.out.println("Cannot use " + target.getClass().getSimpleName() + " on the door.");
            return false;
        }
    }
    
    // Door logic
    public void unlock(Item key) {
        if (isLocked) {
            this.isLocked = false;
            System.out.println(this.getName() + " is now unlocked using the " + key.getName() + "!");
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
    public String getId()       
        { return id; }
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
        return playerBounds.intersects(rect.getBoundsInParent());
    }
    public double spawnX(int playerWidth) {
        return rect.getX() + rect.getWidth()/2.0 - playerWidth/2.0;
    }
}
