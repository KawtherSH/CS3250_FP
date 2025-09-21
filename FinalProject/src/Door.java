
public class Door extends Item {
	
	// Variables 
    private String doorId;         
    private boolean locked;
    private boolean open;
    private String requiredKeyId;
    
    // Constructors
    public Door() {
        super("door", "Door", "Exit room door.", false, false);
        this.doorId = null;
        this.locked = true;
        this.open = false;
        this.requiredKeyId = null;
    }
    
    
    // Getters and Setters

	public String getDoorId() {
		return doorId;
	}
	public void setDoorId(String doorId) {
		this.doorId = doorId;
	}
	
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public String getRequiredKeyId() {
		return requiredKeyId;
	}
	public void setRequiredKeyId(String requiredKeyId) {
		this.requiredKeyId = requiredKeyId;
	}
	
	
	// Methods "PlaceHolders"

    // Open door
    public boolean open() {
    	System.out.println("Opening door..");
        return false;
    }

    // Close door.
    public void close() {
    	System.out.println("Door is closed, requiers key");
    }

    // Locked door
    public void lock() {
    	System.out.println("Door locked");
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
