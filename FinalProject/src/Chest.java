import javafx.scene.shape.Rectangle;

public class Chest extends Item {

    private Boolean isLocked = true;
    private String combo = "1234";    
    private String itemName = "Key";  // The item player obtains when unlocked

    // Constructor
    public Chest(String id, String name, boolean isLocked, Rectangle rect, int floor) {
        super(id, name, false, false, rect, floor);
        this.isLocked = isLocked;
    }
    
    //Chest hitbox
    public static Chest make(double x, double y, double w, double h, int floor, double opacity, String id, String name) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(opacity);
        // isLocked = true
        return new Chest(id, name, true, r, floor);
    }

    @Override
    public boolean useOn(Object target) {
        System.out.println(this.getName() + " is being interacted with. Is locked: " + isLocked);
        return true; 
    }


    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }


    public double spawnX(int playerWidth) {
        return rect.getX() + rect.getWidth()/2.0 - playerWidth/2.0;
    }

    public void setCombo(String combo) { this.combo = combo; }
    public String getCombo() { return combo; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    // Call when unlocked
    public void onUnlocked() {
        setIsLocked(false);
        System.out.println("Chest opened! You obtained: " + itemName);
        
        // TODO: Display on GUI
    }

}
