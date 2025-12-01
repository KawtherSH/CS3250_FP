import javafx.scene.shape.Rectangle;

public class Letter extends Item {

    private String imagePath = "Images/Letter.png";
    private String riddleText = "Solve!";

    // Constructor
    public Letter(Rectangle rect, int floor, String id, String name) {
        super(id, name, false, false, rect, floor);
    }

    //Letter hitbox
    public static Letter make(double x, double y, double w, double h, int floor, double Opacity, String id, String name) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setOpacity(Opacity);          
        return new Letter(r, floor, id, name);
    }

    @Override
    public boolean useOn(Object target) {
        System.out.println(this.riddleText);
        return true; 
    }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getRiddleText() { return riddleText; }
    public void setRiddleText(String riddleText) { this.riddleText = riddleText; }
}
