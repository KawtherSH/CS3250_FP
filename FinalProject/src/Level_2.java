import javafx.scene.layout.Pane;

	
public class Level_2 {


	    private final Door topDoor;
	    private final Door midDoor;
	    private final Door botDoor;
	    private final Door exitDoor;

	    private final Chest midChest;
	    private final Chest botChest;

	    private final Letter midLetter, botLetter;

	    private final Player player;
	    private final double DOOR_H;
	    
	    // Keys
	    private final String midDoorKeyID = "MID_KEY";
	    private final String exitDoorKeyID = "EXIT_KEY";
	    
	    public Level_2(Pane root, Player player, double sceneW, double sceneH) {

	        this.player = player;
	        this.DOOR_H = sceneH / 3.0;

	        // Door
	        topDoor = Door.make(23, yForFloorCenter(0) - 110, 200, 200, 0, 0.0, "D001", "Top floor Door", "Images/DoorL2.png");
	        topDoor.setVisible(false);
	        midDoor = Door.make(24, yForFloorCenter(1) - 85, 200, 200, 1, 0.0, "D002", "Middle floor Door", "Images/DoorL2.png");
	        midDoor.setIsLocked(true);
	        midDoor.setRequiredKeyId(midDoorKeyID);
	        botDoor = Door.make(23, yForFloorCenter(2) - 120, 200, 200, 2, 0.0, "D003", "Bottom floor Door", "Images/DoorL2.png");
	        botDoor.setVisible(false);


	        exitDoor = Door.make(779, yForFloorCenter(0) - 98, 200, 200, 0, 0.0, "D004", "Exit Door", "Images/DoorL2.png");
	        exitDoor.setIsLocked(true);
	        exitDoor.setRequiredKeyId(exitDoorKeyID);
	        
	        root.getChildren().addAll(
	                topDoor, 
	                midDoor,
	                botDoor,
	                exitDoor
	                
	        );
	        player.getView().toFront();

	        midDoor.setUpTarget(topDoor).setDownTarget(botDoor);
	        topDoor.setDownTarget(midDoor);
	        botDoor.setUpTarget(midDoor);

	        // Middle Floor chest
	        midChest = Chest.make(250, yForFloorCenter(1) + 40, 80, 60, 1, 0.0, "C01", "Middle floor chest");
	        midChest.setCombo("4526731");
	        midChest.setItemName("Middle Floor Key");
	        midChest.setId(midDoorKeyID);
	        midChest.addTo(root);
	        
	        // Bottom Floor Chest
	        botChest = Chest.make(420, yForFloorCenter(2) + 20, 80, 60, 2, 0.0, "C02", "Bottom floor chest");
	        botChest.setCombo("3876");
	        botChest.setItemName("Exit door key");
	        botChest.addTo(root);

	        // Middle Floor Letter
	        midLetter = Letter.make(390, yForFloorCenter(1) + 10, 60, 60, 1, 0.0, "L01", "Middle floor letter");
	        midLetter.setImagePath("Images/Letter2.png");
	        midLetter.setRiddleText("");
	        midLetter.addTo(root);
	        
	        // Bottom Floor Letter
	        botLetter = Letter.make(650, yForFloorCenter(2), 60, 60, 2, 0.0, "L02", "Bottom floor letter");
	        botLetter.setImagePath("Images/Letter3.png");
	        botLetter.setRiddleText("“Treasure sleeps behind this lock, its secret hung up high.\r\n"
	        		+ "Climb where rooms stack three in all and lift your searching eye.\r\n"
	        		+ "Among the dangling numbers, let red be your guide and fix");
	        botLetter.addTo(root);
	    }

	    private double yForFloorCenter(int floor) {
	        return (DOOR_H * floor) + (DOOR_H - player.getFrameH()) / 1.0;
	    }
	    
	    


	    // getters 
	    public Door getTopDoor()  { return topDoor; }
	    public Door getMidDoor()  { return midDoor; }
	    public Door getBotDoor()  { return botDoor; }
	    public Door getExitDoor() { return exitDoor; }

	    public Chest getMidChest() { return midChest; }
	    public Chest getBotChest() { return botChest; }    
	    public Letter getMidLetter() { return midLetter; }
	    public Letter getBotLetter() { return botLetter; }	
	
}
