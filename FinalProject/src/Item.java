import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public abstract class Item {
	
	// Variables 
	private String id;              
    private String name;            
    private boolean portable;       
    private boolean consumable;     
    
    protected final Rectangle rect; // Hitbox for interaction
    protected final int floor;      // The floor/room the item is on
    
    
    // Constructors
    public Item(String id, String name, boolean portable, boolean consumable, Rectangle rect, int floor) {
        this.id = id;
        this.name = name;
        this.portable = portable;
        this.consumable = consumable;
        this.rect = rect;
        this.floor = floor;
        if (rect != null) {
        	this.rect.setMouseTransparent(true);
        }
    }
    
    public Item(String id, String name, boolean portable, boolean consumable) {
    	this(id, name, portable, consumable, null, 0);
    }
    
    
    // Methods
    public abstract boolean useOn(Object target);

    
    // addTo Method
    public void addTo(Pane root) {
    	if (rect != null) {
            root.getChildren().add(rect);
        }
    }
    
    public boolean intersects(Bounds playerBounds) {
    	if (rect == null) return false;
        return playerBounds.intersects(rect.getBoundsInParent());
    }


	// Getters and Setters 
	public String getId() 
		{ return id; }
	public void setId(String id) 
		{ this.id = id; }
	
	public String getName() 
		{ return name; }
	public void setName(String name) 
		{ this.name = name; }
	

	public boolean isPortable() 
		{ return portable; }
	public void setPortable(boolean portable) 
		{ this.portable = portable; }
	
	public boolean isConsumable() 
		{ return consumable; }
	public void setConsumable(boolean consumable) 
		{ this.consumable = consumable; }

	public Rectangle getRect() 
		{ return rect; }
	
	public int getFloor() 
		{ return floor; }
}
