import java.util.ArrayList;

public class Inventory {

	// Variables 
	private ArrayList<Item> items;
    private int inventorySize;
    
    // Constructors
    public Inventory() {
        this(-1);
    }

    public Inventory(int inventorySize) {
        this.items = new ArrayList<>();
        this.inventorySize = inventorySize;
    }
    
    // Getter and Setters
	public ArrayList<Item> getItems() {
		return items;
	}
	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public int getInventorySize() {
		return inventorySize;
	}
	public void setInventorySize(int inventorySize) {
		this.inventorySize = inventorySize;
	}
	
	
	// Methods "PlaceHolders"

    // Adding item to inventory
    public boolean add(Item item) {
    	System.out.println("An item has been add to inventory");
        return false;
    }

    // Removing item from inventory "Dropped?"
    public boolean remove(Item item) {
    	System.out.println("An item has been remove from inventory");
        return false;
    }

    // Removing a specific item by their ID "After being used?"
    public boolean removeById(String id) {
    	System.out.println("A specific item has been remove from inventory");
        return false;
    }

    // Inventory contains an item
    public boolean contains(Item item) {
    	System.out.println("A specific item is in the inventory");
        return false;
    }

    // List of all items 
    public ArrayList<Item> listAll() {
    	System.out.println("The inventory has XXX Items");
    	// Maybe display the number of Items too
        return null;
    }

    // Remove items from inventory after completing the level
    public void clear() {
    	System.out.println("The inventory has been cleared");
    }
	
	
}
