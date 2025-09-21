
public class Item {
	
	// Variables 
	private String id;              
    private String name;            
    private String description;
    private boolean portable;       // used multiple times
    private boolean consumable;     // used once 
    
    
    // Constructors
    public Item() {
    }


    public Item(String id, String name, String description) {
        this(id, name, description, true, false);
    }

    public Item(String id, String name, String description2, boolean portable, boolean consumable) {
        this.id = id;
        this.name = name;
        this.description = description2;
        this.portable = portable;
        this.consumable = consumable;
    }
    
    
    // getters and Setters 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isPortable() {
		return portable;
	}
	public void setPortable(boolean portable) {
		this.portable = portable;
	}
	
	public boolean isConsumable() {
		return consumable;
	}
	public void setConsumable(boolean consumable) {
		this.consumable = consumable;
	}
	
	
	
	
	// Methods "PlaceHolders"

    // Using an item ; 
    public boolean use(Puzzle target) {
    	System.out.println("Item is in use..");
        return false;
    }


    // Add two item to create something to solve the puzzle
    public Item combineWith(Item other) {
    	System.out.println("Combining X with Y gives Z");
        return null;
    }


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	// Use key on puzzle.
	public boolean useOn(Puzzle target) {
		System.out.println("Usable Key; Opening..");
		return false;
	}

}
