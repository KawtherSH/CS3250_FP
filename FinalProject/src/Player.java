import java.util.ArrayList;

public class Player {

	// Variables 
    private String name;
    private Room currentRoom;
    private Inventory inventory;
    
    // Constructors
    public Player() {
        this("Player");
    }

    public Player(String name) {
        this(name, null);
    }

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
        this.setInventory(new Inventory());
    }
    
    
    // Getters and Setters
    public String getName() {
    	return name; 
    }
    public void setName(String name) { 
    	this.name = name; 
	}

    public Room getCurrentRoom() { 
    	return currentRoom; 
	}
    public void setCurrentRoom(Room currentRoom) { 
    	this.currentRoom = currentRoom; 
	}

    
    // Methods "PlacHolders"
    
    // Player Movement
    public void moveTo(Room room) {
        System.out.println("The player is moving..");
    }

    // Picking up Items from the room
    public boolean pickUp(Item item) {
        System.out.println("The player is Picking up an item.. *Item added to inventory*");
        return false;
    }


    // Using item from inventory
    public boolean useItemOn(Item item, Puzzle target) {
        System.out.println("The player is using an item to slove a puzzle..");
        return false;
    }

    
    // Dropping item from inventory
    public boolean drop(Item item) {
        System.out.println("The player is dropping an item..");
        return false;
    }
    
    // Inventory check "Viewing inventory's items"
    public ArrayList<Item> listItems() {
        System.out.println("The player has XXX items in their inventory..");
        return null;
    }

    // Examining something in room
    public void examine(String subject) {
        System.out.println("This is an item..");
    }

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
