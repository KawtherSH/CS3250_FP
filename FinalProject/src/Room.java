import java.util.ArrayList;

public class Room {

	// Variables
	private String id;                 
    private String name;
    
    private ArrayList<Item> items;          
    private ArrayList<Puzzle> puzzles;
    private Door door;
    
    
    // Constructors
    public Room() {
        // empty
    }

    
    // Getters and Setters
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
	
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Puzzle> getPuzzles() {
		return puzzles;
	}
	public void setPuzzles(ArrayList<Puzzle> puzzles) {
		this.puzzles = puzzles;
	}

	public Door getDoor() {
		return door;
	}
	public void setDoor(Door door) {
		this.door = door;
	}

	

	// Methods "PlaceHolders"
    // Add an item into the room
    public boolean addItem(Item item) {
    	System.out.println("An item has been added to the room");
        return false;
    }

    // Remove an item into the room
    public boolean removeItem(Item item) {
    	System.out.println("An item has been removed from the room");
        return false;
    }

    // Add a puzzle to the room
    public boolean addPuzzle(Puzzle puzzle) {
    	System.out.println("A puzzle has been Added to the room");
        return false;
    }

    // Remove a puzzle from the room
    public boolean removePuzzle(Puzzle puzzle) {
    	System.out.println("A puzzle has been removed from the room");
        return false;
    }
    

    // When all puzzles has been solved "Room is cleared"
    public boolean isCleared() {
    	System.out.println("The room has been solved!");
        return false;
    }

    // Door of the room
    public boolean openDoor(Door door) {
    	System.out.println("isCleaed == true; Door is open");
        return false;
    }
}
