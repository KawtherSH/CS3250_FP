import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Letter {

    private final Rectangle rect;   // hitbox
    private final int floor;

    private String imagePath = "Images/Letter.png";
    private String riddleText = "Solve!";

    public Letter(Rectangle rect, int floor) {
        this.rect = rect;
        this.floor = floor;
        this.rect.setMouseTransparent(true);
    }

    public static Letter make(double x, double y, double w, double h, int floor, double Opacity) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(Opacity);          
        r.setMouseTransparent(true);
        return new Letter(r, floor);
    }

    // TODO: DELETE: for debugging
    public void addTo(Pane root) {
        root.getChildren().add(rect); // test
    }

    public boolean intersects(Bounds playerBounds) {
        return playerBounds.intersects(rect.getBoundsInParent());
    }

    public int getFloor() { return floor; }

    public Rectangle getRect() { return rect; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getRiddleText() { return riddleText; }
    public void setRiddleText(String riddleText) { this.riddleText = riddleText; }
}
