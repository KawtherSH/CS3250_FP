import java.util.ArrayList;

public class Level {

	// Variables 
	private String id;
    private String name;
    private String description;

    
    private ArrayList<Room> rooms; 
    private Room startRoom;         
    private boolean completed;
    
    // Constructors
    public Level() {
    }

    public Level(String id, String name) {
        this(id, name, "");
    }

    public Level(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.setDescription(description);
        this.rooms = new ArrayList<>();
        this.startRoom = null;
        this.completed = false;
    }
    
    
    // Getter and Setters 
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
	
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	public Room getStartRoom() {
		return startRoom;
	}
	public void setStartRoom(Room startRoom) {
		this.startRoom = startRoom;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	// Methods "PlaceHolders"

    // Add room to level
    public void addRoom(Room room) {
    	System.out.println("A room has been added to a level");
    }

    // Remove room from level
    public boolean removeRoom(Room room) {
    	System.out.println("A room has been Removed from a level");
        return false;
    }

    // Sitting room
    public boolean setStartRoom(String room) {
    	System.out.println("Setting environment... Starting room..");
        return false;
    }

    // Sitting goal
    public boolean setGoalRoom(String room) {
    	System.out.println("Setting goal..");
        return false;
    }

    // Starting level - Player in first room
    public void start(Player player) {
    	System.out.println("Player begin.. Good luck!");
    }

 // Room Completed 
    public boolean moveRooms(Player player) {
    	System.out.println("Moving to next room..");
    	// Player enters the next room after solving the puzzles
        return false;
    }
    
    // Room Completed 
    public boolean checkCompletion(Player player) {
    	System.out.println("Room has been solved..");
        return false;
    }
	
	
}
