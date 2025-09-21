
public class Key extends Item {

	
	// Variables 
    private String targetId;   
    private boolean singleUse;	// Used one time to open something
    
    // Constructors
    public Key() {
        super("key", "Key", "A simple key.", true, false);
        this.targetId = null;
        this.singleUse = false;
    }

    public Key(String id, String name, String description, String targetId, boolean singleUse) {
        super(id, name, description, true, false);
        this.targetId = targetId;
        this.singleUse = singleUse;
    }
    
    
    // Getters and Setters
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	public boolean isSingleUse() {
		return singleUse;
	}
	public void setSingleUse(boolean singleUse) {
		this.singleUse = singleUse;
	}
	
	// Methods "PlaceHolders"

    // Key is correct to unlock something
    public boolean usable(Puzzle puzzle) {
    	System.out.println("Correct Key; Opening door/chest");
        return false;
    }

    // Code adapted with assistance from ChatGPT (September 2025).
    // Prompt: "If a puzzle in an escape room need A key, what method would be used to do that?"
    // Student review: Only used the Override method after explaining the concept of the game.
    
    // Use the key on a puzzle; one time only 
    @Override
    public boolean useOn(Puzzle target) {
    	System.out.println("Key used; remove key from inventory");
        return false;
    }
}
