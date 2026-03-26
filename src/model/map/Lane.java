package model.map;

import java.util.List;

public class Lane {
    private List<Field> fields;

    public Field getFirstField() {
        if(fields==null){
            return null;
        }
        return fields.getFirst();
    }

    public Field getLastField() {
        if(fields==null){
            return null;
        }
        return fields.getLast();
    }

    public void snowfall() {
        //TODO
    }


}
