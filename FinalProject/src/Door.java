import javafx.scene.shape.Rectangle;

public class Door extends Item {

    // Variables 
    private final Rectangle hitbox;    // sits over the drawn door in the background
    private final int targetFloor;     // 0 = top, 1 = middle, 2 = bottom
    private final double spawnX;       // where to place player on target floor (x only)
    private final double spawnYOffset; // y offset inside the band (fine-tune)

    // Constructors
    public Door(Rectangle hitbox, int targetFloor, double spawnX, double spawnYOffset) {
        this.hitbox = hitbox;
        this.targetFloor = targetFloor;
        this.spawnX = spawnX;
        this.spawnYOffset = spawnYOffset;
    }

    // --- Getters used by Game ---
    public Rectangle getHitbox() { return hitbox; }
    public int getTargetFloor() { return targetFloor; }
    public double getSpawnX() { return spawnX; }
    public double getSpawnYOffset() { return spawnYOffset; }

    // Methods "PlaceHolders"
    public void lock() {
        System.out.println("Door locked, key requierd");
    }

    /** Unlock the door with a Key. Returns true if it fits and unlocks. */
    public boolean unlock(Key key) {
        System.out.println("Key == requiered key: Correct key to unlock the door..");
        return false;
    }

    // Using key to unlock the door
    public boolean applyItem(Item item) {
        System.out.println("Using key..");
        return false;
    }
}
