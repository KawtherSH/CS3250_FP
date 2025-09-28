import java.util.ArrayList;

public class Game {

	// Variables
    private String title;
    private Player player;
    private ArrayList<Level> levels;
    private Inventory inventory;
    
    // Constructors 
    public Game() {
    }
    
    public Game(String title, Player player, Inventory inventory) {
    	
    	this.title = title;
    	this.player = player;
    	this.inventory = inventory;
    }

    
    // Getter and Setters
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}


	public ArrayList<Level> getLevels() {
		return levels;
	}
	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}


	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	// Methods 
	public void Info() {
        System.out.println("This game is made by Kawther A.");
    }
	
	public void startLevel() {
        System.out.println("Starting level: Entering the room..");
    }
}
