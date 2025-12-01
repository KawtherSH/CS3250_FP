
public class Key extends Item {

    public Key(String id, String name) {
        // portable=true, consumable=false
        super(id, name, true, false); 
    }

    @Override
    public boolean useOn(Object target) {
        if (target instanceof Door door) {
            return door.useOn(this);
        }
        return false;
    }
}