import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;

public class Door extends Item {

    // Variables
    private final Rectangle rect;   //  hitbox 
    private final int floor;        // 0 = top, 1 = middle, 2 = bottom
    private Door upTarget;          
    private Door downTarget;     
    private Boolean isLocked;

    public Door(Rectangle rect, int floor) {
        this.rect = rect;
        this.floor = floor;
    }

    
    // Constructor 
    public static Door make(double x, double y, double w, double h, int floor) {
        return make(x, y, w, h, floor, 0.3); // (0.001)
    }
    
    // Create hitboxs
    public static Door make(double x, double y, double w, double h, int floor, double opacity) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(opacity);
        r.setMouseTransparent(true);
        return new Door(r, floor);
    }

    // Setters
    public Door setUpTarget(Door target)   
    	{ this.upTarget = target; return this; }
    public Door setDownTarget(Door target) 
    	{ this.downTarget = target; return this; }
    public void setIsLocked(Boolean isLocked) 
    	{ this.isLocked = isLocked; }
    
    // Getters
    public Rectangle getRect()  
    	{ return rect; }
    public int getFloor()       
    	{ return floor; }
    public Door getUpTarget()   
    	{ return upTarget; }
    public Door getDownTarget() 
    	{ return downTarget; }
    public Boolean getIsLocked() 	
    	{ return isLocked; }
    
    
    // Helpers 
    public boolean intersects(Bounds playerBounds) {
        return playerBounds.intersects(rect.getBoundsInParent());
    }
    public double spawnX(int playerWidth) {
        return rect.getX() + rect.getWidth()/2.0 - playerWidth/2.0;
    }

    
    
    
    public void lock() {
        System.out.println("Door locked, key requierd");
    }
    public boolean unlock(Key key) {
        System.out.println("Key == requiered key: Correct key to unlock the door..");
        return false;
    }
    public boolean applyItem(Item item) {
        System.out.println("Using key..");
        return false;
    }


	


	
}
