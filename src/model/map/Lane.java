package model.map;


import java.util.ArrayList;
import java.util.List;

public class Lane {
    /// Fields:
    private List<Field> fields;
    private Road road;

    /// Constructors:
    public Lane() {}
    public Lane(Road road, int numOfFieldsOnOneLane) {
        this.road = road;
        fields = new ArrayList<>();

        // Create fields on the lane
        for (int i = 0; i < numOfFieldsOnOneLane; i++) {
            Field newField = new Field();
            fields.add(newField);
        }

        // Set nextField references
        for (int i = 0; i < numOfFieldsOnOneLane-1; i++) {
            fields.get(i).setNextField(fields.get(i+1));
        }
    }

    /// Getters:
    public Field getFirstField() {
        if(fields==null){
            return null;
        }
        return fields.get(0);
    }

    public Field getLastField() {
        if(fields==null){
            return null;
        }
        return fields.get(fields.size()-1);
    }

    public Field getField(int index) {
        if (fields == null || index < 0 || index >= fields.size()) {
            return null;
        }
        return fields.get(index);
    }

    /// Setters:
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
    public void setRoad(Road road) {
        this.road = road;
    }

    /// Functional functions:
    public void snowfall() {
        for (Field field: fields) {
            field.addSnow(5);
        }
    }

    public void tickTimers() {
        if (fields != null) {
            for (Field field : fields) {
                field.tickTimers();
            }
        }
    }


}
