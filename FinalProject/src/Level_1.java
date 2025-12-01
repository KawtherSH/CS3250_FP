import javafx.scene.layout.Pane;

public class Level_1 {

    private final Door topDoor;
    private final Door midDoor;
    private final Door botDoor;
    private final Door exitDoor;

    private final Chest midChest;
    private final Chest topChest;

    private final Letter midLetter, botLetter;

    private final Player player;
    private final double DOOR_H;
    
    // Keys
    private final String midDoorKeyID = "MID_KEY";
    private final String exitDoorKeyID = "EXIT_KEY";
    
    public Level_1(Pane root, Player player, double sceneW, double sceneH) {

        this.player = player;
        this.DOOR_H = sceneH / 3.0;

        // Door
        topDoor = Door.make(23, yForFloorCenter(0) - 110, 200, 220, 0, 0.0, "D001", "Top floor Door", "Images/DoorL1.png");
        topDoor.setVisible(false);
        midDoor = Door.make(770, yForFloorCenter(1) - 110, 200, 220, 1, 0.0, "D002", "Middle floor Door", "Images/DoorL1.png");
        midDoor.setIsLocked(true);
        midDoor.setRequiredKeyId(midDoorKeyID);
        botDoor = Door.make(23, yForFloorCenter(2) - 120, 200, 220, 2, 0.0, "D003", "Bottom floor Door", "Images/DoorL1.png");
        botDoor.setVisible(false);


        exitDoor = Door.make(862, yForFloorCenter(2) - 99, 200, 200, 2, 0.0, "D004", "Exit Door", "Images/ExitDoorL1.png");
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

        // Middle chest
        midChest = Chest.make(390, yForFloorCenter(1) + 40, 80, 60, 1, 0.0, "C01", "First floor chest");
        midChest.setCombo("2345");
        midChest.setItemName("Middle Floor Key");
        midChest.setId(midDoorKeyID);
        midChest.addTo(root);
        
        // Top Chest
        topChest = Chest.make(770, yForFloorCenter(0) + 20, 80, 60, 0, 0.0, "C02", "Top floor chest");
        topChest.setCombo("2910");
        topChest.setItemName("Exit door key");
        topChest.addTo(root);

        // Letters
        midLetter = Letter.make(510, yForFloorCenter(1) + 10, 130, 60, 1, 0.0, "L01", "First floor letter");
        midLetter.setImagePath("Images/Letter.png");
        midLetter.setRiddleText("Five frames hang upon the wall,\r\n"
        		+ "Three show leaves, not one, but all.\r\n"
        		+ "One has two, still small and new,\r\n"
        		+ "One has three, in plain old view,\r\n"
        		+ "One has four, grown out once more.\r\n"
        		+ "Beside them stand a cactus tall,\r\n"
        		+ "And one red rose that rules them all.\r\n"
        		+ "\r\n"
        		+ "To break the lock and claim your prize,\r\n"
        		+ "take the leaf-counts in rising size,\r\n"
        		+ "then last, the number of frames you see —\r\n"
        		+ "that four-digit code will set you free.");
        midLetter.addTo(root);
        
        botLetter = Letter.make(260, yForFloorCenter(2), 130, 60, 2, 0.0, "L02", "First floor letter");
        botLetter.setImagePath("Images/Letter.png");
        botLetter.setRiddleText("Three red faces watch the hall,\r\n"
        		+ "Each one frozen, hands and all.\r\n"
        		+ "One reads half past two in pause,\r\n"
        		+ "One shows ten and twenty-five because,\r\n"
        		+ "One stands sharp at nine o'clock,\r\n"
        		+ "guarding tight the upper lock.\r\n"
        		+ "\r\n"
        		+ "Ignore the minutes, let them slide,\r\n"
        		+ "take just the hours they try to hide.\r\n"
        		+ "Arrange them from the first of day\r\n"
        		+ "to last that night would slip away.\r\n"
        		+ "Press their digits, close in line —\r\n"
        		+ "the code you seek is theirs, not mine.");
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
    public Chest getTopChest() { return topChest; }    
    public Letter getMidLetter() { return midLetter; }
    public Letter getBotLetter() { return botLetter; }
}
