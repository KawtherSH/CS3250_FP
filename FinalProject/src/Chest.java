import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Chest {

    private final Rectangle rect;   // hitbox
    private Boolean isLocked = true;
    private final int floor;
    private String combo = "1234";    
    private String itemName = "Key";  // player need to get to unlock

    public Chest(Rectangle rect, boolean isLocked, int floor) {
        this.rect = rect;
        this.isLocked = isLocked;
        this.floor = floor;
    }

    // Create hitbox
    public static Chest make(double x, double y, double w, double h, int floor, double opacity) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(opacity);
        r.setMouseTransparent(true);
        return new Chest(r, true, floor);
    }

    // Add hitbox to scene graph 
    public void addTo(Pane root) {
        root.getChildren().add(rect); // test
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public Rectangle getRect() {
        return rect;
    }

    public int getFloor() { return floor; }

    public boolean intersects(Bounds playerBounds) {
        return playerBounds.intersects(rect.getBoundsInParent());
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
    }

}
