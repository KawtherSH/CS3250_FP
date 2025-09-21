
public class Puzzle {

	// Variables
	private String id;                 
    private String name;               
    private String description;        
    private boolean solved;
    private String solution;
    
    // Constructors
    public Puzzle() {
    }

    public Puzzle(String id, String name) {
        this(id, name, "");
    }

    public Puzzle(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.solved = false;
    }
    
    
    
    // Getter and setters
    
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
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isSolved() {
		return solved;
	}
	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	
	//Methods "PlaceHolders"

    // Checking the solution
    public boolean attempt(String input) {
    	System.out.println("attempt == solution: Puzzle has been solved");
        return false;
    }

    // Using item to solve puzzle
    public boolean applyItem(Item item) {
    	System.out.println("Item is usable here: lock has been opened");
        return false;
    }

    // 
    public boolean Solved() {
    	System.out.println("Puzzle solved: door is open / Closet is open");
		return false;
    }
    
}
